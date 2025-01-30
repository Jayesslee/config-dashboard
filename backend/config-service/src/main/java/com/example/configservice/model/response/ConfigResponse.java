package com.example.configservice.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ConfigResponse {
    private String key;
    private String value;
}
