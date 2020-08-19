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
public class OaSwfDefProcDao extends MainDaoSupport<OaSwfDefProc, String> {
	//获取全部流程
	public List<OaSwfDefProc> getAll() {
		String hql = "from "+OaSwfDefProc.class.getName()+" where 1=1 ";
		return this.find(hql);
	}

}
