package com.coderman.admin.service.common.impl;

import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.ResultVO;
import com.coderman.admin.dao.dept.DeptDAO;
import com.coderman.admin.model.dept.DeptExample;
import com.coderman.admin.model.dept.DeptModel;
import com.coderman.admin.service.common.DeptService;
import com.coderman.service.anntation.LogError;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author coderman
 * @date 2022/3/1216:15
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptDAO deptDAO;

    @Override
    @LogError(value = "所有部门信息")
    public ResultVO<List<DeptModel>> list() {
        List<DeptModel> deptModels = this.deptDAO.selectByExample(null);
        return ResultUtil.getSuccessList(DeptModel.class, deptModels);
    }

    @Override
    @LogError(value = "根据部门编号查询部门信息")
    public ResultVO<DeptModel> selectDeptByCode(String deptCode) {

        DeptModel deptModel = null;

        DeptExample example = new DeptExample();
        example.createCriteria().andDeptCodeEqualTo(deptCode);
        List<DeptModel> deptModels = this.deptDAO.selectByExample(example);

        if(CollectionUtils.isNotEmpty(deptModels)){

            deptModel =  deptModels.get(0);
        }

        return ResultUtil.getSuccess(DeptModel.class,deptModel);
    }
}
