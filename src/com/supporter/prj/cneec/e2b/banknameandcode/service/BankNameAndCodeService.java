package com.supporter.prj.cneec.e2b.banknameandcode.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.e2b.banknameandcode.dao.BankNameAndCodeDao;
import com.supporter.prj.cneec.e2b.banknameandcode.entity.BankNameAndCode;
import com.supporter.prj.cneec.e2b.constant.AuthConstant;
import com.supporter.prj.cneec.e2b.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: BankNameAndCodeService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2018-11-08
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class BankNameAndCodeService {

	@Autowired
	private BankNameAndCodeDao bankNameAndCodeDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, BankNameAndCode.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 数据维护权限过滤
	 * @param userProfile
	 * @param bankNameAndCode
	 */
	public void getAuthCanExecute(UserProfile userProfile, BankNameAndCode bankNameAndCode) {
		// 权限验证
		AuthUtil.canExecute(userProfile, AuthConstant.AUTH_OPER_NAME_SETVAL, BankNameAndCode.MODULE_ID, bankNameAndCode.getCodeId(), bankNameAndCode);
	}

	/**
	 * 获取银企直联对象集合
	 * @param user
	 * @param jqGrid
	 * @param bankNameAndCode
	 * @return
	 */
	public List<BankNameAndCode> getGrid(UserProfile user, JqGrid jqGrid, BankNameAndCode bankNameAndCode) {
		String authFilter = getAuthFilter(user);
		return this.bankNameAndCodeDao.findPage(jqGrid, bankNameAndCode, authFilter);
	}

	/**
	 * 获取单个银企直联对象
	 * @param codeId
	 * @return
	 */
	public BankNameAndCode get(String codeId) {
		return this.bankNameAndCodeDao.get(codeId);
	}

	/**
	 * 获取银企直联对象ListPage集合
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<BankNameAndCode> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<BankNameAndCode> listPage = new ListPage<BankNameAndCode>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.bankNameAndCodeDao.getListPage(listPage, parameters, authFilter);
		return listPage;
	}

}
