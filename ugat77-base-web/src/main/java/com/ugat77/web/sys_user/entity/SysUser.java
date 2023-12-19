package com.ugat77.web.sys_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sys_user")
public class SysUser {
    @TableId(type = IdType.AUTO)
    private Long userId;

    //表明roleId不属于sys_user表，需要排除
    @TableField(exist = false)
    private Long roleId;
    private String username;
    private String password;
    private String phone;
    private String email;
    private String sex;
    private String isAdmin;
    private BigDecimal salary;
    //type 1:employee, 2:coach
    private String userType;
    //status 0:disable 1:enable
    private String status;
    private String name;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
}
