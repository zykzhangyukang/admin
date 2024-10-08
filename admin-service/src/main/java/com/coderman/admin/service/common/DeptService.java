package com.coderman.admin.service.common;


import com.coderman.api.vo.ResultVO;
import com.coderman.admin.model.dept.DeptModel;

import java.util.List;

/**
 * @author coderman
 * @date 2022/3/1216:14
 */
public interface DeptService {

    /**
     * 所有部门信息
     *
     * @return
     */
    ResultVO<List<DeptModel>> list();


    /**
     * 根据部门编号查询部门信息
     *
     * @param deptCode 部门编号
     * @return
     */
    ResultVO<DeptModel> selectDeptByCode(String deptCode);

}
