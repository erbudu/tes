package com.supporter.prj.ppm.prebid.review.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
	import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewEmpDao;
import com.supporter.prj.ppm.prebid.review.dao.PpmPrebidReviewEmpRpDao;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmp;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpForGrid;
import com.supporter.prj.ppm.prebid.review.entity.PpmPrebidReviewEmpRp;
import com.supporter.util.UUIDHex;
/**
 * @author 王康
 *
 */

@Service
@Transactional(TransManager.APP)
public class PpmPrebidReviewEmpService {
	

	@Autowired
	private PpmPrebidReviewEmpDao ppmPrebidReviewEmpDao;
	@Autowired
	private PpmPrebidReviewEmpRpDao ppmPrebidReviewEmpRpDao;	
	
	/**
	 * 初始化加载负责人表单数据
	 * @param reviewEmpId
	 * @param user
	 * @return
	 */
	public PpmPrebidReviewEmpForGrid initEditOrViewPageReviewEmp(String reviewEmpId,UserProfile user) {
		if(StringUtils.isNotBlank(reviewEmpId)&&reviewEmpId !="") {
			String hql = " from " + PpmPrebidReviewEmpRp.class.getName() + " where reviewEmpId = ?";
			List<PpmPrebidReviewEmpRp> ppmPrebidReviewEmpRpList = ppmPrebidReviewEmpRpDao.find(hql,reviewEmpId);
			String rpId ="";
			if(ppmPrebidReviewEmpRpList.size()!=0){
				for (int i = 0; i < ppmPrebidReviewEmpRpList.size(); i++) {
					PpmPrebidReviewEmpRp ppmPrebidReviewEmpRp = ppmPrebidReviewEmpRpList.get(i);
					rpId = ppmPrebidReviewEmpRp.getRpId();
				}
			}
			PpmPrebidReviewEmp ppmPrebidReviewEmp = (PpmPrebidReviewEmp) this.ppmPrebidReviewEmpDao.get(reviewEmpId);
			PpmPrebidReviewEmpRp ppmPrebidReviewEmpRp = (PpmPrebidReviewEmpRp) this.ppmPrebidReviewEmpRpDao.get(rpId);
			PpmPrebidReviewEmpForGrid ppmPrebidReviewEmpForGrid = new PpmPrebidReviewEmpForGrid();
			ppmPrebidReviewEmpForGrid.setReviewEmpId(reviewEmpId);
			ppmPrebidReviewEmpForGrid.setPersonId(ppmPrebidReviewEmp.getPersonId());
			ppmPrebidReviewEmpForGrid.setPersonName(ppmPrebidReviewEmp.getPersonName());
			ppmPrebidReviewEmpForGrid.setRpId(rpId);
			ppmPrebidReviewEmpForGrid.setReviewPointsId(ppmPrebidReviewEmpRp.getReviewPointsId());
			ppmPrebidReviewEmpForGrid.setReviewRoleId(ppmPrebidReviewEmp.getReviewRoleId());
			return ppmPrebidReviewEmpForGrid;
		}
		return null;
	}
	/**
	 * saveOrUpdate 保存或更新对象
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReviewEmp saveOrUpdateForEmp(UserProfile user, PpmPrebidReviewEmp ppmPrebidReviewEmp, Map<String, Object> valueMap) {
		// 保存评审人员信息
		if (ppmPrebidReviewEmp.getAdd()==1) {
			this.ppmPrebidReviewEmpDao.update(ppmPrebidReviewEmp);
			return ppmPrebidReviewEmp;
		}
		ppmPrebidReviewEmp.setReviewEmpId(UUIDHex.newId());
		this.ppmPrebidReviewEmpDao.save(ppmPrebidReviewEmp);
		return ppmPrebidReviewEmp;
	}
	
	/**
	 * saveOrUpdate 保存或更新对象
	 * @param user
	 * @param ppmReportingStart
	 * @param valueMap
	 * @return
	 */
	public PpmPrebidReviewEmpRp saveOrUpdateForEmpRp(UserProfile user, PpmPrebidReviewEmpRp ppmPrebidReviewEmpRp, Map<String, Object> valueMap) {
		if (ppmPrebidReviewEmpRp.getAdd()==1) {
			ppmPrebidReviewEmpRp.setReviewPointsId(ppmPrebidReviewEmpRp.getReviewPointsId());
			this.ppmPrebidReviewEmpRpDao.update(ppmPrebidReviewEmpRp);
			return ppmPrebidReviewEmpRp;
		}
		// 保存评审人员信息
		ppmPrebidReviewEmpRp.setRpId(UUIDHex.newId());
		ppmPrebidReviewEmpRp.setReviewPointsId(ppmPrebidReviewEmpRp.getReviewPointsId());
		this.ppmPrebidReviewEmpRpDao.save(ppmPrebidReviewEmpRp);
		return ppmPrebidReviewEmpRp;
	}
}
