package com.ugat77.web.sys_menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MakeMenuTree {
    public static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid){
        List<SysMenu> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>()) //判断menulist是否为空，如果为空走oeElse返回空list，如果不为空走stream
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item,menu);
                    //递归查找下级，自己调用自己
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }

    public static List<RouterVO> makeRouter(List<SysMenu> menuList, Long pid) {
        List<RouterVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    //设置children,每一项的下级
                    List<RouterVO> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    //parent为0时,为一级菜单，component为Layout
                    if ((item.getParentId() == 0L)) {
                        //parent为0时,为一级菜单，component为Layout
                        router.setComponent("Layout");
                        //判断一级菜单是否为菜单类型 0：目录 1：菜单
                        if (item.getType().equals("1")) { // 菜单
                            //一级菜单，设置redirect转发到他的children
                            router.setRedirect(item.getPath());
                            //如果一级菜单属于菜单类型，需要设置他的children,前端才会显示
                            List<RouterVO> listChild = new ArrayList<>();
                            //构造children
                            RouterVO child = new RouterVO();
                            child.setName(item.getName());
                            child.setPath(item.getPath());
                            child.setComponent(item.getUrl());
                            child.setMeta(child.new Meta(
                                    item.getTitle(),
                                    item.getIcon(),
                                    item.getCode().split(",")
                            ));
                            listChild.add(child);
                            //设置children
                            router.setChildren(listChild);
                            router.setPath(item.getPath() + "parent");
                            router.setName(item.getName() + "parent");
                        }
                    } else {
                        router.setComponent(item.getUrl());
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getTitle(),
                            item.getIcon(),
                            item.getCode().split(",")
                    ));
                    list.add(router);
                });
        return list;
    }
}
