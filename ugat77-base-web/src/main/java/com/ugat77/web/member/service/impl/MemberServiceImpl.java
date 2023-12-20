package com.ugat77.web.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.member.entity.JoinParm;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.entity.RechargeParm;
import com.ugat77.web.member.mapper.MemberMapper;
import com.ugat77.web.member.service.MemberService;
import com.ugat77.web.member_apply.entity.MemberApply;
import com.ugat77.web.member_apply.mapper.MemberApplyMapper;
import com.ugat77.web.member_apply.service.MemberApplyService;
import com.ugat77.web.member_card.entity.MemberCard;
import com.ugat77.web.member_card.mapper.MemberCardMapper;
import com.ugat77.web.member_card.service.MemberCardService;
import com.ugat77.web.member_recharge.entity.MemberRecharge;
import com.ugat77.web.member_recharge.service.MemberRechargerService;
import com.ugat77.web.member_role.entity.MemberRole;
import com.ugat77.web.member_role.service.MemberRoleService;
import com.ugat77.web.sys_user.entity.SysUser;
import com.ugat77.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private MemberRoleService memberRoleService;
    @Autowired
    private MemberCardService memberCardService;
    @Autowired
    private MemberApplyService memberApplyService;
    @Autowired
    private MemberRechargerService memberRechargerService;
    @Autowired
    private SysUserService sysUserService;

    @Override
    @Transactional
    public void addMember(Member member) {
        //新增会员
        int insert = this.baseMapper.insert(member);
        //设置会员角色
        if(insert >0){
            MemberRole role = new MemberRole();
            role.setMemberId(member.getMemberId());
            role.setRoleId(member.getRoleId());
            memberRoleService.save(role);
        }
    }

    @Override
    @Transactional
    public void editMember(Member member) {
        int i = this.baseMapper.updateById(member);

        //setting role, Delete first then insert
        if (i > 0) {
            //Delete member_role table
            QueryWrapper<MemberRole> query = new QueryWrapper<>();
            query.lambda().eq(MemberRole::getMemberId, member.getMemberId());
            memberRoleService.remove(query);

            //Insert
            MemberRole role = new MemberRole();
            role.setMemberId(member.getMemberId());
            role.setRoleId(member.getRoleId());
            memberRoleService.save(role);
        }
    }

    @Override
    @Transactional
    public void deleteMember(Long memberId) {
        //Delete
        int i = this.baseMapper.deleteById(memberId);
        if (i > 0) {
            QueryWrapper<MemberRole> query = new QueryWrapper<>();
            query.lambda().eq(MemberRole::getMemberId, memberId);
            memberRoleService.remove(query);
        }
    }

    @Transactional
    @Override
    public void joinApply(JoinParm joinParm) throws ParseException {
        //search member info by id
        Member select = this.baseMapper.selectById(joinParm.getMemberId());
        //Renew member
        MemberCard card = memberCardService.getById(joinParm.getCardId());
        Member member = new Member();
        member.setMemberId((joinParm.getMemberId()));
        member.setCardType(card.getTitle());
        member.setCardDay(card.getCardDay());
        member.setPrice(card.getPrice());
        String endTime = select.getEndTime();
        Calendar calendar = Calendar.getInstance();
        //Check if the end time is empty
        if (StringUtils.isNotEmpty(endTime)) {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(select.getEndTime());
            calendar.setTime(date);
            //endtime, add membership time
            calendar.add(Calendar.DATE, card.getCardDay());
        } else {
            //current date
            Date data = new Date();
            calendar.setTime(data);
            //current date + membership date = end date
            calendar.add(Calendar.DATE, card.getCardDay());
        }
        member.setEndTime(new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()));
        this.baseMapper.updateById(member);
        //插入明细
        MemberApply memberApply = new MemberApply();
        memberApply.setCardDay(card.getCardDay());
        memberApply.setCardType(card.getTitle());
        memberApply.setMemberId(joinParm.getMemberId());
        memberApply.setPrice(card.getPrice());
        memberApply.setCreateTime(new Date());
        memberApplyService.save(memberApply);
    }

    @Override
    @Transactional
    public void recharge(RechargeParm parm) {
        Long userId = parm.getUserId();
        SysUser user = sysUserService.getById(userId);
        //生成充值明细
        MemberRecharge recharge = new MemberRecharge();
        recharge.setMemberId(parm.getMemberId());
        recharge.setMoney(parm.getMoney());
        recharge.setCreateTime(new Date());
        recharge.setCreateUser(user.getName());
        boolean save = memberRechargerService.save(recharge);
        if (save) {
            //Renew member account amount
            this.baseMapper.addMoney(parm);
        }
    }
}
