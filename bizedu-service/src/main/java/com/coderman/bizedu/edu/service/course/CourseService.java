package com.coderman.bizedu.edu.service.course;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.dto.course.CoursePageDTO;
import com.coderman.bizedu.edu.vo.course.CourseVO;

import java.util.List;

public interface CourseService {

    /**
     * 课程管理列表
     *
     * @param coursePageDTO
     * @return
     */
    ResultVO<PageVO<List<CourseVO>>> page(CoursePageDTO coursePageDTO);
}
