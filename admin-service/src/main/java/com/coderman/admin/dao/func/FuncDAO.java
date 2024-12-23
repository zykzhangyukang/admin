package com.coderman.admin.dao.func;

import com.coderman.admin.model.func.FuncExample;
import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.vo.func.FuncExcelVO;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.func.FuncVO;
import com.coderman.admin.vo.func.MenuVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhangyukang
 */
public interface FuncDAO extends BaseDAO<FuncModel, FuncExample> {

    /**
     * 查询
     * @param parentId
     * @return
     */
    Long countChildrenByParentId(@Param(value = "parentId") Integer parentId);

    /**
     * 通过功能key查询功能
     * @param funcKey
     * @return
     */
    FuncModel selectByFuncKey(@Param(value = "funcKey") String funcKey);


    /**
     * 查询所有功能
     *
     * @return
     */
    List<FuncTreeVO> selectAllFuncTreeVO();

    /**
     * 功能列表
     *
     * @return
     */
    List<FuncVO> page(Map<String, Object> conditionMap);


    /**
     * 分页总条数
     * @param conditionMap
     * @return
     */
    Long countPage(Map<String, Object> conditionMap);


    /**
     * 插入返回组件
     *
     * @param insert
     */
    void insertSelectiveReturnKey(FuncModel insert);

    /**
     * 查询功能信息
     * @param funcIdList
     * @return
     */
    List<FuncModel> selectAllByFuncIdList(@Param(value = "funcIdList") Collection<Integer> funcIdList);

    /**
     * 根据用户id获取所有目录
     *
     * @param userId
     * @return
     */
    List<FuncTreeVO> selectAllByUserIdAndFuncType(@Param(value = "userId") Integer userId,@Param(value = "funcType") String funcType);


    /**
     * 根据角色id查询功能列表
     * @param roleId
     * @return
     */
    List<FuncModel> selectByRoleId(@Param(value = "roleId") Integer roleId);

    /**
     * 获取用户所有菜单数据
     *
     * @param userId
     * @return
     */
    List<MenuVO> selectUserAllMenus(@Param(value = "userId") Integer userId);

    /**
     * 获取用户功能按钮
     * @param userId
     * @return
     */
    List<String> selectUserButtons(@Param(value = "userId") Integer userId);

    /**
     * 查询功能导出列表
     * @param conditionMap
     * @return
     */
    List<FuncExcelVO> selectExportList(Map<String, Object> conditionMap);
}
