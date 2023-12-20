package com.ugat77.web.login;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.ugat77.jwt.JwtUtils;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.service.MemberService;
import com.ugat77.web.sys_menu.entity.MakeMenuTree;
import com.ugat77.web.sys_menu.entity.RouterVO;
import com.ugat77.web.sys_menu.entity.SysMenu;
import com.ugat77.web.sys_menu.service.SysMenuService;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/login")
public class LoginController {
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @PostMapping("/image")
    public ResultVo imageCode(HttpServletRequest request) throws IOException {
        //Generate code
        String text = defaultKaptcha.createText();
        //存储验证码到session
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        //生成图片
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);

            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            ResultVo result = new ResultVo("Generate success", 200, captchaBase64);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @PostMapping("/login")
    public ResultVo login(HttpServletRequest request, @RequestBody LoginParm loginParm) {
        //获取Session
        HttpSession session = request.getSession();
        //获取验证吗
        String code = (String) session.getAttribute("code");
        //判断验证吗过期
        if (StringUtils.isNotEmpty(loginParm.getCode()) && StringUtils.isEmpty(code)){
            return ResultUtils.error("The code has expired");
        }
        //验证验证吗
        if (!code.equals(loginParm.getCode())) {
            return ResultUtils.error("Wrong code");
        }

        String password = DigestUtils.md5DigestAsHex(loginParm.getPassword().getBytes());

        if (loginParm.getUserType().equals("1")) {//user
            //构造查询条件
            QueryWrapper<Member> query = new QueryWrapper<>();
            query.lambda().eq(Member::getUsername, loginParm.getUsername()).eq(Member::getPrice, loginParm.getPassword());
            Member one = memberService.getOne(query);
            if (one == null) {
                return ResultUtils.error("Incorrect username or password");
            }
            //生成token
            Map<String, String> map = new HashMap<>();
            map.put("userId", Long.toString(one.getMemberId()));
            map.put("username", one.getUsername());
            String token = jwtUtils.generateToken(map);
            //返回登录成功信息
            LoginResult result = new LoginResult();
            result.setToken(token);
            result.setUserId(one.getMemberId());
            result.setUsername(one.getName());
            result.setUserType(loginParm.getUserType());
            return ResultUtils.success("Login success", result);

        } else if (loginParm.getUserType().equals("2")) {//employee
            QueryWrapper<SysUser> query = new QueryWrapper<>();
            query.lambda().eq(SysUser::getPassword, password).eq(SysUser::getUsername, loginParm.getUsername());
            SysUser one = sysUserService.getOne(query);
            if (one == null) {
                return ResultUtils.error("Incorrect username or password!");
            }
            //生成token
            Map<String, String> map = new HashMap<>();
            map.put("userId", Long.toString(one.getUserId()));
            map.put("username", one.getUsername());
            String token = jwtUtils.generateToken(map);
            //返回登录成功信息
            LoginResult result = new LoginResult();
            result.setToken(token);
            result.setUserId(one.getUserId());
            result.setUsername(one.getName());
            result.setUserType(loginParm.getUserType());
            return ResultUtils.success("Login success", result);

        } else {
            return ResultUtils.error("Wrong usertype");
        }
    }

    //查询用户信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(InfoParm parm) {
        UserInfo userInfo = new UserInfo();
        if (parm.getUserType().equals("1")) { //Member
            //根据会员id查询权限字段
            List<SysMenu> menuList = sysMenuService.getMenuByMemberId(parm.getUserId());
            //获取全部的code字段
            List<String> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                    .stream()
                    .map(item -> item.getCode())
                    .filter(item -> item != null)
                    .collect(Collectors.toList());
            //转为数组
            String[] strings = collect.toArray(new String[collect.size()]);
            //查询用户信息
            Member member = memberService.getById(parm.getUserId());
            //设置返回信息
            userInfo.setName(member.getName());
            userInfo.setUserId(member.getMemberId());
            userInfo.setPermissions(strings);
            return ResultUtils.success("Searching success", userInfo);
        } else if (parm.getUserType().equals("2")) { //Employee
            //查询用户信息
            SysUser user = sysUserService.getById(parm.getUserId());
            List<SysMenu> menuList = null;
            if (StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")) { //超级管理员
                menuList = sysMenuService.list();
            } else {
                menuList = sysMenuService.getMenuByUserId(user.getUserId());
            }
            //获取全部的code字段
            List<String> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                    .stream()
                    .map(item -> item.getCode())
                    .filter(item -> item != null)
                    .collect(Collectors.toList());
            //转为数组
            String[] strings = collect.toArray(new String[collect.size()]);

            //设置返回信息
            userInfo.setName(user.getName());
            userInfo.setUserId(user.getUserId());
            userInfo.setPermissions(strings);
            return ResultUtils.success("Searching success", userInfo);
        } else {
            return ResultUtils.error("Wrong user type!");
        }
    }

    @GetMapping("/getMenuList")
    public ResultVo getMenuList(InfoParm parm) {
        if (parm.getUserType().equals("1")) { //Member
            List<SysMenu> menus = sysMenuService.getMenuByMemberId(parm.getUserId());
            //获取菜单和目录
            List<SysMenu> collect = Optional.ofNullable(menus).orElse(new ArrayList<>())
                    .stream()
                    .filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            List<RouterVO> router = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("Searching success", router);
        } else if (parm.getUserType().equals("2")) { //Employee
            SysUser user = sysUserService.getById(parm.getUserId());
            List<SysMenu> menuList = null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(user.getIsAdmin()) && user.getIsAdmin().equals("1")) {
                menuList = sysMenuService.list();
            } else {
                menuList = sysMenuService.getMenuByUserId(parm.getUserId());
            }
            //获取菜单和目录
            List<SysMenu> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
                    .stream()
                    .filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            List<RouterVO> router = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("Searching success", router);
        } else {
            return ResultUtils.error("Wrong usertype!");
        }
    }

}
