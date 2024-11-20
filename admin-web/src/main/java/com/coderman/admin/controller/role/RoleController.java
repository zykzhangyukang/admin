package com.coderman.admin.controller.role;

import com.coderman.admin.dto.role.*;
import com.coderman.admin.dto.user.UserPageDTO;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.service.role.RoleService;
import com.coderman.admin.vo.role.RoleFuncCheckVO;
import com.coderman.admin.vo.role.RoleFuncInitVO;
import com.coderman.admin.vo.role.RoleUserInitVO;
import com.coderman.admin.vo.role.RoleVO;
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
 * @author coderman
 * @Title: 角色管理
 * @Description: 角色管理控制器
 * @date 2022/2/27 11:55
 */
@Api(value = "角色管理", tags = "角色管理")
@RestController
@RequestMapping(value = "/auth/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "角色分配用户")
    @PutMapping(value = "/user/update")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_INT, value = "角色id"),
            @ApiImplicitParam(name = "userIdList", paramType = SwaggerConstant.PARAM_BODY, dataType = SwaggerConstant.DATA_OBJECT, value = "分配的用户id集合"),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> updateRoleUser(@RequestBody RoleUpdateUserDTO dto) {
        return this.roleService.updateRoleUser(dto.getRoleId(), dto.getUserIdList());
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "角色分配用户初始化")
    @GetMapping(value = "/user/update/init")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_INT, value = "角色id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RoleUserInitVO", value = {"assignedIdList", "userList", "roleId", "userIdList"})
    })
    public ResultVO<RoleUserInitVO> selectRoleUserInit(@RequestParam(value = "roleId") Integer roleId) {
        return this.roleService.selectRoleUserInit(roleId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "角色绑定功能")
    @PutMapping(value = "/func/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
    })
    public ResultVO<Void> updateRoleFunc(@RequestBody RoleFuncUpdateDTO roleFuncUpdateDTO) {
        return this.roleService.updateRoleFunc(roleFuncUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "角色分配功能初始化")
    @GetMapping(value = "/func/update/init")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", paramType = SwaggerConstant.PARAM_FORM, dataType = SwaggerConstant.DATA_INT, value = "角色id", required = true),
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RoleFuncInitVO", value = {"allTreeList", "roleId", "roleName", "roleDesc", "createTime", "updateTime", "funcIdList","userList","funcIdList"})
    })
    public ResultVO<RoleFuncInitVO> selectRoleFuncInit(Integer roleId) {
        return this.roleService.selectRoleFuncInit(roleId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "角色绑定功能预先检查")
    @PostMapping(value = "/func/update/check")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RoleFuncCheckVO", value = {"insertList", "delList"})
    })
    public ResultVO<RoleFuncCheckVO> roleFuncBeforeCheck(@RequestBody RoleFuncUpdateDTO roleFuncUpdateDTO) {
        return this.roleService.roleFuncBeforeCheck(roleFuncUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "角色列表")
    @PostMapping(value = "/page")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "PageVO", value = {"dataList", "pageRow", "totalRow", "currPage", "totalPage"}),
            @ApiReturnParam(name = "RoleVO", value = {"roleDesc", "createTime", "roleId", "roleName", "updateTime","userVOList"})
    })
    public ResultVO<PageVO<List<RoleVO>>> page(@RequestBody RolePageDTO rolePageDTO) {
        return this.roleService.page(rolePageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "列表导出")
    @PostMapping(value = "/export")
    public void export(@RequestBody RolePageDTO rolePageDTO) {
        this.roleService.export(rolePageDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_POST, value = "新增角色")
    @PostMapping(value = "/save")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> save(@RequestBody RoleSaveDTO roleSaveDTO) {
        return this.roleService.save(roleSaveDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_PUT, value = "更新角色")
    @PutMapping(value = "/update")
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> update(@RequestBody RoleUpdateDTO roleUpdateDTO) {
        return this.roleService.update(roleUpdateDTO);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_DELETE, value = "删除角色")
    @DeleteMapping(value = "/delete")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "角色Id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"})
    })
    public ResultVO<Void> delete(@RequestParam(value = "roleId") Integer roleId) {
        return this.roleService.delete(roleId);
    }

    @ApiOperation(httpMethod = SwaggerConstant.METHOD_GET, value = "获取角色")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", paramType = SwaggerConstant.PARAM_PATH, dataType = SwaggerConstant.DATA_INT, value = "角色Id", required = true)
    })
    @ApiReturnParams({
            @ApiReturnParam(name = "ResultVO", value = {"code", "msg", "result"}),
            @ApiReturnParam(name = "RoleVO", value = {"roleName", "roleDesc", "roleId", "updateTime", "createTime"})
    })
    public ResultVO<RoleVO> selectRoleById(@RequestParam(value = "roleId") Integer roleId) {
        return this.roleService.selectRoleById(roleId);
    }
}
