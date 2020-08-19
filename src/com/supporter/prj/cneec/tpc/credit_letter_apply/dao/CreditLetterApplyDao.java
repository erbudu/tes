package com.supporter.prj.cneec.tpc.credit_letter_apply.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.credit_letter_apply.entity.CreditLetterApply;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.runtime.entity.WfProcBusiEntity;
import com.supporter.util.CommonUtil;

@Repository
public class CreditLetterApplyDao extends MainDaoSupport<CreditLetterApply, String> {
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param creditLetterApply
	 * @return
	 */
	public List<CreditLetterApply> findPage(JqGrid jqGrid, CreditLetterApply creditLetterApply,String authFilter){
		if (creditLetterApply != null){
			//查询
			String keyword = creditLetterApply.getKeyword();
			if(StringUtils.isNotBlank(keyword)){
				jqGrid.addHqlFilter(
						" projectName like ? or contractName like ? or recordFilingNo like ? or contractNO like ? or creditLetterOrderNo like ? ", 
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%",
						"%" + keyword + "%");
			}
			//条件过滤
			Integer swfStatus = creditLetterApply.getSwfStatus();
			if (swfStatus != null){//状态
				jqGrid.addHqlFilter(" swfStatus = ? ", swfStatus);
			}
	        //根据备案编号倒叙排列
			jqGrid.addSortPropertyDesc("creditLetterOrderNo");
			jqGrid.addSortPropertyDesc("createdDate");
		}
		jqGrid.addHqlFilter(authFilter);
		return this.retrievePage(jqGrid);
	}

	/**
	 * 获取最大流水号
	 * @param NoHead
	 * @return
	 */
	public Integer getCreditLetterApplyIndex(String NoHead) {
		Integer count = 1;
		String Hql = "select max(cla.creditLetterOrderNo) from " + CreditLetterApply.class.getName() + " as cla where 1=1 and cla.creditLetterOrderNo like ?";
		List<String> list = this.find(Hql, "%" + NoHead + "%");
		if (list.size() > 0) {
			String str = list.get(0);
			if (str != null || !"".equals(CommonUtil.trim(str))) {
				String count_s = str.substring(str.length() - 5);
				Integer b = Integer.parseInt(count_s);
				count = b > 0 ? (b + 1) : 1;
			}
		}
		return count;
	}

	/**
	 * 根据业务单ID获取流程业务实体
	 * 
	 * @param entityId
	 * @return
	 */
	public WfProcBusiEntity getWfProcBusiEntity(String entityId) {
		String Hql = " from " + WfProcBusiEntity.class.getName() + " where 1=1 and entityId = ? order by recordId desc";
		List<WfProcBusiEntity> list = this.retrieve(Hql, entityId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
