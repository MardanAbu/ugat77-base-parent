package com.ugat77.web.member_recharge.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.member_recharge.entity.MemberRecharge;
import com.ugat77.web.member_recharge.mapper.MemberRechargeMapper;
import com.ugat77.web.member_recharge.service.MemberRechargerService;
import org.springframework.stereotype.Service;

@Service
public class MemberRechargerServiceImpl extends ServiceImpl<MemberRechargeMapper, MemberRecharge> implements MemberRechargerService {
}
