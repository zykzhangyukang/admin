package com.coderman.admin.service.role.impl;

import com.coderman.admin.utils.MsgBuilder;
import com.coderman.admin.utils.ProjectEnum;
import com.coderman.admin.utils.SyncUtil;
import com.coderman.admin.vo.sync.PlanMsg;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.exception.BusinessException;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
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
import com.coderman.admin.model.user.UserRoleModel;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.service.role.RoleService;
import com.coderman.admin.utils.TreeUtils;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.role.RoleFuncCheckVO;
import com.coderman.admin.vo.role.RoleFuncInitVO;
import com.coderman.admin.vo.role.RoleUserInitVO;
import com.coderman.admin.vo.role.RoleVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
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

        Integer currentPage = rolePageDTO.getCurrentPage();
        Integer pageSize = rolePageDTO.getPageSize();
        String roleName = rolePageDTO.getRoleName();

        Map<String, Object> conditionMap = new HashMap<>(1);

        if (StringUtils.isNotBlank(roleName)) {

            conditionMap.put("roleName", roleName);
        }
        // 字段排序
        String sortType = rolePageDTO.getSortType();
        String sortField = rolePageDTO.getSortField();
        String val = StringUtils.EMPTY;
        if (StringUtils.isNotBlank(sortType)) {

            if (StringUtils.equals(sortField, "updateTime")) {
                val = "update_time";
            } else if (StringUtils.equals(sortField, "createTime")) {
                val = "create_time";
            }
            conditionMap.put("sortType", sortType);
            conditionMap.put("sortField", val);
        }

        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        List<RoleVO> rolevos = new ArrayList<>();

        Long count = this.roleDAO.countPage(conditionMap);

        if (count > 0) {

            rolevos = this.roleDAO.page(conditionMap);
        }

        return ResultUtil.getSuccessPage(RoleVO.class, new PageVO<>(count, rolevos, currentPage, pageSize));
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

        // 查询全部角色信息
        List<UserModel> userModelList = this.userDAO.selectByExample(null);
        roleUserInitVO.setUserList(userModelList);

        // 查询角色已有的用户
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<UserRoleModel> userRoleModels = this.userRoleDAO.selectByExample(example);
        List<Integer> roleUserIds = userRoleModels.stream().map(UserRoleModel::getUserId).collect(Collectors.toList());
        roleUserInitVO.setUserIdList(roleUserIds);

        return ResultUtil.getSuccess(RoleUserInitVO.class, roleUserInitVO);
    }

    @Override
    @LogError(value = "角色分配用户")
    public ResultVO<Void> updateRoleUser(Integer roleId, List<Integer> assignedIdList) {

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (roleModel == null) {
            throw new BusinessException("需要分配的角色不存在!");
        }

        // 清空之前的权限
        UserRoleExample example = new UserRoleExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        this.userRoleDAO.deleteByExample(example);
        // 批量新增
        if (CollectionUtils.isNotEmpty(assignedIdList)) {
            this.userRoleDAO.insertBatchByRoleId(roleId, assignedIdList);
        }
        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_ROLE,AuthConstant.LOG_MODULE_IMPORTANT, "角色分配用户");
        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "角色分配功能初始化")
    public ResultVO<RoleFuncInitVO> selectRoleFuncInit(@LogErrorParam String roleIdStr) {

        Integer roleId = null;

        try {
            roleId = Integer.parseInt(roleIdStr);
        } catch (Exception ignored) {
        }

        if (Objects.isNull(roleId)) {

            return ResultUtil.getWarn("角色id不能为空！");
        }

        RoleModel roleModel = this.roleDAO.selectByPrimaryKey(roleId);
        if (Objects.isNull(roleModel)) {

            return ResultUtil.getFail("角色不存在！");
        }

        RoleFuncInitVO roleFuncInitVO = new RoleFuncInitVO();
        Map<Integer, Collection<Integer>> halfCheckedMap = new HashMap<>();
        Map<Integer, Collection<Integer>> allCheckedMap = new HashMap<>();

        // 功能树查询
        ResultVO<List<FuncTreeVO>> listResultVO = this.funcService.listTree();
        if (!ResultConstant.RESULT_CODE_200.equals(listResultVO.getCode())) {

            return ResultUtil.getWarn(listResultVO.getMsg());
        }

        List<FuncTreeVO> treeVoList = listResultVO.getResult();
        if (CollectionUtils.isEmpty(treeVoList)) {

            return ResultUtil.getWarn("暂无可分配的功能！");
        }

        // 查询该角色拥有的功能
        List<Integer> ownerFuncIdList = this.roleFuncDAO.selectAllByRoleId(roleId).stream().map(RoleFuncModel::getFuncId).distinct().collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(ownerFuncIdList)) {
            for (FuncTreeVO funcTreeVO : treeVoList) {

                List<Integer> tempList = new ArrayList<>();
                List<Integer> tempList2 = new ArrayList<>();

                // 半选
                TreeUtils.getDeepFuncIdList(tempList, funcTreeVO);
                if (CollectionUtils.isNotEmpty(tempList)) {
                    Collection<Integer> checkedIdList = CollectionUtils.intersection(tempList, ownerFuncIdList);
                    halfCheckedMap.putIfAbsent(funcTreeVO.getFuncId(), checkedIdList);
                }

                // 全选
                TreeUtils.getAllFuncIdList(tempList2, funcTreeVO);
                if (CollectionUtils.isNotEmpty(tempList2)) {
                    Collection<Integer> checkedIdList = CollectionUtils.intersection(tempList2, ownerFuncIdList);
                    allCheckedMap.putIfAbsent(funcTreeVO.getFuncId(), checkedIdList);
                }
            }
        }

        // 查询拥有该角色的用户
        List<String> nameList = this.roleDAO.selectUserByRoleId(roleId);

        roleFuncInitVO.setUsernameList(nameList);
        roleFuncInitVO.setRoleId(roleModel.getRoleId());
        roleFuncInitVO.setRoleName(roleModel.getRoleName());
        roleFuncInitVO.setRoleDesc(roleModel.getRoleDesc());
        roleFuncInitVO.setCreateTime(roleModel.getCreateTime());
        roleFuncInitVO.setUpdateTime(roleModel.getUpdateTime());
        roleFuncInitVO.setAllTreeList(treeVoList);
        roleFuncInitVO.setHalfCheckedMap(halfCheckedMap);
        roleFuncInitVO.setAllCheckedMap(allCheckedMap);
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

}
