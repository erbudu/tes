package com.supporter.prj.linkworks.oa.abroad.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.abroad.constants.AbroadAuthConstant;
import com.supporter.prj.linkworks.oa.abroad.entity.Abroad;
import com.supporter.prj.linkworks.oa.report.constant.AuthConstant;
import com.supporter.prj.linkworks.oa.report.constant.ReportConstant;
import com.supporter.prj.linkworks.oa.report.entity.Report;

/**
 * @Title: Entity
 * @Description: 功能模块表
 * @author linxiaosong
 * @version V1.0
 * 
 */
@Repository
public class AbroadDAO extends MainDaoSupport<Abroad, String> {

	/**
	 * 分页查询
	 * 
	 * @param jqGrid
	 * @param attr
	 * @return
	 */
	public List<Abroad> findPage(UserProfile user, JqGrid jqGrid, String attr) {
		List<Abroad> retList = new ArrayList<Abroad>();
		StringBuffer sb = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sb.append(" from ").append(Abroad.class.getName())
				.append(" where 1=1 ");
		if (StringUtils.isNotBlank(attr)) {
			sb
					.append(" and (applierName like ? or applierDept like ? or organizingUnit like ? or prjName like ? or prjChar like ? or "
							+ " tgtCountries like ? or passedCountries like ? or abroadUnit like ? or expenseSource like ? or notifiedUnit like ? ) ");
			CollectionUtils.addAll(params, new String[] { "%" + attr + "%",
					"%" + attr + "%", "%" + attr + "%", "%" + attr + "%",
					"%" + attr + "%", "%" + attr + "%", "%" + attr + "%",
					"%" + attr + "%", "%" + attr + "%", "%" + attr + "%" });
		}
		// 授权
		String authHql = EIPService.getAuthorityService().getHqlFilter(user,
				AbroadAuthConstant.MODULE_ID,
				AbroadAuthConstant.AUTH_OPER_NAME_SETVALABROAD);
		if (StringUtils.isNotBlank(authHql)) {
			sb.append("and (" + authHql + ")");
		}
		sb.append(" order by leavingDate desc");
		// jqGrid.addHqlFilter(authHql);
		this.retrievePage(jqGrid, sb.toString(), params.toArray());
		List<Abroad> list = jqGrid.getRows();
		for (Abroad obj : list) {
			Abroad abroad = obj;
			retList.add(abroad);
		}
		List<Abroad> listAbroad = new ArrayList<Abroad>();
		for (Abroad abroad : retList) {
			if (abroad.getRecordStatus() == Abroad.DRAFT) {
				abroad.setStatuts("草稿");
			} else {
				if (abroad.getRecordStatus() == Abroad.PROCESSING) {
					abroad.setStatuts("审核中");
				} else {
					abroad.setStatuts("已归档");
				}
			}
			listAbroad.add(abroad);
		}
		jqGrid.setRows(listAbroad);
		return retList;
	}

	public List<Abroad> getReportList() {
		StringBuffer hql = new StringBuffer("from "+Abroad.class.getName()+" where 1=1 ");
		List<Abroad> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}

}
