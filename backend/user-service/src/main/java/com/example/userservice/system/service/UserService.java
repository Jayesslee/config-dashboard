package com.example.userservice.system.service;

import com.example.userservice.system.entity.Role;
import com.example.userservice.system.entity.User;
import com.example.userservice.system.exception.RoleNotFoundException;
import com.example.userservice.system.exception.UserNameAlreadyExistException;
import com.example.userservice.system.exception.UserNameNotFoundException;
import com.example.userservice.system.model.request.UserRegisterRequest;
import com.example.userservice.system.model.request.UserUpdateRequest;
import com.example.userservice.system.model.response.UserResponse;
import com.example.userservice.system.type.RoleType;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface UserService {

    void save(UserRegisterRequest userRegisterRequest);

    User find(String userName);

    void update(UserUpdateRequest userUpdateRequest);

    void delete(String userName);

    Page<UserResponse> getAll(int pageNum, int pageSize);

    boolean check(String currentPassword, String password);
}
