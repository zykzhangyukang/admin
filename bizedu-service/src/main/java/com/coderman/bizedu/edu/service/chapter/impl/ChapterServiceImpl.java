package com.coderman.bizedu.edu.service.chapter.impl;

import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.edu.dao.chapter.ChapterDAO;
import com.coderman.bizedu.edu.dto.chapter.ChapterPageDTO;
import com.coderman.bizedu.edu.dto.chapter.ChapterSaveDTO;
import com.coderman.bizedu.edu.dto.chapter.ChapterUpdateDTO;
import com.coderman.bizedu.edu.model.chapter.ChapterModel;
import com.coderman.bizedu.edu.service.chapter.ChapterService;
import com.coderman.bizedu.edu.vo.chapter.ChapterVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 16:56
 */
@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterDAO chapterDAO;

    @Override
    @LogError(value = "课程大章管理")
    public ResultVO<PageVO<List<ChapterVO>>> page(ChapterPageDTO queryVO) {

        Map<String, Object> conditionMap = new HashMap<>(2);
        Integer pageSize = queryVO.getPageSize();
        Integer currentPage = queryVO.getCurrentPage();

        Integer courseId = queryVO.getCourseId();
        if (Objects.isNull(courseId)) {
            return ResultUtil.getWarn("课程id不能为空！");
        }
        conditionMap.put("courseId", courseId);

        String chapterName = queryVO.getChapterName();
        if (StringUtils.isNotBlank(chapterName)) {
            conditionMap.put("chapterName", chapterName);
        }

        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        // 总条数
        Long count = this.chapterDAO.countPage(conditionMap);
        List<ChapterVO> voArrayList = new ArrayList<>();
        if (count > 0) {
            voArrayList = this.chapterDAO.selectPage(conditionMap);
        }

        return ResultUtil.getSuccessPage(ChapterVO.class, PageUtil.getPageVO(count, voArrayList, currentPage, pageSize));
    }

    @Override
    @LogError(value = "更新课程大章")
    public ResultVO<Void> update(@LogErrorParam ChapterUpdateDTO chapterUpdateDTO) {

        Integer chapterId = chapterUpdateDTO.getChapterId();
        String chapterName = chapterUpdateDTO.getChapterName();

        if (Objects.isNull(chapterId)) {
            return ResultUtil.getWarn("课程大章id不能为空！");
        }

        if (StringUtils.isBlank(chapterName) || StringUtils.length(chapterName) < 5 || StringUtils.length(chapterName) > 25) {
            return ResultUtil.getWarn("课程大章标题不能为空！长度限制在 5-25字符内");
        }

        ChapterModel update = new ChapterModel();
        update.setChapterId(chapterId);
        update.setChapterName(chapterName);
        update.setUpdateTime(new Date());
        this.chapterDAO.updateByPrimaryKeySelective(update);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "新增课程大章")
    public ResultVO<Void> save(@LogErrorParam ChapterSaveDTO chapterSaveDTO) {

        String chapterName = chapterSaveDTO.getChapterName();
        Integer courseId = chapterSaveDTO.getCourseId();

        if (Objects.isNull(courseId)) {
            return ResultUtil.getWarn("课程id不能为空！");
        }
        if (StringUtils.isBlank(chapterName) || StringUtils.length(chapterName) < 5 || StringUtils.length(chapterName) > 25) {
            return ResultUtil.getWarn("课程大章标题不能为空！长度限制在 5-25字符内");
        }

        ChapterModel insert = new ChapterModel();
        insert.setCourseId(courseId);
        insert.setChapterName(chapterName);
        insert.setCreateTime(new Date());
        this.chapterDAO.insertSelective(insert);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "根据ID获取大章信息")
    public ResultVO<ChapterVO> selectChapterById(@LogErrorParam Integer chapterId) {

        if (Objects.isNull(chapterId)) {
            return ResultUtil.getWarn("课程大章id不能为空！");
        }

        ChapterModel chapterModel = this.chapterDAO.selectByPrimaryKey(chapterId);
        if (null == chapterModel) {
            return ResultUtil.getWarn("课程大章不存在！");
        }

        ChapterVO chapterVO = new ChapterVO();
        BeanUtils.copyProperties(chapterModel, chapterVO);
        return ResultUtil.getSuccess(ChapterVO.class, chapterVO);
    }
}
