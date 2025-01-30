package com.example.userservice.system.repository;

import com.example.userservice.system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xiao
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

}
