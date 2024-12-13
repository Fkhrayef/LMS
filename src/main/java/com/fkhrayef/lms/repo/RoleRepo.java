package com.fkhrayef.lms.repo;

import com.fkhrayef.lms.model.AppRole;
import com.fkhrayef.lms.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRoleName(AppRole appRole);
}
