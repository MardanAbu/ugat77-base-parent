package com.ugat77.web.member_card.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.member_card.entity.ListCard;
import com.ugat77.web.member_card.entity.MemberCard;
import com.ugat77.web.member_card.service.MemberCardService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/memberCard")
public class MemberCardController {
    @Autowired
    private MemberCardService memberCardService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody MemberCard memberCard){
        if (memberCardService.save(memberCard)){
            return ResultUtils.success("Creating success");
        }
        return ResultUtils.error("Creating failed");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody MemberCard memberCard){
        if (memberCardService.updateById(memberCard)){
            return ResultUtils.success("Editing success");
        }
        return ResultUtils.error("Editing failed");
    }

    //Delete
    @DeleteMapping("/{cardId}")
    public ResultVo delete(@PathVariable("cardId") Long cardId){
        if (memberCardService.removeById(cardId)){
            return ResultUtils.success("Deleting success");
        }
        return ResultUtils.error("Deleting failed");
    }

    //List
    @GetMapping("/list")
    public ResultVo list(ListCard listCard){
        //构造分页对象
        IPage<MemberCard> page = new Page<>(listCard.getCurrentPage(), listCard.getPageSize());
        //构造查询条件
        QueryWrapper<MemberCard> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(listCard.getTitle())){
            query.lambda().like(MemberCard::getTitle, listCard.getTitle());
        }
        IPage<MemberCard> list = memberCardService.page(page, query);
        return ResultUtils.success("Searching success", list);
    }
}
