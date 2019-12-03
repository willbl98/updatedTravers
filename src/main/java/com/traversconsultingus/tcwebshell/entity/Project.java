package com.traversconsultingus.tcwebshell.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "number")
    private Long projectNumber;
    @Column(name = "ProjectName")
    @NotEmpty(message="Please enter a Project Name")
    private String projectName;

}
