package com.ugat77.web.login;

import lombok.Data;

@Data
public class UserInfo {
    private Long userId;
    private String name;
    private Object[] permissions;
}
