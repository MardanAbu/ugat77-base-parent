package com.ugat77.web.member.entity;

import lombok.Data;

@Data
public class RechargeParmList {
    private Long currentPage;
    private Long pageSize;
    private Long memberId;
    private String userType;
}
