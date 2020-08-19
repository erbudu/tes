/**
 * 
 */
package com.supporter.prj.ppm.bid_startup.preparation.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.bid_startup.preparation.constant.StartContant;
import com.supporter.prj.ppm.bid_startup.preparation.dao.StartDao;
import com.supporter.prj.ppm.bid_startup.preparation.entity.BFDEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.StartEntity;
import com.supporter.prj.ppm.bid_startup.preparation.entity.base.BaseBFDEntity;
import com.supporter.prj.ppm.ecc.project_ecc.service.EccService;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: 投议标备案及许可->申报准备->启动申报业务层</p>
 *<p>Description: 启动申报业务逻辑处理操作，由Controller调用</p>
 *<p>Company:东华后盾 </p>
 * @author YUEYUN
 * @date 2019年8月13日
 * 
 */
@Service
public class StartService {
	
	@Autowired
	private StartDao startDao;
	
	@Autowired
	private BFDService bfdService;//资料清单service
	
	@Autowired
	private BFD_FService bfd_fService;//资料清单附件service
	
	@Autowired
	private EccService eccService;//出口管制service
	
	@Autowired
	 PrjInfo prjInfo;
	/**
	 * <pre>
	 * @Title 单项删除
	 * @Description 只删除当前表单信息
	 * @param bidIngUpID 主键
	 * </pre>
	 */
	public void delete(String bidIngUpId) {
		StartEntity startEntity = startDao.get(bidIngUpId);
		if(startEntity == null) {
			
		}else {
			bfd_fService.deleteByBidIngUpId(bidIngUpId);
			bfdService.deleteByBidIngUpId(bidIngUpId);
			startDao.delete(bidIngUpId);
		}
		
	}


	/**
	 * @Dsecription : 新建、编辑初始化service
	 * <p>call function: 1.StartController.initEditOrViewPage</p>
	 * @return 投议标备案实体类
	 * @param 项目主键
	 */
	public StartEntity initEditOrViewPage(String prjId) {
		Prj prjInformation = prjInfo.PrjInformation(prjId);//项目信息
		
		StartEntity entity = startDao.findEntityByPrjId(prjId);
		if(entity != null){//根据项目主键查到的实体类不为空直接返回对象、编辑状态
			/*资料清单改为树结构，暂时不需要这个字段*/
			//List<BaseBFDEntity> dataList = getDataListEdit(entity.getBidIngUpId());
			//entity.setDataList(dataList);
			return entity;			
		}else {
			StartEntity backEntity = new StartEntity();
			backEntity.setBidIngUpId(UUIDHex.newId());
			backEntity.setIsNew(true);//标识为新建
			backEntity.setPrjId(prjInformation.getPrjId());//项目主键
			backEntity.setPrjNo(prjInformation.getPrjNo());//项目编号
			backEntity.setPrjNameC(prjInformation.getPrjCName());//项目中文名称
			backEntity.setPrjNameE(prjInformation.getPrjEName());//项目 英文名称
			backEntity.setStatus(StartContant.DRAFT);
			//List<BaseBFDEntity> dataList = getBFDDateListInit(backEntity.getBidIngUpId());//资料清单
			//backEntity.setDataList(dataList);
			return backEntity;
		}
	}
	
	
	/**
	 * <pre>功能:初始化资料清单无序列表数据-编辑页面</pre>
	 * <pre>描述：根据资料清单码表ID和启动申报主键获取资料清单数据，获取到数据则返回</pre>
	 * @return 资料清单实体类数据
	 */
	private List<BaseBFDEntity> getDataListEdit(String bidIngUpId) {
		List<BaseBFDEntity> list = new ArrayList<BaseBFDEntity>();
		
		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_BIDSTARTUP_BFD");//码表数据
		for (IComCodeTableItem item : items) {
			List<BFDEntity> bfdEntity = bfdService.getDataListEdit(bidIngUpId,item.getItemId());//
			if(bfdEntity.size() == 0 || bfdEntity == null) {
				BFDEntity bfd = new BFDEntity();
				bfd.setBfdTypeId(item.getItemId());
				bfd.setBfdTypeName(item.getItemValue());
				bfd.setBidIngUpId(bidIngUpId);
				bfd.setBfdId(com.supporter.util.UUIDHex.newId());
				list.add(bfd);
			}else {
				for(int i= 0;i<bfdEntity.size();i++) {
					BFDEntity bfd = new BFDEntity(); 
					bfd.setBfdId(bfdEntity.get(i).getBfdId());
					bfd.setBidIngUpId(bfdEntity.get(i).getBidIngUpId());
					bfd.setBfdTypeId(bfdEntity.get(i).getBfdTypeId());
					bfd.setBfdTypeName(bfdEntity.get(i).getBfdTypeName());
					list.add(bfd);
				}
			}
		}
		return list;
	}


	/**
	 * <pre>功能:初始化资料清单列表数据-初始化新建页面</pre>
	 * @return 资料清单实体类
	 */
	private List<BaseBFDEntity> getBFDDateListInit(String bidIngUpId) {
		List<BaseBFDEntity> list = new ArrayList<BaseBFDEntity>();

		List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems("PPM_BIDSTARTUP_BFD");//资料清单码表
		for (IComCodeTableItem item : items) {
			BFDEntity bfd = new BFDEntity();
			bfd.setBfdTypeId(item.getItemId());
			bfd.setBfdTypeName(item.getItemValue());
			bfd.setBidIngUpId(bidIngUpId);
			bfd.setBfdId(com.supporter.util.UUIDHex.newId());
			list.add(bfd);
		}
		return list;
	}


	/**
	 * <pre>
	 * @Description  Form save or update
	 * @param entity Form entity class
	 * @return entity Form entity class
	 * @callFunction :1.The method to 'saveOrUpdate' the StartController class
	 * @businessLogic : After successful save,set the value of 'isNew' to false.
	 * </pre>
	 */
	public StartEntity saveOrUpdate(UserProfile user, StartEntity entity) {
		
		if(entity.getIsNew()) {
			setCreaetInfo(user,entity);
			startDao.save(entity);
			entity.setIsNew(false);//保存成功后标识为更新
			PPMService.getScheduleStatusService().saveScheduleStatus(entity.getPrjId(), "preparation01", user);
		}else {
			setMOdifiedInfo(user,entity);
			startDao.update(entity);
		}
		return entity;
	}
	
	/**
	 * <pre>
	 * @Description : Set the modifier information of the form.
	 * @param user Login user information
	 * @param entity Form entity class
	 * call function: 1.The method to "saveOrUpdate"  the this class;
	 * </pre>
	 */
	private void setMOdifiedInfo(UserProfile user, StartEntity entity) {

		entity.setModifiedBy(user.getName());
		entity.setModifiedById(user.getPersonId());
		entity.setModifiedDate(new Date());
		
	}

	/**
	 * <pre>
	 * @Description : Form creator information
	 * @param user user information 
	 * @param entity Form entity class
	 * call function: 1.The method to "saveOrUpdate"  the this class;
	 * </pre>
	 */
	private void setCreaetInfo(UserProfile user, StartEntity entity) {
		
		entity.setStatus(0);
		entity.setCreatedBy(user.getName());
		entity.setCreatedById(user.getPersonId());
		entity.setCreatedDate(new Date());
		Dept dept = user.getDept();
		if(dept != null) {
			entity.setDeptName(dept.getName());
			entity.setDeptId(dept.getDeptId());
		}else {
			entity.setDeptName("admin");
			entity.setDeptId("1");
		}
	}

	/**
	 * <p>根据业务主键获取业务实体类信息-流程中用到</p>
	 * @param entityId 业务主键
	 * @return 业务主键实体类信息
	 */
	public StartEntity get(String entityId) {
		StartEntity startEntity = startDao.get(entityId);
		return startEntity;
		
	}
	/**
	 * <pre>更新 - 流程中用</pre>
	 * @param startEntity
	 */
	public void update(StartEntity startEntity) {
		startDao.update(startEntity);
	}


	/**
	 * <p>获取用印业务</p>
	 * @return
	 */
	public String getUseSealBusiness() {
		return StartContant.USE_SEAL_BUSINESS;
	}


	/**
	 * <pre>保存其他节点的内容</pre>
	 * @param prjId 项目主键
	 * @param other 字段值
	 * @return 表单实体类
	 */
	public StartEntity staging(String prjId, String otherContent) {
		
		if(prjId == null || prjId == "") { return null;}
		
		StartEntity startEntity = startDao.findUniqueResult("prjId", prjId);
		if(startEntity != null) {
			startEntity.setOtherContent(otherContent);
			this.startDao.update(startEntity);
			return startEntity;
		}
		return null;
	}


	/**
	 * <pre>用印流程启动成功后更新业务表单的流程状态</pre>
	 * @param prjId 项目主键
	 * @return 项目 信息
	 */
	public StartEntity updateStautsAfterPrintStart(String bidIngUpId) {
		if(bidIngUpId == "" || bidIngUpId == null) {
			return null;
		}
		StartEntity startEntity = startDao.get(bidIngUpId);
		startEntity.setStatus(StartContant.START_UP_PRINT);
		startDao.update(startEntity);
		return startEntity;
	}


	/**
	 * <pre>判断是否通过出口管制</pre>
	 * @param prjId
	 * @return 
	 */
	public Map<String , Object> checkECC(String prjId) {
		if(prjId =="" || prjId == null) {
			return null;
		}
		Map<String , Object> map = new HashMap<String, Object>();
		String msg = "";
		boolean flag = false;
		Prj prjInformation = prjInfo.PrjInformation(prjId);
		if(prjInformation.getStatus() == 1 || prjInformation.getStatus() == 2) {
			if(prjInformation.getIsEccConfirm()) {
				if(!eccService.prjIsEccComplete(prjId)) {
					msg = "该项目未通过管制，不能继续操作！";
				}else {
					flag = true;
				}
			}else {
				flag = true;
			}
		}else {
			msg = "该项目尚未生效！";
		}
		map.put("flag", flag);
		map.put("msg", msg);
		
		return map;
	}

	/**
	 * 将流程id置空
	 * @param entity
	 */

	public void clearProcId(StartEntity entity) {
		
		entity.setProcId("");
		startDao.update(entity);
	}
}
