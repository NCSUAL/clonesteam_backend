package com.example.steam_clone.controller;

import com.example.steam_clone.model.Membership;
import com.example.steam_clone.repository.MembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private MembershipRepository membershipRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Membership membership) {
        Membership dbMembership = membershipRepository.findById(membership.getId()).orElse(null);
        if (dbMembership != null && dbMembership.getPassword().equals(membership.getPassword())) {
            return ResponseEntity.ok().body("Login successful");
        } else {
            return ResponseEntity.badRequest().body("Login failed");
        }
    }
}
