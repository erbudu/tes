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
import com.supporter.prj.linkworks.oa.consignment.entity.OaSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.PcDefProc;
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
public class PcSwfDefProcDao extends MainDaoSupport<PcDefProc, String> {
	//获取全部流程
	public List<PcDefProc> getAll() {
		String hql = "from "+PcDefProc.class.getName()+" where proc_name not "
					+" in ('付款申请','合格供应商评审','项目预算变更申请审批','分包合同信息确认流程',"
					+" '保函申请_ONE','月报审批流程','国外（现场）付款申请','月报审批','分包合同变更流程')";
		return this.find(hql);
	}

}
