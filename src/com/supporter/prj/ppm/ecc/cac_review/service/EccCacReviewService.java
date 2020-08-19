package com.supporter.prj.ppm.ecc.cac_review.service;

import java.util.*;

import com.supporter.prj.eip.emp.entity.Employee;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IEmployee;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.ecc.cac_review.dao.EccCacRevieConDao;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;
import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptRevieConDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.ecc.wg_review.dao.EccWgRevieConDao;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgReview;
import com.supporter.prj.ppm.ecc.wg_review.service.EccWgReviewService;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.prj.ppm.util.PpmSyncFilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.cac_review.dao.EccCacReviewDao;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 出口管制委员会审核表.
 * @date 2019-08-16 18:45:21
 */
@Service
public class EccCacReviewService {

    @Autowired
    private EccCacReviewDao eccCacReviewDao;
    @Autowired
    private EccCacRevieConDao eccCacRevieConDao;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private EccService eccService;
    @Autowired
    private EccWgRevieConDao wgRevieConDao;
    @Autowired
    private EccDeptReviewService deptReviewService;
    @Autowired
    private EccWgReviewService wgReviewService;

    /**
     * 根据主键获取出口管制委员会审核表.
     *
     * @param eccCacId 主键
     * @return EccCacReview
     */
    public EccCacReview get(String eccCacId) {
        return eccCacReviewDao.get(eccCacId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<EccCacReview> getGrid(UserProfile user, JqGrid jqGrid, EccCacReview eccCacReview) {
        return eccCacReviewDao.findPage(jqGrid, eccCacReview);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param eccCacId
     * @return
     */
    public EccCacReview initEditOrViewPage(UserProfile user, String prjId) {
        if (StringUtils.isBlank(prjId)) {// 新建
            EccCacReview entity = new EccCacReview();
            return entity;
        } else {// 编辑
            EccCacReview entity = getByPrjId(user, prjId);
            return entity;
        }
    }

    public EccCacReview initEditOrViewPageById(String eccCacId) {
        if (StringUtils.isBlank(eccCacId)) {// 新建
            EccCacReview entity = new EccCacReview();
            return entity;
        } else {// 编辑
            EccCacReview entity = get(eccCacId);
            entity = initPrjInfo(entity.getPrjId(), entity);
            //初始化委员详细信息
            if (StringUtils.isNotBlank(entity.getMemberIds())) {
                List<IEmployee> employees = new ArrayList<IEmployee>();
                for (String memberId : entity.getMemberIds().split(",")
                ) {
                    IEmployee emp = EIPService.getEmpService().getEmp(memberId);
                    employees.add(emp);
                }
                entity.setEmployeeList(employees);
            }
            return entity;
        }
    }

    public EccCacReview getByPrjId(UserProfile user, String prjId) {
        List<EccCacReview> list = this.eccCacReviewDao.findBy("prjId", prjId);
        EccCacReview entity = new EccCacReview();
        if (list != null && list.size() > 0) {
            entity = list.get(0);
        } else {
            entity.setPrjId(prjId);
            entity.setEccCacId(com.supporter.util.UUIDHex.newId());
            entity.setApplyDate(new Date());
            EccWgReview wgReview = this.wgReviewService.getByPrjId(null, prjId);
            if (wgReview != null && user != null) {
                //初始化资料清单
                PpmSyncFilesUtil.syncFiles(EccUtils.MODULE_ID, EccUtils.File_BusiType, wgReview.getEccWgId(), prjId,
                        EccUtils.MODULE_ID, EccUtils.File_BusiType, entity.getEccCacId(), prjId, user);
            }
        }
        entity = initPrjInfo(prjId, entity);
        return entity;
    }

    //初始化项目信息
    private EccCacReview initPrjInfo(String prjId, EccCacReview entity) {
        Prj prj = this.prjService.initBaseInfoView(prjId);
        if (prj != null) {
            entity.setPrjCName(prj.getPrjCName());
            entity.setPrjEName(prj.getPrjEName());
            entity.setPrjNo(prj.getPrjNo());
        }
        return entity;
    }

    /**
     * 保存或更新.
     *
     * @param user         用户信息
     * @param eccCacReview 实体类
     * @return
     */
    public EccCacReview saveOrUpdate(UserProfile user, EccCacReview eccCacReview) {
        if (StringUtils.isBlank(eccCacReview.getEccCacId())) {// 新建
            return this.save(user, eccCacReview);
        } else {// 编辑
            return this.update(user, eccCacReview);
        }
    }

    /**
     * 保存.
     *
     * @param user         用户信息
     * @param eccCacReview 实体类
     * @return
     */
    private EccCacReview save(UserProfile user, EccCacReview eccCacReview) {
        eccCacReview.setCreatedBy(user.getName());
        eccCacReview.setCreatedById(user.getPersonId());
        eccCacReview.setCreatedDate(new Date());
        eccCacReview.setModifiedById(user.getPersonId());
        eccCacReview.setModifiedBy(user.getName());
        eccCacReview.setModifiedDate(new Date());
        eccCacReview.setDeptId(user.getDeptId());
        eccCacReview.setDeptName(user.getDept() == null ? "" : user.getDept().getName());
        this.eccCacReviewDao.save(eccCacReview);
        return eccCacReview;
    }

    /**
     * 更新.
     *
     * @param user         用户信息
     * @param eccCacReview 实体类
     * @return
     */
    private EccCacReview update(UserProfile user, EccCacReview eccCacReview) {
        EccCacReview eccCacReviewDb = this.eccCacReviewDao.get(eccCacReview.getEccCacId());
        if (eccCacReviewDb == null) {
            return this.save(user, eccCacReview);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(eccCacReview, eccCacReviewDb);
            eccCacReviewDb.setModifiedById(user.getPersonId());
            eccCacReviewDb.setModifiedBy(user.getName());
            eccCacReviewDb.setModifiedDate(new Date());
            this.eccCacReviewDao.update(eccCacReviewDb);
            return eccCacReviewDb;
        }

    }

    /**
     * 流程类中更新
     *
     * @param entity
     */
    public void update(EccCacReview entity) {
        if (entity != null) {
            this.eccCacReviewDao.update(entity);
        }
    }

    /**
     * 删除
     *
     * @param user      用户信息
     * @param eccCacIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String eccCacIds) {
        if (StringUtils.isNotBlank(eccCacIds)) {
            for (String eccCacId : eccCacIds.split(",")) {
                EccCacReview eccCacReviewDb = this.eccCacReviewDao.get(eccCacId);
                this.eccCacReviewDao.delete(eccCacId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param eccCacId
     * @param eccCacName
     * @return
     */
    public boolean nameIsValid(String eccCacId, String eccCacName) {
        return this.eccCacReviewDao.nameIsValid(eccCacId, eccCacName);
    }

    /**
     * 验证是否需要管制
     *
     * @param prjId
     * @param message
     * @param result
     * @return
     */
    public Map<String, Object> validEcc(String prjId, String message, boolean result) {
        List<Ecc> eccs = this.eccService.findBy("prjId", prjId);
        Map<String, Object> map = new HashMap<String, Object>();
        if (eccs != null && eccs.size() > 0) {
            List<EccWgRevieCon> cons = this.wgRevieConDao.findBy("eccId", eccs.get(0).getEccId());
            if (cons != null && cons.size() > 0) {
                if (cons.get(0).getRvConStatus() == EccDeptRevieCon.pass) {
                    if (cons.get(0).getRvConBussinesStatus() == EccDeptRevieCon.notNeedNext) {
                        result = true;
                        message = eccs.get(0).getEccId();
                        //获取是查看还是编辑页面
                        EccCacReview entity = this.getByPrjId(null, prjId);
                        if (entity.getDeptEccStatus() == EccCacReview.DRAFT) {
                            map.put("nextPage", "edit");
                        } else {
                            map.put("nextPage", "view");
                        }
                    } else {
                        message = "该项目不需要出口管制委员会会商！";
                    }
                } else {
                    message = "出口管制工作组会商未通过！";
                }
            } else {
                message = "该项目尚未初审完成！";
            }
        } else {
            message = "该项目无需管制！";
        }

        map.put("msg", message);
        map.put("result", result);
        return map;
    }

    //开启部门初审
    public void startEccCac(EccCacReview entity) {
        update(entity);
    }

    //中止审核
    public void abortEccCac(EccCacReview entity) {
        update(entity);
    }

    //审核结束
    public void endEccCac(EccCacReview entity) {
        update(entity);
        String eccId = entity.getEccId();
        //获取管制结果
        List<EccCacRevieCon> list = this.eccCacRevieConDao.findBy("eccId", eccId);
        String result = "";
        int status = 1;
        if (list != null && list.size() > 0) {
            EccCacRevieCon con = list.get(0);
            int conStatus = con.getRvConStatus();
            int businessStatus = con.getRvConBussinesStatus();
            int nextPage = con.getRvConNexWf();

            //代办地址
            String url = "ppm/ecc/cac_review/cacReview_swf.html?prjId=" + entity.getPrjId() + "&pageType=swfCc&noticeType=";
            //代办标题
            String title = "（知会）项目开发负责人：出口委员会会商-";
            Prj prj = this.prjService.initBaseInfoView(entity.getPrjId());
            title += prj.getPrjCName();
            if (conStatus == EccDeptRevieCon.pass) {

                status = Ecc.StatusCodeTable.ECCING;
                result += "进入CMEC评审流程。";


            } else {
                result = "出口管制委员会审核不通过。";
                status = Ecc.StatusCodeTable.COMPLETENO;
                //通知项目开发负责人
                //知会项目开发负责人审核不通过
                this.deptReviewService.sendMessageToPrjM(entity.getPrjId(), title, url, EccCacReview.MODULE_ID, entity.getProcId(), "项目开发负责人");
            }
            //获取事业部管制专员id
            // EccDeptReview deptReview = this.deptReviewService.getByPrjId(entity.getPrjId());
            // String createdById = deptReview.getCreatedById();
            //知会
            //EccUtils.sendMessage(createdById, title, url, EccCacReview.MODULE_ID, entity.getProcId());
        }
        this.eccService.updateEcc(eccId, status, result);

    }

    /**
     * 发送知会
     *
     * @param cacEccId
     * @param rvConStatus
     */
    public void sendMessage(String cacEccId, int rvConStatus) {
        EccCacReview review = this.get(cacEccId);
        if (review != null) {
            //代办地址
            String url = "ppm/ecc/cac_review/cacReview_swf.html?prjId=" + review.getPrjId() + "&pageType=swfCc&noticeType=";
            //代办标题
            String title1 = "（知会）事业部管制专员：出口委员会会商-";
            String title2 = "（知会）项目开发负责人：出口委员会会商-";
            String title3 = "（知会）经营管理部外派事业部经理：出口委员会会商-";
            Prj prj = this.prjService.initBaseInfoView(review.getPrjId());
            title1 += prj.getPrjCName();
            title2 += prj.getPrjCName();
            title3 += prj.getPrjCName();

            //获取事业部管制专员id
            EccDeptReview deptReview = this.deptReviewService.getByPrjId(review.getPrjId());
            String createdById = deptReview.getCreatedById();
            //知会 事业部管制专员
            EccUtils.sendMessage(createdById, title1, url, EccCacReview.MODULE_ID, review.getEccCacId(), "");
            //知会经营管理部外派事业部经理
            EccUtils.sendMessages(EccUtils.ROLE_JINGYINGWAIPAI_MANAGER, title3, url, EccCacReview.MODULE_ID,review.getEccCacId(), "");
            //如果通过 发送知会 给静音管理部外派事业部经理 ，事业部出口管制员。不通过再只会给项目开发负责人
            if (rvConStatus != EccCacRevieCon.STATUS_PASS) {
                this.deptReviewService.sendMessageToPrjM(review.getPrjId(), title2, url, EccCacReview.MODULE_ID, review.getEccCacId(), "项目开发负责人");
            }
        }
    }
}

