package com.login.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import com.login.login.repository.LoginRepository;
import com.login.login.model.Login;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {

    @Autowired
    LoginRepository loginRepository;
    @GetMapping("/")
    public String homePage(){
        return "Sign In";
    }

    @GetMapping("login/all")
    List<Login> GetAllLogin(){
        return loginRepository.findAll();
    }

    @PostMapping("signup/post/{user}/{pass}")
    public Login signUp(@PathVariable String user, @PathVariable String pass){
        Login newLogin = new Login();
        newLogin.setUsername(user);
        newLogin.setPassword(pass);

        loginRepository.save(newLogin);
        return newLogin;
    }

    @PostMapping("login/post")
    Login PostTriviaByPath(@RequestBody Login newLogin){
        loginRepository.save(newLogin);
        return newLogin;
    }
}
