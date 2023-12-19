package com.ugat77.web.suggest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.suggest.entity.Suggest;
import com.ugat77.web.suggest.mapper.SuggestMapper;
import com.ugat77.web.suggest.service.SuggestService;
import org.springframework.stereotype.Service;

@Service
public class SuggestServiceImpl extends ServiceImpl<SuggestMapper, Suggest> implements SuggestService {
}
