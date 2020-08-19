package com.supporter.prj.cneec.tpc.supplier.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.supplier.dao.SupplierBankAccountDao;
import com.supporter.prj.cneec.tpc.supplier.dao.SupplierDao;
import com.supporter.prj.cneec.tpc.supplier.entity.Supplier;
import com.supporter.prj.cneec.tpc.supplier.entity.SupplierBankAccount;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;

@Service
@Transactional(TransManager.APP)
public class SupplierService {
	@Autowired
	private SupplierDao supplierDao;
	
	@Autowired
	private SupplierBankAccountDao supplierBankAccountDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, Supplier.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param supplier
	 */
	public void getAuthCanExecute(UserProfile userProfile, Supplier supplier) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, Supplier.MODULE_ID, supplier.getSupplierId(), supplier);
	}

	/**
	 * 初始化新建供应商
	 * 
	 * @param user 用户信息
	 */
	public Supplier newSupplier(UserProfile user){
		Supplier supplier = new Supplier();
		loadingSupplier(supplier, user);
		return supplier;
	}
	
	/**
	 * 装填基础信息
	 * 
	 * @param user 用户信息
	 */
	public Supplier loadingSupplier(Supplier lsupplier_N, UserProfile user){
		lsupplier_N.setCreatedBy(user.getName());
		lsupplier_N.setCreatedById(user.getPersonId());
		lsupplier_N.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lsupplier_N.setModifiedBy(user.getName());
		lsupplier_N.setModifiedById(user.getPersonId());
		lsupplier_N.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		lsupplier_N.setSupplierControlStatusCode(Supplier.EFFECTIV);
		lsupplier_N.setSupplierControlStatus(Supplier.EFFECTIV_DESC);
		return lsupplier_N;
	}
	
	/**
	 * 进入新建、编辑或查看页面需要加载的信息。
	 */
	public Supplier initEditOrviewPage(String supplierId, UserProfile user){
		Supplier supplier;
		if (StringUtils.isBlank(supplierId)){//新建
			supplier = newSupplier(user);
			supplier.setSupplierId(UUIDHex.newId());
			supplier.setIsNew(true);
		}else {//编辑
			supplier = this.supplierDao.get(supplierId);
			supplier.setIsNew(false);
		}
		return supplier;
	}
	
	/**
	 * 分页展示供应商数据
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 */
	public List<Supplier> getGrid(UserProfile user, JqGrid jqGrid, Supplier supplier){
		String authFilter = getAuthFilter(user);
		return supplierDao.findPage(jqGrid, supplier, authFilter);
	}
	
	/**
	 * 分页展示收款单位数据
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 */
	public List<SupplierBankAccount> getBankAccountGrid(UserProfile user, JqGrid jqGrid, String supplierId, String pagetype){
		List<SupplierBankAccount> supplierBankAccount = supplierBankAccountDao.findPage(jqGrid,supplierId);
		if (pagetype != null && pagetype.equals("view")) {
			return supplierBankAccount;
		}
		return supplierBankAccount;
	}
	
	/**
	 * 保存或更新
	 */
	public Supplier saveOrUpdate(UserProfile user, Supplier supplier, Map<String, Object> valueMap){
		//获取相关文件名称
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("SUPPLIER", "filesName", supplier.getSupplierId());
		StringBuffer sb = new StringBuffer();
		for(IFile file:list){
			sb.append(file.getFileName()).append(",");
		}
		if(list !=null && list.size()>0){
			sb.deleteCharAt(sb.length() - 1);
		}
		supplier.setFilesName(sb.toString());
		if(supplier.getIsNew()){
			supplier = loadingSupplier(supplier, user);
			this.supplierDao.save(supplier);
		}else{
			supplier.setModifiedBy(user.getName());
			supplier.setModifiedById(user.getPersonId());
			supplier.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.supplierDao.update(supplier);
		}
		return supplier;
	}
	
	/**
	 * 失效
	 */
	public void batchLnvalid(UserProfile user, String supplierIds) {
		if (StringUtils.isNotBlank(supplierIds)) {
			for (String supplierId : supplierIds.split(",")) {
				Supplier supplier = get(supplierId);
				supplier.setModifiedBy(user.getName());
				supplier.setModifiedById(user.getPersonId());
				supplier.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
				supplier.setSupplierControlStatusCode(Supplier.FAILURE);
				supplier.setSupplierControlStatus(Supplier.FAILURE_DESC);
				this.supplierDao.update(supplier);
			}
		}
	}
	
	/**
	 * 删除
	 */
	public void delete(UserProfile user, String supplierIds) {
		if (StringUtils.isNotBlank(supplierIds)) {
			Supplier supplier;
			for (String supplierId : supplierIds.split(",")) {
				supplier = this.get(supplierId);
				if (supplier == null) continue;
				// 权限验证
				this.getAuthCanExecute(user, supplier);
				//先删除客户下的所有联系人信息
				this.supplierBankAccountDao.deleteInner(get(supplierId));
				//再删除相关附件
				deleteFile(supplierId);
				//最后删除客户信息
				this.supplierDao.delete(supplier);
			}
		}
	}
	
	/**
	 * 删除相关附件
	 */
	public void deleteFile(String supplierId){
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("SUPPLIER", "filesName", supplierId);
		if (CollectionUtils.isNotEmpty(list)){
			for(IFile file:list){
				fileUploadService.deleteFile(file.getFileId());
			}
		}
	}
	
	/**
	 * 获取单个供应商对象
	 */
	public Supplier get(String supplierId) {
		return this.supplierDao.get(supplierId);
	}
	
	/**
	 * 判断名字是否重复
	 */
	public boolean checkNameIsValid(String moduleId, String moduleName) {
		return this.supplierDao.checkNameIsValid(moduleId, moduleName);
	}
	
	/**
	 * 
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<Supplier> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<Supplier> listPage = new ListPage<Supplier>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.supplierDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
