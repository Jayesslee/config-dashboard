package com.example.userservice.system.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserResponse {
    private String userName;
    private Boolean enabled;
}
