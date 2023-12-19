package com.ugat77.web.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.member.entity.JoinParm;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.entity.RechargeParm;

import java.text.ParseException;

public interface MemberService extends IService<Member> {
    void addMember(Member member);
    void editMember(Member member);
    void deleteMember(Long memberId);
    //Purchase membership
    void joinApply(JoinParm joinParm) throws ParseException;
    //recharge
    void recharge(RechargeParm parm);
}
