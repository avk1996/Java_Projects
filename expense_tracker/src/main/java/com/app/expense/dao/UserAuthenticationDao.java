package com.app.expense.dao;

import com.app.expense.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAuthenticationDao extends JpaRepository<User, Integer> {
    Optional<User> findByNameAndPassword(String name, String password);

    Optional<User> findByName(String username);

    Optional<String> findByPassword(String identifier);
}
