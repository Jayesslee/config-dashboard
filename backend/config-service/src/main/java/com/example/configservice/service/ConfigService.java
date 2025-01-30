package com.example.configservice.service;

import com.example.configservice.entity.Config;
import com.example.configservice.model.request.ConfigSaveRequest;
import com.example.configservice.model.request.ConfigUpdateRequest;
import com.example.configservice.model.response.ConfigResponse;
import org.springframework.data.domain.Page;

public interface ConfigService {

    void save(ConfigSaveRequest configSaveRequest);

    ConfigResponse find(String key);

    void delete(String key);

    Page<ConfigResponse> getAll(int pageNum, int pageSize);
}
