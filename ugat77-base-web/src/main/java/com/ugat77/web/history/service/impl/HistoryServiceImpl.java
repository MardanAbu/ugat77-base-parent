package com.ugat77.web.history.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.history.entity.History;
import com.ugat77.web.history.mapper.HistoryMapper;
import com.ugat77.web.history.service.HistoryService;
import org.springframework.stereotype.Service;

@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
}
