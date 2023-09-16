package com.coderman.bizedu.controller.edu.course;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.dto.course.CoursePageDTO;
import com.coderman.bizedu.edu.service.course.CourseService;
import com.coderman.bizedu.edu.vo.course.CourseVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "课程管理", tags = "课程管理")
@RestController
@RequestMapping(value = "/edu/course")
public class CourseController {

    @Resource
    private CourseService courseService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "查询课程列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "CourseVO", value = {"updateTime", "courseId", "courseName", "createTime", "creatorId","creatorName"}),
    })
    public ResultVO<PageVO<List<CourseVO>>> page(@RequestBody CoursePageDTO coursePageDTO) {
        return this.courseService.page(coursePageDTO);
    }

}
