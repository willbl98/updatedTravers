package com.traversconsultingus.tcwebshell.dao;

import com.traversconsultingus.tcwebshell.entity.Timesheet;
import com.traversconsultingus.tcwebshell.repository.TimesheetRepository;
import com.traversconsultingus.tcwebshell.repository.RoleRepository;
import com.traversconsultingus.tcwebshell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

@Service("timesheetService")
public class TimesheetDAO {
    private UserRepository userRepository;
    private TimesheetRepository timesheetRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private Properties appProps;

    @Autowired
    public TimesheetDAO(UserRepository userRepository,
                   RoleRepository roleRepository,
                   TimesheetRepository timesheetRepository,
                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.timesheetRepository = timesheetRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public Timesheet findTimesheetByPunch(Long punch) {
        return timesheetRepository.findByPunch(punch);
    }

    public Timesheet findTimesheetByLastName(String LastName){
        Optional<Timesheet> timesheet =  timesheetRepository.findByLastName(LastName);
        return timesheet.orElse(null);
    }

    public Timesheet saveTimesheet(Timesheet timesheet){
        timesheet = timesheetRepository.save(timesheet);
        return timesheet;
    }

    public void deleteTimesheet(Timesheet timesheet){
        timesheetRepository.delete(timesheet);
    }

    public List<Timesheet> listTimesheets(){
        return timesheetRepository.findAll();
    }

}
