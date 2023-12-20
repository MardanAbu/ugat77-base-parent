package com.ugat77.web.member_recharge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.member.entity.RechargeParmList;
import com.ugat77.web.member_recharge.entity.MemberRecharge;
import com.ugat77.web.member_recharge.mapper.MemberRechargeMapper;
import com.ugat77.web.member_recharge.service.MemberRechargerService;
import org.springframework.stereotype.Service;

@Service
public class MemberRechargerServiceImpl extends ServiceImpl<MemberRechargeMapper, MemberRecharge> implements MemberRechargerService {
    @Override
    public IPage<MemberRecharge> getRechargeList(RechargeParmList parmList) {
        //构造分页对象
        IPage<MemberRecharge> page = new Page<>(parmList.getCurrentPage(),parmList.getPageSize());
        return this.baseMapper.getRechargeList(page);
    }

    @Override
    public IPage<MemberRecharge> getRechargeByMember(RechargeParmList parmList) {
        //构造分页对象
        IPage<MemberRecharge> page = new Page<>(parmList.getCurrentPage(),parmList.getPageSize());
        return this.baseMapper.getRechargeByMember(page,parmList.getMemberId());
    }
}
