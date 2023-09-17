package com.coderman.bizedu.edu.constant;

import com.coderman.api.anntation.ConstList;
import com.coderman.api.anntation.Constant;

@Constant
public interface CourseConstant {

    /**
     * 课程状态
     */
    String COURSE_STATUS_GROUP = "course_status_group";

    @ConstList(group = COURSE_STATUS_GROUP, name = "正常")
    String COURSE_STATUS_ENABLE = "enable";

    @ConstList(group = COURSE_STATUS_GROUP, name = "禁用")
    String COURSE_STATUS_DISABLE = "disable";
}
