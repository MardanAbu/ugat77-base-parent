package com.ugat77.web.sys_role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.sys_menu.entity.MakeMenuTree;
import com.ugat77.web.sys_menu.entity.SysMenu;
import com.ugat77.web.sys_menu.service.SysMenuService;
import com.ugat77.web.sys_role.entity.RoleAssignParm;
import com.ugat77.web.sys_role.entity.RoleParm;
import com.ugat77.web.sys_role.entity.RolePermissionVo;
import com.ugat77.web.sys_role.entity.SysRole;
import com.ugat77.web.sys_role.mapper.SysRoleMapper;
import com.ugat77.web.sys_role.service.SysRoleService;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.alibaba.druid.sql.visitor.SQLEvalVisitorUtils.like;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysRole> getList(RoleParm parm) {
        //build paging object
        IPage<SysRole> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //conditional query
        QueryWrapper<SysRole> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(SysRole::getRoleName,parm.getRoleName());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    public RolePermissionVo getMenuTree(RoleAssignParm parm) {
        //get user info
        SysUser user = sysUserService.getById(parm.getUserId());
        List<SysMenu> list = null;
        //如果是admin，直接查询所有的菜单
        if(StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")){
            list = sysMenuService.list();
        }else{
            list = sysMenuService.getMenuByUserId(user.getUserId());
        }
        //组装树形数据
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        //查询角色原来的数据
        List<SysMenu> roleList = sysMenuService.getMenuByRoleId(parm.getRoleId());
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(roleList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item -> {
                    ids.add(item.getMenuId());
                });
        //组装数据
        RolePermissionVo vo = new RolePermissionVo();
        vo.setListmenu(menuList);
        vo.setCheckList(ids.toArray());
        return vo;
    }
}
