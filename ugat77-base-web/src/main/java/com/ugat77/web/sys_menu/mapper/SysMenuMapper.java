package com.ugat77.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugat77.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //get menu by employee id
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);

    //get menu by member if
    List<SysMenu> getMenuByMemberId(@Param("userId") Long userId);

    //get menu by role id
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);
}
