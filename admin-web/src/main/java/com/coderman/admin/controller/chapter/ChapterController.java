package com.coderman.admin.controller.chapter;

import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.chapter.ChapterPageDTO;
import com.coderman.admin.dto.chapter.ChapterSaveDTO;
import com.coderman.admin.dto.chapter.ChapterUpdateDTO;
import com.coderman.admin.service.chapter.ChapterService;
import com.coderman.admin.vo.chapter.ChapterVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 16:51
 */
@Api(value = "课程大章管理", tags = "课程大章")
@RestController
@RequestMapping(value = "/edu/chapter")
public class ChapterController {

    @Resource
    private ChapterService chapterService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "查询大章列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
    })
    public ResultVO<PageVO<List<ChapterVO>>> page(@RequestBody ChapterPageDTO chapterPageDTO) {
        return this.chapterService.page(chapterPageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新课程大章")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody ChapterUpdateDTO chapterUpdateDTO) {
        return this.chapterService.update(chapterUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "新增课程大章")
    @PostMapping(value = "/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody ChapterSaveDTO chapterSaveDTO) {
        return this.chapterService.save(chapterSaveDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "根据ID获取课程大章")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chapterId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<ChapterVO> selectChapterById(@RequestParam(value = "chapterId") Integer chapterId) {
        return this.chapterService.selectChapterById(chapterId);
    }

}
