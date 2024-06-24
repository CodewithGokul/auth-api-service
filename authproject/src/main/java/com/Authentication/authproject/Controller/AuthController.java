package com.Authentication.authproject.Controller;

import com.Authentication.authproject.DTO.LoginDTO;
import com.Authentication.authproject.DTO.SignupDto;
import com.Authentication.authproject.Response.TokenResponse;
import com.Authentication.authproject.Service.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    @Autowired
    AuthServices authServices;


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginDTO loginDTO){

        return  new ResponseEntity(authServices.ValidateLogin(loginDTO), HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto){

        return  new ResponseEntity(authServices.Signup(signupDto), HttpStatus.OK);
    }

    @GetMapping("/Home")
    public String Home(){
        return "Home Page";
    }
}
