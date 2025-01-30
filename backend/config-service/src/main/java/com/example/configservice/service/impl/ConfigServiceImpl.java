package com.example.configservice.service.impl;


import com.example.configservice.entity.Config;
import com.example.configservice.exception.ConfigKeyFoundException;
import com.example.configservice.model.request.ConfigSaveRequest;
import com.example.configservice.model.request.ConfigUpdateRequest;
import com.example.configservice.model.response.ConfigResponse;
import com.example.configservice.repository.ConfigRepository;
import com.example.configservice.service.ConfigService;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ConfigServiceImpl implements ConfigService {

    @Transactional(rollbackFor = Exception.class)
    public void save(ConfigSaveRequest configSaveRequest) {
        Config config = configRepository.findByKey(configSaveRequest.getKey())
                .map(c -> c.setValue(configSaveRequest.getValue()))
                .orElse(new Config()
                        .setKey(configSaveRequest.getKey())
                        .setValue(configSaveRequest.getValue()));
        configRepository.save(config);
    }

    public ConfigResponse find(String key) {
        return configRepository.findByKey(key)
                .map(config -> new ConfigResponse()
                        .setKey(config.getKey())
                        .setValue(config.getValue()))
                .orElseThrow(() -> new ConfigKeyFoundException(ImmutableMap.of("key", key)));
    }

    @Transactional(rollbackFor = Exception.class)
    public void update(ConfigUpdateRequest configUpdateRequest) {
        Config config = configRepository.findByKey(configUpdateRequest.getKey())
                .orElseThrow(() -> new ConfigKeyFoundException(ImmutableMap.of("key", configUpdateRequest.getKey())));
        if (!Strings.isNullOrEmpty(configUpdateRequest.getValue())) {
            config.setValue(configUpdateRequest.getValue());
        }
        configRepository.save(config);
    }

    @Transactional(rollbackFor = Exception.class)
    public void delete(String key) {
        configRepository.deleteByKey(key);
    }

    public Page<ConfigResponse> getAll(int pageNum, int pageSize) {
        return configRepository.findAll(PageRequest.of(pageNum, pageSize))
                .map(config -> new ConfigResponse()
                        .setKey(config.getKey())
                        .setValue(config.getValue()));
    }


    private final ConfigRepository configRepository;


}
