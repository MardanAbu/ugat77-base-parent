package com.ugat77.web.member_recharge.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    private Date createTime;
    private String createUser;
}