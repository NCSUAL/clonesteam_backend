package com.example.steam_clone.repository;

import com.example.steam_clone.model.Membership;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {
    
    Optional<Membership> findById(String id);
    Optional<Membership> findByEmail(String email);
}