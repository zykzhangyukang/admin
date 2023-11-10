package com.coderman.admin.service.func;


import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dto.func.FuncPageDTO;
import com.coderman.admin.dto.func.FuncRescUpdateDTO;
import com.coderman.admin.dto.func.FuncSaveDTO;
import com.coderman.admin.dto.func.FuncUpdateDTO;
import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.admin.vo.func.FuncVO;

import java.util.Collection;
import java.util.List;

/**
 * @author coderman
 * @Title: 功能服务
 * @Description: TOD
 * @date 2022/3/1915:38
 */
public interface FuncService {

    /**
     * 获取功能树
     *
     * @return
     */
    ResultVO<List<FuncTreeVO>> listTree();



    /**
     * 功能列表
     * @param funcPageDTO 查询参数
     * @return
     */
    ResultVO<PageVO<List<FuncVO>>> page(FuncPageDTO funcPageDTO);


    /**
     * 保存功能
     * @param funcSaveDTO 参数
     * @return
     */
    ResultVO<Void> save(FuncSaveDTO funcSaveDTO);


    /**
     * 更新功能
     * @param funcUpdateDTO
     * @return
     */
    ResultVO<Void> update(FuncUpdateDTO funcUpdateDTO);


    /**
     * 删除功能
     * @param funcId
     * @return
     */
    ResultVO<Void> delete(Integer funcId);


    /**
     * 获取功能
     * @param funcId
     * @return
     */
    ResultVO<FuncVO> selectById(Integer funcId);

    /**
     * 功能解绑用户
     *
     * @param funcId
     * @return
     */
    ResultVO<Void> deleteUserBind(Integer funcId);

    /**
     * 功能解绑资源
     *
     * @param funcId
     * @return
     */
    ResultVO<Void> deleteResourceBind(Integer funcId);


    /**
     * 查询菜单树
     *
     * @param userId
     * @return
     */
    ResultVO<List<FuncTreeVO>> selectMenusTreeByUserId(Integer userId);


    /**
     * 查询功能按钮key
     *
     * @param userId
     * @return
     */
    ResultVO<List<String>> selectFuncKeyListByUserId(Integer userId);


    /**
     * 功能绑定资源
     *
     * @param funcRescUpdateDTO
     * @return
     */
    ResultVO<Void> updateFuncResc(FuncRescUpdateDTO funcRescUpdateDTO);


    /**
     * 根据角色id查询功能列表
     *
     * @param roleId
     * @return
     */
    List<FuncModel> selectByRoleId(Integer roleId);

    /**
     * 根据功能id计划查询功能
     *
     * @param funcIdList
     * @return
     */
    List<FuncModel> selectAllByFuncIdList(Collection<Integer> funcIdList);
}
