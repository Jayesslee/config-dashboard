package com.example.userservice.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiao
 */
@Data
@ToString(exclude = "userRoles")
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User extends AbstractAuditBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String userName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<UserRole> userRoles = Lists.newArrayList();

    public List<SimpleGrantedAuthority> getAuthorities() {
        List<Role> roles = userRoles.stream()
                .map(userRole -> userRole.getRole())
                .collect(Collectors.toList());
        List<SimpleGrantedAuthority> authorities = Lists.newArrayList();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        return authorities;
    }

}
