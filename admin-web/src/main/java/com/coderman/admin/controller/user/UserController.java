package com.coderman.admin.controller.user;

import com.coderman.admin.dto.user.*;
import com.coderman.admin.service.user.UserService;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.admin.vo.user.UserLoginRespVO;
import com.coderman.admin.vo.user.UserPermissionVO;
import com.coderman.admin.vo.user.UserRoleInitVO;
import com.coderman.admin.vo.user.UserVO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.service.util.IpUtil;
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
import java.util.Objects;

/**
 * @author coderman
 * @Title: 用户模块
 * @date 2022/2/2711:37
 */
@Api(value = "用户管理", tags = "用户管理")
@RestController
@RequestMapping(value = "/auth/user")
public class UserController {

    @Resource
    private UserService userService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户登录")
    @PostMapping(value = "/login")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserLoginRespVO", value = {"realName", "deptCode", "username", "token", "deptName"})
    })
    public ResultVO<UserLoginRespVO> login(@RequestBody UserLoginDTO userLoginDTO) {
        return this.userService.login(userLoginDTO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "切换登录")
    @PostMapping(value = "/switch/login")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserLoginRespVO", value = {"realName", "deptCode", "username", "token", "deptName"})
    })
    public ResultVO<UserLoginRespVO> switchLogin(@RequestBody UserSwitchLoginDTO userSwitchLoginDTO) {
        return this.userService.switchLogin(userSwitchLoginDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户离线消息拉取")
    @PostMapping(value = "/pull/notify")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<List<Object>> pullNotify() {
        return this.userService.pullNotify(Objects.requireNonNull(AuthUtil.getCurrent()).getUserId());
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "刷新会话")
    @PostMapping(value = "/refresh/login")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<String> refreshLogin() {
        return this.userService.refreshLogin(Objects.requireNonNull(AuthUtil.getCurrent()).getToken());
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "注销登录")
    @PostMapping(value = "/logout")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_STRING, value = "用户token")
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> logout(@RequestBody UserLogoutDTO userLogoutDTO) {
        return this.userService.logout(userLogoutDTO.getToken());
    }

    @GetMapping(value = "/info")
    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取菜单权限")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserPermissionVO", value = {"realName", "deptCode", "deptName", "username", "token", "userId", "buttons", "menus", "expiredTime"}),
    })
    public ResultVO<UserPermissionVO> info() {
        return this.userService.info(Objects.requireNonNull(AuthUtil.getCurrent()).getToken());
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "分配角色初始化")
    @GetMapping(value = "/role/init")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserRoleInitVO", value = {"assignedIdList", "roleList", "userId"})
    })
    public ResultVO<UserRoleInitVO> selectUserRoleInit(@RequestParam(value = "userId") Integer userId) {
        return this.userService.selectUserRoleInit(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "分配角色")
    @PutMapping(value = "/role/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateUserRole(@RequestBody UserRoleUpdateDTO userRoleUpdateDTO) {
        return this.userService.updateUserRole(userRoleUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "用户详情")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "用户id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "UserVO", value = {"createTime", "updateTime", "username", "realName", "userStatus", "deptCode", "userId"})
    })
    public ResultVO<UserVO> selectUserById(@RequestParam(value = "userId") Integer userId) {
        return this.userService.selectUserById(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "用户列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "UserVO", value = {"realName", "deptName", "password", "userStatus", "createTime",
                    "updateTime", "userId", "deptCode", "username"})
    })
    public ResultVO<PageVO<List<UserVO>>> page(@RequestBody UserPageDTO queryVO) {
        return this.userService.page(queryVO);
    }


    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新密码")
    @PutMapping(value = "/pwd/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> updateUserPwd(@RequestBody UserPwdUpdateDTO userPwdUpdateDTO) {

        return this.userService.updateUserPwd(userPwdUpdateDTO);
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
        return this.userService.updateEnable(userId);
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
        return this.userService.updateDisable(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "新增用户")
    @PostMapping(value = "/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody UserSaveDTO userSaveDTO) {
        return this.userService.save(userSaveDTO);
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
        return this.userService.delete(userId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新用户")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody UserUpdateDTO userUpdateDTO) {
        return this.userService.update(userUpdateDTO);
    }


}
