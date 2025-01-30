package com.example.userservice.system.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;

import jakarta.persistence.*;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @author xiao
 */
@Data
@ToString(exclude = "userRoles")
@Accessors(chain = true)
@Entity
@Table(name = "role")
public class Role extends AbstractAuditBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private String description;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRole> userRoles = Lists.newArrayList();

}
