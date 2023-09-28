package com.coderman.bizedu.service.course.impl;

import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.utils.AuthUtil;
import com.coderman.bizedu.vo.user.AuthUserVO;
import com.coderman.bizedu.constant.CourseConstant;
import com.coderman.bizedu.dao.course.CourseCatalogDAO;
import com.coderman.bizedu.dao.course.CourseDAO;
import com.coderman.bizedu.dto.course.CoursePageDTO;
import com.coderman.bizedu.dto.course.CourseSaveDTO;
import com.coderman.bizedu.dto.course.CourseUpdateDTO;
import com.coderman.bizedu.model.course.CourseModel;
import com.coderman.bizedu.service.catalog.CatalogService;
import com.coderman.bizedu.service.course.CourseService;
import com.coderman.bizedu.vo.catalog.CatalogVO;
import com.coderman.bizedu.vo.course.CourseVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author zhangyukang
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseDAO courseDAO;

    @Resource
    private CourseCatalogDAO courseCatalogDAO;

    @Resource
    private CatalogService catalogService;

    @Override
    @LogError(value = "课程管理列表")
    public ResultVO<PageVO<List<CourseVO>>> page(@LogErrorParam CoursePageDTO queryVO) {

        Map<String, Object> conditionMap = new HashMap<>(4);

        Integer pageSize = queryVO.getPageSize();
        Integer currentPage = queryVO.getCurrentPage();

        String courseName = queryVO.getCourseName();
        if (StringUtils.isNotBlank(courseName)) {
            conditionMap.put("courseName", courseName);
        }
        String status = queryVO.getStatus();
        if (StringUtils.isNotBlank(status)) {
            conditionMap.put("status", status);
        }
        String creatorName = queryVO.getCreatorName();
        if (StringUtils.isNotBlank(creatorName)) {
            conditionMap.put("creatorName", creatorName);
        }
        List<Integer> catalogIdList = queryVO.getCatalogIdList();
        if (CollectionUtils.isNotEmpty(catalogIdList)) {
            conditionMap.put("catalogIdList", catalogIdList);
        }

        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        // 总条数
        Long count = this.courseDAO.countPage(conditionMap);
        List<CourseVO> voArrayList = new ArrayList<>();
        if (count > 0) {
            voArrayList = this.courseDAO.selectPage(conditionMap);
        }

        return ResultUtil.getSuccessPage(CourseVO.class, PageUtil.getPageVO(count, voArrayList, currentPage, pageSize));
    }

    @Override
    @LogError(value = "创建课程")
    public ResultVO<Void> save(@LogErrorParam CourseSaveDTO courseSaveDTO) {

        AuthUserVO current = AuthUtil.getCurrent();
        String courseName = courseSaveDTO.getCourseName();
        String description = courseSaveDTO.getDescription();
        List<Integer> catalogIdList = courseSaveDTO.getCatalogIdList();

        Assert.notNull(current, "currentUser is null");

        if (StringUtils.isBlank(courseName) || StringUtils.length(courseName) < 5 || StringUtils.length(courseName) > 30) {
            return ResultUtil.getWarn("课程标题必填，字数限制5-30字符！");
        }
        if (StringUtils.isBlank(description)) {
            return ResultUtil.getWarn("课程描述信息必填！");
        }
        if(CollectionUtils.isEmpty(catalogIdList) || CollectionUtils.size(catalogIdList) > 3) {

            return ResultUtil.getWarn("课程分类不能为空，且最多选择3个分类！");
        }

        // 保存课程信息
        CourseModel courseModel = new CourseModel();
        courseModel.setCourseName(courseName);
        courseModel.setCreateTime(new Date());
        courseModel.setCreatorId(current.getUserId());
        courseModel.setDescription(description);
        courseModel.setStatus(CourseConstant.COURSE_STATUS_WAIT);
        this.courseDAO.insertSelectiveReturnKey(courseModel);

        // 保存课程分类关系
        if (CollectionUtils.isNotEmpty(catalogIdList)) {
            Map<Integer, CatalogVO> catalogVoMap = this.catalogService.selectCatalogVoMapByIds(catalogIdList);
            for (Integer catalogId : catalogIdList) {
                CatalogVO catalogVO = catalogVoMap.get(catalogId);
                if (Objects.isNull(catalogVO)) {
                    throw new BusinessException("课程分类不存在！【" + catalogId + "】");
                }
            }
            this.courseCatalogDAO.insertBatch(courseModel.getCourseId(), catalogIdList);
        }

        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<Void> updateStatus(CourseUpdateDTO courseUpdateDTO) {

        Integer courseId = courseUpdateDTO.getCourseId();
        String status = courseUpdateDTO.getStatus();

        Assert.notNull(courseId, "courseId is null");
        Assert.notNull(status, "status is null");

        CourseModel courseModel = this.courseDAO.selectByPrimaryKey(courseId);
        Assert.notNull(courseModel, "课程不存在，请刷新页面重试！");
        String oldStatus = courseModel.getStatus();

        // 下架操作
        if (StringUtils.equals(CourseConstant.COURSE_STATUS_DISABLE, status)) {
            if (!StringUtils.equals(CourseConstant.COURSE_STATUS_ENABLE, oldStatus)) {
                return ResultUtil.getWarn("请选择 [已上架] 的记录进行操作！");
            }

        } else if (StringUtils.equals(CourseConstant.COURSE_STATUS_ENABLE, status)) {
            // 上架操作
            if (!StringUtils.equals(CourseConstant.COURSE_STATUS_WAIT, oldStatus) && !StringUtils.equals(CourseConstant.COURSE_STATUS_DISABLE, oldStatus)) {
                return ResultUtil.getWarn("请选择[待发布, 已下架] 的记录进行操作！");
            }
        }else {

            throw new BusinessException("status is error param!");
        }

        CourseModel update = new CourseModel();
        update.setCourseId(courseId);
        update.setStatus(status);
        update.setUpdateTime(new Date());
        this.courseDAO.updateByPrimaryKeySelective(update);

        return ResultUtil.getSuccess();
    }
}
