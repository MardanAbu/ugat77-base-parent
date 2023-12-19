package com.ugat77.web.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.course.entity.Course;
import com.ugat77.web.course.entity.CourseList;
import com.ugat77.web.course.service.CourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    //Create
    @PostMapping
    public ResultVo add(@RequestBody Course course) {
        if (courseService.save(course)) {
            return ResultUtils.success("Creating success");
        }
        return ResultUtils.error("Creating failed");
    }

    //Edit
    @PutMapping
    public ResultVo edit(@RequestBody Course course) {
        if (courseService.updateById(course)) {
            return ResultUtils.success("Editing success");
        }
        return ResultUtils.error("Editing failed");
    }

    //Delete
    @DeleteMapping("{courseId}")
    public ResultVo delete(@PathVariable("courseId") Long courseId) {
        if (courseService.removeById(courseId)) {
            return ResultUtils.success("Deleting success");
        }
        return ResultUtils.error("Deleting failed");
    }

    //List
    @GetMapping("/list")
    public ResultVo list(CourseList courseList) {
        //构造分页对象
        IPage<Course> page = new Page<>(courseList.getCurrentPage(), courseList.getPageSize());
        //构造查询条件
        QueryWrapper<Course> query = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(courseList.getCourseName())) {
            query.lambda().like(Course::getCourseName, courseList.getCourseName());
        }
        if (StringUtils.isNotEmpty(courseList.getTeacherName())) {
            query.lambda().like(Course::getTeacherName, courseList.getTeacherName());
        }
        IPage<Course> list = courseService.page(page, query);
        return ResultUtils.success("Searching success", list);
    }
}
