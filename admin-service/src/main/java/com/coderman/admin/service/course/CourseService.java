package com.coderman.admin.service.course;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.course.CoursePageDTO;
import com.coderman.admin.dto.course.CourseSaveDTO;
import com.coderman.admin.dto.course.CourseUpdateDTO;
import com.coderman.admin.vo.course.CourseVO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface CourseService {

    /**
     * 课程管理列表
     *
     * @param coursePageDTO
     * @return
     */
    ResultVO<PageVO<List<CourseVO>>> page(CoursePageDTO coursePageDTO);


    /**
     * 创建课程
     *
     * @param courseSaveDTO
     * @return
     */
    ResultVO<Void> save(CourseSaveDTO courseSaveDTO);


    /**
     * 更新课程状态
     *
     * @param courseUpdateDTO
     * @return
     */
    ResultVO<Void> updateStatus(CourseUpdateDTO courseUpdateDTO);
}
