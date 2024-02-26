package com.login.login.repository;

import com.login.login.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LoginRepository extends JpaRepository<Login, Long> {
    @Query("")
    Login findByUsername(String username);
}
