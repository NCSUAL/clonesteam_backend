package com.example.steam_clone.controller;

import com.example.steam_clone.dto.EmailValid_Dto;
import com.example.steam_clone.dto.SignUpValid_Dto;
import com.example.steam_clone.model.Membership;
import com.example.steam_clone.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private MembershipRepository membershipRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Membership membership) {
        Membership dbMembership = membershipRepository.findById(membership.getId()).orElse(null);
        if (dbMembership != null && dbMembership.getPassword().equals(membership.getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Login failed");
        }
    }

    @PostMapping("/email-valid")
    public ResponseEntity<?> email_Valid(EmailValid_Dto emailValidDto){
        Membership dbmembership = membershipRepository.findByEmail(emailValidDto.getEmail()).orElse(null);
        if(dbmembership == null){
            return ResponseEntity.ok().body("exist");
        }
        else{
            return ResponseEntity.badRequest().body("not exist");
        }
    }

    @PostMapping("/sign_in")
    public ResponseEntity<?> sign_in(SignUpValid_Dto signUpValidDto){
        Membership membership = membershipRepository.findById(signUpValidDto.getId()).orElse(null);
        if(membership ==null){

            Membership user_Membership = new Membership();
            user_Membership.setEmail(signUpValidDto.getEmail());
            user_Membership.setId(signUpValidDto.getId());
            user_Membership.setPassword(bCryptPasswordEncoder.encode(signUpValidDto.getPassword()));
            //save
            membershipRepository.save(user_Membership);

            return ResponseEntity.ok().body("Sign-in successful");
        }
        else{
            return ResponseEntity.badRequest().body("Sign-in failed");
        }

    }

}
