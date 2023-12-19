package com.ugat77.web.sys_role.entity;

import lombok.Data;

import java.util.List;

@Data
public class SaveMenuParm {
    private Long roleId;
    private List<Long> list;
}
