package com.ugat77.web.sys_role_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.sys_role.entity.SaveMenuParm;
import com.ugat77.web.sys_role_menu.entity.RoleMenu;
import com.ugat77.web.sys_role_menu.mapper.RoleMenuMapper;
import com.ugat77.web.sys_role_menu.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Transactional
    @Override
    public void saveMenu(SaveMenuParm parm) {
        //先删除角色原来的权限
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId, parm.getRoleId());
        this.baseMapper.delete(query);
        //重新保存
        this.baseMapper.saveRoleMenu(parm.getRoleId(), parm.getList());
    }
}
