package com.supporter.prj.eip.code_share.code.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.eip.code_share.code.entity.EntitySalesContract;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

/**
 * @Title: 销售合同
 * @Description: CS_ENTITY_SALES_CONTRACT.
 * @author Administrator
 * @date 2019-07-17 16:46:39
 * @version V1.0
 *
 */
@Repository
public class EntitySalesContractDao extends MainDaoSupport<EntitySalesContract, String> {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格
	 * @param contract 销售合同对象
	 * @return List<EntitySalesContract>
	 */
	public List<EntitySalesContract> findPage(UserProfile user, JqGrid jqGrid,
			EntitySalesContract contract) {
		if (contract != null) {
			if (StringUtils.isNotBlank(contract.getPrjLib())) {
				String hql = "prjLib=?";
				jqGrid.addHqlFilter(hql, contract.getPrjLib());
			}
			if (StringUtils.isNotBlank(contract.getPrjId())) {
				String hql = "prjId=?";
				jqGrid.addHqlFilter(hql, contract.getPrjId());
			}
			// 搜索关键字
			String keyword = contract.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "contractNo like ? or contractName like ? or prjName like ?";
				jqGrid.addHqlFilter(hql, likeKeyword, likeKeyword, likeKeyword);
			}
		}
		jqGrid.addSortPropertyDesc("createdDate");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 根据项目库和销售合同ID获取销售合同对象
	 * @param prjLib 项目库
	 * @param saleContractId 销售合同ID
	 * @return EntitySalesContract
	 */
	public EntitySalesContract getContractInLibById(String prjLib, String saleContractId) {
		String recId = CommonUtil.trim(prjLib)  + "_" + CommonUtil.trim(saleContractId);
		EntitySalesContract contract = this.get(recId);
		return contract;
	}
	
	/**
	 * 根据项目库和销售合同ID或编号获取销售合同对象
	 * @param prjLib 项目库
	 * @param saleContractIdOrNo 销售合同ID或者编号
	 * @return EntitySalesContract
	 */
	public EntitySalesContract getContractByIdOrNo(String prjLib, String saleContractIdOrNo) {
		String hql = "from " + EntitySalesContract.class.getName()
				+ " where prjLib=? and (contractId=? or contractNo=?)";
		return findUniqueResult(hql, prjLib, saleContractIdOrNo, saleContractIdOrNo);
	}

}
