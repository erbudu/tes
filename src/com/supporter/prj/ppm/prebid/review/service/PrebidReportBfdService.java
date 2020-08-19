/**
 * 
 */
package com.supporter.prj.ppm.prebid.review.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.ppm.prebid.report.dao.ProjectReportBfdDao;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfd;
import com.supporter.prj.ppm.prebid.report.entity.PpmPrebidReportBfdF;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReview;

/**
 *<p>Title: BFDService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年8月26日
 * 
 */
@Service
public class PrebidReportBfdService {

	@Autowired
	private ProjectReportBfdDao bfdDao;
	
	@Autowired
	private PrebidReportBfd_FService bfd_fService;
	

	/**
	 * <p>获取用印业务</p>
	 * @return
	 */
	public String getUseSealBusiness() {
		return PpmPrebidReview.USE_SEAL_BUSINESS;
	}
	
	/**
	 * <pre>功能:根据启动申报主键和码表中资料清单的ID获取资料清单数据</pre>
	 * <pre>描述:根据启动申报主键和码表资料清单项的id可以确定到对应的而且唯一的，已经保存过的资料清单项</pre>
	 * <pre>调用:1.启动申报编辑页面初始化数据</pre>
	 * @param prbidReportId 资料清单业务表单实体类主键
	 * @param itemId 资料清单id
	 * @return 资料清单业务表单实体类数据
	 */
	public List<PpmPrebidReportBfd> getDataListEdit(String prbidReportId, String bfdTypeId) {
		if(prbidReportId == null || prbidReportId == "" && bfdTypeId == null || bfdTypeId == "") {
			return null;
		}
		String hql = "from "+PpmPrebidReportBfd.class.getName()+" where prbidReportId = ? and bfdTypeId = ?";
		List<PpmPrebidReportBfd> list = bfdDao.find(hql,prbidReportId,bfdTypeId);
		return list;
	}
	
	/**
	 * <pre>根据启动申报业务表单实体类主键删除资料清单信息</pre>
	 * <pre>1.启动申报单项删除的时候调用</pre>
	 * @param bidStartUpId 启动申报业务表单实体类主键
	 */
	public void deleteByprbidReportId(String prbidReportId) {
		bfdDao.deleteByProperty("prbidReportId", prbidReportId);
	}

	/**
	 * <pre>初始化确认用印文件</pre>
	 * @return
	 */
	public List<PpmPrebidReportBfd> initPrintFile(String prbidReportId) {
		List<PpmPrebidReportBfd> list = bfdDao.findBy("prbidReportId", prbidReportId);
		return list;
	}

	/**
	 * <pre>标记用印文件</pre>
	 * <pre>逻辑：标记前先将是否用印文件设为否，再将需要用印的设为是</pre>
	 * @param bfdIds 资料清单主键
	 */
	public void markUsePrintFile(String bfdIds, String prbidReportId) {
		if (bfdIds == null || bfdIds == "") {
		} ;
		List<PpmPrebidReportBfdF> entityList = bfd_fService
				.getPpmPrebidReportBfdFByPrbidReportId(prbidReportId);
		for (PpmPrebidReportBfdF PpmPrebidReportBfdF : entityList) {
			PpmPrebidReportBfdF.setIsUseSeal(0);
			bfd_fService.updateWhole(PpmPrebidReportBfdF);
		}
		String[] bfdIdArr = bfdIds.split(",");
		for (int i = 0; i < bfdIdArr.length; i++) {//用主键和清单文件类型确定数据的唯一性
			String hql = "from " + PpmPrebidReportBfd.class.getName() + " where bfdTypeId = ? and prbidReportId = ?";
			PpmPrebidReportBfd ppmPrebidReportBfd = bfdDao.findUniqueResult(hql, (bfdIdArr[i]),prbidReportId);
			ppmPrebidReportBfd.setIsUseSeal(1);
			bfdDao.update(ppmPrebidReportBfd);
			bfd_fService.markUsePrintFile(ppmPrebidReportBfd.getBfdId());
		}
	}

	public PpmPrebidReportBfd get(String bfdId) {
		return bfdDao.get(bfdId);
	}

	public String backtUseSealFile(String promaryKey, String fuSealFileId,
			String fuSealFileName) {
		// TODO Auto-generated method stub
		return null;
	}
}
