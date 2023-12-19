package com.ugat77.web.lost.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.lost.entity.Lost;
import com.ugat77.web.lost.mapper.LostMapper;
import com.ugat77.web.lost.service.LostService;
import org.springframework.stereotype.Service;

@Service
public class LostMapperImpl extends ServiceImpl<LostMapper, Lost> implements LostService {
}
