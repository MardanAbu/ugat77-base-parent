package com.ugat77.web.lost.entity;

import lombok.Data;

@Data
public class LostParm {
    private Long currentPage;
    private Long pageSize;
    private String lostName;
}
