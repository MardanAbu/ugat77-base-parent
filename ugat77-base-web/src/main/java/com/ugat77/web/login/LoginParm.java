package com.ugat77.web.login;

import lombok.Data;

@Data
public class LoginParm {
    private String username;
    private String password;
    private String code;
    private String userType;
}
