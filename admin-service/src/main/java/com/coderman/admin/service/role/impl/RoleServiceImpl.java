package com.coderman.admin.service.role.impl;

import com.coderman.admin.constant.AuthConstant;
import com.coderman.admin.dao.role.RoleDAO;
import com.coderman.admin.dao.role.RoleFuncDAO;
import com.coderman.admin.dao.user.UserDAO;
import com.coderman.admin.dao.user.UserRoleDAO;
import com.coderman.admin.dto.role.RoleFuncUpdateDTO;
import com.coderman.admin.dto.role.RolePageDTO;
import com.coderman.admin.dto.role.RoleSaveDTO;
import com.coderman.admin.dto.role.RoleUpdateDTO;
import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.model.role.RoleFuncExample;
import com.coderman.admin.model.role.RoleFuncModel;
import com.coderman.admin.model.role.RoleModel;
import com.coderman.admin.model.user.UserModel;
import com.coderman.admin.model.user.UserRoleExample;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.service.role.RoleService;
import com.coderman.admin.utils.EasyExcelUtils;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.role.*;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.sync.util.MsgBuilder;
import com.coderman.sync.util.ProjectEnum;
import com.coderman.sync.util.SyncUtil;
import com.coderman.sync.vo.PlanMsg;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author coderman
 * @Title: 角色服务实现
 * @date 2022/2/2711:58
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleDAO roleDAO;
    @Resource
    private LogService logService;
    @Resource
    private UserRoleDAO userRoleDAO;
    @Resource
    private UserDAO userDAO;
    @Resource
    private FuncService funcService;
    @Resource
    private RoleFuncDAO roleFuncDAO;

    @Override
    @LogError(value = "角色列表")
    public ResultVO<PageVO<List<RoleVO>>> page(@LogErrorParam RolePageDTO rolePageDTO) {

        Map<String, Object> conditionMap = this.getCondition(rolePageDTO);

        // 查询条件
        PageUtil.getConditionMap(conditionMap, rolePageDTO.getCurrentPage(), rolePageDTO.getPageSize());

        List<RoleVO> roleVOS = new ArrayList<>();
        Long count = this.roleDAO.countPage(conditionMap);
        if (count > 0) {
            roleVOS = this.roleDAO.page(conditionMap);
        }

        // 查询角色对应的用户列表
        if(CollectionUtils.isNotEmpty(roleVOS)){
            List<Integer> roleIdList = roleVOS.stream().map(RoleModel::getRoleId).distinct().collect(Collectors.toList());
            Map<Integer, List<RoleUserVO>> roleUsersMap = this.userRoleDAO.selectUserListByRoleIds(roleIdList).stream()
                    .collect(Collectors.groupingBy(RoleUserVO::getRoleId));

            for (RoleVO roleVO : roleVOS) {
                List<RoleUserVO> userList = roleUsersMap.getOrDefault(roleVO.getRoleId(), new ArrayList<>());
                roleVO.setUserVOList(userList);
            }
        }

        return ResultUtil.getSuccessPage(RoleVO.class, new PageVO<>(count, roleVOS, rolePageDTO.getCurrentPage(), rolePageDTO.getPageSize()));
    }

    @Override
    @LogError(value = "新增角色信息")
    public ResultVO<Void> save(RoleSaveDTO roleSaveDTO) {

        String roleName = roleSaveDTO.getRoleName();
        String roleDesc = roleSaveDTO.getRoleDesc();
        Date currentDate = new Date();

        if (StringUtils.isBlank(roleName)) {

            return ResultUtil.getWarn("角色名称不能为空！");
        }

        if (StringUtils.isBlank(roleDesc)) {

            return ResultUtil.getWarn("角色描述不能为空！");
        }

        if (StringUtils.length(roleName) > 15) {

            return ResultUtil.getWarn("角色名称最多15个字符！");
        }

        if (StringUtils.length(roleDesc) > 20) {

            return ResultUtil.getWarn("角色描述最多20个字符！");
        }

        // 角色名称唯一性校验
        RoleModel roleModel = this.roleDAO.selectByRoleName(roleName);

        if (Objects.nonNull(roleModel)) {

            return ResultUtil.getFail("存在重复的角色:" + roleName);
        }

        RoleModel insert = new RoleModel();
        insert.setRoleName(roleName);
        insert.setRoleDesc(roleDesc);
        insert.setCreateTime(currentDate);
        insert.setUpdateTime(currentDate);
        this.roleDAO.insertReturnKey(insert);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE, AuthConstant.LOG_MODULE_MIDDLE,"新增角色信息");

        PlanMsg build = MsgBuilder.createOrderlyMsg("insert_admin_sync_role", ProjectEnum.ADMIN, ProjectEnum.SYNC,String.valueOf(insert.getRoleId()))
                .addIntList("insert_admin_sync_role", Collections.singletonList(insert.getRoleId()))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "删除角色信息")
    public ResultVO<Void> delete(Integer roleId) {

        if (Objects.isNull(roleId)) {
            return ResultUtil.getWarn("角色id不能为空！");
        }

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (null == roleModel) {
            return ResultUtil.getWarn("角色不存在！");
        }

        // 查询当前角色是否有关联用户
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        long count = this.userRoleDAO.countByExample(example);
        if (count > 0) {
            return ResultUtil.getWarn("角色已关联用户 ！");
        }

        // 删除角色-功能关联
        RoleFuncExample roleFuncModelExample = new RoleFuncExample();
        roleFuncModelExample.createCriteria().andRoleIdEqualTo(roleId);
        this.roleFuncDAO.deleteByExample(roleFuncModelExample);

        // 删除角色
        this.roleDAO.deleteByPrimaryKey(roleId);
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE, AuthConstant.LOG_MODULE_IMPORTANT, "删除角色信息");

        PlanMsg build = MsgBuilder.createOrderlyMsg("delete_admin_sync_role", ProjectEnum.ADMIN, ProjectEnum.SYNC,String.valueOf(roleId))
                .addIntList("delete_admin_sync_role", Collections.singletonList(roleId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "更新角色信息")
    public ResultVO<Void> update(@LogErrorParam RoleUpdateDTO roleUpdateDTO) {

        Integer roleId = roleUpdateDTO.getRoleId();
        String roleName = roleUpdateDTO.getRoleName();
        String roleDesc = roleUpdateDTO.getRoleDesc();

        if (Objects.isNull(roleId)) {

            return ResultUtil.getWarn("角色id不能为空！");
        }
        if (StringUtils.length(roleName) > 15) {

            return ResultUtil.getWarn("角色名称最多15个字符！");
        }
        if (StringUtils.isBlank(roleName)) {

            return ResultUtil.getWarn("角色名称不能为空！");
        }
        if (StringUtils.length(roleDesc) > 20) {

            return ResultUtil.getWarn("角色描述最多20个字符！");
        }
        if (StringUtils.isBlank(roleDesc)) {

            return ResultUtil.getWarn("角色描述不能为空！");
        }
        RoleModel dbRoleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if(null == dbRoleModel){
            return ResultUtil.getWarn("角色不存在！");
        }

        // 角色名称唯一性校验
        RoleModel roleModel = this.roleDAO.selectByRoleName(roleName);
        if (Objects.nonNull(roleModel) && !Objects.equals(roleModel.getRoleId(), roleId)) {
            return ResultUtil.getWarn("存在重复的角色:" + roleName);
        }

        // 更新角色
        RoleModel update = new RoleModel();
        update.setRoleId(roleId);
        update.setRoleName(roleName);
        update.setRoleDesc(roleDesc);
        update.setUpdateTime(new Date());
        this.roleDAO.updateByPrimaryKeySelective(update);
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE, AuthConstant.LOG_MODULE_MIDDLE, "更新角色信息");

        PlanMsg build = MsgBuilder.create("update_admin_sync_role", ProjectEnum.ADMIN, ProjectEnum.SYNC)
                .addIntList("update_admin_sync_role", Collections.singletonList(roleId))
                .build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "查询角色信息")
    public ResultVO<RoleVO> selectRoleById(Integer roleId) {

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);

        if (null == roleModel) {

            return ResultUtil.getWarn("角色不存在！");
        }

        RoleVO roleVO = new RoleVO();
        roleVO.setRoleDesc(roleModel.getRoleDesc());
        roleVO.setRoleId(roleModel.getRoleId());
        roleVO.setRoleName(roleModel.getRoleName());
        roleVO.setCreateTime(roleModel.getCreateTime());
        roleVO.setUpdateTime(roleModel.getUpdateTime());
        return ResultUtil.getSuccess(RoleVO.class, roleVO);
    }

    @LogError(value = "角色分配用户初始化")
    @Override
    public ResultVO<RoleUserInitVO> selectRoleUserInit(@LogErrorParam Integer roleId) {

        RoleUserInitVO roleUserInitVO = new RoleUserInitVO();

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (roleModel == null) {
            throw new BusinessException("需要分配的角色不存在!");
        }

        roleUserInitVO.setRoleId(roleId);

        // 查询全部用户信息
        List<UserModel> userModelList = this.userDAO.selectByExample(null);
        roleUserInitVO.setUserList(userModelList);

        // 查询角色已有的用户
        List<RoleUserVO> userList = this.userRoleDAO.selectUserListByRoleIds(Collections.singletonList(roleId));
        List<Integer> roleUserIds = userList.stream().map(RoleUserVO::getUserId).collect(Collectors.toList());
        roleUserInitVO.setUserIdList(roleUserIds);

        return ResultUtil.getSuccess(RoleUserInitVO.class, roleUserInitVO);
    }

    @Override
    @LogError(value = "角色分配用户")
    public ResultVO<Void> updateRoleUser(Integer roleId, List<Integer> userIdList) {

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (roleModel == null) {
            throw new BusinessException("需要分配的角色不存在!");
        }

        // 清空之前的权限
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        this.userRoleDAO.deleteByExample(example);
        // 批量新增
        if (CollectionUtils.isNotEmpty(userIdList)) {
            this.userRoleDAO.insertBatchByRoleId(roleId, userIdList);
        }
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE,AuthConstant.LOG_MODULE_IMPORTANT, "角色分配用户");
        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "角色分配功能初始化")
    public ResultVO<RoleFuncInitVO> selectRoleFuncInit(Integer roleId) {

        if (Objects.isNull(roleId)) {

            return ResultUtil.getWarn("角色id不能为空！");
        }

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (Objects.isNull(roleModel)) {

            return ResultUtil.getFail("角色不存在！");
        }

        RoleFuncInitVO roleFuncInitVO = new RoleFuncInitVO();

        // 功能树查询
        List<FuncTreeVO> treeVoList = this.funcService.selectAllFuncTree();
        if (CollectionUtils.isEmpty(treeVoList)) {
            return ResultUtil.getWarn("暂无可分配的功能！");
        }

        // 查询拥有该角色的用户
        List<RoleUserVO> userList = this.userRoleDAO.selectUserListByRoleIds(Collections.singletonList(roleId));

        // 查询角色拥有的功能
        List<RoleFuncModel> roleFuncModels = this.roleFuncDAO.selectAllByRoleId(roleId);
        List<Integer> funcIdList = roleFuncModels.stream().map(RoleFuncModel::getFuncId).distinct().collect(Collectors.toList());

        roleFuncInitVO.setUserList(userList);
        roleFuncInitVO.setRoleId(roleModel.getRoleId());
        roleFuncInitVO.setRoleName(roleModel.getRoleName());
        roleFuncInitVO.setRoleDesc(roleModel.getRoleDesc());
        roleFuncInitVO.setCreateTime(roleModel.getCreateTime());
        roleFuncInitVO.setUpdateTime(roleModel.getUpdateTime());
        roleFuncInitVO.setAllTreeList(treeVoList);
        roleFuncInitVO.setFuncIdList(funcIdList);
        return ResultUtil.getSuccess(RoleFuncInitVO.class, roleFuncInitVO);
    }

    @Override
    @LogError(value = "角色分配功能")
    public ResultVO<Void> updateRoleFunc(@LogErrorParam RoleFuncUpdateDTO roleFuncUpdateDTO) {

        Integer roleId = null;

        try {
            roleId = Integer.parseInt(roleFuncUpdateDTO.getRoleId());
        } catch (Exception ignored) {
        }

        if (Objects.isNull(roleId)) {
            return ResultUtil.getWarn("角色id不能为空！");
        }

        List<Integer> funcIdList = roleFuncUpdateDTO.getFuncIdList();

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (null == roleModel) {

            return ResultUtil.getWarn("角色不存在！");
        }

        // 删除之前该角色拥有的功能
        this.roleFuncDAO.deleteByRoleId(roleId);

        // 插入角色-功能关联
        if (CollectionUtils.isNotEmpty(funcIdList)) {

            this.roleFuncDAO.batchInsertByRoleId(roleId, funcIdList);
        }
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE,AuthConstant.LOG_MODULE_IMPORTANT, "角色分配功能");

        return ResultUtil.getSuccess();
    }


    @Override
    @LogError(value = "角色分配功能预先检查")
    public ResultVO<RoleFuncCheckVO> roleFuncBeforeCheck(RoleFuncUpdateDTO roleAuthorizedDTO) {

        Integer roleId = null;
        try {
            roleId = Integer.parseInt(roleAuthorizedDTO.getRoleId());
        } catch (Exception ignored) {
        }

        List<Integer> funcIdList = roleAuthorizedDTO.getFuncIdList();
        if(null == roleId){
            return ResultUtil.getWarn("角色id不能为空！");
        }

        // 本次需要分配的功能查出来
        List<Integer> needAuthFuncIdList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(funcIdList)) {

            List<FuncModel> models = this.funcService.selectAllByFuncIdList(funcIdList);
            needAuthFuncIdList = models.stream().map(FuncModel::getFuncId).distinct().collect(Collectors.toList());
        }

        // 查出该角色原本有的功能
        List<FuncModel> models = this.funcService.selectByRoleId(roleId);
        List<Integer> historyAuthFuncIdList = models.stream().map(FuncModel::getFuncId).distinct().collect(Collectors.toList());

        // 取交集
        Collection<Integer> intersection = CollectionUtils.intersection(needAuthFuncIdList, historyAuthFuncIdList);
        // 新增的
        Collection<Integer> addList = CollectionUtils.subtract(needAuthFuncIdList, intersection);
        // 删除的
        Collection<Integer> delList = CollectionUtils.subtract(historyAuthFuncIdList, intersection);

        List<FuncModel> addListModels = new ArrayList<>();
        List<FuncModel> delListModels = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(addList)) {
            addListModels = this.funcService.selectAllByFuncIdList(addList);
        }
        if (CollectionUtils.isNotEmpty(delList)) {
            delListModels = this.funcService.selectAllByFuncIdList(delList);
        }

        RoleFuncCheckVO checkVO = new RoleFuncCheckVO();
        checkVO.setInsertList(addListModels);
        checkVO.setDelList(delListModels);
        return ResultUtil.getSuccess(RoleFuncCheckVO.class, checkVO);
    }

    private Map<String, Object> getCondition(@LogErrorParam RolePageDTO rolePageDTO) {

        Map<String, Object> conditionMap = new HashMap<>(3);

        if (StringUtils.isNotBlank(rolePageDTO.getRoleName())) {
            conditionMap.put("roleName", rolePageDTO.getRoleName());
        }
        // 字段排序
        String sortType = rolePageDTO.getSortType();
        String sortField = rolePageDTO.getSortField();
        if (StringUtils.isNotBlank(sortType) && StringUtils.isNotBlank(sortField)) {
            conditionMap.put("sortField", sortField);
            conditionMap.put("sortType", sortType);
        }
        return conditionMap;
    }

    @Override
    @LogError(value = "角色列表导出")
    public void export(RolePageDTO rolePageDTO) {
        // 构建条件
        Map<String, Object> conditionMap = this.getCondition(rolePageDTO);
        // 查询
        List<RoleExcelVO> roleExcelVos = this.roleDAO.selectExportList(conditionMap);
        // 导出excel
        EasyExcelUtils.exportExcel(RoleExcelVO.class, roleExcelVos, "角色列表.xlsx");
    }

}
