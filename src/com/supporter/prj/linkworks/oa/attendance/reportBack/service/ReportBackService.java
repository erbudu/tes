/**
 * 
 */
package com.supporter.prj.linkworks.oa.attendance.reportBack.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.attendance.reportBack.dao.ReportBackDao;
import com.supporter.prj.linkworks.oa.attendance.reportBack.entity.ReportBackEntity;
import com.supporter.util.CommonUtil;

/**
 * @author YUEYUN
 * @date 2019年7月21日
 * 
 */
@Service
public class ReportBackService {
	
	@Autowired
	private ReportBackDao reportBackDao;

	public void update(ReportBackEntity entity) {
		reportBackDao.update(entity);
	}

	/**
	 *  	描述：此方法用于获取销假单对象实例
	 * @param reportBackId 销假单id
	 * @return 销假单对象实例
	 */
	public ReportBackEntity get(String reportBackId) {
		ReportBackEntity reportBackEntity = reportBackDao.get(reportBackId);
		return reportBackEntity;
	}

	/**
	 *   	描述：此方法用于更新销假单
	 * @param user 当前登陆用户
	 * @param reportBackEntity 销假单数据模型实体类
	 * @return 保存的销假单数据
	 */
	public ReportBackEntity saveOrUpdate(UserProfile user, ReportBackEntity reportBackEntity) {

		if(reportBackEntity!=null && reportBackEntity .getReportBackId() != "") {
			setModifiedInfo(user,reportBackEntity);
			reportBackDao.update(reportBackEntity);
			return reportBackEntity;
		}
		return null;
	}

	/**
	 * @param reportBackEntity
	 */
//	private void setCreateDate(ReportBackEntity reportBackEntity) {
//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//		String stringDate = format.format(date);
//		reportBackEntity.setCreateDate(stringDate);
//		
//	}

	/**
	 * 		描述：此方法用于设置修改人信息
	 * @param user 当前登陆的用户
	 * @param reportBackEntity
	 */
	private void setModifiedInfo(UserProfile user, ReportBackEntity reportBackEntity) {
		reportBackEntity.setModifiedBy(user.getName());
		reportBackEntity.setModifiedById(user.getPersonId());
		reportBackEntity.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * @param leaveId
	 * @return
	 */
	public List<ReportBackEntity> findEntityByLeaveId(String leaveId) {
		List<ReportBackEntity> list = reportBackDao.findBy("leaveId", leaveId);
		return list;
	}

	/**
	 * @param leaveId
	 * @return
	 */
	public Map<String,String> getProcAndStatus(String leaveId) {
		List<ReportBackEntity> entityModule = reportBackDao.findBy("leaveId",leaveId);
		if(entityModule != null) {
			HashMap<String,String> map = new HashMap<String,String>();
			for (ReportBackEntity reportBackEntity : entityModule) {
				map.put("procId", reportBackEntity.getProcId());
				map.put("status", String.valueOf(reportBackEntity.getStatus()));
				map.put("reportBackId",reportBackEntity.getReportBackId());
			}
			return map;
		}
		
		return null;
	}
	
	public void save(ReportBackEntity reportBackEntity) {
		reportBackEntity.setCreateDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		reportBackDao.save(reportBackEntity);
	}

	/**
	 * 描述：根据请假单id，在销假单中删除请假单id对应的销假单数据
	 * 注：此方法在请假单删除方法的servic中调用
	 */
	public void deleteByProperty(String id) {
		String leaveId = "leaveId";
		reportBackDao.deleteByProperty(leaveId, id);
	}
}

