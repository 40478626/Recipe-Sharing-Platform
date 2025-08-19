package com.example.recipe_sharing_platform.Repository;

import com.example.recipe_sharing_platform.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository <User, Long> {
    Optional<User> findByUsername(String username);
}
