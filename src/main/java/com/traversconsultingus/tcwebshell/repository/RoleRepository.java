package com.traversconsultingus.tcwebshell.repository;

import com.traversconsultingus.tcwebshell.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
}
