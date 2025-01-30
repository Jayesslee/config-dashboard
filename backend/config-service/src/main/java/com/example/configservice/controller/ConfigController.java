package com.example.configservice.controller;

import com.example.configservice.entity.Config;
import com.example.configservice.model.request.ConfigSaveRequest;
import com.example.configservice.model.request.ConfigUpdateRequest;
import com.example.configservice.model.response.ConfigResponse;
import com.example.configservice.service.ConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiao
 */
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("/api/config")
@Api(tags = "config")
public class ConfigController {

    @PostMapping("/save")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ApiOperation("Create or update config")
    public ResponseEntity<Void> save(@RequestBody @Valid ConfigSaveRequest userRegisterRequest) {
        configService.save(userRegisterRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/all")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ApiOperation("Get all configs with page")
    public ResponseEntity<Page<ConfigResponse>> getAllConfigs(@RequestParam(value = "pageNum", defaultValue = "0") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<ConfigResponse> allConfig = configService.getAll(pageNum, pageSize);
        return ResponseEntity.ok().body(allConfig);
    }

    @GetMapping("/get")
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    @ApiOperation("Get config by key")
    public ResponseEntity<ConfigResponse> findByKey(@RequestParam(value = "key") @Valid @NotBlank String key) {
        ConfigResponse config = configService.find(key);
        return ResponseEntity.ok().body(config);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @ApiOperation("Delete config, needs admin authority")
    public ResponseEntity<Void> deleteByKey(@RequestParam("key") @Valid @NotBlank String key) {
        configService.delete(key);
        return ResponseEntity.ok().build();
    }

    private final ConfigService configService;

}
