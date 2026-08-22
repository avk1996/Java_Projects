package com.app.expense.service;

import com.app.expense.dao.UserDao;
import com.app.expense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    public User addUser(User user){
        try{
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            String encryptPassword = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(encryptPassword);

            return userDao.save(newUser);
        } catch (Exception e) {
            return null;
        }
    }

    public List<User> getUsers(){
        try {
            return userDao.findAll();
        }catch (Exception e){
            return null;
        }
    }
}
