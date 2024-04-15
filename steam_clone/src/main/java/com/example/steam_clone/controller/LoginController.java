package com.example.steam_clone.controller;

import com.example.steam_clone.dto.SignUpValid_Dto;
import com.example.steam_clone.dto.Sign_Dto;
import com.example.steam_clone.model.Membership;
import com.example.steam_clone.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> login(Sign_Dto signDto) {
        //로그인 유효성 검사
        String result = loginService.login(signDto);

        if(result.equals("Login successful")){
            return ResponseEntity.ok().body(result);
        }
        else{
            return ResponseEntity.badRequest().body(result);
        }
    }


    //회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> sign_in(SignUpValid_Dto signUpValidDto){
        //이메일 유효성 검사
        if(signUpValidDto.getId()==null && signUpValidDto.getPassword()==null){
            String result = loginService.email_valid(signUpValidDto);

            if(result.equals("email ok")){
                return ResponseEntity.ok().body(result);
            }
            else{
                return ResponseEntity.badRequest().body(result);
            }
        }
        //로그인 진행
        else{
            String result = loginService.sign_in(signUpValidDto);

            if(result.equals("success")){
                return ResponseEntity.ok().body(result);
            }
            else{
                return ResponseEntity.badRequest().body(result);
            }
        }
    }

}
