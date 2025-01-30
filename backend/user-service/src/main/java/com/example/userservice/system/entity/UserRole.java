package com.example.userservice.system.entity;

import lombok.Data;

import jakarta.persistence.*;
import lombok.experimental.Accessors;


/**
 * @author xiao
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "user_role")
public class UserRole extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

}
