package com.ugat77.web.course.entity;

import lombok.Data;

@Data
public class PageParm {
    private Long currentPage;
    private Long pageSize;
    private String userType;
    private Long userId;
}
