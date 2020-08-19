package com.supporter.prj.cneec.tpc.project_goods_bill.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.tpc.constant.AuthConstant;
import com.supporter.prj.cneec.tpc.project_goods_bill.dao.ProjectGoodsBillDao;
import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBill;
import com.supporter.prj.cneec.tpc.project_goods_bill.entity.ProjectGoodsBillVO;
import com.supporter.prj.cneec.tpc.util.AuthUtil;
import com.supporter.prj.core.orm.hibernate.ListPage;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: ProjectGoodsBillService
 * @Description: 业务操作类
 * @author: yanweichao
 * @date: 2017-11-08
 * @version: V1.0
 */
@Service
@Transactional(TransManager.APP)
public class ProjectGoodsBillVOService {

	@Autowired
	private ProjectGoodsBillDao projectGoodsBillDao;

	/**
	 * 数据显示权限过滤
	 * @param userProfile
	 * @return
	 */
	public String getAuthFilter(UserProfile userProfile) {
		String authFilter = AuthUtil.getAuthFilter(userProfile, ProjectGoodsBill.MODULE_ID, AuthConstant.AUTH_OPER_NAME_DATASHOW);
		return authFilter;
	}

	/**
	 * 拼装货品清单信息
	 * @param listPage
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	private List<ProjectGoodsBillVO> refineProjectGoodsBillVO(ListPage<ProjectGoodsBillVO> listPage, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		String authFilter = getAuthFilter(userProfile);
		List<ProjectGoodsBill> list = this.projectGoodsBillDao.getList(parameters, authFilter);
		List<ProjectGoodsBillVO> refinedList = listPage.getRows();
		Map<String, ProjectGoodsBillVO> contractMap = new HashMap<String, ProjectGoodsBillVO>();
		// 是否展开（默认折叠）
		boolean expand = Boolean.valueOf(String.valueOf(parameters.get("expand")));
		ProjectGoodsBillVO vo;
		for (ProjectGoodsBill bill : list) {
			// 设置一级项
			if (!contractMap.containsKey(bill.getContractId())) {
				vo = new ProjectGoodsBillVO(bill.getContractId(), bill.getContractId(), bill.getContractNo(), bill.getContractName());
				// 设置tree属性
				vo.putRootTreeVO(expand);
				refinedList.add(vo);
				contractMap.put(bill.getContractId(), vo);
			}
			// 默认均为二级项
			vo = new ProjectGoodsBillVO();
			BeanUtils.copyProperties(vo, bill);
			// 清除合同属性
			vo.clearContract();
			vo.setExpanded(expand);
			refinedList.add(vo);
		}
		listPage.setRowCount(list.size() + contractMap.size());
		return refinedList;
	}

	/**
	 * 获取货品清单信息
	 * @param page
	 * @param pageSize
	 * @param parameters
	 * @param userProfile
	 * @return
	 * @throws Exception
	 */
	public ListPage<ProjectGoodsBillVO> getListPage(int page, int pageSize, Map<String, Object> parameters, UserProfile userProfile) throws Exception {
		ListPage<ProjectGoodsBillVO> listPage = new ListPage<ProjectGoodsBillVO>();
		listPage.setPageNo(page);
		listPage.setPageSize(pageSize);
		listPage.setAutoCount(true);

		List<ProjectGoodsBillVO> refinedList = refineProjectGoodsBillVO(listPage, parameters, userProfile);
		listPage.setRows(refinedList);
		return listPage;
	}

	/**
	 * 
	 * @param user
	 * @param jqGrid
	 * @param keyword
	 * @return
	 * @throws ParseException 
	 */
	public List<String> findTreeGrid(UserProfile userProfile, Map<String, Object> parameters) throws ParseException {
		String authFilter = getAuthFilter(userProfile);
		List<String> retList = new ArrayList<String>();
		List<ProjectGoodsBill> list = this.projectGoodsBillDao.getList(parameters, authFilter);
		for (ProjectGoodsBill bill : list) {
			retList.add(bill.getBillId() + "," + bill.getContractId() + ",");
		}
		return retList;
	}

}
