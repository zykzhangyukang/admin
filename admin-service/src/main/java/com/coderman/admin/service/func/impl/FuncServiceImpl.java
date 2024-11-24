package com.coderman.admin.service.func.impl;

import com.coderman.admin.constant.AuthConstant;
import com.coderman.admin.dao.func.FuncDAO;
import com.coderman.admin.dao.func.FuncRescDAO;
import com.coderman.admin.dao.role.RoleFuncDAO;
import com.coderman.admin.dao.user.UserRoleDAO;
import com.coderman.admin.dto.func.FuncPageDTO;
import com.coderman.admin.dto.func.FuncRescUpdateDTO;
import com.coderman.admin.dto.func.FuncSaveDTO;
import com.coderman.admin.dto.func.FuncUpdateDTO;
import com.coderman.admin.model.func.FuncExample;
import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.model.func.FuncRescExample;
import com.coderman.admin.model.func.FuncRescModel;
import com.coderman.admin.model.role.RoleFuncExample;
import com.coderman.admin.model.role.RoleFuncModel;
import com.coderman.admin.model.user.UserRoleExample;
import com.coderman.admin.model.user.UserRoleModel;
import com.coderman.admin.service.func.FuncService;
import com.coderman.admin.service.log.LogService;
import com.coderman.admin.utils.EasyExcelUtils;
import com.coderman.admin.utils.TreeUtils;
import com.coderman.admin.vo.func.*;
import com.coderman.api.constant.ResultConstant;
import com.coderman.api.util.PageUtil;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.service.dict.ConstItem;
import com.coderman.service.dict.ConstService;
import com.coderman.sync.util.MsgBuilder;
import com.coderman.sync.util.ProjectEnum;
import com.coderman.sync.util.SyncUtil;
import com.coderman.sync.vo.PlanMsg;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author coderman
 * @Title: 功能服务
 * @Description: TOD
 * @date 2022/3/1915:40
 */
@Service
public class FuncServiceImpl implements FuncService {

    @Resource
    private FuncDAO funcDAO;
    @Resource
    private LogService logService;
    @Resource
    private RoleFuncDAO roleFuncDAO;
    @Resource
    private FuncRescDAO funcRescDAO;
    @Resource
    private UserRoleDAO userRoleDAO;


    @Override
    @LogError(value = "获取功能树")
    public List<FuncTreeVO> selectAllFuncTree() {
        List<FuncTreeVO> funcTreeVos = this.funcDAO.selectAllFuncTreeVO();
        return TreeUtils.buildFuncTreeByList(funcTreeVos);
    }

    private Map<String, Object> getCondition(@LogErrorParam FuncPageDTO funcPageDTO) {


        Map<String, Object> conditionMap = new HashMap<>(5);
        String funcName = funcPageDTO.getFuncName();
        String funcKey = funcPageDTO.getFuncKey();
        String funcType = funcPageDTO.getFuncType();
        Integer parentId = funcPageDTO.getParentId();
        Integer hide = funcPageDTO.getHide();
        String rescUrl = funcPageDTO.getRescUrl();
        Date startTime = funcPageDTO.getStartTime();
        Date endTime = funcPageDTO.getEndTime();


        if (StringUtils.isNotBlank(funcName)) {
            conditionMap.put("funcName", funcName.trim());
        }
        if (StringUtils.isNotBlank(funcType)) {
            conditionMap.put("funcType", funcType);
        }
        if (Objects.nonNull(hide)) {
            conditionMap.put("hide", hide);
        }
        if (StringUtils.isNotBlank(funcKey)) {
            conditionMap.put("funcKey", funcKey.trim());
        }
        if (StringUtils.isNotBlank(rescUrl)) {
            conditionMap.put("rescUrl", rescUrl.trim());
        }
        if (Objects.nonNull(parentId)) {
            conditionMap.put("parentId", parentId);
        }
        if (Objects.nonNull(startTime)) {
            conditionMap.put("startTime", startTime);
        }
        if (Objects.nonNull(endTime)) {
            conditionMap.put("endTime", endTime);
        }
        if (CollectionUtils.isNotEmpty(funcPageDTO.getIdList())) {
            conditionMap.put("idList", funcPageDTO.getIdList());
        }
        // 字段排序
        String sortType = funcPageDTO.getSortType();
        String sortField = funcPageDTO.getSortField();
        if (StringUtils.isNotBlank(sortType)) {
            conditionMap.put("sortType", sortType);
            conditionMap.put("sortField", sortField);
        }

        return conditionMap;
    }

    @Override
    @LogError(value = "列表导出")
    public void export(FuncPageDTO funcPageDTO) {

        Map<String, Object> conditionMap = this.getCondition(funcPageDTO);

        Map<Object, String> typeCostMap = ConstService.getConstList(AuthConstant.FUNC_TYPE_GROUP).stream().collect(Collectors.toMap(ConstItem::getCode, ConstItem::getName));
        Map<Object, String> hideCostMap = ConstService.getConstList(AuthConstant.FUNC_HIDE_GROUP).stream().collect(Collectors.toMap(ConstItem::getCode, ConstItem::getName));

        List<FuncExcelVO> funcExcelVOList = this.funcDAO.selectExportList(conditionMap);
        List<Integer> funcIdList = funcExcelVOList.stream().map(FuncExcelVO::getFuncId).distinct().collect(Collectors.toList());
        List<Integer> parentIdList = funcExcelVOList.stream().map(FuncExcelVO::getParentId).distinct().collect(Collectors.toList());

        Map<Integer, FuncModel> parentFuncMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(parentIdList)) {
            List<FuncModel> funcModels = this.funcDAO.selectAllByFuncIdList(parentIdList);
            parentFuncMap = funcModels.stream().collect(Collectors.toMap(FuncModel::getFuncId, e -> e, (k1, k2) -> k2));
        }

        Map<Integer, List<FuncRescVO>> funcRescVoMap = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(funcIdList)) {
            funcRescVoMap = this.funcRescDAO.selectResListByFuncId(funcIdList)
                    .stream().collect(Collectors.groupingBy(FuncRescVO::getFuncId));
        }

        for (FuncExcelVO funcExcelVO : funcExcelVOList) {
            String funcType = typeCostMap.get(funcExcelVO.getFuncType());
            String hide = hideCostMap.get(Integer.parseInt(funcExcelVO.getHide()));
            if (StringUtils.isNotBlank(funcType)) {
                funcExcelVO.setFuncType(funcType);
            }
            if (StringUtils.isNotBlank(hide)) {
                funcExcelVO.setHide(hide);
            }

            String rescUrlList = funcRescVoMap.getOrDefault(funcExcelVO.getFuncId(), Lists.newArrayList()).stream()
                    .map(e -> e.getRescUrl() + "【" + e.getRescName() + "】").collect(Collectors.joining("\n"));
            funcExcelVO.setRescUrlList(rescUrlList);

            if (Objects.nonNull(funcExcelVO.getParentId())) {
                FuncModel parent = parentFuncMap.get(funcExcelVO.getParentId());
                if (parent != null) {
                    funcExcelVO.setParentFuncName(parent.getFuncName());
                    funcExcelVO.setParentFuncKey(parent.getFuncKey());
                }
            }
        }
        EasyExcelUtils.exportExcel(FuncExcelVO.class, funcExcelVOList, "功能列表.xlsx");
    }


    @Override
    @LogError(value = "功能列表")
    public ResultVO<PageVO<List<FuncVO>>> page(@LogErrorParam FuncPageDTO funcPageDTO) {

        Integer currentPage = funcPageDTO.getCurrentPage();
        Integer pageSize = funcPageDTO.getPageSize();
        Map<String, Object> conditionMap = this.getCondition(funcPageDTO);

        List<FuncVO> funcVOList = new ArrayList<>();
        PageUtil.getConditionMap(conditionMap, currentPage, pageSize);

        Long count = this.funcDAO.countPage(conditionMap);
        if (count > 0) {
            funcVOList = this.funcDAO.page(conditionMap);
        }

        // 设置资源列表
        if (CollectionUtils.isNotEmpty(funcVOList)) {
            List<Integer> funcIdList = funcVOList.stream().map(FuncModel::getFuncId).distinct().collect(Collectors.toList());
            Map<Integer, List<FuncRescVO>> funcRescVoMap = this.funcRescDAO.selectResListByFuncId(funcIdList)
                    .stream().collect(Collectors.groupingBy(FuncRescVO::getFuncId));
            for (FuncVO vo : funcVOList) {
                List<FuncRescVO> list = funcRescVoMap.getOrDefault(vo.getFuncId(), new ArrayList<>());
                vo.setRescVOList(list);
            }
        }

        return ResultUtil.getSuccessPage(FuncVO.class, new PageVO<>(count, funcVOList, currentPage, pageSize));
    }

    @Override
    @LogError(value = "新增功能信息")
    public ResultVO<Void> save(@LogErrorParam FuncSaveDTO funcSaveDTO) {

        Integer parentId = funcSaveDTO.getParentId();
        String funcName = funcSaveDTO.getFuncName();
        String funcKey = funcSaveDTO.getFuncKey();
        String funcType = funcSaveDTO.getFuncType();
        Integer funcSort = funcSaveDTO.getFuncSort();
        Integer hide = funcSaveDTO.getHide();

        if (StringUtils.isBlank(funcName) || StringUtils.length(funcName) > 15) {

            return ResultUtil.getWarn("功能名称不能为空，且在15个字符之内！");
        }

        if (StringUtils.isBlank(funcKey) || StringUtils.length(funcKey) > 30) {

            return ResultUtil.getWarn("功能key不能为空,且在30个字符之内！");
        }

        if (StringUtils.isBlank(funcType)) {

            return ResultUtil.getWarn("功能类型不能为空！");
        }

        if (funcSort == null || funcSort < 0 || funcSort > 100) {

            return ResultUtil.getWarn("功能排序不能为空，请输入0-100之间的整数！");
        }

        if (StringUtils.equals(AuthConstant.FUNC_TYPE_DIR, funcType) && Objects.isNull(hide)) {

            return ResultUtil.getWarn("请选择目录是显示还是隐藏！");
        }

        FuncModel funcModel = this.funcDAO.selectByFuncKey(funcKey);

        if (Objects.nonNull(funcModel)) {

            return ResultUtil.getWarn("存在重复功能key！");
        }

        FuncModel insert = new FuncModel();
        insert.setFuncKey(funcKey);
        insert.setFuncName(funcName);
        insert.setCreateTime(new Date());
        insert.setParentId(parentId == null ? 0 : parentId);
        insert.setHide(hide);
        insert.setFuncType(funcType);
        insert.setFuncSort(funcSort);
        insert.setUpdateTime(new Date());
        this.funcDAO.insertSelectiveReturnKey(insert);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_FUNC, AuthConstant.LOG_MODULE_MIDDLE, "新增功能信息");

        PlanMsg build = MsgBuilder.create("insert_admin_sync_func", ProjectEnum.ADMIN, ProjectEnum.SYNC).addIntList("insert_admin_sync_func", Collections.singletonList(insert.getFuncId())).build();
        SyncUtil.sync(build);


        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "更新功能信息")
    public ResultVO<Void> update(@LogErrorParam FuncUpdateDTO funcUpdateDTO) {

        Integer funcId = funcUpdateDTO.getFuncId();
        String funcName = funcUpdateDTO.getFuncName();
        String funcKey = funcUpdateDTO.getFuncKey();
        String funcType = funcUpdateDTO.getFuncType();
        Integer funcSort = funcUpdateDTO.getFuncSort();
        Integer hide = funcUpdateDTO.getHide();

        if (Objects.isNull(funcId)) {

            return ResultUtil.getWarn("功能id不能为空！");
        }

        FuncModel funcModel = this.funcDAO.selectByPrimaryKey(funcId);
        if (null == funcModel) {

            return ResultUtil.getWarn("功能id不能为空！");
        }

        if (StringUtils.isBlank(funcKey) || StringUtils.length(funcKey) > 30) {

            return ResultUtil.getWarn("功能key不能为空,且在30个字符之内！");
        }

        if (StringUtils.isBlank(funcName) || StringUtils.length(funcName) > 15) {

            return ResultUtil.getWarn("功能名称不能为空，且在15个字符之内！");
        }

        if (funcSort == null || funcSort < 0 || funcSort > 100) {

            return ResultUtil.getWarn("功能排序不能为空，请输入0-100之间的整数！");
        }

        if (StringUtils.isBlank(funcType)) {

            return ResultUtil.getWarn("功能类型不能为空！");
        }

        if (AuthConstant.FUNC_TYPE_DIR.equals(funcType) && Objects.isNull(hide)) {

            return ResultUtil.getWarn("请选择目录是显示还是隐藏！");
        }

        // 功能key唯一性校验
        FuncModel dbFuncModel = this.funcDAO.selectByFuncKey(funcKey);

        if (Objects.nonNull(dbFuncModel) && !funcId.equals(dbFuncModel.getFuncId())) {

            return ResultUtil.getWarn("存在重复的功能key！");
        }

        // 更新功能
        FuncModel update = new FuncModel();
        update.setFuncId(funcId);
        update.setFuncKey(funcKey);
        update.setFuncName(funcName);
        update.setFuncType(funcType);
        update.setFuncSort(funcSort);
        update.setHide(hide);
        update.setUpdateTime(new Date());
        this.funcDAO.updateByPrimaryKeySelective(update);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_FUNC, AuthConstant.LOG_MODULE_MIDDLE, "更新功能信息");

        PlanMsg build = MsgBuilder.create("update_admin_sync_func", ProjectEnum.ADMIN, ProjectEnum.SYNC).addIntList("update_admin_sync_func", Collections.singletonList(funcId)).build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "删除功能信息")
    public ResultVO<Void> delete(Integer funcId) {

        if (Objects.isNull(funcId)) {

            return ResultUtil.getWarn("功能id不能为空！");
        }

        FuncModel funcModel = this.funcDAO.selectByPrimaryKey(funcId);
        if (null == funcModel) {

            return ResultUtil.getWarn("功能不存在！");
        }

        // 校验功能是否存在子功能
        Long childrenCount = this.funcDAO.countChildrenByParentId(funcId);
        if (childrenCount > 0) {

            return ResultUtil.getWarn("存在子功能！");
        }

        // 校验是否有功能-资源关联
        Long rescCount = this.funcRescDAO.countByFuncId(funcId);
        if (rescCount > 0) {

            return ResultUtil.getWarn("请先清空绑定的资源！");
        }

        // 校验是否有用户绑定了该功能
        RoleFuncExample roleFuncModelExample = new RoleFuncExample();
        roleFuncModelExample.createCriteria().andFuncIdEqualTo(funcId);
        List<Integer> roleIds = this.roleFuncDAO.selectByExample(roleFuncModelExample).stream().map(RoleFuncModel::getRoleId).distinct().collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(roleIds)) {

            UserRoleExample userRoleModelExample = new UserRoleExample();
            userRoleModelExample.createCriteria().andRoleIdIn(roleIds);
            List<UserRoleModel> userRoleModels = this.userRoleDAO.selectByExample(userRoleModelExample);
            if (!userRoleModels.isEmpty()) {
                return ResultUtil.getWarn("请先解绑用户！");
            }
        }
        // 删除功能
        this.funcDAO.deleteByPrimaryKey(funcId);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_FUNC, AuthConstant.LOG_MODULE_IMPORTANT, "删除功能信息");

        PlanMsg build = MsgBuilder.create("delete_admin_sync_func", ProjectEnum.ADMIN, ProjectEnum.SYNC).addIntList("delete_admin_sync_func", Collections.singletonList(funcId)).build();
        SyncUtil.sync(build);

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "获取功能")
    public ResultVO<FuncVO> selectById(@LogErrorParam Integer funcId) {

        if (Objects.isNull(funcId)) {

            return ResultUtil.getWarn("功能id不能为空！");
        }

        FuncModel funcModel = this.funcDAO.selectByPrimaryKey(funcId);
        if (null == funcModel) {

            return ResultUtil.getWarn("功能不存在！");
        }

        // 查询资源信息
        List<FuncRescVO> rescVOS = this.funcRescDAO.selectResListByFuncId(Collections.singletonList(funcModel.getFuncId()));

        FuncVO funcVO = new FuncVO();
        BeanUtils.copyProperties(funcModel, funcVO);
        funcVO.setRescVOList(rescVOS);
        return ResultUtil.getSuccess(FuncVO.class, funcVO);
    }


    @Override
    @LogError(value = "功能解绑用户")
    public ResultVO<Void> deleteUserBind(@LogErrorParam Integer funcId) {

        // 递归查询出所有的功能id,包括子功能id
        List<Integer> funcIdList = new ArrayList<>();
        ResultVO<Void> resultVO = this.getDeepFuncIds(funcIdList, funcId);

        if (!ResultConstant.RESULT_CODE_200.equals(resultVO.getCode())) {

            return resultVO;
        }

        // 所谓功能解绑用户,即删除所有该功能-角色的绑定
        if (CollectionUtils.isNotEmpty(funcIdList)) {

            this.roleFuncDAO.deleteByFuncIdIn(funcIdList);
        }

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_FUNC, AuthConstant.LOG_MODULE_IMPORTANT, "功能解绑用户");

        return ResultUtil.getSuccess();
    }

    /**
     * 递归查询出所有子功能
     *
     * @param funcIdList 功能id集合
     * @param rootFuncId 父级id
     * @return
     */
    private ResultVO<Void> getDeepFuncIds(List<Integer> funcIdList, Integer rootFuncId) {

        FuncModel rootNode = this.funcDAO.selectByPrimaryKey(rootFuncId);

        if (AuthConstant.FUNC_ROOT_PARENT_ID.equals(rootNode.getParentId())) {

            return ResultUtil.getWarn("不允许解绑最顶级的功能！");
        }

        funcIdList.add(rootFuncId);
        FuncExample example = new FuncExample();
        example.createCriteria().andParentIdEqualTo(rootFuncId);
        List<FuncModel> funcModels = this.funcDAO.selectByExample(example);

        if (!CollectionUtils.isEmpty(funcModels)) {

            for (FuncModel funcModel : funcModels) {

                getDeepFuncIds(funcIdList, funcModel.getFuncId());
            }
        }

        return ResultUtil.getSuccess();
    }


    @Override
    @LogError(value = "功能解绑资源")
    public ResultVO<Void> funcRescRemove(@LogErrorParam Integer funcId) {

        if (Objects.isNull(funcId)) {

            return ResultUtil.getWarn("功能id不能为空！");
        }

        FuncModel funcModel = this.funcDAO.selectByPrimaryKey(funcId);
        if (null == funcModel) {

            return ResultUtil.getWarn("功能不存在");
        }

        // 所谓功能解绑资源,即删除所有该功能-资源的绑定
        this.funcRescDAO.deleteByFuncId(funcId);

        // 记录日志
        this.logService.saveLog(AuthConstant.LOG_MODULE_FUNC, AuthConstant.LOG_MODULE_IMPORTANT, "功能解绑资源");

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "功能绑定资源")
    public ResultVO<Void> updateFuncResc(@LogErrorParam FuncRescUpdateDTO rescBindDTO) {

        Integer funcId = rescBindDTO.getFuncId();
        String type = rescBindDTO.getType();
        Integer rescId = rescBindDTO.getRescId();

        if (StringUtils.isBlank(type)) {
            return ResultUtil.getWarn("参数错误！");
        }
        if (Objects.isNull(funcId)) {
            return ResultUtil.getWarn("资源id不能为空！");
        }

        // 新增
        if ("add".equals(type)) {

            FuncRescExample example = new FuncRescExample();
            example.createCriteria().andFuncIdEqualTo(funcId).andRescIdEqualTo(rescId);
            List<FuncRescModel> funcRescModels = this.funcRescDAO.selectByExample(example);
            if (CollectionUtils.isNotEmpty(funcRescModels)) {
                return ResultUtil.getWarn("资源已绑定，请勿重复操作！");
            }

            this.funcRescDAO.insertBatchByFuncId(funcId, Collections.singletonList(rescId));

        } else if ("remove".equals(type)) {
            // 删除
            this.funcRescDAO.deleteByFuncIdAndRescId(funcId, rescId);
        }

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "根据角色id查询功能列表")
    public List<FuncModel> selectByRoleId(@LogErrorParam Integer roleId) {
        Assert.notNull(roleId, "角色id不能为空！");
        return this.funcDAO.selectByRoleId(roleId);
    }

    @Override
    @LogError(value = "根据功能ids查询功能列表")
    public List<FuncModel> selectAllByFuncIdList(Collection<Integer> funcIdList) {
        Assert.notEmpty(funcIdList, "funcIdList is empty");
        return this.funcDAO.selectAllByFuncIdList(funcIdList);
    }

    @Override
    public List<MenuVO> selectUserMenusTree(List<MenuVO> allMenus) {

        if (CollectionUtils.isEmpty(allMenus)) {
            return Lists.newArrayList();
        }

        List<MenuVO> rootMenus = new ArrayList<>();
        Map<Integer, MenuVO> menuMap = allMenus.stream().collect(Collectors.toMap(MenuVO::getId, menu -> menu));

        for (MenuVO menu : allMenus) {
            if (menu.getParentId() == null || menu.getParentId() == 0) {
                // 如果是根节点，添加到根菜单列表
                rootMenus.add(menu);
            } else {
                // 如果不是根节点，找到其父菜单并添加到其 children 列表中
                MenuVO parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    parentMenu.getChildren().add(menu);
                }
            }
        }
        // 对根菜单和所有子菜单进行排序
        sortMenus(rootMenus);
        return rootMenus;
    }

    @Override
    @LogError(value = "获取用户菜单扁平化")
    public List<MenuVO> selectUserAllMenus(Integer userId) {

        return this.funcDAO.selectUserAllMenus(userId);
    }

    /**
     * 对菜单列表及其子菜单进行递归排序
     *
     * @param menus 菜单列表
     */
    private void sortMenus(List<MenuVO> menus) {
        // 按照 funcSort 字段排序
        menus.sort(Comparator.comparingInt(MenuVO::getSort));

        // 递归排序子菜单
        for (MenuVO menu : menus) {
            if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
                sortMenus(menu.getChildren());
            }
        }
    }

    @Override
    @LogError(value = "获取用户按钮")
    public List<String> selectUserButtons(Integer userId) {
        return this.funcDAO.selectUserButtons(userId);
    }
}
