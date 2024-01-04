package com.ugat77.web.history.controller;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.history.entity.History;
import com.ugat77.web.history.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @GetMapping("getCode")
    public ResultVo getCode(Long userId) {
        History history = historyService.getOne(Wrappers.lambdaQuery(History.class).eq(History::getMemberId, userId)
                .eq(History::getCreateTime, LocalDate.now()));
        Map<String, Object> data = new HashMap<>();
        if (history == null) {
            history = new History();
            Digester md5 = new Digester(DigestAlgorithm.MD5);
            history.setCode(md5.digestHex(userId + LocalDateTime.now().toString()));
            history.setMemberId(userId);
            history.setCreateTime(LocalDate.now());
            history.setIsEnter(false);
            historyService.save(history);
        }
        data.put("code", history.getCode());
        data.put("isEnter", history.getIsEnter());
        int count = historyService.count(Wrappers.lambdaQuery(History.class).eq(History::getCreateTime, LocalDate.now()).eq(History::getIsEnter, true));
        data.put("count", count);
        return ResultUtils.success("succeed", data);
    }

    @GetMapping("exit")
    public ResultVo exit(Long userId) {
        History history = historyService.getOne(Wrappers.lambdaQuery(History.class)
                .eq(History::getCreateTime, LocalDate.now())
                .eq(History::getMemberId, userId));
        history.setIsEnter(false);
        historyService.updateById(history);
        return ResultUtils.success("succeed");
    }

    @GetMapping("enter")
    public ResultVo enter(String code) {
        History history = historyService.getOne(Wrappers.lambdaQuery(History.class)
                .eq(History::getCode, code));
        history.setIsEnter(true);
        historyService.updateById(history);
        return ResultUtils.success("succeed");
    }


}

