package com.ugat77.web.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.entity.RechargeParm;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper extends BaseMapper<Member> {
    int addMoney(@Param("parm") RechargeParm parm);
}
