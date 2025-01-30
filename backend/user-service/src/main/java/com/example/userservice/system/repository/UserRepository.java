package com.example.userservice.system.repository;

import com.example.userservice.system.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author xiao
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * User EntityGraph for better performance.
     * @param username
     * @return
     */
    @EntityGraph(attributePaths = {"userRoles", "userRoles.role"})
    Optional<User> findByUserName(String username);

    @Modifying
    @Transactional(rollbackFor = Exception.class)
    void deleteByUserName(String userName);

    boolean existsByUserName(String username);
}
