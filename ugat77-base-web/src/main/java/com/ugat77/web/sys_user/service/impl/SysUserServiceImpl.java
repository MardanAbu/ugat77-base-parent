package com.ugat77.web.sys_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.sys_user.entity.PageParm;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.mapper.SysUserMapper;
import com.ugat77.web.sys_user.service.SysUserService;
import com.ugat77.web.sys_user_role.entity.SysUSerRole;
import com.ugat77.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    @Transactional //我们操作了两个表，在这个注释的作用下，如果有一个表操作失败，那整个就不会成功
    public void addUser(SysUser sysUser) {
        //Save user first, then save the role
        int insert = this.baseMapper.insert(sysUser);
        if (insert >0){
            //save role
            SysUSerRole sysUSerRole = new SysUSerRole();
            sysUSerRole.setRoleId(sysUser.getRoleId());
            sysUSerRole.setUserId(sysUser.getUserId());
            sysUserRoleService.save(sysUSerRole);
        }
    }

    @Override
    @Transactional
    public void editUser(SysUser sysUser) {
        //Edit user first, then delete the old one
        int insert = this.baseMapper.updateById(sysUser);
        if (insert >0){
            //delete
            QueryWrapper<SysUSerRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUSerRole::getUserId, sysUser.getUserId());
            sysUserRoleService.remove(query);
            //save
            SysUSerRole sysUSerRole = new SysUSerRole();
            sysUSerRole.setRoleId(sysUser.getRoleId());
            sysUSerRole.setUserId(sysUser.getUserId());
            sysUserRoleService.save(sysUSerRole);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        int i = this.baseMapper.deleteById(userId);
        if (i > 0) {
            //Delete user's role
            QueryWrapper<SysUSerRole> query = new QueryWrapper<>();
            query.lambda().eq(SysUSerRole::getUserId, userId);
            sysUserRoleService.remove(query);
        }
    }

    @Override
    public IPage<SysUser> getList(PageParm parm) {
        //build paging object
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());

        //query condition
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getPhone, parm.getPhone());
        }
        if (StringUtils.isNotEmpty(parm.getName())) {
            query.lambda().like(SysUser::getName, parm.getName());
        }
        return this.baseMapper.selectPage(page,query);
    }
}
