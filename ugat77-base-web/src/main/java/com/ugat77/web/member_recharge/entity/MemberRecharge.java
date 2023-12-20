package com.ugat77.web.member_recharge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("member_recharge")
public class MemberRecharge {
    @TableId(type = IdType.AUTO)
    private Long rechargeId;
    private Long memberId;
    private BigDecimal money;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+1")
    private Date createTime;
    private String createUser;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String username;
}