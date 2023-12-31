package com.ugat77.web.member.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.member.entity.JoinParm;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.entity.RechargeParm;
import com.ugat77.web.member.entity.RechargeParmList;
import com.ugat77.web.member.service.MemberService;
import com.ugat77.web.member_card.entity.MemberCard;
import com.ugat77.web.member_card.service.MemberCardService;
import com.ugat77.web.member_recharge.entity.MemberRecharge;
import com.ugat77.web.member_recharge.service.MemberRechargerService;
import com.ugat77.web.member_role.entity.MemberRole;
import com.ugat77.web.member_role.service.MemberRoleService;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/member")
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private MemberCardService memberCardService;
    @Autowired
    private MemberRechargerService memberRechargerService;

    //Recharge list
    @GetMapping("/getMyRecharge")
    public ResultVo getMyRecharge(RechargeParmList parm){
        //判断是会员还是员工
        if(parm.getUserType().equals("1")){//会员
            IPage<MemberRecharge> list = memberRechargerService.getRechargeByMember(parm);
            return ResultUtils.success("Searching success",list);
        }else if(parm.getUserType().equals("2")){ //员工
            IPage<MemberRecharge> list = memberRechargerService.getRechargeList(parm);
            return ResultUtils.success("Searching success",list);
        }else{
            return ResultUtils.error("Wrong usertype!");
        }
    }

    //Create
    @PostMapping
    public ResultVo add(@RequestBody Member member){
        //If ID exist
        QueryWrapper<Member> query = new QueryWrapper<>();
        query.lambda().eq(Member::getUsername, member.getUsername());
        Member one = memberService.getOne(query);
        if (one != null){
            return ResultUtils.error("ID already exist");
        }
        memberService.addMember(member);
        return ResultUtils.success("Creating success");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody Member member){
        //If ID exist
        QueryWrapper<Member> query = new QueryWrapper<>();
        query.lambda().eq(Member::getUsername, member.getUsername());
        Member one = memberService.getOne(query);
        if (one != null && !one.getMemberId().equals(member.getMemberId())){
            return ResultUtils.error("ID already exist");
        }
        memberService.editMember(member);
        return ResultUtils.success("Editing success");
    }

    //Delete
    @DeleteMapping("/{memberId}")
    public ResultVo delete(@PathVariable("memberId") Long memberId){
        memberService.deleteMember(memberId);
        return ResultUtils.success("Deleting success");
    }

    //list
    @GetMapping("/list")
    public ResultVo list(PageParm pageParm){
        if (pageParm.getUserType().equals("1")) {
            //构造分页对象
            IPage<Member> page = new Page<>(pageParm.getCurrentPage(), pageParm.getPageSize());
            //构造查询条件
            QueryWrapper<Member> query = new QueryWrapper<>();
            if (StringUtils.isNotEmpty(pageParm.getName())) {
                query.lambda().like(Member::getName, pageParm.getName());
            }
            if (StringUtils.isNotEmpty(pageParm.getPhone())) {
                query.lambda().like(Member::getPhone, pageParm.getPhone());
            }
            if (StringUtils.isNotEmpty(pageParm.getUsername())) {
                query.lambda().like(Member::getUsername, pageParm.getUsername());
            }
            query.lambda().eq(Member::getMemberId,pageParm.getMemberId());
            query.lambda().orderByDesc(Member::getJoinTime);
            IPage<Member> list = memberService.page(page, query);
            return ResultUtils.success("Searching success", list);
        } else {
            //构造分页对象
            IPage<Member> page = new Page<>(pageParm.getCurrentPage(), pageParm.getPageSize());
            //构造查询条件
            QueryWrapper<Member> query = new QueryWrapper<>();
            if (StringUtils.isNotEmpty(pageParm.getName())) {
                query.lambda().like(Member::getName, pageParm.getName());
            }
            if (StringUtils.isNotEmpty(pageParm.getPhone())) {
                query.lambda().like(Member::getPhone, pageParm.getPhone());
            }
            if (StringUtils.isNotEmpty(pageParm.getUsername())) {
                query.lambda().like(Member::getUsername, pageParm.getUsername());
            }
            query.lambda().orderByDesc(Member::getJoinTime);
            IPage<Member> list = memberService.page(page, query);
            return ResultUtils.success("Searching success", list);
        }
    }

    //Search role id by member id
    @GetMapping("/getRoleByMemberId")
    public ResultVo getRoleByMemberId(Long memberId){
        QueryWrapper<MemberRole> query = new QueryWrapper<>();
        query.lambda().eq(MemberRole::getMemberId,memberId);
        MemberRole one = memberRoleService.getOne(query);
        return ResultUtils.success("Searching success",one);
    }

    //search membership
    @GetMapping("/getCardList")
    public ResultVo getCardList(){
        QueryWrapper<MemberCard> query = new QueryWrapper<>();
        query.lambda().eq(MemberCard::getStatus,"1");
        List<MemberCard> list = memberCardService.list(query);
        return ResultUtils.success("Searching success",list);
    }

    //Purchase membership
    @PostMapping("/joinApply")
    public ResultVo joinApply(@RequestBody JoinParm joinParm) throws ParseException {
        memberService.joinApply(joinParm);
        return  ResultUtils.success("Purchasing success!");
    }

    //recharge
    @PostMapping("/recharge")
    public ResultVo recharge(@RequestBody RechargeParm parm){
        memberService.recharge(parm);
        return ResultUtils.success("Recharging success!");
    }
}
