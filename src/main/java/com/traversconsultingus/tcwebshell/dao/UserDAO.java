package com.traversconsultingus.tcwebshell.dao;

import com.traversconsultingus.tcwebshell.entity.User;
import com.traversconsultingus.tcwebshell.repository.RoleRepository;
import com.traversconsultingus.tcwebshell.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserDAO {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Properties appProps;

    @Autowired
    public UserDAO(UserRepository userRepository,
                   RoleRepository roleRepository,
                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(Long id){
        Optional<User> user =  userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        else{
            return null;
        }

    }

    public User saveNewUser(User user) {
        String tempPassword = RandomStringUtils.randomAlphabetic(10);
        user.setPassword(bCryptPasswordEncoder.encode(tempPassword));
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        appProps.getProperty("mail.message.text");
        mailMessage.setFrom(appProps.getProperty("mail.message.from"));
        mailMessage.setReplyTo(appProps.getProperty("mail.message.replyto"));
        javaMailSender.send(mailMessage);
        user.setActive(1);
        user.setResetRequired(true);
        user = userRepository.save(user);
        return user;
    }

    //If update user is true we will save the user with the password stored in the database
    public User saveUser(User user, boolean updateUser){
        if(updateUser){
            Optional<User> oldUser = userRepository.findById(user.getId());
            if(oldUser.isPresent()){
                user.setPassword(oldUser.get().getPassword());
                user = userRepository.save(user);
                return user;
            }
            else{
                return null;
            }
        }
        else {
            user = userRepository.save(user);
            return user;
        }
    }

    public List<User> listUsers(){
        return userRepository.findAllByActive(1);
    }

    public List<User> listDeleted(){
        return userRepository.findAllByActive(0);
    }

}
