package com.traversconsultingus.tcwebshell.request;

import com.traversconsultingus.tcwebshell.entity.Role;
import com.traversconsultingus.tcwebshell.entity.User;
import com.traversconsultingus.tcwebshell.dao.RoleDAO;
import com.traversconsultingus.tcwebshell.dao.UserDAO;
import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@RestController
@RequestMapping(value="/api/users")
@CommonsLog
public class UsersController {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/{id}")
    public User get(@PathVariable long id) throws NotFoundException{

        User user = (User) userDAO.findUserById(id);
        if(user == null){
            throw new NotFoundException("User with id "+id+" was not found.");
        }
        return user;
    }

    @PostMapping("/")
    public User save(@RequestParam Long[] rolesId, @RequestParam Map<String, String> requestParams, User m, BindingResult result, Model model) {
        List<Role> roles = new ArrayList<>();
        for(Long id : rolesId){
            Role role = roleDAO.findRoleById(id);
            if(role != null){
                roles.add(role);
            }
            else{
                System.out.print("We received a role where it was null when creating a user, this should not happen but user will simply be created without this role.");
            }
        }
        m.setRoles(roles);
        if(requestParams.get("newOrReset") != null &&requestParams.get("newOrReset").equalsIgnoreCase("true")){
            m = userDAO.saveNewUser(m);
        }
        else {
            m = userDAO.saveUser(m, true);
        }
        return m;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {

        User m = userDAO.findUserById(id);

        if(m != null) {
            m.setActive(0);
            userDAO.saveUser(m, false);
            return "User with id "+id+" successfully deleted";

        } else {
            return "User with id "+id+" was not found and could not be deleted";
        }
    }


    @GetMapping("/list")
    public Map<String, List<User>> list() {
        Map<String, List<User>> outMap = new HashMap<String, List<User>>();
        List<User> list = userDAO.listUsers();
        outMap.put("data", list);
        return outMap;
    }

    @GetMapping("/listdeleted")
    public Map<String, List<User>> listDeleted() {
        Map<String, List<User>> outMap = new HashMap<String, List<User>>();
        List<User> list = userDAO.listDeleted();
        outMap.put("data", list);
        return outMap;
    }

    @PostMapping("/changepassword")
    public ModelAndView changePassword(@RequestParam Map<String, String> requestParams){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ModelAndView modelAndView = new ModelAndView();
        String userName = authentication.getName();
        User user = userDAO.findUserByEmail(userName);
        String oldPassword = requestParams.get("oldPassword");
        String newPassword = requestParams.get("newPassword");
        String confirmPassword = requestParams.get("confirmPassword");
        if(null != authentication && !("anonymousUser").equals(authentication.getName()) && oldPassword != null && bCryptPasswordEncoder.matches(oldPassword, user.getPassword()) && newPassword != null && newPassword.equals(confirmPassword)) {
            user.setPassword(bCryptPasswordEncoder.encode(newPassword));
            user.setResetRequired(false);
            user = userDAO.saveUser(user, true);
            modelAndView.setViewName("home");
        }
        else{
            modelAndView.setViewName("changepassword");
            modelAndView.addObject("oldPassword", oldPassword);
            modelAndView.addObject("newPassword", newPassword);
            modelAndView.addObject("confirmPassword", confirmPassword);
            modelAndView.addObject("param.error", "An error occurred while changing your password, please try again. If this problem persists please contact your administrator.");
        }
        return modelAndView;
    }
}
