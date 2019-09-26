package com.traversconsultingus.tcwebshell.configuration;
import com.traversconsultingus.tcwebshell.dao.UserDAO;
import com.traversconsultingus.tcwebshell.entity.Role;
import com.traversconsultingus.tcwebshell.entity.User;
import com.traversconsultingus.tcwebshell.repository.RoleRepository;
import lombok.extern.java.Log;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
@Log
@Component
public class EventListener {

    @org.springframework.context.event.EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        String appName = context.getEnvironment().getProperty("spring.application.name");

        String devInstance = context.getEnvironment().getProperty("dev.preload.data", "false");

        if(devInstance.equalsIgnoreCase("true")) {
            log.info(String.format("Application %s is in development mode.", appName));
            createAdminUser(context);
            //createTestPatients(context);
        } else {
            log.info(String.format("Application %s is in producton mode.", appName));
        }
    }
    private static void createAdminUser(ApplicationContext context){
        UserDAO userDAO = context.getBean(UserDAO.class);
        RoleRepository roleRepository = context.getBean(RoleRepository.class);
        BCryptPasswordEncoder bCryptPasswordEncoder = context.getBean(BCryptPasswordEncoder.class);
        User user = new User();
        user.setEmail("ggh1421@jagmail.southalabama.edu");
        user.setFirstName("Graydon");
        user.setLastName("Hodges");
        user.setPassword(bCryptPasswordEncoder.encode("Root"));
        user.setActive(1);
        user.setResetRequired(false);
        Role userRole = roleRepository.findByRole("ADMIN");
        user.setRoles(new ArrayList<>(Arrays.asList(userRole)));
        if(userDAO.findUserByEmail(user.getEmail()) == null) {
            userDAO.saveUser(user, false);
        }
    }

}
