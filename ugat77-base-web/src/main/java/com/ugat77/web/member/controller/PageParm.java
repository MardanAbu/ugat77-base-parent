package com.ugat77.web.member.controller;

import lombok.Data;

@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String phone;
    private String name;
    private String username;
    private String userType;
    private String memberId;
}
