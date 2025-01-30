package com.example.userservice.system.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiao
 */
@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER("USER"),
    ADMIN("ADMIN");
    private final String name;
}
