package com.ugat77.web.member_apply.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.member_apply.entity.MemberApply;
import com.ugat77.web.member_apply.mapper.MemberApplyMapper;
import com.ugat77.web.member_apply.service.MemberApplyService;
import org.springframework.stereotype.Service;

@Service
public class MemberApplyServiceImpl extends ServiceImpl<MemberApplyMapper, MemberApply> implements MemberApplyService {
}
