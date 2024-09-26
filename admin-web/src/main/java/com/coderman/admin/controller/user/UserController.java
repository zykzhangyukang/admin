package com.coderman.admin.controller.user;

import com.coderman.admin.dto.user.*;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.vo.user.TokenResultVO;
import com.coderman.admin.vo.user.UserRoleInitVO;
import com.coderman.admin.vo.user.UserVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
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
import java.util.Map;

/**
 * @author coderman
 * @Title: 用户模块
 * @date 2022/2/27 11:37
 */
@Api(value = "用户管理", tags = "用户管理")
@RestController
@RequestMapping(value = "/auth/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取用户权限信息")
    @GetMapping(value = "/permission")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Map<String,Object>> getPermissionInfo() {
        return userService.getPermissionInfo();
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取菜单权限")
    @GetMapping(value = "/info")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserVO", value = {"userId","username","realName","deptName","deptCode","roleList","createTime","updateTime","userStatus","password"}),
    })
    public ResultVO<UserVO> info() {
        return userService.getUserInfo();
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "获取访问令牌")
    @PostMapping(value = "/token")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "TokenResultVO", value = {"accessToken","refreshToken","expiresIn"})
    })
    public ResultVO<TokenResultVO> token(@RequestBody UserLoginDTO userLoginDTO) {
        return userService.token(userLoginDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "切换登录")
    @PostMapping(value = "/switch/login")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserLoginRespVO", value = {"realName", "deptCode", "username", "token", "deptName"})
    })
    public ResultVO<TokenResultVO> switchLogin(@RequestBody UserSwitchLoginDTO userSwitchLoginDTO) {
        return userService.switchLogin(userSwitchLoginDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "刷新用户令牌")
    @GetMapping(value = "/refresh/token")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refreshToken", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "用户刷新令牌")
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "TokenResultVO", value = {"expiresIn", "accessToken", "refreshToken"}),
    })
    public ResultVO<TokenResultVO> refreshToken(String refreshToken) {
        return userService.refreshToken(refreshToken);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "注销登录")
    @GetMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "accessToken", paramType = SwaggerConstant.PARAM_QUERY, dataType = SwaggerConstant.DATA_STRING, value = "用户刷新令牌")
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> logout(String accessToken) {
        return userService.logout(accessToken);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "分配角色初始化")
    @GetMapping(value = "/role/update/init")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserRoleInitVO", value = {"assignedIdList", "roleList", "userId"})
    })
    public ResultVO<UserRoleInitVO> selectUserRoleInit(@RequestParam(value = "userId") Integer userId) {
        return userService.selectUserRoleInit(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "分配角色")
    @PutMapping(value = "/role/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateUserRole(@RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        return userService.updateUserRole(userRoleUpdateDTO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "用户分配功能")
    @PutMapping(value = "/func/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateUserFunc(@RequestBody UserFuncUpdateDTO userFuncUpdateDTO) {
        return userService.updateUserFunc(userFuncUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "用户详情")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserVO", value = {"createTime", "updateTime", "username", "realName", "userStatus", "deptCode", "userId","deptId","phone","email"})
    })
    public ResultVO<UserVO> selectUserById(@RequestParam(value = "userId") Integer userId) {
        return userService.selectUserById(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "UserVO", value = {"deptId", "realName", "deptName", "userStatus", "createTime", "updateTime", "userId", "deptCode", "username", "roleList","password","phone","email"})
    })
    public ResultVO<PageVO<List<UserVO>>> page(@RequestBody UserPageDTO queryVO) {
        return userService.page(queryVO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新密码")
    @PutMapping(value = "/pwd/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateUserPwd(@RequestBody UserPwdUpdateDTO userPwdUpdateDTO) {
        return userService.updateUserPwd(userPwdUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "启用用户")
    @PutMapping(value = "/enable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateEnable(@RequestParam(value = "userId") Integer userId) {
        return userService.updateEnable(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "禁用用户")
    @PutMapping(value = "/disable")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateDisable(@RequestParam(value = "userId") Integer userId) {
        return userService.updateDisable(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "新增用户")
    @PostMapping(value = "/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody UserSaveDTO userSaveDTO) {
        return userService.save(userSaveDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_DELETE, value = "删除用户")
    @DeleteMapping(value = "/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> delete(@RequestParam(value = "userId") Integer userId) {
        return userService.delete(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新用户")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(userUpdateDTO);
    }
}
