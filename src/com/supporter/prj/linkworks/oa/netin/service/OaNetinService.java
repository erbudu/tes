package com.supporter.prj.linkworks.oa.netin.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.netin.dao.OaNetinDao;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetinPerson;
import com.supporter.prj.linkworks.oa.outside_person.dao.OutsidePersonDao;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;
import com.supporter.util.CommonUtil;

@Service
public class OaNetinService {
	@Autowired
	private OaNetinDao netinDao;
	
	@Autowired
	private OaNetinPersonService netinPersonService;
	
	@Autowired
	private OutsidePersonDao outsidePersonDao;
	
	/**
	 * 根据主键id获得实体
	 * @param netinId
	 * @return
	 */
	public OaNetin get(String netinId){
		return netinDao.get(netinId);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param netinId
	 * @param user
	 * @return
	 */
	public OaNetin initEditOrViewPage(String netinId, UserProfile user){
		if (StringUtils.isBlank(netinId)){//新建
			OaNetin netin = new OaNetin();
			netin.setNetinId(com.supporter.util.UUIDHex.newId());
			netin.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			netin.setStatus(OaNetin.DRAFT);
			if (user != null){
				netin.setCreatedBy(user.getName());
				netin.setCreatedById(user.getPersonId());
				Dept dept = user.getDept();
				if (dept != null){
					netin.setDeptId(dept.getDeptId());
					netin.setDeptName(dept.getName());
				}
			}
			//新建即保存草稿，以免增加了人员信息但没有保存入网申请单而导致产生人员的垃圾数据
			this.netinDao.save(netin);
			return netin;
		}else{//编辑
			return netinDao.get(netinId);
		}
	}
	
	/**
	 * 分页查询数据
	 * @param user
	 * @param jqGrid
	 * @param netin
	 * @return
	 */
	public List<OaNetin> getGrid(JqGrid jqGrid, OaNetin netin){
		return netinDao.findPage(jqGrid, netin);
	}
	
	/**
	 * 查看
	 * @param netinId
	 * @param user
	 * @return
	 */
	public OaNetin viewPage(String netinId){
		if (StringUtils.isNotBlank(netinId)){
			OaNetin netin = netinDao.get(netinId);
			return netin;
		}
		return null;
	}
	
	/**
	 * 保存或更新
	 * @param user
	 * @param netin
	 * @return
	 */
	public OaNetin saveOrUpdate(UserProfile user, OaNetin netin){
		if (netin != null){
			if (user != null){
				netin.setModifiedBy(user.getName());
				netin.setModifiedById(user.getPersonId());
			}
			netin.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.netinDao.update(netin);
		}
		return netin;
	}
	
	public void update(OaNetin netin){
		this.netinDao.update(netin);
	}
	
	/**
	 * 保存并提交
	 * @param user
	 * @param netin
	 * @return
	 */
	public OaNetin commit(UserProfile user, OaNetin netin){
		if (netin != null){
			if (user != null){
				netin.setModifiedBy(user.getName());
				netin.setModifiedById(user.getPersonId());
			}
			netin.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			netin.setStatus(OaNetin.PROCESSING);
			this.netinDao.update(netin);
		}
		return netin;
	}
	
	/**
	 * 删除
	 * @param netin
	 */
	public void batchDel(String netinId){
		if (StringUtils.isNotBlank(netinId)){
			//删除入网申请下的所有人员信息
			this.netinPersonService.deleteAllNetinPerson(netinId);
			//删除相关附件
			deleteFile(netinId);
			//删除入网申请单
			this.netinDao.delete(netinId);
		}
	}
	
	/**
	 * 重置流程状态
	 * @param netinId
	 * @param status
	 * @return
	 */
	public OaNetin procReset(String netinId, Integer status){
		if(StringUtils.isNotBlank(netinId) && status != null){
			OaNetin netin = this.get(netinId);
			netin.setStatus(status);
			this.netinDao.update(netin);
			return netin;
		}
		return null;
	}
	
	/**
	 * 删除相关附件
	 * @param netinId
	 */
	public void deleteFile(String netinId){
		List<IFile> list = getFilesList(netinId);
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	
	/**
	 * 获取相关附件
	 * @param netinId
	 * @return
	 */
	public List<IFile> getFilesList(String netinId){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OANETIN", "OA_NETIN", netinId);
		return list;
	}
	
	/**
	 * 获取维护单下离退休职工人数
	 * @param netinId
	 * @return
	 */
	public Integer getRetirementCount(String netinId){
		return this.netinPersonService.getRetirementCount(netinId);
	}
	
	/**
	 * 获取维护单下非离退休职工人数
	 * @param netinId
	 * @return
	 */
	public Integer getNotRetirementCount(String netinId){
		return this.netinPersonService.getNotRetirementCount(netinId);
	}
	
	/**
	 * 将外聘人员信息转到外聘人员管理
	 * @param netin
	 */
	public void personIntoOutsideManager(OaNetin netin){
		List<OaNetinPerson> list = this.netinPersonService.getOutsidePerosn(netin.getNetinId());
		if (list != null){
			for (OaNetinPerson person : list){
				OutsidePerson outside = new OutsidePerson();
				outside.setId(com.supporter.util.UUIDHex.newId());
				outside.setName(person.getName());
				outside.setNameSpell(person.getNameSpell());
				outside.setSex(person.getSex());
				outside.setAccount(person.getUserAccount());
				outside.setMail(person.getMail());
				outside.setDeptId(netin.getDeptId());
				outside.setDeptName(netin.getDeptName());
				outside.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				outside.setStatus(OutsidePerson.ON_JOB);
				this.outsidePersonDao.save(outside);
			}
		}
	}
}
