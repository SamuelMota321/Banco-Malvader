package com.BancoMalvader.Java_Api.controllers;

import com.BancoMalvader.Java_Api.entities.user.User;
import com.BancoMalvader.Java_Api.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/usuarios")
public class UsersController {

    @Autowired
    private UserServices services;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = services.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User obj = services.findById(id);
        return ResponseEntity.ok().body(obj);
    }
}

