package com.example.configservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author xiao
 */
@Data
@Accessors(chain = true)
@Entity
@Table(name = "config")
public class Config extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String key;
    @Column(nullable = false)
    private String value;
}
