package com.coderman.admin.controller.sync;

import com.coderman.admin.utils.MsgBuilder;
import com.coderman.admin.utils.ProjectEnum;
import com.coderman.admin.utils.SyncUtil;
import com.coderman.admin.vo.sync.PlanMsg;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.swagger.annotation.ApiReturnParam;
import com.coderman.swagger.annotation.ApiReturnParams;
import com.coderman.swagger.constant.SwaggerConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * @author ：zhangyukang
 * @date ：2024/06/17 12:26
 */
@Api(value = "同步测试", tags = "同步测试")
@RestController
@RequestMapping(value = "/sync/test")
public class TestController {

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "同步测试")
    @GetMapping(value = "")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> test() {
        PlanMsg build = MsgBuilder.create("update_admin_sync_update_resc", ProjectEnum.ADMIN, ProjectEnum.ADMIN_SYNC)
                .addIntList("update_admin_sync_update_resc", Collections.singletonList(1))
                .build();
        SyncUtil.sync(build);
        return ResultUtil.getSuccess();
    }

}
