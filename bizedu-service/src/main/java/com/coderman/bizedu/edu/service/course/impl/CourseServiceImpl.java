package com.coderman.bizedu.edu.service.course.impl;

import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.dao.course.CourseDAO;
import com.coderman.bizedu.edu.dto.course.CoursePageDTO;
import com.coderman.bizedu.edu.service.course.CourseService;
import com.coderman.bizedu.edu.vo.course.CourseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDAO courseDAO;

    @Override
    public ResultVO<PageVO<List<CourseVO>>> page(CoursePageDTO queryVO) {

        Map<String, Object> conditionMap = new HashMap<>(4);

        Integer pageSize = queryVO.getPageSize();
        Integer currentPage = queryVO.getCurrentPage();

        String courseName = queryVO.getCourseName();
        if(StringUtils.isNotBlank(courseName)){
            conditionMap.put("courseName",courseName);
        }
        String status = queryVO.getStatus();
        if(StringUtils.isNotBlank(status)){
            conditionMap.put("status",status);
        }

        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        // 总条数
        Long count = this.courseDAO.countPage(conditionMap);

        List<CourseVO> courseVOs = new ArrayList<>();
        if (count > 0) {
            courseVOs = this.courseDAO.selectPage(conditionMap);
        }

        return ResultUtil.getSuccessPage(CourseVO.class, PageUtil.getPageVO(count, courseVOs, currentPage, pageSize));
    }
}
