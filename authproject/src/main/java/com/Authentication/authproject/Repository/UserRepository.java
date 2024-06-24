package com.Authentication.authproject.Repository;

import com.Authentication.authproject.Models.Users;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Users findByUsername(String username);
    Optional<UserDetails> findByEmail(String email);

}
