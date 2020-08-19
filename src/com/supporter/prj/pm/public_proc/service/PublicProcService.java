package com.supporter.prj.pm.public_proc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.public_proc.dao.PublicProcDao;
import com.supporter.prj.pm.public_proc.entity.PublicProc;

@Service
@Transactional(TransManager.APP)
public class PublicProcService {
	@Autowired
	private PublicProcDao dao;

	public void updatePublicProcByEntityId(UserProfile user, String entityId, String entityName,
			String examOne, String examTwo, String examThree, String examFour) {
		PublicProc publicProc = dao.getPublicProcByEntityId(entityId);
		if(publicProc != null) {
			//更新
			publicProc.setExamOne(examOne);
			publicProc.setExamTwo(examTwo);
			publicProc.setExamThree(examThree);
			publicProc.setExamFour(examFour);
			this.dao.update(publicProc);
		}else {
			//新建
			PublicProc publicProcNew = new PublicProc();
			publicProcNew.setId(com.supporter.util.UUIDHex.newId());
			publicProcNew.setEntityId(entityId);
			publicProcNew.setEntityName(entityName);
			publicProcNew.setExamOne(examOne);
			publicProcNew.setExamTwo(examTwo);
			publicProcNew.setExamThree(examThree);
			publicProcNew.setExamFour(examFour);
			this.dao.save(publicProcNew);
		}
	}

}
