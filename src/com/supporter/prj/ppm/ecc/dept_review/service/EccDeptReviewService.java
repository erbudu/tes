package com.supporter.prj.ppm.ecc.dept_review.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.ecc.base_info.customer.service.EccCustomerService;
import com.supporter.prj.ppm.ecc.base_info.fund_plan.service.EccFundPlanService;
import com.supporter.prj.ppm.ecc.base_info.owner.service.EccOwnerService;
import com.supporter.prj.ppm.ecc.base_info.partner.service.EccPartnerService;
import com.supporter.prj.ppm.ecc.base_info.product.service.EccProductService;
import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptRevieConDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgReview;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptReviewDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 部门出口管制评审.
 * @date 2019-08-16 18:43:19
 */
@Service
public class EccDeptReviewService {

    @Autowired
    private EccDeptReviewDao eccDeptReviewDao;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private EccService eccService;
    @Autowired
    private EccDeptRevieConDao eccDeptRevieConDao;
    @Autowired
    private EccCustomerService eccCustomerService;
    @Autowired
    private EccFundPlanService eccFundPlanService;
    @Autowired
    private EccOwnerService eccOwnerService;
    @Autowired
    private EccPartnerService eccPartnerService;
    @Autowired
    private EccProductService eccProductService;

    /**
     * 根据主键获取部门出口管制评审.
     *
     * @param deptEccId 主键
     * @return EccDeptReview
     */
    public EccDeptReview get(String deptEccId) {
        return eccDeptReviewDao.get(deptEccId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<EccDeptReview> getGrid(UserProfile user, JqGrid jqGrid, EccDeptReview eccDeptReview) {
        return eccDeptReviewDao.findPage(jqGrid, eccDeptReview);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param deptEccId
     * @return
     */
    public EccDeptReview initEditOrViewPage(String deptEccId) {
        if (StringUtils.isBlank(deptEccId)) {// 新建
            EccDeptReview entity = new EccDeptReview();
            entity.setApplyDate(new Date());
            return entity;
        } else {// 编辑
            EccDeptReview entity = eccDeptReviewDao.get(deptEccId);
            return entity;
        }
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param deptEccId
     * @return
     */
    public EccDeptReview initPageByPrjId(String prjId) {
        if (StringUtils.isBlank(prjId)) {// 新建
            EccDeptReview entity = new EccDeptReview();
            entity.setApplyDate(new Date());
            return entity;
        } else {// 编辑
            EccDeptReview entity = getByPrjId(prjId);
            return entity;
        }
    }

    public EccDeptReview getByPrjId(String prjId) {
        List<EccDeptReview> list = this.eccDeptReviewDao.findBy("projectId", prjId);
        EccDeptReview entity = null;
        if (list != null && list.size() > 0) {
            entity= list.get(0);
        } else {
            entity = new EccDeptReview();
            entity.setDeptEccId(com.supporter.util.UUIDHex.newId());
            entity.setProjectId(prjId);
            entity.setApplyDate(new Date());
            //初始化项目信息
        }
        entity =  initPrjInfo(prjId, entity);
        return entity;
    }

    public EccDeptReview initPrjInfo(String prjId, EccDeptReview entity) {
        Prj prj = this.prjService.initBaseInfoView(prjId);
        entity.setPrjNameC(prj.getPrjCName());
        entity.setPrjNameE(prj.getPrjEName());
        entity.setPrjNo(prj.getPrjNo());
        return entity;
    }

    /**
     * 保存或更新.
     *
     * @param user          用户信息
     * @param eccDeptReview 实体类
     * @return
     */
    public EccDeptReview saveOrUpdate(UserProfile user, EccDeptReview eccDeptReview) {
        eccDeptReview.setModifiedBy(user.getName());
        eccDeptReview.setModifiedById(user.getPersonId());
        eccDeptReview.setModifiedDate(new Date());
        eccDeptReview.setLinkmanName(eccDeptReview.getCreatedBy());
        if (StringUtils.isBlank(eccDeptReview.getDeptEccId())) {// 新建
            return this.save(user, eccDeptReview);
        } else {// 编辑
            return this.update(user, eccDeptReview);
        }
    }

    /**
     * 保存.
     *
     * @param user          用户信息
     * @param eccDeptReview 实体类
     * @return
     */
    private EccDeptReview save(UserProfile user, EccDeptReview eccDeptReview) {
        eccDeptReview.setDeptEccId(com.supporter.util.UUIDHex.newId());
       // eccDeptReview.setCreatedBy(user.getName());
       // eccDeptReview.setCreatedById(user.getPersonId());
        eccDeptReview.setCreatedDate(new Date());
        eccDeptReview.setDeptId(user.getDeptId());
        eccDeptReview.setDeptName(user.getDept() == null ? "" : user.getDept().getName());
        this.eccDeptReviewDao.save(eccDeptReview);
        return eccDeptReview;
    }

    /**
     * 更新.
     *
     * @param user          用户信息
     * @param eccDeptReview 实体类
     * @return
     */
    private EccDeptReview update(UserProfile user, EccDeptReview eccDeptReview) {
        EccDeptReview eccDeptReviewDb = this.eccDeptReviewDao.get(eccDeptReview.getDeptEccId());
        if (eccDeptReviewDb == null) {
            return this.save(user, eccDeptReview);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(eccDeptReview, eccDeptReviewDb);
            this.eccDeptReviewDao.update(eccDeptReviewDb);
            return eccDeptReviewDb;
        }

    }

    /**
     * 流程处理类更新
     *
     * @param eccDeptReview
     * @return
     */
    public EccDeptReview update(EccDeptReview eccDeptReview) {
        if (eccDeptReview == null) {
            return eccDeptReview;
        }
        this.eccDeptReviewDao.update(eccDeptReview);
        return eccDeptReview;
    }

    /**
     * 删除
     *
     * @param user       用户信息
     * @param deptEccIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String deptEccIds) {
        if (StringUtils.isNotBlank(deptEccIds)) {
            for (String deptEccId : deptEccIds.split(",")) {
                EccDeptReview eccDeptReviewDb = this.eccDeptReviewDao.get(deptEccId);
                this.eccDeptReviewDao.delete(deptEccId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param deptEccId
     * @param deptEccName
     * @return
     */
    public boolean nameIsValid(String deptEccId, String deptEccName) {
        return this.eccDeptReviewDao.nameIsValid(deptEccId, deptEccName);
    }

    /**
     * 验证是否需要管制
     *
     * @param prjId
     * @param message
     * @param result
     */
    public Map<String, Object> validEcc(String prjId, String message, boolean result) {
        Prj prj = this.prjService.initBaseInfoView(prjId);
        Map<String, Object> map = new HashMap<String, Object>();
        if (prj != null) {
            //如果项目生效的话继续验证
            if (prj.getStatus() == 1 || prj.getStatus() == 2) {
                List<Ecc> eccs = this.eccService.findBy("prjId", prjId);
                if (eccs != null && eccs.size() > 0 && eccs.get(0).getIsEcc() == Ecc.NEEDECC) {
                    result = true;
                    message = eccs.get(0).getEccId();
                    //获取是否存在业务单据
                    EccDeptReview entity = this.getByPrjId(prjId);
                    if (entity.getDeptEccStatus() == EccDeptReview.DRAFT) {
                        map.put("nextPage", "edit");
                    } else {
                        map.put("nextPage", "view");
                    }
                } else {
                    result = false;
                    message = "该项目不需要管制！";
                }
            } else {
                result = false;
                message = "该项目尚未生效！";
            }
        }

        map.put("msg", message);
        map.put("result", result);
        return map;
    }

    /**
     * 全部删除
     *
     * @param user
     * @param eccId
     */
    public void batchDelAll(UserProfile user, String eccId) {
        //删除初审信息
        this.eccDeptReviewDao.deleteByProperty("eccId", eccId);
        //删除评审结论
        this.eccDeptRevieConDao.deleteByProperty("eccId", eccId);
        //删除客户信息
        this.eccCustomerService.batchDelAll(user, eccId);
        //删除合作伙伴
        this.eccPartnerService.batchDelAll(user, eccId);
        //删除用户信息
        this.eccOwnerService.batchDelAll(user, eccId);
        //删除产品信息
        this.eccProductService.batchDelAll(user, eccId);
        //删除资金安排
        this.eccFundPlanService.batchDelAll(user, eccId);
    }

    //开启部门初审
    public void startEccDept(EccDeptReview entity) {
        update(entity);
        //更新ECC表
        String eccId = entity.getEccId();
        this.eccService.updateEcc(eccId, Ecc.StatusCodeTable.ECCING, "");
    }

    //中止审核
    public void abortEccDept(EccDeptReview entity) {
        update(entity);
        String eccId = entity.getEccId();
        this.eccService.updateEcc(eccId, Ecc.StatusCodeTable.DRAFT, "");

    }

    //审核结束
    public void endEccDept(EccDeptReview entity) {
        update(entity);
        String eccId = entity.getEccId();
        //获取管制结果
        List<EccDeptRevieCon> list = this.eccDeptRevieConDao.findBy("deptEccId", entity.getDeptEccId());
        String result = "";
        int status = 0;
        //代办地址
        String url = "ppm/ecc/dept_review/deptReview_preview_swf_view.html?prjId=" + entity.getProjectId() + "&pageType=Cc&noticeType=";
        //代办标题
        String title = "（知会）公司出口管制专员：出口管制部门初审-"+ entity.getPrjNameC();
        String procId = entity.getProcId();
        if (list != null && list.size() > 0) {
            EccDeptRevieCon con = list.get(0);
            int conStatus = con.getRvConStatus();
            int businessStatus = con.getRvConBussinesStatus();
            if (conStatus == EccDeptRevieCon.pass) {
                if (businessStatus == EccDeptRevieCon.needNext) {
                    result = "部门初审通过，提交公司会商。";
                    status = Ecc.StatusCodeTable.ECCING;
                    url += "ECC_DEPT_WG";
                    //发送知会 给公司出口管制专员
                    EccUtils.sendMessages("ROLE_EXPORTCONTROL", title, url, EccDeptReview.MODULE_ID, procId,"公司出口管制专员");

                } else {
                    title = "（知会）项目开发负责人：出口管制部门初审-"+ entity.getPrjNameC();
                    result = "部门初审通过，不提交公司会商。";
                    status = Ecc.StatusCodeTable.COMPLETE;
                    url += "ECC_DEPT_NEXT";
                    //发送知会 给项目开发负责人
                    //获取项目开发负责人
                    sendMessageToPrjM(entity.getProjectId(),title,url,EccDeptReview.MODULE_ID,procId,"项目开发负责人");
                }
            } else {
                title = "（知会）项目开发负责人：出口管制部门初审-"+ entity.getPrjNameC();
                result = "部门初审不通过。";
                status = Ecc.StatusCodeTable.COMPLETENO;
                url += "ECC_DEPT_CLOSE";
                //获取项目开发负责人
                sendMessageToPrjM(entity.getProjectId(),title,url,EccDeptReview.MODULE_ID,procId,"项目开发负责人");
            }

        }
        this.eccService.updateEcc(eccId, status, result);

    }
    public void sendMessageToPrjM(String prjId,String title,String url,String moduleId,String procId,String nodeName){
        Prj prj = this.prjService.initBaseInfoView(prjId);
        if (prj != null) {
            String personId = EIPService.getEmpService().getEmployee(prj.getCreatedById()).getPersonId();
            //发送知会 给项目开发负责人
            EccUtils.sendMessage(personId, title, url, moduleId, procId,nodeName);
        }
    }
}
