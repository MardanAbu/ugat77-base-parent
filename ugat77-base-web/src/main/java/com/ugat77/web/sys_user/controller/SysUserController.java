package com.ugat77.web.sys_user.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.sys_role.entity.SelectType;
import com.ugat77.web.sys_user.entity.PageParm;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.service.SysUserService;
import com.ugat77.web.sys_user_role.entity.SysUSerRole;
import com.ugat77.web.sys_user_role.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser){
        sysUser.setCreateTime(new Date());

        //password encryption
        if (!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }
        //if it's admin
        sysUser.setIsAdmin("0");

        //if the user exist
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(query);
        if (one != null){
            return ResultUtils.error("The username already exists");
        }

        //save
        sysUserService.addUser(sysUser);
        return ResultUtils.success("Create succeed");

    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser){
        sysUser.setUpdateTime(new Date());

        //password encryption
        if (!StringUtils.isEmpty(sysUser.getPassword())){
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }

        //if the user exist
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername,sysUser.getUsername());
        SysUser one = sysUserService.getOne(query);
        if (one != null && !sysUser.getUserId().equals(one.getUserId())){
            return ResultUtils.error("The username already exists");
        }
        //update
        sysUserService.editUser(sysUser);
        return ResultUtils.success("Update succeed");
    }

    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
        sysUserService.deleteUser(userId);
        return ResultUtils.success("Delete succeed");
    }

    @GetMapping("/list")
    public ResultVo list(PageParm parm) {
        IPage<SysUser> list = sysUserService.getList(parm);
        //密码设空
        if(list.getRecords().size() > 0) {
            list.getRecords().forEach(item->{
                item.setPassword("");
            });
        }
        return ResultUtils.success("Searching succeed", list);
    }

    //Search role by user id
    @GetMapping("/role")
    public ResultVo getRole(Long userId){
        QueryWrapper<SysUSerRole> query = new QueryWrapper<>();
        query.lambda().eq(SysUSerRole::getUserId, userId);
        SysUSerRole one = sysUserRoleService.getOne(query);
        return ResultUtils.success("Searching success",one);
    }

    //Search coach of the course
    @GetMapping("/getTeacher")
    public ResultVo getTeacher(){
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUserType,"2");
        List<SysUser> list = sysUserService.list(query);
        //组装后的select数据
        List<SelectType> selectTypeList = new ArrayList<>();
        if(list.size() >0){
            list.stream().forEach(item ->{
                SelectType selectType = new SelectType();
                selectType.setLabel(item.getName());
                selectType.setValue(item.getRoleId());
                selectTypeList.add(selectType);
            });
        }
        return ResultUtils.success("Searching success",selectTypeList);
    }

}
