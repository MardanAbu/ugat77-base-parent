package com.ugat77.web.member.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("member")
@Data
public class Member {
    @TableId(type = IdType.AUTO)
    private Long memberId;
    @TableField(exist = false)
    private Long roleId;
    private String name;
    private String sex;
    private String phone;
    private Integer age;
    private String birthDay;
    private Integer height;
    private Integer weight;
    private String joinTime;
    private String endTime;
    private String username;
    private String password;
    private String status;
    private BigDecimal money;
    private String cardType;
    private Integer cardDay;
    private BigDecimal price;
}
