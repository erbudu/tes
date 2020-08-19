/**
 * 
 */
package com.supporter.prj.ppm.prj_org.base_info.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.ppm.prj_org.base_info.dao.PrjAddrDao;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.ppm.prj_org.base_info.entity.PrjAddr;
import com.supporter.util.UUIDHex;

/**
 *<p>Title: PrjAddrService</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月25日
 * 
 */
@Service
public class PrjAddrService {
	
	@Autowired
	private PrjAddrDao prjAddrDao;
	
	
	public PrjAddr getEntityByPrjId(String prjId) {
		
		if(prjId==null) {
			return null;
		}
		
		PrjAddr result = this.prjAddrDao.findUniqueResult("prjId", prjId);
		return result;
	}
	
	//保存更新
	public void saveOrUpdate(Prj prj) {
		if(prj == null) {
			return;
		}
		
		PrjAddr uniqueResult = prjAddrDao.findUniqueResult("prjId",prj.getPrjId());
		if(uniqueResult == null) {//新建
			PrjAddr prjAddr = new PrjAddr();
			prjAddr.setAddrId(UUIDHex.newId());
			prjAddr.setPrjId(prj.getPrjId());
			prjAddr.setCountryId(prj.getCountryId());
			prjAddr.setCountryEName(prj.getCountryEName());
			prjAddr.setCountryCName(prj.getCountryCName());
			prjAddr.setProvinceId(prj.getProvinceId());
			prjAddr.setProvinceEName(prj.getProvinceEName());
			prjAddr.setProvinceCName(prj.getProvinceCName());
			prjAddr.setAddrC(prj.getAddrC());
			prjAddr.setAddrE(prj.getAddrE());
			this.prjAddrDao.save(prjAddr);
		}else {//更新
			uniqueResult.setCountryId(prj.getCountryId());
			uniqueResult.setCountryEName(prj.getCountryEName());
			uniqueResult.setCountryCName(prj.getCountryCName());
			uniqueResult.setProvinceId(prj.getProvinceId());
			uniqueResult.setProvinceEName(prj.getProvinceEName());
			uniqueResult.setProvinceCName(prj.getProvinceCName());
			uniqueResult.setAddrC(prj.getAddrC());
			uniqueResult.setAddrE(prj.getAddrE());

			this.prjAddrDao.update(uniqueResult);
		}
	}
	

}
