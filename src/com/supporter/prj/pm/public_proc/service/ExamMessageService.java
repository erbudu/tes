package com.supporter.prj.pm.public_proc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.swf.runtime.dao.WfTaskPfmResultDao;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.entity.WfTaskPfmResult;
import com.supporter.util.CommonUtil;


/**   
 * @Title: Service
 * @Description: OA_HISTORY_SWF_EXAM.
 * @author T
 * @date 2017-09-30 10:27:57
 * @version V1.0   
 *
 */
@Service
public class ExamMessageService {
	@Autowired
	private WfTaskPfmResultDao wfTaskPfmResultDao;
	
	/**
	 * 根据AssignerId判断是不是加签人
	 * @param entity
	 * @return
	 */
	public boolean getSignerByAssignerId(WfExam wfExam){
		boolean isSigner=false;
		WfTaskPfmResult result = wfTaskPfmResultDao.get(wfExam.getRecordId());
		if (result != null) {
			if (CommonUtil.trim(result.getAssignerId()).length() > 0) {//说明是加签人
				isSigner=true;
			}
		}
		return isSigner;
	}
	
}

