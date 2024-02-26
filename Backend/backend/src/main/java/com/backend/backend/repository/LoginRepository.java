package com.backend.backend.repository;

import com.backend.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);

}
