package com.example.steam_clone.controller;

import com.example.steam_clone.dto.Email_Dto;
import com.example.steam_clone.dto.SignUpValid_Dto;
import com.example.steam_clone.dto.Sign_Dto;
import com.example.steam_clone.model.Membership;
import com.example.steam_clone.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {


    private MembershipRepository membershipRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //Dependency injection
    @Autowired
    public LoginController(MembershipRepository membershipRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.membershipRepository = membershipRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(Sign_Dto signDto) {

        //id가 이메일 인지 확인
        boolean idisemail = signDto.getId().contains("@");
        Membership membership;
        if(idisemail){
            Email_Dto emailDto = new Email_Dto();
            emailDto.setEmail(signDto.getId());
            membership = membershipRepository.findByEmail(emailDto.getEmail()).orElse(null);

            /**
             * matches( 인자1, 인자2)
             * 인자1 : 입력받은 비교할 비밀번호
             * 인자2 : 암호화 된 비밀번호
             */

            if (membership != null && bCryptPasswordEncoder.matches(signDto.getPassword(),membership.getPassword())) {
                return ResponseEntity.ok().body("Login successful");
            }
            else{
                return ResponseEntity.badRequest().body("Login failed");
            }
        }
        else{
            membership = membershipRepository.findById(signDto.getId()).orElse(null);

            if(membership != null && bCryptPasswordEncoder.matches(signDto.getPassword(),membership.getPassword())){
                return ResponseEntity.ok().body("Login successful");
            }
            else{
                return ResponseEntity.badRequest().body("Login failed");
            }
        }

    }

    //이메일 중복 검사
    @PostMapping("/email-valid")
    public ResponseEntity<?> email_Valid(Email_Dto emailDto){
        //register과 통합
        Membership dbmembership = membershipRepository.findByEmail(emailDto.getEmail()).orElse(null);
        if(dbmembership != null){
            return ResponseEntity.badRequest().body("exist");
        }
        else{
            return ResponseEntity.ok().body("not exist");
        }
    }

    //회원 가입
    @PostMapping("/register")
    public ResponseEntity<?> sign_in(SignUpValid_Dto signUpValidDto){
        //서비스에서 따로 만들어서 관리
        Membership dbmembership = membershipRepository.findById(signUpValidDto.getId()).orElse(null);
        if(dbmembership ==null){

            Membership user_Membership = new Membership();
            user_Membership.setEmail(signUpValidDto.getEmail());
            user_Membership.setId(signUpValidDto.getId());
            user_Membership.setPassword(bCryptPasswordEncoder.encode(signUpValidDto.getPassword()));
            //save
            membershipRepository.save(user_Membership);

            return ResponseEntity.ok().body("success");
        }
        else{
            return ResponseEntity.badRequest().body("id exists");
        }

    }

}
