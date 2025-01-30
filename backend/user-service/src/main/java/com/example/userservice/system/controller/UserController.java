package com.example.userservice.system.controller;

import com.example.userservice.system.model.request.UserRegisterRequest;
import com.example.userservice.system.model.request.UserUpdateRequest;
import com.example.userservice.system.model.response.UserResponse;
import com.example.userservice.system.service.UserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @author xiao
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/user")
@Api(tags = "user")
public class UserController {

    @PostMapping("/register")
    @ApiOperation("User register")
    public ResponseEntity<Void> signUp(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        userService.save(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ApiOperation("Get all users with page")
    public ResponseEntity<Page<UserResponse>> getAllUser(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<UserResponse> allUser = userService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allUser);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation("Update user")
    public ResponseEntity<Void> update(@RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        userService.update(userUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation("Delete user by key")
    public ResponseEntity<Void> deleteUserByUserName(@RequestParam("username") String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }

    private final UserService userService;

}
