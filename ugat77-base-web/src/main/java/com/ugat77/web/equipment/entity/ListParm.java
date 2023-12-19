package com.ugat77.web.equipment.entity;

import lombok.Data;

@Data
public class ListParm {
    private Long currentPage;
    private Long pageSize;
    private String name;
}
