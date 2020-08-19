package com.supporter.prj.ppm.ecc.wg_review.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.prj.eip.module.util.ModuleUtils;
import com.supporter.prj.ppm.ecc.EccUtils;
import com.supporter.prj.ppm.ecc.dept_review.dao.EccDeptRevieConDao;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptRevieCon;
import com.supporter.prj.ppm.ecc.dept_review.entity.EccDeptReview;
import com.supporter.prj.ppm.ecc.dept_review.service.EccDeptReviewService;
import com.supporter.prj.ppm.ecc.project_ecc.entity.Ecc;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.ecc.wg_review.dao.EccWgRevieConDao;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgRevieCon;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.service.BaseInfoService;
import com.supporter.prj.ppm.util.PpmSyncFilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.beanutils.BeanUtils;

import com.supporter.prj.ppm.ecc.wg_review.dao.EccWgReviewDao;
import com.supporter.prj.ppm.ecc.wg_review.entity.EccWgReview;

/**
 * @Title: Service
 * @Description: 出口管制工作组会商审核表.
 * @author YAN
 * @date 2019-08-16 18:44:47
 * @version V1.0
 */
@Service
public class EccWgReviewService {

	@Autowired
	private EccWgReviewDao eccWgReviewDao;
	@Autowired
	private EccWgRevieConDao eccWgReviewConDao;
	@Autowired
	private EccDeptReviewService deptReviewService;
	@Autowired
	private BaseInfoService prjService;
	@Autowired
	private EccService eccService;
	@Autowired
	private EccDeptRevieConDao deptRevieConDao;
	/**
	 * 根据主键获取出口管制工作组会商审核表.
	 * 
	 * @param eccWgId 主键
	 * @return EccWgReview
	 */
	public EccWgReview get(String eccWgId) {
		return eccWgReviewDao.get(eccWgId);
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List< EccWgReview > getGrid(UserProfile user, JqGrid jqGrid, EccWgReview eccWgReview) {
		return eccWgReviewDao.findPage(jqGrid, eccWgReview);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息
	 * 
	 * @param user
	 * @param eccWgId
	 * @return
	 */
	public EccWgReview initEditOrViewPage(UserProfile user,String prjId) {
		if (StringUtils.isBlank(prjId)) {// 新建
			EccWgReview entity = new EccWgReview();
			return entity;
		} else {// 编辑
			EccWgReview entity = getByPrjId(user,prjId);
			return entity;
		}
	}

	public EccWgReview getByPrjId(UserProfile user,String prjId){
		List<EccWgReview> list = this.eccWgReviewDao.findBy("prjId",prjId);
		EccWgReview entity = null;
		if (list!=null&&list.size()>0){
			entity = list.get(0);
		}else {
			entity = new EccWgReview();
			entity.setEccWgId(com.supporter.util.UUIDHex.newId());
			entity.setPrjId(prjId);
			entity.setApplyDate(new Date());
			//获取 部门初审对象
			EccDeptReview deptReview = this.deptReviewService.getByPrjId(prjId);
			if (deptReview!=null&&user!=null){
				//初始化资料清单
				PpmSyncFilesUtil.syncFiles(EccUtils.MODULE_ID,EccUtils.Dept_File_BusiType,deptReview.getDeptEccId(),"",
						EccUtils.MODULE_ID,EccUtils.File_BusiType,entity.getEccWgId(),prjId,user);
			}
		}
		//初始化项目信息
		entity = initPrjInfo(prjId,entity);
		return entity;
	}
	//初始化项目信息
	private EccWgReview initPrjInfo(String prjId, EccWgReview entity) {
		Prj prj = this.prjService.initBaseInfoView(prjId);
		if (prj!=null){
			entity.setPrjCName(prj.getPrjCName());
			entity.setPrjEName(prj.getPrjEName());
			entity.setPrjNo(prj.getPrjNo());
		}
		return entity;
	}

	/**
	 * 保存或更新.
	 * 
	 * @param user 用户信息
	 * @param eccWgReview 实体类
	 * @return
	 */
	public EccWgReview saveOrUpdate(UserProfile user, EccWgReview eccWgReview) {
		if (StringUtils.isBlank(eccWgReview.getEccWgId())) {// 新建
			return this.save(user, eccWgReview);
		} else {// 编辑
			return this.update(user, eccWgReview);
		}
	}

	/**
	 * 保存.
	 * @param user 用户信息
	 * @param eccWgReview 实体类
	 * @return
	 */
	private EccWgReview save(UserProfile user, EccWgReview eccWgReview) {
		eccWgReview.setCreatedBy(user.getName());
		eccWgReview.setCreatedById(user.getPersonId());
		eccWgReview.setCreatedDate(new Date());
		eccWgReview.setModifiedById(user.getPersonId());
		eccWgReview.setModifiedBy(user.getName());
		eccWgReview.setModifiedDate(new Date());
		eccWgReview.setDeptId(user.getDeptId());
		eccWgReview.setDeptName(user.getDept()==null?"":user.getDept().getName());
		this.eccWgReviewDao.save(eccWgReview);
		return eccWgReview;
	}

	/**
	 * 更新.
	 * @param user 用户信息
	 * @param eccWgReview 实体类
	 * @return
	 */
	private EccWgReview update(UserProfile user, EccWgReview eccWgReview) {
		EccWgReview eccWgReviewDb = this.eccWgReviewDao.get(eccWgReview.getEccWgId());
		if(eccWgReviewDb == null) {
			return this.save(user, eccWgReview);
		}else{
			ModuleUtils.copyPropertiesExcludeNullProperty(eccWgReview, eccWgReviewDb);
			eccWgReviewDb.setModifiedById(user.getPersonId());
			eccWgReviewDb.setModifiedBy(user.getName());
			eccWgReviewDb.setModifiedDate(new Date());
			this.eccWgReviewDao.update(eccWgReviewDb);
			return eccWgReviewDb;
		}
		
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param eccWgIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String eccWgIds) {
		if(StringUtils.isNotBlank(eccWgIds)) {
			for(String eccWgId : eccWgIds.split(",")) {
				EccWgReview eccWgReviewDb = this.eccWgReviewDao.get(eccWgId);
				this.eccWgReviewDao.delete(eccWgId);
			}
		}
	}

	/**
	 * 判断名字是否重复
	 * 
	 * @param eccWgId
	 * @param eccWgName
	 * @return
	 */
	public boolean nameIsValid(String eccWgId,String eccWgName) {
		return this.eccWgReviewDao.nameIsValid( eccWgId, eccWgName);
	}

	/**
	 * 流程处理类更新操作
	 * @param entity
	 */
    public void update(EccWgReview entity) {
		if (entity!=null){
			this.eccWgReviewDao.update(entity);
		}
    }
	/**
	 * 验证是否需要管制
	 * @param prjId
	 * @param message
	 * @param result
	 * @return
	 */
	public Map<String, Object> validEcc(String prjId, String message, boolean result) {
		List<Ecc> eccs = this.eccService.findBy("prjId",prjId);
		Map <String , Object> map = new HashMap<String, Object>();
		if (eccs!=null&&eccs.size()>0){
			List<EccDeptRevieCon> cons = this.deptRevieConDao.findBy("eccId",eccs.get(0).getEccId());
			if (cons!=null&&cons.size()>0){
				//如果选择通过
				if(cons.get(0).getRvConStatus() == EccDeptRevieCon.pass){
					if (cons.get(0).getRvConBussinesStatus()== EccDeptRevieCon.needNext){
						result = true;
						message = eccs.get(0).getEccId();
						//判断是编辑还是查看
						EccWgReview entity = this.getByPrjId(null,prjId);
						if (entity.getDeptEccStatus()==EccWgReview.DRAFT){
							map.put("nextPage","edit");
						}else {
							map.put("nextPage","view");
						}
					}else{
						message = "该项目不需要出口管制工作组会商！";
					}
				}else{

				}message = "审核不通过！";
			}else{
				message = "该项目尚未初审完成！";
			}
		}else{
			message = "该项目无需管制！";
		}

		map.put("msg",message);
		map.put("result",result);
		return map;
	}

	//开启部门初审
	public void startEccWg(EccWgReview entity) {
		update(entity);
	}
	//中止审核
	public void abortEccWg(EccWgReview entity) {
		update(entity);
	}
	//审核结束
	public void endEccWg(EccWgReview entity) {
		update(entity);
		String eccId = entity.getEccId();
		//获取管制结果
		List<EccWgRevieCon> list = this.eccWgReviewConDao.findBy("eccId",eccId);
		String result = "";
		int status = 1;
		//代办地址
		String url = "ppm/ecc/wg_review/wgReview_swf.html?prjId=" + entity.getPrjId() + "&pageType=swfCc&noticeType=";
		//代办标题
		String title = "（知会）项目开发负责人：出口工作组会商知会-";
		Prj prj = this.prjService.initBaseInfoView(entity.getPrjId());
		title +=prj.getPrjCName();
		if (list!=null&&list.size()>0){
			EccWgRevieCon con = list.get(0);
			int conStatus = con.getRvConStatus();
			int businessStatus = con.getRvConBussinesStatus();
			if (conStatus==EccDeptRevieCon.pass){
				if (businessStatus==EccDeptRevieCon.needNext){
					result = "出口管制工作组审核通过，情况清晰的有限制裁。";
					status = Ecc.StatusCodeTable.ECCING;
					//下一步进入CMEC审核 知会事业部管制专员
				}else{
					result = "出口管制工作组审核通过，情况复杂的有限制裁/全面制裁。";
					status = Ecc.StatusCodeTable.ECCING;
					//下一步进入出口管制委员会审核 知会事业部管制专员
				}
			}else{
				result = "出口管制工作组审核不通过。";
				status = Ecc.StatusCodeTable.COMPLETENO;
				//知会项目开发负责人审核不通过
				this.deptReviewService.sendMessageToPrjM(entity.getPrjId(),title,url,EccWgReview.MODULE_ID,entity.getProcId(),"项目开发负责人");
			}
			//获取事业部管制专员id
			EccDeptReview deptReview =this.deptReviewService.getByPrjId(entity.getPrjId());
			String createdById = deptReview.getCreatedById();
			//知会
			EccUtils.sendMessage(createdById, title, url, EccWgReview.MODULE_ID, entity.getProcId(),"事业部管制专员");
		}
		this.eccService.updateEcc(eccId,status,result);

	}
}

