package com.example.configservice.repository;

import com.example.configservice.entity.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author xiao
 */
@Repository
public interface ConfigRepository extends JpaRepository<Config, Long> {
    Optional<Config> findByKey(String key);

    @Modifying
    Long deleteByKey(String key);

}
