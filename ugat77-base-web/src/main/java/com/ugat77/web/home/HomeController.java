package com.ugat77.web.home;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.equipment.service.EquipmentService;
import com.ugat77.web.member.service.MemberService;
import com.ugat77.web.suggest.entity.Suggest;
import com.ugat77.web.suggest.service.SuggestService;
import com.ugat77.web.sys_user.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private EquipmentService equipmentService;
    @Autowired
    private SuggestService suggestService;

    //统计总数
    @GetMapping("/getTotal")
    public ResultVo getTotal(){
        TotalCount totalCount = new TotalCount();
        int memberCount = memberService.count();
        totalCount.setMemberCount(memberCount);
        int userCount = sysUserService.count();
        totalCount.setUserCount(userCount);
        int equipCount = equipmentService.count();
        totalCount.setEquipCount(equipCount);
        return ResultUtils.success("Searching success",totalCount);
    }

    @GetMapping("/getSuggestList")
    public ResultVo getSuggestList(){
        QueryWrapper<Suggest> query = new QueryWrapper<>();
        query.lambda().orderByDesc(Suggest::getDateTime).last(" limit 3");
        List<Suggest> list = suggestService.list(query);
        return ResultUtils.success("Searching success",list);
    }
}
