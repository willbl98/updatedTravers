package com.traversconsultingus.tcwebshell.repository;

import com.traversconsultingus.tcwebshell.entity.Timesheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface TimesheetRepository extends JpaRepository<Timesheet, Long> {
    Timesheet findByPunch(Long punch);

    Optional<Timesheet> findByLastName(String LastName);



}
