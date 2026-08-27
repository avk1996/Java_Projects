package com.app.expense.service;

import com.app.expense.dao.UserDao;
import com.app.expense.entity.User;
import com.app.expense.helper.Role;
import com.app.expense.response_dto.UserResponseDTO;
import org.modelmapper.ModelMapper;
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

    @Autowired
    ModelMapper modelMapper;

    public UserResponseDTO addUser(User user){
        try{
            System.out.println("service: "+user.toString());
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            String encryptPassword = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(encryptPassword);
            newUser.setRole(Role.USER);

            return modelMapper.map(userDao.save(newUser), UserResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<UserResponseDTO> getUsers(){
        try {
            List<User> users = userDao.findAll();
            return users.stream().map(user -> modelMapper.map(user, UserResponseDTO.class)).toList();
        }catch (Exception e){
            return null;
        }
    }

    public UserResponseDTO deleteUser(int userId) {
        try{
            User existingUser = userDao.findById(userId).orElseThrow(()-> new RuntimeException("User not found"));
            userDao.delete(existingUser);
            return modelMapper.map(existingUser, UserResponseDTO.class);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public UserResponseDTO createUser(User user){
        try{
            User newUser = new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            String encryptPassword = passwordEncoder.encode(user.getPassword());
            newUser.setPassword(encryptPassword);
            newUser.setRole(user.getRole());

            return modelMapper.map(userDao.save(newUser), UserResponseDTO.class);
        } catch (Exception e) {
            return null;
        }
    }
}
