package com.traversconsultingus.tcwebshell.dao;

import com.traversconsultingus.tcwebshell.entity.Project;
import com.traversconsultingus.tcwebshell.repository.ProjectRepository;
import com.traversconsultingus.tcwebshell.repository.RoleRepository;
import com.traversconsultingus.tcwebshell.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("projectService")
public class ProjectDAO {
    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private RoleRepository roleRepository;

    @Autowired
    public ProjectDAO(UserRepository userRepository,
                        RoleRepository roleRepository,
                        ProjectRepository projectRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.roleRepository = roleRepository;
    }

    public Project findProjectByProjectName(String ProjectName) {
        return projectRepository.findByProjectName(ProjectName);
    }
    public Project findProjectByProjectNumber(Long ProjectNumber) {
        return projectRepository.findByProjectNumber(ProjectNumber);
    }


    public Project saveProject(Project project){
        project = projectRepository.save(project);
        return project;
    }

    public void deleteProject(Project project){
        projectRepository.delete(project);
    }

    public List<Project> listProjects(){
        return projectRepository.findAll();
    }

}