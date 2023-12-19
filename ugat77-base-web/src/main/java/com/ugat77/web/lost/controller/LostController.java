package com.ugat77.web.lost.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.lost.entity.Lost;
import com.ugat77.web.lost.entity.LostParm;
import com.ugat77.web.lost.service.LostService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lost")
public class LostController {
    @Autowired
    private LostService lostService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody Lost lost){
        if (lostService.save(lost)){
            return ResultUtils.success("Creating success");
        }
        return ResultUtils.error("Creating failed");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody Lost lost){
        if (lostService.updateById(lost)){
            return ResultUtils.success("Editing success");
        }
        return ResultUtils.error("Editing fialed");
    }

    //Delete
    @DeleteMapping("/{lostId}")
    public ResultVo delete(@PathVariable("lostId") Long lostId){
        if (lostService.removeById(lostId)){
           return ResultUtils.success("Deleting success");
        }
        return ResultUtils.error("Deleting failed");
    }

    //List
    @GetMapping("/list")
    public ResultVo list(LostParm parm){
        IPage<Lost> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());

        QueryWrapper<Lost> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getLostName())){
            query.lambda().like(Lost::getLostName, parm.getLostName());
        }
        IPage<Lost> list = lostService.page(page, query);
        return ResultUtils.success("Searching success", list);
    }
}
