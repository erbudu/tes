package com.supporter.prj.cneec.tpc.order_change.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.cneec.tpc.contract_change.entity.ContractSeal;
import com.supporter.prj.cneec.tpc.order_change.entity.OrderSeal;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;

@Repository
public class OrderSealDao extends MainDaoSupport<OrderSeal, String> {
	/**
	 * 根据合同changeId查询对应的合同变更信息
	 */
	public OrderSeal getSealByChangeId(String changeId) {
		String hql = "from OrderSeal where changeId = ?";
		List<OrderSeal> sealList = this.find(hql, changeId);
		if(sealList != null && sealList.size() > 0){
			return sealList.get(0);
		}else{
			return null;
		}		 
	}
	
	/**
	 * 获取当前年月最大流水号
	 * 
	 * @param NoHead
	 * @return
	 */
	public Integer getBusinessNoIndex(String NoHead) {
		Integer count = 1;
		String hql = "select max(t.businessNo) from " + OrderSeal.class.getName() + " t where t.businessNo like ?";
		List<String> list = this.find(hql, NoHead + "%");
		if (list.size() > 0) {
			String str = list.get(0);
			if (StringUtils.isNotBlank(str)) {
				String count_s = str.substring(str.length() - 3);
				Integer b = Integer.parseInt(count_s);
				count = b > 0 ? (b + 1) : 1;
			}
		}
		return count;
	}

}
