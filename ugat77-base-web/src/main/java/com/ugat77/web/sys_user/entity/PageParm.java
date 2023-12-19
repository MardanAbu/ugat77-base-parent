package com.ugat77.web.sys_user.entity;

import lombok.Data;

@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String name;
}
