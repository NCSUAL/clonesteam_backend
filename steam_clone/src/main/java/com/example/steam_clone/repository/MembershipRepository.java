package com.example.steam_clone.repository;

import com.example.steam_clone.model.Membership;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, String> {

    Optional<Membership> findByEmailOrId(String Email, String id);

}