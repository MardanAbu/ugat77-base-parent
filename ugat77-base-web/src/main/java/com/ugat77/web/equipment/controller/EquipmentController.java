package com.ugat77.web.equipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.equipment.entity.Equipment;
import com.ugat77.web.equipment.entity.ListParm;
import com.ugat77.web.equipment.service.EquipmentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    EquipmentService equipmentService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody Equipment equipment) {
        if (equipmentService.save(equipment)) {
            return ResultUtils.success("Creating success");
        }
        return ResultUtils.error("Creating failed");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody Equipment equipment){
        if (equipmentService.updateById(equipment)){
            return ResultUtils.success("Editing success");
        }
        return ResultUtils.error("Editing failed");
        }

    //Delete
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable("id") Long id){
        if(equipmentService.removeById(id)){
            return ResultUtils.success("Deleting success!");
        }
        return ResultUtils.error("Deleting failed!");
    }

    //List
    @GetMapping("/list")
    public ResultVo list(ListParm parm){
        //构造分页对象
        IPage<Equipment> page = new Page<>(parm.getCurrentPage(),parm.getPageSize());
        //构造查询条件
        QueryWrapper<Equipment> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getName())){
            query.lambda().like(Equipment::getName,parm.getName());
        }
        IPage<Equipment> list = equipmentService.page(page, query);
        return ResultUtils.success("Searching success",list);
    }


}
