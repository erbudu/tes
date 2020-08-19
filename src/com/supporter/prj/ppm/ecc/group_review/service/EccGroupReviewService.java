package com.supporter.prj.ppm.ecc.group_review.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.ecc.cac_review.dao.EccCacRevieConDao;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacRevieCon;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.ppm.ecc.cac_review.service.EccCacReviewService;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.group_review.dao.EccGroupRevieConDao;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupRevieCon;
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

import com.supporter.prj.ppm.ecc.group_review.dao.EccGroupReviewDao;
import com.supporter.prj.ppm.ecc.group_review.entity.EccGroupReview;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 集团管制审核表.
 * @date 2019-08-16 18:46:29
 */
@Service
public class EccGroupReviewService {

    @Autowired
    private EccGroupReviewDao eccGroupReviewDao;
    @Autowired
    private EccGroupRevieConDao eccGroupRevieConDao;
    @Autowired
    private BaseInfoService prjService;
    @Autowired
    private EccService eccService;
    @Autowired
    private EccCacRevieConDao cacRevieConDao;
    @Autowired
    private EccWgRevieConDao wgRevieConDao;
    @Autowired
    private EccDeptReviewService deptReviewService;
    @Autowired
    private EccCacReviewService cacReviewService;
    @Autowired
    private EccWgReviewService wgReviewService;
    /**
     * 根据主键获取集团管制审核表.
     *
     * @param eccGroupId 主键
     * @return EccGroupReview
     */
    public EccGroupReview get(String eccGroupId) {
        return eccGroupReviewDao.get(eccGroupId);
    }

    /**
     * 分页表格展示数据.
     *
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<EccGroupReview> getGrid(UserProfile user, JqGrid jqGrid, EccGroupReview eccGroupReview) {
        return eccGroupReviewDao.findPage(jqGrid, eccGroupReview);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param eccGroupId
     * @return
     */
    public EccGroupReview initEditOrViewPage(UserProfile user,String prjId) {
        if (StringUtils.isBlank(prjId)) {// 新建
            EccGroupReview entity = new EccGroupReview();
            return entity;
        } else {// 编辑
            EccGroupReview entity = getByPrjId(user,prjId);
            return entity;
        }
    }

    public EccGroupReview getByPrjId(UserProfile user,String prjId) {
        List<EccGroupReview> list = this.eccGroupReviewDao.findBy("prjId", prjId);
        EccGroupReview entity = new EccGroupReview();
        if (list != null && list.size() > 0) {
            entity = list.get(0);
        } else {
            entity.setEccGroupId(com.supporter.util.UUIDHex.newId());
            entity.setPrjId(prjId);
            //初始化资料清单
            syncFile(entity,user);
        }
        entity = initPrjInfo(prjId, entity);
        return entity;
    }
    public void syncFile(EccGroupReview entity,UserProfile user){
        //判断是否经过委员会审核
        EccCacReview cacReview = this.cacReviewService.getByPrjId(null,entity.getPrjId());
        if (cacReview!=null&&user!=null){
            //初始化资料清单
            PpmSyncFilesUtil.syncFiles(EccUtils.MODULE_ID,EccUtils.File_BusiType,cacReview.getEccCacId(),entity.getPrjId(),
                    EccUtils.MODULE_ID,EccUtils.File_BusiType,entity.getEccGroupId(),"",user);
            return ;
        }
        EccWgReview wgReview  =this.wgReviewService.getByPrjId(null,entity.getPrjId());
        if (wgReview!=null&&user!=null){
            //初始化资料清单
            PpmSyncFilesUtil.syncFiles(EccUtils.MODULE_ID,EccUtils.File_BusiType,wgReview.getEccWgId(),entity.getPrjId(),
                    EccUtils.MODULE_ID,EccUtils.File_BusiType,entity.getEccGroupId(),"",user);
            return ;
        }

    }
    //初始化项目信息
    private EccGroupReview initPrjInfo(String prjId, EccGroupReview entity) {
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
     * @param user           用户信息
     * @param eccGroupReview 实体类
     * @return
     */
    public EccGroupReview saveOrUpdate(UserProfile user, EccGroupReview eccGroupReview) {
        if (StringUtils.isBlank(eccGroupReview.getEccGroupId())) {// 新建
            return this.save(user, eccGroupReview);
        } else {// 编辑
            return this.update(user, eccGroupReview);
        }
    }

    /**
     * 保存.
     *
     * @param user           用户信息
     * @param eccGroupReview 实体类
     * @return
     */
    private EccGroupReview save(UserProfile user, EccGroupReview eccGroupReview) {

        eccGroupReview.setCreatedBy(user.getName());
        eccGroupReview.setCreatedById(user.getPersonId());
        eccGroupReview.setCreatedDate(new Date());
        eccGroupReview.setModifiedById(user.getPersonId());
        eccGroupReview.setModifiedBy(user.getName());
        eccGroupReview.setModifiedDate(new Date());
        eccGroupReview.setDeptId(user.getDeptId());
        eccGroupReview.setDeptName(user.getDept() == null ? "" : user.getDept().getName());
        this.eccGroupReviewDao.save(eccGroupReview);
        return eccGroupReview;
    }

    /**
     * 更新.
     *
     * @param user           用户信息
     * @param eccGroupReview 实体类
     * @return
     */
    private EccGroupReview update(UserProfile user, EccGroupReview eccGroupReview) {
        EccGroupReview eccGroupReviewDb = this.eccGroupReviewDao.get(eccGroupReview.getEccGroupId());
        if (eccGroupReviewDb == null) {
            return this.save(user, eccGroupReview);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(eccGroupReview, eccGroupReviewDb);
            eccGroupReviewDb.setModifiedById(user.getPersonId());
            eccGroupReviewDb.setModifiedBy(user.getName());
            eccGroupReviewDb.setModifiedDate(new Date());
            this.eccGroupReviewDao.update(eccGroupReviewDb);
            return eccGroupReviewDb;
        }

    }

    /**
     * 删除
     *
     * @param user        用户信息
     * @param eccGroupIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String eccGroupIds) {
        if (StringUtils.isNotBlank(eccGroupIds)) {
            for (String eccGroupId : eccGroupIds.split(",")) {
                EccGroupReview eccGroupReviewDb = this.eccGroupReviewDao.get(eccGroupId);
                this.eccGroupReviewDao.delete(eccGroupId);
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param eccGroupId
     * @param eccGroupName
     * @return
     */
    public boolean nameIsValid(String eccGroupId, String eccGroupName) {
        return this.eccGroupReviewDao.nameIsValid(eccGroupId, eccGroupName);
    }

    /**
     * 流程处理类更新
     *
     * @param entity
     */
    public void update(EccGroupReview entity) {
        if (entity != null) {
            this.eccGroupReviewDao.update(entity);
        }
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
                    //如果是 情况清晰有限制裁进入CMEC报审
                    if (cons.get(0).getRvConBussinesStatus() == EccDeptRevieCon.needNext) {
                        result = true;
                        message = eccs.get(0).getEccId();
                        EccGroupReview entity = this.getByPrjId(null,prjId);
                        if (entity.getGroupEccStatus() == EccGroupReview.DRAFT) {
                            map.put("nextPage", "edit");
                        } else {
                            map.put("nextPage", "view");
                        }
                    } else {
                        message = "该项目尚不需要CMEC会商！";
                    }
                } else {
                    message = "该项目出口管制工作组尚未审核完成！";
                }
            }else{
                message = "该项目出口管制工作组尚未审核完成或审核不通过！";
            }

        } else {
            message = "该项目无需管制！";
        }

        map.put("msg", message);
        map.put("result", result);
        if (result) {
            return map;
        }
        if (eccs != null && eccs.size() > 0) {
            List<EccCacRevieCon> cons = this.cacRevieConDao.findBy("eccId", eccs.get(0).getEccId());
            if (cons != null && cons.size() > 0) {
                if (cons.get(0).getRvConBussinesStatus() == EccDeptRevieCon.needNext) {
                    result = true;
                    message = eccs.get(0).getEccId();
                    //判断是查看还是编辑
                    EccGroupReview entity = this.getByPrjId(null,prjId);
                    if (entity.getGroupEccStatus() == EccGroupReview.DRAFT) {
                        map.put("nextPage", "edit");
                    } else {
                        map.put("nextPage", "view");
                    }
                } else {
                    message = "该项目不需要CMEC会商！";
                }
            } else {
                message = "该项目尚未完成出口管制委员会审核！";
            }
        } else {
            message = "该项目无需管制！";
        }

        map.put("msg", message);
        map.put("result", result);
        return map;
    }

    //开启部门初审
    public void startEccGroup(EccGroupReview entity) {
        update(entity);
    }

    //中止审核
    public void abortEccGroup(EccGroupReview entity) {
        update(entity);
    }

    //审核结束
    public void endEccGroup(EccGroupReview entity) {
        entity.setProcId(entity.getEccGroupId());
        update(entity);
        String eccId = entity.getEccId();
        //获取管制结果
        List<EccGroupRevieCon> list = this.eccGroupRevieConDao.findBy("eccId", entity.getEccId());
        String result = "";
        int status = 0;
        if (list != null && list.size() > 0) {
            EccGroupRevieCon con = list.get(0);
            int conStatus = con.getRvConStatus();
            if (conStatus == EccDeptRevieCon.pass) {
                result = "集团评审通过。";
                status = Ecc.StatusCodeTable.COMPLETE;
            } else {
                result = "集团评审不通过。";
                status = Ecc.StatusCodeTable.COMPLETENO;
            }

            //代办地址
            String url = "ppm/ecc/group_review/groupReview_swf.html?prjId=" + entity.getPrjId() + "&pageType=swfCc&noticeType=";
            //代办标题
            String title = "（知会）部门管制专员：出口委员会会商-";
            Prj prj = this.prjService.initBaseInfoView(entity.getPrjId());
            title += prj.getPrjCName();
            //获取事业部管制专员id
            EccDeptReview deptReview = this.deptReviewService.getByPrjId(entity.getPrjId());
            String createdById = deptReview.getCreatedById();
            //知会

            EccUtils.sendMessage(createdById, title, url, EccGroupReview.MODULE_ID, entity.getProcId(),"部门管制专员");

            title = "（知会）项目开发负责人：出口委员会会商-";
            title += prj.getPrjCName();
            this.deptReviewService.sendMessageToPrjM(entity.getPrjId(), title, url, EccGroupReview.MODULE_ID, entity.getProcId(),"项目开发负责人");
        }

        this.eccService.updateEcc(eccId, status, result);

    }

    public void effect(String groupEccId) {
        EccGroupReview entity = get(groupEccId);
        entity.setGroupEccStatus(EccGroupReview.COMPLETE);
        endEccGroup(entity);
    }
}

