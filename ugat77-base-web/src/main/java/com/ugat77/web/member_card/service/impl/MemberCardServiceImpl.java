package com.ugat77.web.member_card.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.member_card.entity.MemberCard;
import com.ugat77.web.member_card.mapper.MemberCardMapper;
import com.ugat77.web.member_card.service.MemberCardService;
import org.springframework.stereotype.Service;

@Service
public class MemberCardServiceImpl extends ServiceImpl<MemberCardMapper, MemberCard> implements MemberCardService {
}
