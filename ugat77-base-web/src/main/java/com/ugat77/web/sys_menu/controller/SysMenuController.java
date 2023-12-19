package com.ugat77.web.sys_menu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.sys_menu.entity.MakeMenuTree;
import com.ugat77.web.sys_menu.entity.SysMenu;
import com.ugat77.web.sys_menu.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody SysMenu sysMenu){
        sysMenu.setCreateTime(new Date());
        if(sysMenuService.save(sysMenu)){
            return ResultUtils.success("Create success!");
        }
        return ResultUtils.error("Create failed!");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody SysMenu sysMenu){
        sysMenu.setUpdateTime(new Date());
        if(sysMenuService.updateById(sysMenu)){
            return ResultUtils.success("Edit success!");
        }
        return ResultUtils.error("Edit failed!");
    }

    //Delete
    @DeleteMapping("/{menuId}")
    public ResultVo delete(@PathVariable("menuId") Long menuId){
    if(sysMenuService.removeById(menuId)){
        return ResultUtils.success("Delete success!");
    }
    return ResultUtils.error("Delete failed!");
    }

    @GetMapping("/list")
    public ResultVo list(){
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> list = sysMenuService.list(query);
        //树形数据组装
        List<SysMenu> menuList = MakeMenuTree.makeTree(list, 0L);
        return ResultUtils.success("Searching success", menuList);
    }

    //查询上级菜单
    @GetMapping("/parent")
    public ResultVo getParent(){
        List<SysMenu> parent = sysMenuService.getParent();
        return ResultUtils.success("Searcing success", parent);
    }
}
