package com.coderman.admin.dao.func;

import com.coderman.admin.model.func.FuncRescExample;
import com.coderman.admin.model.func.FuncRescModel;
import com.coderman.admin.vo.func.FuncRescVO;
import com.coderman.mybatis.dao.BaseDAO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FuncRescDAO extends BaseDAO<FuncRescModel, FuncRescExample> {


    /**
     * 批量插入功能资源关联
     * @param funcId
     * @param rescIdList
     */
    void insertBatchByFuncId(@Param(value = "funcId") Integer funcId, @Param(value = "rescIdList") List<Integer> rescIdList);


    /**
     * 查询数量通过资源id
     *
     * @param rescId
     * @return
     */
    Long countByRescId(@Param(value = "rescId") Integer rescId);


    /**
     * 查询数量通过功能id
     * @param funcId
     * @return
     */
    Long countByFuncId(@Param(value = "funcId") Integer funcId);

    /**
     * 根据功能id删除
     *
     * @param funcId
     */
    void deleteByFuncId(@Param(value = "funcId") Integer funcId);

    /**
     * 移除功能对应的资源
     * @param funcId
     * @param rescId
     */
    void deleteByFuncIdAndRescId(@Param(value = "funcId") Integer funcId, @Param(value = "rescId") Integer rescId);

    /**
     * 根据功能id查询资源
     *
     * @param funcIdList
     * @return
     */
    List<FuncRescVO> selectResListByFuncId(@Param(value = "funcIdList") List<Integer> funcIdList);
}
