package com.anhvt.shopapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.anhvt.shopapp.models.*;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByPhoneNumber(String phoneNumber);
    //SELECT * FROM users WHERE phoneNumber=?
}
