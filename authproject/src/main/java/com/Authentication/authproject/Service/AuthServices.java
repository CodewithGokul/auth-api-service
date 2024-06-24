package com.Authentication.authproject.Service;

import com.Authentication.authproject.DTO.LoginDTO;
import com.Authentication.authproject.DTO.SignupDto;
import com.Authentication.authproject.Models.Users;
import com.Authentication.authproject.Repository.UserRepository;
import com.Authentication.authproject.Response.TokenResponse;
import com.Authentication.authproject.Utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServices {

    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

   public TokenResponse ValidateLogin(LoginDTO loginDTO)
    {
        UserDetails auth = userRepository.findByEmail(loginDTO.getEmail()).orElseThrow(()->new UsernameNotFoundException("Username Not Found"));
        Users users = userRepository.findByUsername(auth.getUsername());
        users.setLastlogin(new Date());
        userRepository.save(users);
        String token = jwtUtils.JwtGenerate(auth);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setToken(token);
        return tokenResponse;
    }

    public String Signup(SignupDto signupDto)
    {
        Users users = new Users();
        users.setUsername(signupDto.getUsername());
        users.setEmail(signupDto.getGmail());
        users.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        users.setAccountCreation(new Date());
        users.setLastlogin(new Date());
        userRepository.save(users);
        return "Account Created";
    }
}
