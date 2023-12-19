package com.ugat77.web.equipment.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.equipment.entity.Equipment;
import com.ugat77.web.equipment.mapper.EquipmentMapper;
import com.ugat77.web.equipment.service.EquipmentService;
import org.springframework.stereotype.Service;

@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements EquipmentService {
}
