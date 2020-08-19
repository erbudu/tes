package com.supporter.prj.linkworks.oa.consignment.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ibm.icu.text.SimpleDateFormat;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.consign.entity.WfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationPersons;
import com.supporter.util.CommonUtil;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0
 * 
 */
@Repository
public class ConsignmentDao extends MainDaoSupport<Consignment, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<Consignment> findPage(JqGrid jqGrid, String attr) {
		/*
		 * List<Consignment> retList = new ArrayList<Consignment>();
		 * StringBuffer sb = new StringBuffer(); List<Object> params = new
		 * ArrayList<Object>(); sb.append(" from
		 * ").append(Consignment.class.getName()).append(" t1, ")
		 * .append(WfConsignation.class.getName()).append(" t2 ").append( "
		 * where t1.consignId = t2.consignId "); // sb.append(" from
		 * ").append(Consignment.class.getName()).append( " // where 1=1 "); if
		 * (StringUtils.isNotBlank(attr)) { sb .append(" and ( t1.consignerName
		 * like ? or t1.consigneeName like ? ) ");
		 * 
		 * sb.append( " and ( consignerName like ? ) ");
		 * 
		 * CollectionUtils.addAll(params, new String[] { "%" + attr + "%", "%" +
		 * attr + "%" }); } sb.append(" or t1.history='T'"); sb.append(" order
		 * by t1.dateFrom desc "); this.retrievePage(jqGrid, sb.toString(),
		 * params.toArray()); List<Consignment[]> list = jqGrid.getRows(); for
		 * (Object[] obj : list) { Consignment consignment = (Consignment)
		 * obj[0]; WfConsignation consign = (WfConsignation) obj[1];
		 * consignment.setConsignType(consign.getConsignType());
		 * retList.add(consignment); } List<Consignment> listConsignment = new
		 * ArrayList<Consignment>();
		 */
		String key = attr;
		if (key != null) {
			key = "%" + key + "%";
			jqGrid.addHqlFilter(" consignerName like ? ", key);
		}
		List<Consignment> retList =  this.retrievePage(jqGrid);
		// 权限过滤
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		for (Consignment consignment : retList) {
			Date dateTo = null;
			Date dateFrom = null;
			Date today = null;
			try {
				String _dateTo = consignment.getDateTo();
				String _dateFrom = consignment.getDateFrom();
				if (StringUtils.isNotBlank(_dateTo)) {
					dateTo = df.parse(_dateTo);
				}
				if (StringUtils.isNotBlank(_dateFrom)) {
					dateFrom = df.parse(_dateFrom);
				}

				today = df.parse(df.format(new Date()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (dateTo != null && today.before(dateFrom)) {
				consignment.setFailureName("尚未生效");
			} else if (dateTo != null
					&& (consignment.getIsFailure() == 0 || today.after(dateTo))) {
				consignment.setFailureName("过期失效");
			} else {
				consignment.setFailureName("有效");
			}
		}
		jqGrid.setRows(retList);
		return retList;
	}

	/**
	 * 通过业务委托获id取业务授权
	 * 
	 * @param consignId
	 * @return
	 */
	public Consignment getByConsignId(String consignId) {
		String hql = " from " + Consignment.class.getName()
				+ " where consignId = ?";
		List<Consignment> list = this.find(hql, consignId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过多个业务委托id获取业务授权
	 * 
	 * @param consignId
	 * @return
	 */
	public List<Consignment> getByConsignIds(String consignIds) {
		if (StringUtils.isNotBlank(consignIds)) {
			StringBuffer sb = new StringBuffer();
			sb.append(" from " + Consignment.class.getName() + " where ");
			for (String consignId : consignIds.split(",")) {
				sb.append(" consignId = '" + consignId + "' or");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(sb.length() - 1);
			System.out.println(sb.toString());
			List<Consignment> list = this.find(sb.toString());
			System.out.println(list.size());
			return list;
		}
		return null;
	}

}
