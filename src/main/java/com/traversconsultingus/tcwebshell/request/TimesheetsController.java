//package com.traversconsultingus.tcwebshell.controllers.request;
//^^^This is the old path

package com.traversconsultingus.tcwebshell.request;

import com.sun.mail.imap.protocol.ID;
import com.traversconsultingus.tcwebshell.entity.Timesheet;
import com.traversconsultingus.tcwebshell.dao.TimesheetDAO;
import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/timesheets")
@CommonsLog
public class TimesheetsController {
    @Autowired
    private TimesheetDAO timesheetDAO;

    @GetMapping("/{id}")
    public Timesheet get(@PathVariable long id) throws NotFoundException{

        Timesheet timesheet = timesheetDAO.findTimesheetByPunch(id);
        if(timesheet == null){
            throw new NotFoundException("Timesheet with id "+id+" was not found.");
        }
        return timesheet;
    }

    @PostMapping("/")
    public Timesheet save(@RequestParam Map<String, String> requestParams, Timesheet m, BindingResult result, Model model) {
        m = timesheetDAO.saveTimesheet(m);
        return m;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {

        Timesheet m = timesheetDAO.findTimesheetByPunch(id);
        if(m != null) {
            timesheetDAO.deleteTimesheet(m);
            return "Timesheet with id "+id+" successfully deleted";

        } else {
            return "Timesheet with id "+id+" was not found and could not be deleted";
        }
    }


    @GetMapping("/list")
    public Map<String, List<Timesheet>> list() {
        Map<String, List<Timesheet>> outMap = new HashMap<String, List<Timesheet>>();
        List<Timesheet> list = timesheetDAO.listTimesheets();
        outMap.put("data", list);
        return outMap;
    }

}