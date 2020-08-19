package com.supporter.prj.cneec.tpc.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.control.dao.TpcProjectDao;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: TpcProjectService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-09-19
 * @version: V1.0
 */
@Service("tpcProjectService")
public class TpcProjectService {

	@Autowired
	private TpcProjectDao projectDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, RegisterProject.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 
	 * @param prjLst
	 * @return
	 */
	private List<RegisterProject> refineProject(List<RegisterProject> projectLst) {
		if (CollectionUtils.isNotEmpty(projectLst)) {
			List<RegisterProject> refinedLst = new ArrayList<RegisterProject>();
			for (RegisterProject project : projectLst) {
				refinedLst.add(project);
			}
			return refinedLst;
		}
		return projectLst;
	}

	/**
	 * 获取注册项目ListPage集合
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<RegisterProject> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		ListPage<RegisterProject> listPage = new ListPage<RegisterProject>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);
		listPage = this.projectDao.getListPage(listPage, parameters, authFilter);
		List<RegisterProject> refinedLst = refineProject(listPage.getRows());
		listPage.setRows(refinedLst);
		return listPage;
	}

	public List<RegisterProject> getGrid(UserProfile user, JqGrid jqGrid, RegisterProject registerProject) {
		String authFilter = getAuthFilter(user);
		return this.projectDao.findPage(jqGrid, registerProject, authFilter);
	}

}
