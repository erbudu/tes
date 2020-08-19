package com.supporter.prj.cneec.emp_mapping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.cneec.emp_mapping.dao.EmpMappingDao;
import com.supporter.prj.eip.transaction.TransManager;

@Service
@Transactional(TransManager.APP)
public class EmpMappingService {
	
	@Autowired
	private EmpMappingDao empMappingDao;
	
	/**
	 * 根据6.0人员ID获取映射ID
	 * @param persionId
	 * @return
	 */
	public String getEmpId(String personId){
		return this.empMappingDao.getEmpId(personId);
	}
}
