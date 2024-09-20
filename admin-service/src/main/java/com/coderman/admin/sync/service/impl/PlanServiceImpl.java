package com.coderman.admin.sync.service.impl;

import com.coderman.admin.sync.dto.*;
import com.coderman.admin.sync.model.PlanHistoryModel;
import com.coderman.admin.sync.util.XmlFormatter;
import com.coderman.admin.utils.AuthUtil;
import com.coderman.api.constant.CommonConstant;
import com.coderman.api.util.ResultUtil;
import com.coderman.api.vo.PageVO;
import com.coderman.api.vo.ResultVO;
import com.coderman.redis.service.RedisService;
import com.coderman.service.anntation.LogError;
import com.coderman.service.anntation.LogErrorParam;
import com.coderman.service.util.UUIDUtils;
import com.coderman.admin.sync.constant.PlanConstant;
import com.coderman.admin.sync.plan.meta.PlanMeta;
import com.coderman.admin.sync.plan.parser.MetaParser;
import com.coderman.admin.sync.service.PlanService;
import com.coderman.admin.sync.vo.PlanVO;
import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class PlanServiceImpl implements PlanService {

    @Resource
    private JdbcTemplate jdbcTemplate;

    @Resource
    private RedisService redisService;


    @Override
    @LogError(value = "同步计划新增")
    public ResultVO<Void> save(@LogErrorParam PlanSaveDTO planSaveDTO) {

        String planContent = planSaveDTO.getPlanContent();

        if (StringUtils.isBlank(planContent)) {

            return ResultUtil.getWarn("同步计划内容不能为空.");
        }

        String planCode, srcDb, destDb, srcProject, destProject, description;

        try {

            // 解析同步计划
            PlanMeta planMeta = MetaParser.parse(planContent);

            planCode = planMeta.getCode();
            destProject = planMeta.getProjectMeta().getDestProject();
            srcProject = planMeta.getProjectMeta().getSrcProject();
            srcDb = planMeta.getDbMeta().getSrcDb();
            destDb = planMeta.getDbMeta().getDestDb();
            description = planMeta.getName();

        } catch (Exception E) {

            return ResultUtil.getWarn("同步计划解析错误");
        }

        Assert.notNull(planCode, "planCode is null");
        Assert.notNull(destDb, "destDb is null");
        Assert.notNull(srcDb, "srcDb is null");
        Assert.notNull(destProject, "destProject is null");
        Assert.notNull(srcProject, "srcProject is null");
        Assert.notNull(description, "description is null");

        List<PlanVO> planVOByCode = this.getPlanVOByCode(planCode);

        if (CollectionUtils.isNotEmpty(planVOByCode)) {

            return ResultUtil.getWarn("已存在编号为 " + planCode + " 的同步计划");
        }

        String primaryValue = UUIDUtils.getPrimaryValue();

        String sql = "INSERT INTO sync_plan(uuid,plan_code,description,src_db,dest_db,src_project,dest_project,plan_content,status,create_time,update_time) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        int count = jdbcTemplate.update(sql,
                preparedStatement -> {
                    preparedStatement.setString(1, primaryValue);
                    preparedStatement.setString(2, planCode);
                    preparedStatement.setString(3, description);
                    preparedStatement.setString(4, srcDb);
                    preparedStatement.setString(5, destDb);
                    preparedStatement.setString(6, srcProject);
                    preparedStatement.setString(7, destProject);
                    preparedStatement.setString(8, XmlFormatter.toPrettyString(planContent, 2));
                    preparedStatement.setString(9, PlanConstant.STATUS_NORMAL);
                    preparedStatement.setObject(10, new Date());
                    preparedStatement.setObject(11, new Date());

                });

        if (count <= 0) {

            throw new RuntimeException("新增同步计划失败");
        }

        // 记录一下变更记录
        String historySql = "INSERT INTO sync_plan_history (uuid , operation_type , last_content, this_content, username, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(historySql, preparedStatement -> {
            preparedStatement.setString(1, primaryValue);
            preparedStatement.setString(2, "新增");
            preparedStatement.setString(3, StringUtils.EMPTY);
            preparedStatement.setString(4, planContent);
            preparedStatement.setString(5, Objects.requireNonNull(AuthUtil.getCurrent()).getUsername());
            preparedStatement.setObject(6, new Date());
        });

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "同步计划删除")
    public ResultVO<Void> delete(String uuid) {

        List<PlanVO> planVOByUuid = this.getPlanVOByUuid(uuid);

        if (CollectionUtils.isEmpty(planVOByUuid)) {

            return ResultUtil.getWarn("同步计划不存在,请刷新重试!");
        }

        PlanVO planVO = planVOByUuid.get(0);

        if (!StringUtils.equals(planVO.getStatus(), PlanConstant.STATUS_FORBID)) {

            return ResultUtil.getWarn("启用状态不允许删除!");
        }

        List<Object> params = new ArrayList<>();

        params.add(uuid);

        int rowCount = this.jdbcTemplate.update("delete from sync_plan where uuid=?", params.toArray());

        if (rowCount <= 0) {

            return ResultUtil.getWarn("删除同步计划失败!");
        }

        // 删除操作记录
        this.jdbcTemplate.update("delete from sync_plan_history where uuid=?", params.toArray());

        return ResultUtil.getSuccess();
    }

    @Override
    public ResultVO<Void> refreshSyncPlan() {

        this.redisService.sendTopicMessage(PlanConstant.TOPIC_REFRESH_PLAN, "刷新同步计划！");

        return ResultUtil.getSuccess();
    }

    @Override
    @LogError(value = "同步计划更新")
    public ResultVO<Void> update(@LogErrorParam PlanUpdateDTO planUpdateDTO) {

        String uuid = planUpdateDTO.getUuid();
        String planContent = planUpdateDTO.getPlanContent();

        if (StringUtils.isBlank(uuid)) {

            return ResultUtil.getWarn("uuid不能为空");
        }

        if (StringUtils.isBlank(planContent)) {

            return ResultUtil.getWarn("同步计划内容不能为空");
        }

        String planCode, srcDb, destDb, destProject, srcProject, description;

        try {

            // 解析同步计划
            PlanMeta planMeta = MetaParser.parse(planContent);

            planCode = planMeta.getCode();
            srcProject = planMeta.getProjectMeta().getSrcProject();
            destProject = planMeta.getProjectMeta().getDestProject();
            srcDb = planMeta.getDbMeta().getSrcDb();
            destDb = planMeta.getDbMeta().getDestDb();
            description = planMeta.getName();

        } catch (Exception E) {

            return ResultUtil.getWarn("同步计划解析错误");
        }

        Assert.notNull(planCode, "planCode is null");
        Assert.notNull(srcDb, "srcDb is null");
        Assert.notNull(destDb, "destDb is null");
        Assert.notNull(destProject, "destProject is null");
        Assert.notNull(srcProject, "srcProject is null");
        Assert.notNull(description, "description is null");

        List<PlanVO> planVOByCode = this.getPlanVOByCode(planCode);

        if (CollectionUtils.isNotEmpty(planVOByCode)) {

            Optional<PlanVO> optional = planVOByCode.stream().filter(e -> !StringUtils.equals(e.getUuid(), uuid)).findAny();
            if (optional.isPresent()) {

                return ResultUtil.getWarn("已存在编号为 " + planCode + " 的同步计划");
            }
        }

        String sql = "update sync_plan set plan_content=? ,plan_code = ?,description=? ,src_db=?,dest_db=?,src_project=?,dest_project=?,update_time=?  where uuid=?";
        int count = this.jdbcTemplate.update(sql, planContent, planCode, description, srcDb, destDb, srcProject, destProject, new Date(), uuid);

        if (count <= 0) {

            throw new RuntimeException("更新同步计划失败");
        }

        // 记录一下变更记录
        String historySql = "INSERT INTO sync_plan_history (uuid , operation_type , last_content, this_content, username, create_time) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(historySql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, "更新");
            preparedStatement.setString(3, planVOByCode.get(0).getPlanContent());
            preparedStatement.setString(4, planContent);
            preparedStatement.setString(5, Objects.requireNonNull(AuthUtil.getCurrent()).getUsername());
            preparedStatement.setObject(6, new Date());
        });

        return ResultUtil.getSuccess();
    }


    @Override
    @LogError(value = "启用/禁用 同步内容")
    public ResultVO<Void> updateStatus(PlanUpdateStatusDTO planUpdateStatusDTO) {

        String uuid = planUpdateStatusDTO.getUuid();
        String status = planUpdateStatusDTO.getStatus();

        if (StringUtils.isBlank(uuid)) {

            return ResultUtil.getWarn("uuid不能为空");
        }

        if (StringUtils.isBlank(status)) {

            return ResultUtil.getWarn("status不能为空");
        }

        List<PlanVO> list = getPlanVOByUuid(uuid);

        if (CollectionUtils.isEmpty(list)) {

            return ResultUtil.getWarn("同步计划不存在");
        }

        String sql = "update sync_plan set status=?,update_time=? where uuid=?";

        int count = this.jdbcTemplate.update(sql, status, new Date(), uuid);
        if (count <= 0) {

            throw new RuntimeException("更新状态失败");
        }

        return ResultUtil.getSuccess();
    }


    private List<PlanVO> getPlanVOByUuid(String uuid) {

        Assert.isTrue(StringUtils.isNotBlank(uuid), "uuid is null");
        List<Object> params = new ArrayList<>();

        params.add(uuid);
        String sql = "select uuid,plan_code,description,src_db,dest_db,src_project,dest_project,plan_content,status,create_time,update_time,plan_content from sync_plan where uuid=?";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PlanVO.class), params.toArray());
    }


    private List<PlanVO> getPlanVOByCode(String code) {

        Assert.isTrue(StringUtils.isNotBlank(code), "code is null");
        List<Object> params = new ArrayList<>();

        params.add(code);
        String sql = "select uuid,plan_code,src_db,dest_db,src_project,dest_project,plan_content,status,create_time,update_time,plan_content from sync_plan where plan_code=?";
        return this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(PlanVO.class), params.toArray());
    }


    @Override
    @LogError(value = "同步计划变更历史")
    public ResultVO<PageVO<List<PlanHistoryModel>>> selectHistoryPage(PlanHistoryDTO planHistoryDTO) {

        Integer currentPage = planHistoryDTO.getCurrentPage();
        Integer pageSize = planHistoryDTO.getPageSize();
        String uuid = planHistoryDTO.getUuid();

        if (currentPage == null) {
            currentPage = 1;
        }

        if (pageSize == null) {
            pageSize = CommonConstant.SYS_PAGE_SIZE;
        }

        final String countSql = "SELECT COUNT(1) FROM sync_plan_history WHERE uuid = ?";
        Integer count = jdbcTemplate.queryForObject(countSql, Integer.class, uuid);

        if (count == null || count <= 0) {

            return ResultUtil.getSuccessPage(PlanHistoryModel.class, new PageVO<>(0, Collections.emptyList(), currentPage, pageSize));
        }

        List<Object> params = new ArrayList<>();
        params.add((currentPage - 1) * pageSize);
        params.add(pageSize);
        final String querySql = "SELECT uuid, operation_type, last_content, this_content, username, create_time FROM sync_plan_history ORDER BY create_time DESC LIMIT ?,? ";
        List<PlanHistoryModel> list = jdbcTemplate.query(querySql, new BeanPropertyRowMapper<>(PlanHistoryModel.class), params.toArray());

        return ResultUtil.getSuccessPage(PlanHistoryModel.class, new PageVO<>(count, list, currentPage, pageSize));
    }

    /**
     * @param planPageDTO 查询参数
     * @return
     */
    @Override
    @LogError(value = "同步计划列表查询")
    public ResultVO<PageVO<List<PlanVO>>> page(@LogErrorParam PlanPageDTO planPageDTO) {

        Integer currentPage = planPageDTO.getCurrentPage();
        Integer pageSize = planPageDTO.getPageSize();
        String planCode = planPageDTO.getPlanCode();
        String status = planPageDTO.getStatus();
        String srcDb = planPageDTO.getSrcDb();
        String descDb = planPageDTO.getDescDb();
        String sortField = planPageDTO.getSortField();
        String sortOrder = planPageDTO.getSortOrder();
        String srcProject = planPageDTO.getSrcProject();
        String destProject = planPageDTO.getDestProject();
        String description = planPageDTO.getDescription();

        StringBuilder countSql = new StringBuilder("select count(1) ");
        StringBuilder realSql = new StringBuilder("select uuid,plan_code,description,src_db,dest_db,src_project,dest_project,plan_content,status,create_time,update_time,plan_content");
        StringBuilder sql = new StringBuilder(" from sync_plan where 1=1");

        if (currentPage == null) {

            currentPage = 1;
        }

        if (pageSize == null) {

            pageSize = CommonConstant.SYS_PAGE_SIZE;
        }

        // 参数
        List<Object> params = new ArrayList<>();

        if (StringUtils.isNotBlank(planCode)) {

            sql.append(" and plan_code like ?");
            params.add("%" + planCode + "%");
        }

        if (StringUtils.isNotBlank(description)) {

            sql.append(" and description like ?");
            params.add("%" + description + "%");
        }

        if (StringUtils.isNotBlank(status)) {

            sql.append(" and status=?");
            params.add(status);
        }

        if (StringUtils.isNotBlank(srcDb)) {

            sql.append(" and src_db=?");
            params.add(srcDb);
        }

        if (StringUtils.isNotBlank(descDb)) {

            sql.append(" and dest_db=?");
            params.add(descDb);
        }

        if (StringUtils.isNotBlank(srcProject)) {

            sql.append(" and src_project=?");
            params.add(srcProject);
        }

        if (StringUtils.isNotBlank(destProject)) {

            sql.append(" and dest_project=?");
            params.add(destProject);
        }

        // 总条数
        countSql.append(sql);
        Integer count = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.toArray());

        List<PlanVO> list = new ArrayList<>();

        if (Objects.nonNull(count) && count > 0) {

            // 驼峰转下划线
            realSql.append(sql);
            String dbField = StringUtils.EMPTY;

            if (StringUtils.isNotBlank(sortField)) {

                dbField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortField);
            }

            if (StringUtils.equals(dbField, "create_time")) {

                realSql.append(" order by create_time ").append(sortOrder);

            } else if (StringUtils.equals(dbField, "update_time")) {

                realSql.append(" order by update_time ").append(sortOrder);

            } else {
                realSql.append(" order by create_time ").append("desc");
            }

            realSql.append(" limit ?,? ");
            params.add((currentPage - 1) * pageSize);
            params.add(pageSize);
            list = this.jdbcTemplate.query(realSql.toString(), new BeanPropertyRowMapper<>(PlanVO.class), params.toArray());
        }

        Assert.notNull(count, "count is null");
        return ResultUtil.getSuccessPage(PlanVO.class, new PageVO<>(count, list, currentPage, pageSize));
    }

    @Override
    @LogError(value = "查看同步内容")
    public ResultVO<PlanVO> selectPlanByUuid(String uuid) {

        if (StringUtils.isBlank(uuid)) {

            return ResultUtil.getWarn("uuid不能为空");
        }

        List<PlanVO> list = getPlanVOByUuid(uuid);

        if (CollectionUtils.isEmpty(list)) {
            return ResultUtil.getWarn("同步计划不存在");
        }
        PlanVO planVO = list.get(0);

        // 格式化xml
        String planContent = planVO.getPlanContent();
        if(StringUtils.isNotBlank(planContent)){
            String xml = XmlFormatter.toPrettyString(planContent, 2);
            planVO.setPlanContent(xml);
        }

        return ResultUtil.getSuccess(PlanVO.class, planVO);
    }

}