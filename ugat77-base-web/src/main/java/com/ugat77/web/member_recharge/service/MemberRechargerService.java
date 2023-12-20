package com.ugat77.web.member_recharge.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ugat77.web.member.entity.RechargeParmList;
import com.ugat77.web.member_recharge.entity.MemberRecharge;

public interface MemberRechargerService extends IService<MemberRecharge> {
    IPage<MemberRecharge> getRechargeList(RechargeParmList parmList);
    IPage<MemberRecharge> getRechargeByMember(RechargeParmList parmList);
}
