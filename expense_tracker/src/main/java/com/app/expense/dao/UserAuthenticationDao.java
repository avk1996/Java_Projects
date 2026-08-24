package com.app.expense.dao;

import com.app.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Repository
public interface UserAuthenticationDao extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE (u.name = :username OR u.email = :username) AND u.password = :password")
    Optional<User> login(
            @Param("username") String username,
            @Param("password") String password
    );

    Optional<User> findByName(String username);
}
