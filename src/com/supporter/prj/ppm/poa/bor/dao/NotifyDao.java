package com.supporter.prj.ppm.poa.bor.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.ppm.poa.bor.entity.BidNotify;
import com.supporter.prj.ppm.poa.bor.entity.Notify;
import com.supporter.util.CommonUtil;


@Repository
public class NotifyDao extends MainDaoSupport<Notify, String> {
	public List< Notify> getByPersonId( String prjId) {
		String hql = " from " + Notify.class.getName() + 
			      " where NOTIFY_ID=? and status=2 ";
		List listNotify = find(hql, new Object[] { CommonUtil.trim(prjId) });

	    if ((listNotify == null) || (listNotify.size() == 0)) {
	    	return null;
	    } 
	    else {
	    	
	    	return listNotify;
	    }
	}
	public  BidNotify getByPrjId( String prjId) {
		String hql = " from " + BidNotify.class.getName() + 
			      " where PRJ_ID=? ";
		List list = find(hql, new Object[] { CommonUtil.trim(prjId) });

	    if ( (list.size() > 0)) {
	    	return (BidNotify) list.get(0);
	    } 
	    else {
	    	BidNotify bidNotify=new BidNotify();
	    	bidNotify.setRecordId("F");
	    	return bidNotify;
	    	
	    }
	}
	public  Notify getByBidNotifyId( String recordId) {
		String hql = " from " + Notify.class.getName() + 
			      " where bidNotifyId=? ";
		List list = find(hql, new Object[] { CommonUtil.trim(recordId) });

	    if ( (list.size() > 0)) {
	    	return (Notify) list.get(0);
	    } 
	    else {
	    	Notify notify=new Notify();
	    	notify.setRecordId("F");
	    	return notify;
	    	
	    }
	}
	
}
