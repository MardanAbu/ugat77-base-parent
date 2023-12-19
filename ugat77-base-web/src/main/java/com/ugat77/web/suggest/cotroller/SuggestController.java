package com.ugat77.web.suggest.cotroller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.suggest.entity.Suggest;
import com.ugat77.web.suggest.entity.SuggestParm;
import com.ugat77.web.suggest.service.SuggestService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/suggest")
public class SuggestController {
    @Autowired
    private SuggestService suggestService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody Suggest suggest){
        if (suggestService.save(suggest)) {
            return ResultUtils.success("Creating success");
        }
        return ResultUtils.error("Creating failed");
    }

    //Edit
    public ResultVo edit(@RequestBody Suggest suggest){
        if (suggestService.updateById(suggest)){
            return ResultUtils.success("Editing success");
        }
        return ResultUtils.error("Editing failed");
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResultVo delete(@PathVariable("id") Long id){
        if (suggestService.removeById(id)){
            return ResultUtils.success("Deleting success");
        }
        return ResultUtils.error("Deleting failed");
    }

    //List
    @GetMapping("/list")
    public ResultVo list(SuggestParm parm) {
        IPage<Suggest> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());

        QueryWrapper<Suggest> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getTitle())) {
            query.lambda().like(Suggest::getTitle, parm.getTitle());
        }
        query.lambda().orderByDesc(Suggest::getDateTime);
        IPage<Suggest> list = suggestService.page(page, query);
        return ResultUtils.success("Searching success", list);
    }
}
