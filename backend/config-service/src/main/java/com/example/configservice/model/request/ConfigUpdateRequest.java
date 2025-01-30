package com.example.configservice.model.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author xiao
 */
@Data
public class ConfigUpdateRequest {
    @NotBlank
    @Size(max = 64)
    private String key;
    @NotBlank
    @Size(max = 64)
    private String value;

}
