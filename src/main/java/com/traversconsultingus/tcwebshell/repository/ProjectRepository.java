package com.traversconsultingus.tcwebshell.repository;

import com.traversconsultingus.tcwebshell.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectName(String ProjectName);
    Project findByProjectNumber(Long ProjectNumber);
}
