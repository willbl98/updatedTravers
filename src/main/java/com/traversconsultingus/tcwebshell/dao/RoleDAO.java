package com.traversconsultingus.tcwebshell.dao;

import com.traversconsultingus.tcwebshell.entity.Role;
import com.traversconsultingus.tcwebshell.repository.RoleRepository;
import com.traversconsultingus.tcwebshell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service("roleService")
public class RoleDAO {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Properties appProps;

    @Autowired
    public RoleDAO(UserRepository userRepository,
                   RoleRepository roleRepository,
                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Role findRoleByName(String name) {
        return roleRepository.findByRole(name);
    }

    public Role findRoleById(Long id){
        Optional<Role> role =  roleRepository.findById(id);
        if(role.isPresent()){
            return role.get();
        }
        else{
            return null;
        }
    }

    public Role saveRole(Role role){
        role = roleRepository.save(role);
        return role;
    }

    public void deleteRole(Role role){
        roleRepository.delete(role);
    }

    public List<Role> listRoles(){
        return roleRepository.findAll();
    }

}
