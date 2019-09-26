package com.traversconsultingus.tcwebshell.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "email", unique = true)
    @Email(message = "Please provide a valid email")
    @NotEmpty(message = "Please provide an email")
    private String email;
    @Column(name = "password")
    @NotEmpty(message = "Please Provide a password")
    private String password;
    @Column(name = "first_name")
    @NotEmpty(message="Please provide your first name")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;
    @Column(name = "active")
    private int active;
    @Column(name = "reset_required")
    private boolean resetRequired;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

}
