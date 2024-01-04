package com.ugat77.web.history.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

@Data
public class History {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long memberId;
    private String code;
    private LocalDate createTime;
    private Boolean isEnter;
}
