package com.example.steam_clone.service;

import com.example.steam_clone.dto.SignUpValid_Dto;
import com.example.steam_clone.dto.Sign_Dto;
import com.example.steam_clone.model.Membership;
import com.example.steam_clone.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    //로그인
    public String login(Sign_Dto signDto){
        //id가 email 경우를 생각
        Membership membership = membershipRepository.findByEmailOrId(signDto.getId(),signDto.getId()).orElse(null);

        /**
         * matches( 인자1, 인자2)
         * 인자1 : 입력받은 비교 할 값
         * 인자2 : 복호화 할 값
         */

        if (membership!=null && bCryptPasswordEncoder.matches(signDto.getPassword(),membership.getPassword())) {
            return "Login successful";
        }
        else{
            return "Login failed";
        }
    }

    //이메일 유효성 검사
    public String email_valid(SignUpValid_Dto signUpValidDto){
        Membership membership = membershipRepository.findByEmailOrId(signUpValidDto.getEmail(),signUpValidDto.getEmail()).orElse(null);

        if (membership == null) {
            return "email ok";
        }
        else{
            return "email exists";
        }
    }

    //회원가입
    public String sign_in(SignUpValid_Dto signUpValidDto){

        //전체 유효성 검사
        Membership membership = membershipRepository.findByEmailOrId(signUpValidDto.getEmail(),signUpValidDto.getId()).orElse(null);

        if(membership==null){
            membership = new Membership();
            //이메일을 포함시켜야 합니다
            membership.setId(signUpValidDto.getId());
            membership.setUsername(signUpValidDto.getId());
            membership.setEmail(signUpValidDto.getEmail());
            membership.setPassword(bCryptPasswordEncoder.encode(signUpValidDto.getPassword()));

            membershipRepository.save(membership);
            return "success";
        }
        else{
            return "id exists";
        }

    }
}
