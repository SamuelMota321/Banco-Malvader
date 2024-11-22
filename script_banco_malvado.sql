SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema banco_malvadeza
-- -----------------------------------------------------
CREATE DATABASE IF NOT EXISTS banco_malvadeza DEFAULT CHARACTER SET utf8 ;
USE banco_malvadeza ;

-- -----------------------------------------------------
-- Table usuario
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS usuario (
  id_usuario INT UNSIGNED NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  senha VARCHAR(50) NOT NULL,
  cpf VARCHAR(11) NOT NULL,
  data_nascimento DATE NOT NULL,
  tipo_usuario ENUM('FUNCIONARIO', 'CLIENTE') NOT NULL,
  PRIMARY KEY (id_usuario),
  UNIQUE INDEX cpf_UNIQUE (cpf ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table conta
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS conta (
  id_conta INT UNSIGNED NOT NULL AUTO_INCREMENT,
  numero_conta INT(20) UNSIGNED NOT NULL,
  agencia VARCHAR(10) NOT NULL,
  saldo DECIMAL(15,2) DEFAULT 0,
  tipo_conta ENUM('POUPANCA', 'CORRENTE') NOT NULL,
  PRIMARY KEY (id_conta),
  UNIQUE INDEX numero_conta_UNIQUE (numero_conta ASC) VISIBLE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table transacao
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS transacao (
  id_transacao INT UNSIGNED NOT NULL AUTO_INCREMENT,
  tipo_transacao ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') NOT NULL,
  valor DECIMAL(15,2) UNSIGNED NOT NULL,
  data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  conta_id_conta INT UNSIGNED NOT NULL,
  PRIMARY KEY (id_transacao),
  FOREIGN KEY (conta_id_conta)
    REFERENCES conta (id_conta)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table conta_corrente
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS conta_corrente (
  id_conta_corrente INT UNSIGNED NOT NULL AUTO_INCREMENT,
  limite DECIMAL(15,2) NOT NULL DEFAULT 0,
  data_vencimento DATE NOT NULL,
  conta_id_conta INT UNSIGNED NOT NULL,
  PRIMARY KEY (id_conta_corrente, conta_id_conta),
  FOREIGN KEY (conta_id_conta)
    REFERENCES conta (id_conta)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table historico_saldo REL TRIGGER 1
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS historico_saldo (
  id_historico INT UNSIGNED NOT NULL AUTO_INCREMENT,
  id_conta INT UNSIGNED NOT NULL,
  tipo_transacao ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA') NOT NULL,
  valor DECIMAL(15,2) NOT NULL,
  saldo_anterior DECIMAL(15,2) UNSIGNED NOT NULL,
  saldo_atual DECIMAL(15,2) UNSIGNED NOT NULL,
  data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id_historico),
  FOREIGN KEY (id_conta)
    REFERENCES conta (id_conta)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table log_violacao_cpf REL TRIGGER 3
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS log_violacao_cpf (
  id_log INT UNSIGNED NOT NULL AUTO_INCREMENT,
  cpf_tentado VARCHAR(11),
  data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  mensagem TEXT,
  PRIMARY KEY (id_log))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Trigger 1: Atualizar saldo após uma transação
-- -----------------------------------------------------
DELIMITER //
CREATE TRIGGER after_insert_transacao
AFTER INSERT ON transacao
FOR EACH ROW
BEGIN
  -- Atualizar saldo da conta com base no tipo de transação
  IF NEW.tipo_transacao = 'DEPOSITO' THEN
    UPDATE conta
    SET saldo = saldo + NEW.valor
    WHERE id_conta = NEW.conta_id_conta;
  ELSEIF NEW.tipo_transacao = 'SAQUE' THEN
    UPDATE conta
    SET saldo = saldo - NEW.valor
    WHERE id_conta = NEW.conta_id_conta;
  END IF;
END;
//
DELIMITER ;

-- -----------------------------------------------------
-- Trigger 2: Garantir que o limite da conta corrente não seja excedido
-- -----------------------------------------------------
DELIMITER //
CREATE TRIGGER before_insert_transacao
BEFORE INSERT ON transacao
FOR EACH ROW
BEGIN
  DECLARE conta_saldo DECIMAL(15,2);
  DECLARE conta_limite DECIMAL(15,2);

  SELECT saldo, limite INTO conta_saldo, conta_limite
  FROM conta
  LEFT JOIN conta_corrente ON conta.id_conta = conta_corrente.conta_id_conta
  WHERE conta.id_conta = NEW.conta_id_conta;

  IF NEW.tipo_transacao IN ('SAQUE', 'TRANSFERENCIA') AND NEW.valor > (conta_saldo + conta_limite) THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Transação excede o saldo e o limite disponível';
  END IF;
END;
//
DELIMITER ;

-- -----------------------------------------------------
-- Trigger 3: Garantir unicidade de CPF com logs de tentativas
-- -----------------------------------------------------
DELIMITER //
CREATE TRIGGER before_insert_usuario
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
  IF EXISTS (SELECT 1 FROM usuario WHERE cpf = NEW.cpf) THEN
    INSERT INTO log_violacao_cpf (cpf_tentado, mensagem)
    VALUES (NEW.cpf, 'Tentativa de inserir CPF duplicado');
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'CPF já cadastrado no sistema';
  END IF;
END;
//
DELIMITER ;

-- -----------------------------------------------------
-- Trigger 4: Manter histórico de alterações no saldo da conta
-- -----------------------------------------------------
DELIMITER //
CREATE TRIGGER after_update_conta_saldo
AFTER UPDATE ON conta
FOR EACH ROW
BEGIN
  DECLARE tipo_transacao ENUM('DEPOSITO', 'SAQUE', 'TRANSFERENCIA');

  -- Identificar o tipo de transação baseado na alteração do saldo
  IF NEW.saldo > OLD.saldo THEN
    SET tipo_transacao = 'DEPOSITO';
  ELSEIF NEW.saldo < OLD.saldo THEN
    SET tipo_transacao = 'SAQUE';
  ELSE
    SET tipo_transacao = 'TRANSFERENCIA'; -- Caso especial
  END IF;

  -- Inserir registro no histórico
  INSERT INTO historico_saldo (id_conta, tipo_transacao, valor, saldo_anterior, saldo_atual, data_hora)
  VALUES (NEW.id_conta, tipo_transacao, ABS(NEW.saldo - OLD.saldo), OLD.saldo, NEW.saldo, NOW());
END;
//
DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;