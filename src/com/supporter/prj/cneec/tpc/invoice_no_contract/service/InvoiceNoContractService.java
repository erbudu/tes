package com.supporter.prj.cneec.tpc.invoice_no_contract.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.InvoiceNoContractEntity;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.UUIDHex;
import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.dao.InvoiceNoContractDao;

/**
 * 非合同付款收发票Service
 * @Title:InvoiceNoContractService
 * @author CHENHAO
 *
 */

@Service
public class InvoiceNoContractService {
	
	@Autowired
	private RegisterProjectService prjService;
	
	@Autowired
	private InvoiceNoContractRecService service;
	
	@Autowired
	private IFileUploadService fileService;
	
	@Autowired
	private InvoiceNoContractDao dao;

	/**
	 * 通过参数获取list
	 * @param name
	 * @param value
	 * @return
	 */
	
	public List<InvoiceNoContractEntity> findBy(String name, String value){
		if(StringUtils.isBlank(name) || StringUtils.isBlank(value)) {
			return null;
		}
		return dao.findBy(name, value);
	}
	
	/**
	 * 获取分页后的列表
	 * @param jqGrid
	 * @param user
	 * @param entity
	 * @return
	 */
	
	public List<InvoiceNoContractEntity> getGrid(JqGrid jqGrid, UserProfile user, InvoiceNoContractEntity entity) {
		
		if(StringUtils.isBlank(entity.getPrjId())) {
			return null;
		}
		List<InvoiceNoContractEntity> list = dao.findPage(jqGrid, user, entity);
		return list;
	}
	
	/**
	 * 填充新建和编辑页面信息
	 * @param prjId		项目ID
	 * @return
	 */
	public InvoiceNoContractEntity initEditOrViewPage(String invoiceId, String prjId, UserProfile user) {
	
		if(StringUtils.isBlank(invoiceId)) {
			
			if(StringUtils.isNotBlank(prjId)) {
				RegisterProject prj = prjService.get(prjId);
				InvoiceNoContractEntity entity = new InvoiceNoContractEntity();
				setCreaetInfo(user, entity);
				entity.setInvoiceId(UUIDHex.newId());
				entity.setPrjId(prj.getProjectId());
				entity.setPrjName(prj.getProjectName());
				return entity;
			}
		}else {
			return dao.findUniqueResult("invoiceId", invoiceId);
		}
		
		return null;
	}

	/**
	 * 保存或修改发票信息
	 * @param entity
	 * @return
	 */
	public OperResult<InvoiceNoContractEntity> saveOrUpdate(InvoiceNoContractEntity entity, UserProfile user) {
		
		if(dao.get(entity.getInvoiceId()) == null) {
			dao.save(entity);
		}
		
		setMOdifiedInfo(user, entity);
		dao.update(entity);
		
		return OperResult.succeed("保存成功", null, entity);
	}
	

	/**
	 * 删除草稿状态的信息
	 * @param invoiceId
	 * @return
	 */
	public OperResult<InvoiceNoContractEntity> delete(String invoiceIds) {
		if(StringUtils.isNotBlank(invoiceIds)) {
			for(String id : invoiceIds.split(",")) {
				InvoiceNoContractEntity entity = dao.get(id);
				if(entity != null) {
					//删除附件
					for(IFile file : fileService.getFileList(InvoiceNoContractConstant.MODULE_NAME, InvoiceNoContractConstant.FILE_BUSI_TYPE, entity.getInvoiceId())) {
						fileService.deleteFile(file.getFileId());
					}
					service.deleteByInvoiceId(entity.getInvoiceId());
					dao.delete(entity);
				}
			}
			
		}
		return OperResult.succeed(null,"删除成功",null);
	}
	
	/**
	 * <pre>
	 * 给实体类添加创建人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setCreaetInfo(UserProfile user, InvoiceNoContractEntity entity) {
		
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
	 * <pre>
	 * 给实体类添加修改人信息
	 * @param user
	 * @param entity
	 * </pre>
	 */
	
	private void setMOdifiedInfo(UserProfile user, InvoiceNoContractEntity entity) {

		entity.setModifiedBy(user.getName());
		entity.setModifiedById(user.getPersonId());
		entity.setModifiedDate(new Date());
	}
	
	/**
	 * 根据业务主键查找详单信息
	 * @param invoiceId
	 * @return
	 */
	public InvoiceNoContractEntity get(String invoiceId) {
		if(StringUtils.isBlank(invoiceId)) {
			return null;
		}
		return dao.get(invoiceId);
	}

	/**
	 * 保存详单信息
	 * @param entity
	 */
	public void update(InvoiceNoContractEntity entity) {
		
		dao.update(entity);
	}

	public OperResult<InvoiceNoContractEntity> financeDepartmentAdd(InvoiceNoContractEntity entity, UserProfile user) {
		entity.setStatus(InvoiceNoContractConstant.FINANCE_DEPARTMENT_ADD);
		saveOrUpdate(entity, user);
		return OperResult.succeed("保存成功", null, entity);
	}

}
