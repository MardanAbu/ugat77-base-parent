package com.ugat77.web.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugat77.web.sys_role_menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    //Save
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
