package com.traversconsultingus.tcwebshell.request;

import com.sun.mail.imap.protocol.ID;
import com.traversconsultingus.tcwebshell.entity.Project;
import com.traversconsultingus.tcwebshell.dao.ProjectDAO;
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
@RequestMapping(value="/api/projects")
@CommonsLog

public class ProjectsController {
    @Autowired
    private ProjectDAO projectDAO;

    @GetMapping("/{ProjectNumber}")
    public Project get(@PathVariable long ProjectNumber) throws NotFoundException {
        Project project = projectDAO.findProjectByProjectNumber(ProjectNumber);
        if (project == null) {
            throw new NotFoundException("Project with Pnum " + ProjectNumber + " was not found.");

        }
        return project;
    }
    @PostMapping("/")
    public Project save(@RequestParam Map<String, String> requestParams, Project m, BindingResult result, Model model) {
        m = projectDAO.saveProject(m);
        return m;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long ProjectNumber) {

        Project m = projectDAO.findProjectByProjectNumber(ProjectNumber);
        if(m != null) {
            projectDAO.deleteProject(m);
            return "Project with Pnum "+ProjectNumber+" successfully deleted";

        } else {
            return "Project with Pnum "+ProjectNumber+" was not found and could not be deleted";
        }
    }


    @GetMapping("/list")
    public Map<String, List<Project>> list() {
        Map<String, List<Project>> outMap = new HashMap<String, List<Project>>();
        List<Project> list = projectDAO.listProjects();
        outMap.put("data", list);
        return outMap;
    }

}

