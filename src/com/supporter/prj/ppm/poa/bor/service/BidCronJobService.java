package com.supporter.prj.ppm.poa.bor.service;

import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sun.org.apache.xml.internal.resolver.helpers.Debug;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.job_schedule.IJob;
import com.supporter.prj.ppm.poa.bor.dao.BidNotifyDao;
import com.supporter.prj.ppm.poa.bor.dao.NotifyDao;
import com.supporter.prj.ppm.poa.bor.entity.BidNotify;
import com.supporter.prj.ppm.poa.bor.entity.BidOpenResult;
import com.supporter.prj.ppm.poa.bor.entity.Notify;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.prj.ppm.prebid.review.service.PpmPrebidReviewService;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjBidInf;
import com.supporter.util.UUIDHex;

public class BidCronJobService implements IJob {

	@Override
	public void execute(JobExecutionContext arg0) {
		// TODO Auto-generated method stub

		PpmPrebidReviewService ppmPrebidReviewService = (PpmPrebidReviewService) SpringContextHolder
				.getBean(PpmPrebidReviewService.class);
		PrjInfo prjInfo = (PrjInfo) SpringContextHolder
				.getBean(PrjInfo.class);
		List<PpmPrebidReview> list = ppmPrebidReviewService.getProForPass();
		//System.out.println(list.size());
		BidNotifyDao bidNotifyDao = (BidNotifyDao) SpringContextHolder.getBean(BidNotifyDao.class);
		NotifyDao notifyDao = (NotifyDao) SpringContextHolder.getBean(NotifyDao.class);
		BidOpenResultService bidOpenResultService = (BidOpenResultService) SpringContextHolder.getBean(BidOpenResultService.class);
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String prjId = list.get(i).getPrjId();
				Prj prj =prjInfo.PrjInformation(prjId);
//				String endbidTime = "2019-10-16";
//				SimpleDateFormat Time = new SimpleDateFormat("yyyy-MM-dd");

//				Date start = null;
//				try {
//					start = Time.parse(endbidTime);
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				 PrjBidInf prjBidInf=prjInfo.getPrjBidInfInf(prjId);
				 
				 Date start=prjBidInf.getBidCloseTime();
				// Date start1=start;
				Date nowDate = new Date();
				long cha = nowDate.getTime() - start.getTime();
				double result = cha * 1.0 / (1000 * 60 * 60);
				// Date now = Time.parse(nowDate);
				BidNotify bidNotify = bidNotifyDao.getByPrjId(prjId);
				if (bidNotify == null && result > 72) {
					BidNotify bidNotifys=new BidNotify();
					bidNotifys.setRecordId(UUIDHex.newId());
					bidNotifys.setBidEndTime(start);
					bidNotifys.setNotifyTime(new Date());
					bidNotifys.setPrjId(prj.getPrjId());
					bidNotifys.setPrjName(prj.getPrjCName());
					bidNotifys.setPrjNo(prj.getPrjNo());
					bidNotifys.setStatus(1);
					bidNotifyDao.save(bidNotifys);
					Notify notify=new Notify();
					System.out.println(prj.getCreatedById());
					if(prj.getCreatedById().isEmpty()) {
						notify.setNotifyId("1");
					}
					notify.setNotifyId(prj.getCreatedById());
					notify.setNotifyName(prj.getCreatedBy());
					notify.setBidNotifyId(bidNotifys.getRecordId());
					notifyDao.save(notify);
					bidOpenResultService.sendNotices(prj.getCreatedById(), bidNotifys.getRecordId(), prj.getPrjCName(),prj.getPrjId());
				}
			}
		}
	}

}
