package com.supporter.prj.pm.design_change.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.design_change.dao.DesignChangeDao;
import com.supporter.prj.pm.design_change.entity.DesignChange;

@Service
@Transactional(TransManager.APP)
public class DesignChangeService {
	@Autowired
	private DesignChangeDao dao;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param id
	 * @return
	 */
	public DesignChange initEditOrViewPage(String applyId,UserProfile user) {
		DesignChange design = new DesignChange();
		if (StringUtils.isNotBlank(applyId)) {	
			design =  dao.get(applyId);
			design.setIsNew(false);
			design.setModifiedBy(user.getName());
			design.setModifiedById(user.getPersonId());
			design.setModifiedDate(new Date());
			
			return design;
		} else {
			return null;
		}
	}
	
	public DesignChange getToFuJian(String applyId,UserProfile user) {
		if (StringUtils.isNotBlank(applyId)) {			
			DesignChange design =  dao.get(applyId);
			return design;
		}
		return null;
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<DesignChange> getGrid(JqGrid jqGrid, DesignChange design,UserProfile user) {
		return dao.findPage(jqGrid, design,user);
	}
	
	public List<DesignChange> getExamGrid(JqGrid jqGrid, DesignChange design,UserProfile user) {
		return dao.findExamPage(jqGrid, design,user);
	}

	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param design 实体类
	 * @return
	 */
	public DesignChange saveOrUpdate(UserProfile user, DesignChange design) {
		DesignChange ret = null;
		boolean isNew=design.getIsNew();
		String fileType = EIPService.getComCodeTableService().getCodeTable("FILETYPE").getDisplay(design.getFileTypeId());
		design.setFileType(fileType);
		String modelType = EIPService.getComCodeTableService().getCodeTable("FILETYPE").getDisplay(design.getModelTypeId());
		design.setModelType(modelType);
		if(isNew){
			
		} else {// 编辑	
			String speciality = EIPService.getComCodeTableService().getCodeTable("Specialized").getDisplay(design.getSpecialityId());
			design.setSpeciality(speciality);
			String changeReason = EIPService.getComCodeTableService().getCodeTable("change_reason").getDisplay(design.getChangeReasonId());
			design.setChangeReason(changeReason);
			this.dao.update(design);
			ret = design;
		}
		
		return ret;
	}
	
	/**
	 * 直接生效
	 * 
	 * @param user 用户信息
	 * @param design 实体类
	 * @return
	 */
	public DesignChange valid(UserProfile user, DesignChange design) {
		DesignChange ret = null;
		boolean isNew=design.getIsNew();
		String fileType = EIPService.getComCodeTableService().getCodeTable("FILETYPE").getDisplay(design.getFileTypeId());
		design.setFileType(fileType);
		String modelType = EIPService.getComCodeTableService().getCodeTable("FILETYPE").getDisplay(design.getModelTypeId());
		design.setModelType(modelType);
		if(isNew){	
			
		} else {// 编辑
			String speciality = EIPService.getComCodeTableService().getCodeTable("Specialized").getDisplay(design.getSpecialityId());
			design.setSpeciality(speciality);
			String changeReason = EIPService.getComCodeTableService().getCodeTable("change_reason").getDisplay(design.getChangeReasonId());
			design.setChangeReason(changeReason);
			design.setStatus(DesignChange.StatusCodeTable.EXAMED);
			this.dao.update(design);
			ret = design;
		}
		return design;
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param problemIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String ids) {

		if (StringUtils.isNotBlank(ids)) {
			for (String id : ids.split(",")) {
				DesignChange design = dao.get(id);		
				dao.delete(design);
			}
		}
	}
	
	public boolean checkFile(HttpServletRequest request,String applyId) {
	 	String realPath =  request.getSession().getServletContext().getRealPath("");
		String path = realPath.replace("\\", "/");
		String SaveFile=  path + "/data/supp/file_upload/TEMPLATEMAIN/ModelTemplate/"+ applyId +".doc";
		File file=new File(SaveFile);
		if(file.exists()){	
             return true;
         }else{
        	 return false;
         }      
	}
	
	public DesignChange updateRewinding(UserProfile user, String applyId) {
		DesignChange designChange = dao.get(applyId);
		designChange.setIsRewinding(1);
		dao.update(designChange);
		return designChange;
	}
	
	public DesignChange getDesignChangeById(String receiveId) {
		return dao.get(receiveId);
	}
}
