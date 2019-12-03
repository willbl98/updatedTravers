package com.traversconsultingus.tcwebshell.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "timesheet")
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "punch")
    private Long punch;
    //@Column(name = "user_id")
    //private Long user_id;
    @Column(name = "FirstName")
    @NotEmpty(message="Please provide your first name")
    private String FirstName;
    @Column(name = "LastName")
    @NotEmpty(message = "Please provide your last name")
    private String lastName;
    @Column(name = "TimeIn")
    private String TimeIn;
    @Column(name = "TimeOut")
    private String TimeOut;
    @Column(name = "Billed")
    private boolean Billed;
    @Column(name = "Paid")
    private boolean Paid;
    @Column(name = "Invoice")
    private int Invoice;
    @Column(name = "Notes")
    private String Notes;

}