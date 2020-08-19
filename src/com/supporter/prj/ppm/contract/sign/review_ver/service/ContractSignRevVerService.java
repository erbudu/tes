package com.supporter.prj.ppm.contract.sign.review_ver.service;

import java.util.Date;
import java.util.List;

import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDetailDao;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.contract.sign.review.entity.ContractSignReviewCon;
import com.supporter.prj.ppm.contract.sign.review.service.ContractSignReviewConService;
import com.supporter.prj.ppm.contract.sign.review_ver.dao.ContractSignRevVerDao;
import com.supporter.prj.ppm.contract.sign.review_ver.entity.ContractSignRevVer;

/**
 * @author YAN
 * @version V1.0
 * @Title: Service
 * @Description: 评审验证表.
 * @date 2019-09-09 10:46:27
 */
@Service
public class ContractSignRevVerService {

    @Autowired
    private ContractSignRevVerDao contractSignRevVerDao;
    @Autowired
    private ContractSignReviewConService reviewConService;
    @Autowired
    private TemplateRegisterDetailDao templateRegisterDetailDao;

    /**
     * 根据主键获取评审验证表.
     *
     * @param reviewVerId 主键
     * @return ContractSignRevVer
     */
    public ContractSignRevVer get(String reviewVerId) {
        return contractSignRevVerDao.get(reviewVerId);
    }

    /**
     * 分页表格展示数据.
     * @param user      用户信息
     * @param jqGridReq jqgrid请求对象
     * @param moduleIds 多个逗号分隔
     * @return JqGrid
     */
    public List<ContractSignRevVer> getGrid(UserProfile user, JqGrid jqGrid, ContractSignRevVer contractSignRevVer) {
        return contractSignRevVerDao.findPage(jqGrid, contractSignRevVer);
    }

    /**
     * 进入新建或编辑或查看页面需要加载的信息
     *
     * @param user
     * @param reviewVerId
     * @return
     */
    public ContractSignRevVer initEditOrViewPage(String reviewVerId) {
        if (StringUtils.isBlank(reviewVerId)) {// 新建
            ContractSignRevVer entity = new ContractSignRevVer();
            return entity;
        } else {// 编辑
            ContractSignRevVer entity = contractSignRevVerDao.get(reviewVerId);
            return entity;
        }
    }

    /**
     * 保存或更新.
     *
     * @param user               用户信息
     * @param contractSignRevVer 实体类
     * @return
     */
    public ContractSignRevVer saveOrUpdate(UserProfile user, ContractSignRevVer contractSignRevVer) {
        if (StringUtils.isBlank(contractSignRevVer.getReviewVerId())) {// 新建
            contractSignRevVer.setCreatedBy(user.getName());
            contractSignRevVer.setCreatedById(user.getPersonId());
            contractSignRevVer.setCreatedDate(new Date());
            contractSignRevVer.setDeptId(user.getDeptId());
            contractSignRevVer.setDeptName(user.getDept()==null?"":user.getDept().getName());
            return this.save(user, contractSignRevVer);
        } else {// 编辑
            contractSignRevVer.setModifiedBy(user.getName());
            contractSignRevVer.setModifiedById(user.getPersonId());
            contractSignRevVer.setModifiedDate(new Date());
            return this.update(user, contractSignRevVer);
        }
    }

    /**
     * 保存.
     *
     * @param user               用户信息
     * @param contractSignRevVer 实体类
     * @return
     */
    private ContractSignRevVer save(UserProfile user, ContractSignRevVer contractSignRevVer) {
        this.contractSignRevVerDao.save(contractSignRevVer);
        return contractSignRevVer;
    }

    /**
     * 更新.
     *
     * @param user               用户信息
     * @param contractSignRevVer 实体类
     * @return
     */
    private ContractSignRevVer update(UserProfile user, ContractSignRevVer contractSignRevVer) {
        ContractSignRevVer contractSignRevVerDb = this.contractSignRevVerDao.get(contractSignRevVer.getReviewVerId());
        if (contractSignRevVerDb == null) {
            return this.save(user, contractSignRevVer);
        } else {
            ModuleUtils.copyPropertiesExcludeNullProperty(contractSignRevVer, contractSignRevVerDb);
            this.contractSignRevVerDao.update(contractSignRevVerDb);
            return contractSignRevVerDb;
        }

    }

    /**
     * 删除
     *
     * @param user         用户信息
     * @param reviewVerIds 主键集合，多个以逗号分隔
     */
    public void delete(UserProfile user, String reviewVerIds) {
        if (StringUtils.isNotBlank(reviewVerIds)) {
            for (String reviewVerId : reviewVerIds.split(",")) {
                ContractSignRevVer contractSignRevVerDb = this.contractSignRevVerDao.get(reviewVerId);
                this.contractSignRevVerDao.delete(reviewVerId);
                //删除附件类型表
            }
        }
    }

    /**
     * 判断名字是否重复
     *
     * @param reviewVerId
     * @param reviewVerName
     * @return
     */
    public boolean nameIsValid(String reviewVerId, String reviewVerName) {
        return this.contractSignRevVerDao.nameIsValid(reviewVerId, reviewVerName);
    }

    /**
     * 根据项目id初始化
     *
     * @param prjId
     * @return
     */
    public ContractSignRevVer initPageByReviewId(String contractSignReviewId) {
        ContractSignRevVer ver = new ContractSignRevVer();
        if (StringUtils.isNotBlank(contractSignReviewId)) {
            //初始化
            List<ContractSignRevVer> vers = this.contractSignRevVerDao.findBy("contractSignReviewId", contractSignReviewId);
            if (vers != null && vers.size() > 0) {
                ver= vers.get(0);
            }
        }else {
            ver.setReviewVerId(com.supporter.util.UUIDHex.newId());
            ver.setContractSignReviewId(contractSignReviewId);
            //获取评审结果
            List<ContractSignReviewCon> cons = this.reviewConService.getListByRevId(contractSignReviewId);
            if (cons!=null&&cons.size()>0){
                ver.setResult(cons.get(0).getRvConBussinesStatus());
            }
        }


        //根据要点id获得评审段落
        if (StringUtils.isNotBlank(ver.getReviewVerificatContent())){
            String para = "";
            for (String detailId:ver.getReviewVerificatContent().split(",")
                 ) {
                TemplateRegisterDetail detail = templateRegisterDetailDao.get(ver.getReviewVerificatContent());
                para += detail.getParagraphNo() +",";
            }
            if (para.length()>0){
                para = para.substring(0,para.length()-1);
            }
            ver.setParagraphNo(para);
        }
        return ver;
    }

    public ContractSignRevVer commit(UserProfile user, String reviewVerId) {
        ContractSignRevVer ver = this.contractSignRevVerDao.get(reviewVerId);
        if (ver != null) {
            ver.setStatus(2);
            this.contractSignRevVerDao.update(ver);
        }
        return ver;
    }

	/**
	* 流程处理类更新
	* @param entity 实体类
	* @return
	*/
	public ContractSignRevVer updateSimply(ContractSignRevVer entity) {
		if (entity != null) {
			contractSignRevVerDao.update(entity);
		}
		return entity;
	}

}

