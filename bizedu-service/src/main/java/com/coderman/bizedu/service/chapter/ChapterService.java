package com.coderman.bizedu.service.chapter;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.bizedu.dto.chapter.ChapterPageDTO;
import com.coderman.bizedu.dto.chapter.ChapterSaveDTO;
import com.coderman.bizedu.dto.chapter.ChapterUpdateDTO;
import com.coderman.bizedu.vo.chapter.ChapterVO;

import java.util.List;

/**
 * @author zhangyukang
 */
public interface ChapterService {

    /**
     * 课程大章管理
     *
     * @param chapterPageDTO
     * @return
     */
    ResultVO<PageVO<List<ChapterVO>>> page(ChapterPageDTO chapterPageDTO);

    /**
     * 更新课程大章
     *
     * @param chapterUpdateDTO
     * @return
     */
    ResultVO<Void> update(ChapterUpdateDTO chapterUpdateDTO);


    /**
     * 新增课程大章
     *
     * @param chapterSaveDTO
     * @return
     */
    ResultVO<Void> save(ChapterSaveDTO chapterSaveDTO);

    /**
     * 根据ID获取大章信息
     *
     * @param chapterId
     * @return
     */
    ResultVO<ChapterVO> selectChapterById(Integer chapterId);
}
