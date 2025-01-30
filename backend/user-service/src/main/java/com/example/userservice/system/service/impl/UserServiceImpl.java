package com.example.userservice.system.service.impl;

import com.example.userservice.system.entity.Role;
import com.example.userservice.system.entity.User;
import com.example.userservice.system.entity.UserRole;
import com.example.userservice.system.exception.RoleNotFoundException;
import com.example.userservice.system.exception.UserNameAlreadyExistException;
import com.example.userservice.system.exception.UserNameNotFoundException;
import com.example.userservice.system.model.request.UserRegisterRequest;
import com.example.userservice.system.model.request.UserUpdateRequest;
import com.example.userservice.system.model.response.UserResponse;
import com.example.userservice.system.repository.RoleRepository;
import com.example.userservice.system.repository.UserRepository;
import com.example.userservice.system.repository.UserRoleRepository;
import com.example.userservice.system.service.UserService;
import com.example.userservice.system.type.RoleType;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {


    @Transactional(rollbackFor = Exception.class)
    public void save(UserRegisterRequest userRegisterRequest) {
        String userName = userRegisterRequest.getUserName();
        Optional<User> existUser = userRepository.findByUserName(userName);
        if (existUser.isPresent()) {
            throw new UserNameAlreadyExistException(ImmutableMap.of(USERNAME, userName));
        }
        User user = new User()
                .setUserName(userRegisterRequest.getUserName())
                .setPassword(bCryptPasswordEncoder.encode(userRegisterRequest.getPassword()))
                .setEnabled(true);
        Role role = roleRepository.findByRoleName(RoleType.USER.getName())
                .orElseThrow(() -> new RoleNotFoundException(ImmutableMap.of("roleName", RoleType.USER.getName())));
        UserRole userRole = new UserRole().setUser(user).setRole(role);
        user.getUserRoles().add(userRole);
        userRepository.save(user);
    }

    public User find(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(() -> new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName)));
    }

    public void update(UserUpdateRequest userUpdateRequest) {
        User user = find(userUpdateRequest.getUserName());
        if (!Strings.isNullOrEmpty(userUpdateRequest.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
        }
        if (userUpdateRequest.getEnabled() != null) {
            user.setEnabled(userUpdateRequest.getEnabled());
        }
        userRepository.save(user);
    }

    public void delete(String userName) {
        if (!userRepository.existsByUserName(userName)) {
            throw new UserNameNotFoundException(ImmutableMap.of(USERNAME, userName));
        }
        userRepository.deleteByUserName(userName);
    }

    public Page<UserResponse> getAll(int pageNum, int pageSize) {
        return userRepository.findAll(PageRequest.of(pageNum, pageSize))
                .map(user -> new UserResponse()
                        .setUserName(user.getUserName())
                        .setEnabled(user.getEnabled()));
    }

    public boolean check(String currentPassword, String password) {
        return this.bCryptPasswordEncoder.matches(currentPassword, password);
    }

    public static final String USERNAME = "username:";
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

}
