/**
 * 
 */
package com.supporter.prj.ppm.prebid.review.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.supporter.prj.ppm.prebid.report.dao.ProjectReportBfdFDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReport;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.prebid.report.service.ProjectReportService;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewDao;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: BFD_FService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月27日
 * 
 */
@Service
public class PrebidReportBfd_FService {
	@Autowired
	private ProjectReportBfdFDao bfdFDao;
	@Autowired
	private PpmPrebidReviewDao ppmPrebidReviewDao;
	@Autowired
	private PrebidReportBfdService bfdService;
	@Autowired
	private ProjectReportService ProjectReportService; 
	
	public List<PpmPrebidReportBfdF> getPpmPrebidReportBfdFByPrbidReportId(String prbidReportId) {
		if(prbidReportId == "") { return null;}
		List<PpmPrebidReportBfdF> entity = bfdFDao.findBy("prbidReportId", prbidReportId);
		return entity;
	}
	
	public void updateWhole(PpmPrebidReportBfdF entity) {
		bfdFDao.update(entity);
	}

	/**
	 * <pre>标记用印文件</pre>
	 * @param bfdId 资料清单主键
	 */
	public void markUsePrintFile(String bfdId) {
		List<PpmPrebidReportBfdF> list = bfdFDao.findBy("bfdId", bfdId);
		for(int i=0;i<list.size();i++) {
			PpmPrebidReportBfdF PpmPrebidReportBfdF = list.get(i);
			PpmPrebidReportBfdF.setIsUseSeal(1);
			bfdFDao.update(PpmPrebidReportBfdF);
		}
	}

	/**
	 * <p>用印文件列表</p>
	 * @param prbidReportId 业务主键
	 * @return 
	 */
	public List<Map<String, String>> getUseSealGrid(String prbidReviewId) {
		PpmPrebidReview ppmPrebidReview = ppmPrebidReviewDao.get(prbidReviewId);
		String prbidReportId = this.ProjectReportService.getPrbidReportIdByPrjId(ppmPrebidReview.getPrjId());
		System.out.println("prbidReportId="+prbidReportId);
		if(prbidReportId == null || prbidReportId == "") {
			return null;
		}
		List<Map<String,String>> li = new ArrayList<Map<String,String>>();
		String hql = "from "+PpmPrebidReportBfdF.class.getName()+" where prbidReportId = ? and isUseSeal = ?";
		List<PpmPrebidReportBfdF> list = bfdFDao.find(hql,prbidReportId,1);
		System.out.println(JSONArray.toJSONString(list));
		for(int i = 0;i<list.size();i++) {
			Map<String,String> map = new HashMap<String, String>();
			PpmPrebidReportBfd ppmPrebidReportBfd = bfdService.get(list.get(i).getBfdId());
			map.put("sealFileId", UUIDHex.newId());//用印文件主键
			map.put("fileUpBusinessId", list.get(i).getRecordId());//用印文件主键
			map.put("bfdTypeName", ppmPrebidReportBfd.getBfdTypeName());//文件类型
			map.put("fuFileName", list.get(i).getFuFileName());//文件名称
			map.put("fuFileId", list.get(i).getFuFileId());//文件id
			map.put("moduleId", PpmPrebidReport.MODULE_ID);//应用编号
			map.put("busiType", PpmPrebidReport.BUSI_TYPE);//附件上传业务一级分类
			li.add(map);
		}
		return li;
	}

	/**
	 * 获取用印后的文件 更新文件表，用于用印
	 * @param promaryKey
	 * @param fuSealFileId
	 * @param fuSealFileName
	 * @return
	 */
	public String backtUseSealFile(String promaryKey, String fuSealFileId,
			String fuSealFileName) {
		
		if(promaryKey == null || promaryKey == "") {
			return "fail";
		}
		
		PpmPrebidReportBfdF bfdF = bfdFDao.get(promaryKey);

		bfdF.setFuFileId(fuSealFileId);
		bfdFDao.update(bfdF);
		return "success";
	}
}
