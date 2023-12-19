package com.ugat77.web.sys_role.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleAssignParm implements Serializable {
    private Long userId;
    private Long roleId;
}
