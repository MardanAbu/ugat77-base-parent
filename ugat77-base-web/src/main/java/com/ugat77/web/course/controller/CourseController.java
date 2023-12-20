package com.ugat77.web.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ugat77.utils.ResultUtils;
import com.ugat77.utils.ResultVo;
import com.ugat77.web.course.entity.Course;
import com.ugat77.web.course.entity.CourseList;
import com.ugat77.web.course.entity.PageParm;
import com.ugat77.web.course.service.CourseService;
import com.ugat77.web.member.entity.Member;
import com.ugat77.web.member.service.MemberService;
import com.ugat77.web.member_course.entity.MemberCourse;
import com.ugat77.web.member_course.service.MemberCourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private MemberCourseService memberCourseService;
    @Autowired
    private MemberService memberService;

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

    //报名课程
    @PostMapping("/joinCourse")
    public ResultVo joinCourse(@RequestBody MemberCourse memberCourse){
        //查询是否报名过该课程
        QueryWrapper<MemberCourse> query = new QueryWrapper<>();
        query.lambda().eq(MemberCourse::getCourseId,memberCourse.getCourseId())
                .eq(MemberCourse::getMemberId,memberCourse.getMemberId());
        MemberCourse one = memberCourseService.getOne(query);
        if(one != null){
            return ResultUtils.error("You have registered to this course!");
        }
        //判断余额是否充足
        Course course = courseService.getById(memberCourse.getCourseId());
        Member member = memberService.getById(memberCourse.getMemberId());
        int flag = member.getMoney().compareTo(course.getCoursePrice());
        if(flag == -1){
            return ResultUtils.error("Insufficient found!");
        }
        memberCourseService.joinCourse(memberCourse);
        return ResultUtils.success("Registering success!");
    }

    //我的课程列表
    @GetMapping("/getMyCourseList")
    public ResultVo getMyCourseList(PageParm pageParm){
        if(pageParm.getUserType().equals("1")){ //Member
            IPage<MemberCourse> page  = new Page<>(pageParm.getCurrentPage(),pageParm.getPageSize());
            QueryWrapper<MemberCourse> query = new QueryWrapper<>();
            query.lambda().eq(MemberCourse::getMemberId,pageParm.getUserId());
            IPage<MemberCourse> list = memberCourseService.page(page,query);
            return ResultUtils.success("Searching success",list);
        }else{
            IPage<Course> page = new Page<>(pageParm.getCurrentPage(),pageParm.getPageSize());
            QueryWrapper<Course> query = new QueryWrapper<>();
            query.lambda().eq(Course::getTeacherId,pageParm.getUserId());
            IPage<Course> list = courseService.page(page, query);
            return ResultUtils.success("Searching success",list);
        }
    }
}
