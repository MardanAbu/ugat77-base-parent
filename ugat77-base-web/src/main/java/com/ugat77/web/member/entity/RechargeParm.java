package com.ugat77.web.member.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RechargeParm {
    private Long userId;
    private Long memberId;
    private BigDecimal money;
}