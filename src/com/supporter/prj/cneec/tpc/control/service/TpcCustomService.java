package com.supporter.prj.cneec.tpc.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.control.dao.TpcCustomDao;
import com.supporter.prj.cneec.tpc.custom.entity.Custom;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: TpcCustomService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-19
 * @version: V1.0
 */
@Service("tpcCustomService")
public class TpcCustomService {

	@Autowired
	private TpcCustomDao customDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, Custom.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	private List<Custom> refineCustom(List<Custom> customLst) {
		if (CollectionUtils.isNotEmpty(customLst)) {
			List<Custom> refinedLst = new ArrayList<Custom>();
			for (Custom custom : customLst) {
				refinedLst.add(custom);
			}
			return refinedLst;
		}
		return customLst;
	}

	public ListPage<Custom> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<Custom> listPage = new ListPage<Custom>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.customDao.getListPage(listPage, parameters, authFilter);
		List<Custom> refinedLst = refineCustom(listPage.getRows());
		listPage.setRows(refinedLst);
		return listPage;
	}

}
