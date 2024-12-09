package com.BancoMalvader.Java_Api.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class RestErrorMessage {
    private HttpStatus status;
    private Integer code;
    private String message;

    public RestErrorMessage(HttpStatus status, String message) {
        this.status = status;
        this.code = status.value();
        this.message = message;
    }
}
