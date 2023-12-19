package com.ugat77.web.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ugat77.web.course.entity.Course;
import com.ugat77.web.course.mapper.CourseMapper;
import com.ugat77.web.course.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
}
