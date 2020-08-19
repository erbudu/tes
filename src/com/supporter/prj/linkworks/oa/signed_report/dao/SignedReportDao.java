package com.supporter.prj.linkworks.oa.signed_report.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.swf.runtime.entity.WfTaskPfmResult;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.signed_report.constants.SignedReportAuthConstant;
import com.supporter.prj.linkworks.oa.signed_report.entity.SignedReport;
import com.supporter.util.CommonUtil;

/**
 * @Title: DAO
 * @Description: 功能模块表
 * @version V1.0
 * 
 */
@Repository
public class SignedReportDao extends MainDaoSupport<SignedReport, String> {

	/**
	 * 分页查询
	 */
	public List<SignedReport> findPage(UserProfile user,JqGrid jqGrid, String reason, String swfStatus) {
		if (!user.getPersonId().equals("1")){//如果不是系统管理员
			String deptName = CommonUtil.trim(user.getDept().getName());
			String deptId = CommonUtil.trim(user.getDeptId());
			if (deptName.equals("北京兴侨国际工程技术有限公司") || deptId.equals("1000000099")){
				System.out.println("北京兴侨国际工程技术有限公司人员不允许查看签报，返回null！");
				return null;
			}//2018年4月26日
		}
		if (StringUtils.isNotBlank(reason)) {
			jqGrid.addHqlFilter(
				" reason like ? or signNo like ? or createdBy like ? or deptName like ? ",
				"%" + reason + "%", "%" + reason + "%", "%"
						+ reason + "%", "%" + reason + "%");
		}
		
		if(swfStatus != null){
			Integer s = Integer.parseInt(swfStatus);
			jqGrid.addHqlFilter(
					" swfStatus = ?  ", s);
		}
		// 状态过滤
//			if (signedReport.getSwfStatus() != null) {
//				System.out.println("11111："+signedReport.getSwfStatus());
//				jqGrid.addHqlFilter(" swfStatus = ? ", signedReport.getSwfStatus());
//			}
	
	//权限过滤
	String authHql = EIPService.getAuthorityService().getHqlFilter(user,SignedReportAuthConstant.MODULE_ID,SignedReportAuthConstant.AUTH_OPER_NAME_PAGESHOW);
	jqGrid.addHqlFilter(authHql);
		jqGrid.addSortPropertyAsc("swfStatus");
		//jqGrid.addSortPropertyDesc("reportFile");
		jqGrid.addSortPropertyDesc("createdDate");	
		
		List<SignedReport> list = this.retrievePage(jqGrid);
//		for(int i=0;i<list.size();i++){
//			SignedReport report = list.get(i);
//			report.setCreatedDate(report.getCreatedDate().substring(0, 10));
//			list.set(i, report);
//		}
		jqGrid.setRows(list);
		return list;
	}

	/**
	 * 判断名字是否重复
	 */
	public boolean checkNameIsValid(String warehouseId, String warehouseName) {
		String hql = null;
		List retList = null;
		if (StringUtils.isBlank(warehouseId)) {// 新建时
			hql = "from " + SignedReport.class.getName() + " where reason = ?";
			retList = this.retrieve(hql, warehouseName);
		} else {// 编辑时
			hql = "from " + SignedReport.class.getName()
					+ " where signedReportId != ? and reason = ?";
			retList = this.retrieve(hql, warehouseId, warehouseName);
		}
		if (CollectionUtils.isEmpty(retList)) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 获取最大编号
	 * @return
	 */
	public String getLastedNo(){	
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		String year=sdf.format(new Date());
		String hql = "select max(signNo) from "
			+ SignedReport.class.getName() +" where signNo like '签%号（"+year+"）'";
		List<String> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				return list.get(0);
			}
		}
		return "";
	}
	
	/**
	 * 验证编号是否可用
	 * @param signNo
	 * @param signedReportId
	 * @return
	 */
	public boolean verificationNo(String signNo, String signedReportId){
		String hql = " from " + SignedReport.class.getName() 
				+ " where signNo = '" + signNo + "' and signedReportId <> '" + signedReportId + "'";
		List<SignedReport> list = this.find(hql);
		if (list == null || list.size() == 0){//查询结果为空表示此编号可用没有重复
			return true;
		}
		return false;
	}

	public List<SignedReport> getSignedReportList() {
		StringBuffer hql = new StringBuffer("from " + SignedReport.class.getName()
				+ " where 1=1 ");
		List<SignedReport> list = this.find(hql.toString());
		if (list == null || list.size() == 0)
			return null;
		return list;
	}
	
	/**
	 * 根据taskInstanceId获取ExamId
	 * @return
	 */
	public String getExamId(String taskInstanceId){	
		String hql = "select recordId from "
			+ WfTaskPfmResult.class.getName() +" where taskInstanceId = '"+taskInstanceId+"' order  by completeDate desc";
		List<String> list = this.find(hql);
		if (list.size() > 0) {
			if (list.get(0) != null && !list.get(0).equals("")) {
				//System.out.println("list.get(0)==="+list.get(0));
				return list.get(0);
			}
		}
		return "";
	}
	
    public List<FileUpload> getFileUploadList(String moduleName, String busiType, String oneLevelId, String twoLevelId)
    {
        Map<String,String>  params = new HashMap<String,String>();
        String hql = getHql(moduleName, busiType, oneLevelId, twoLevelId, params);
        hql = (new StringBuilder(String.valueOf(hql))).append(" order by oneLevelId,displayOrder").toString();
        return retrieve(hql, params);
    }
    
    public String getHql(String moduleName, String busiType, String oneLevelId, String twoLevelId, Map<String,String> params)
    {
        params.put("moduleName", moduleName);
        StringBuffer hql = new StringBuffer();
        hql.append(" from  FileUpload f where f.moduleName = :moduleName");
        if(busiType.length() > 0)
        {
            params.put("busiType", busiType);
            hql.append(" and f.busiType = :busiType");
        } else
        {
            hql.append(" and (f.busiType is null or f.busiType='')");
        }
        if(oneLevelId.length() > 0)
        {
            String ids[] = oneLevelId.split(",");
            hql.append(" and (");
            for(int i = 0; i < ids.length; i++)
            {
                params.put((new StringBuilder("oneLevelId")).append(i).toString(), ids[i]);
                hql.append((new StringBuilder(" f.oneLevelId = :oneLevelId")).append(i).toString());
                if(i < ids.length - 1)
                    hql.append(" or");
            }

            hql.append(" )");
        } else
        {
            hql.append(" and (f.oneLevelId is null or f.oneLevelId='')");
        }
        if(twoLevelId.length() > 0)
        {
            String ids[] = twoLevelId.split(",");
            hql.append(" and (");
            for(int i = 0; i < ids.length; i++)
            {
                params.put((new StringBuilder("twoLevelId")).append(i).toString(), ids[i]);
                hql.append((new StringBuilder(" f.twoLevelId = :twoLevelId")).append(i).toString());
                if(i < ids.length - 1)
                    hql.append(" or");
            }

            hql.append(" )");
        } 
//        else{
//            hql.append(" and (f.twoLevelId is null or f.twoLevelId='')");
//        }
        return hql.toString();
    }
    
    /**
     * 获取部门内最大协议编号集合（不包含当前编号，且最大编号签报已审批完成）
     * @param signedReportId
     * @param deptId
     * @return
     */
    public List<Object[] > getMaxAgreementNos(String signedReportId, String deptId){
    	String ls_sql = " SELECT S.FIRST_AGREEMENT_ID, S.AGREEMENT_NO FROM OA_SIGNED_REPORT S WHERE S.AGREEMENT_NO IN "
    			+ " (SELECT MAX(T.AGREEMENT_NO) FROM OA_SIGNED_REPORT T WHERE T.DEPT_ID = '" + deptId + "' AND T.AGREEMENT_NO IS NOT NULL GROUP BY T.FIRST_AGREEMENT_ID) "
    			+ " AND S.SWF_STATUS = 2 AND S.SIGNED_REPORT_ID <> '" + signedReportId + "' ORDER BY S.AGREEMENT_NO DESC ";
    	Session session = getHibernateTemplate().getSessionFactory().openSession();
		try{
			Query query = session.createSQLQuery(ls_sql);
			List<Object[]> list = query.list();
			if (list.size() > 0 && list != null) {
				return list;
			}
		}finally{
			session.close();
		}
    	return null;
    }
    
    
    /**
     * 获取最大新签协议编号
     * @param year
     * @param deptId
     * @return
     */
    public String getNewMaxAgreementNo(){
    	String ls_sql = " SELECT t.agreement_no FROM OA_SIGNED_REPORT t WHERE t.agreement_no LIKE '%00' ORDER BY substr(t.agreement_no, 12) DESC ";
    	Session session = getHibernateTemplate().getSessionFactory().openSession();
    	try{
    		Query query = session.createSQLQuery(ls_sql);
    		List<Object> list = query.list();
    		if (list.size() > 0 && list != null) {
    			String newMaxAgreementNo = list.get(0).toString();
    			return newMaxAgreementNo;
    		}
    	}finally{
    		session.close();
    	}
    	return null;
    }

}
