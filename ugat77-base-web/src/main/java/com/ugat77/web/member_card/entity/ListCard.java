package com.ugat77.web.member_card.entity;

import lombok.Data;

@Data
public class ListCard {
    private Long currentPage;
    private Long pageSize;
    private String title;
}
