package com.app.expense.service;

import com.app.expense.dao.UserDao;
import com.app.expense.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserDao userDao;

    public User addUser(User user){
        try{
            return userDao.save(user);
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
