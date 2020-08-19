package com.supporter.prj.cneec.tpc.out_invoice.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.cneec.tpc.out_invoice.entity.OutInvoice;

/**   
 * @Title: Entity
 * @Description: 功能模块表
 * @author liyinfeng
 * @date 2017-11-22 16:25:07
 * @version V1.0   
 *
 */
@Repository
public class OutInvoiceDao extends MainDaoSupport < OutInvoice, String > {
	
	/**
	 * 分页查询
	 * @param jqGrid
	 * @param contractIds 模块ids
	 * @return
	 */
	public List<OutInvoice> findPage(UserProfile user, JqGrid jqGrid, OutInvoice report) {
		Object[] params = new Object[]{false};
		StringBuffer hql = new StringBuffer("from "+OutInvoice.class.getName()+" where 1=1 ");
		String authHql = "";
		if(report != null){
			//查询
			String prjName = report.getPrjName();
			if(StringUtils.isNotBlank(prjName)){
				jqGrid.addHqlFilter(
						"prjName like ? or contractName like ? or ownerName like ? or to_char(contractActMount) like ?", 
						"%" + prjName + "%","%" + prjName + "%","%" + prjName + "%","%" + prjName + "%");
//				hql.append(" and reportTitle like ? or empName like ? ");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
//				params = ArrayUtils.add(params, "%" + reportTitle + "%");
			}
//			 //状态过滤
//	        if(report.getInvoiceStatus() > -1){
//	        	jqGrid.addHqlFilter("invoiceStatus = ?" ,report.getInvoiceStatus());
////	        	hql.append(" and reportStatus = ? ");
////				params = ArrayUtils.add(params, report.getSettlementStatus());
//	        }
	        //授权
//	        authHql = EIPService.getAuthorityService().getHqlFilter(user, ReportConstant.MODULE_ID, AuthConstant.AUTH_OPER_NAME_SETVALREPORT);
			
//	        jqGrid.addHqlFilter(authHql);
			
			jqGrid.addSortPropertyDesc("invoiceOutId");
		}
		//日志
//		EIPService.getLogService(LogConstant.INFO_TYPE_DEBUG).info("hql:" + hql.toString() + "\t params:" + params+"\t authHql:"+authHql);
		return this.retrievePage(jqGrid);
	}

	
	/**
	 * 获取所有的报告记录.
	 * @param user
	 * @return
	 */
	public List<OutInvoice> getReportList() {
		StringBuffer hql = new StringBuffer("from "+OutInvoice.class.getName()+" where 1=1 ");
		List<OutInvoice> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 判断名字是否重复
	 * 
	 * @param contractId
	 * @param contractName
	 * @return
	 */
//	public boolean checkNameIsValid(String contractId, String contractName) {
//		String hql = null;
//		List retList = null;
//		if (StringUtils.isBlank(contractId)) {// 新建时
//			hql = "from " + Report.class.getName() + " where contractName = ?";
//			retList = this.retrieve(hql, contractName);
//		} else {// 编辑时
//			hql = "from " + Report.class.getName() + " where contractId != ? and contractName = ?";
//			retList = this.retrieve(hql, contractId, contractName);
//		}
//		if (CollectionUtils.isEmpty(retList)) {
//			return true;
//		}
//		return false;
//	}
	
}
