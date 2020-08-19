package com.supporter.prj.linkworks.oa.outside_account_manager.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.account.entity.AccountEntity;
import com.supporter.prj.eip.role.entity.RoleMemberRespDeptV;
import com.supporter.prj.eip.role.service.RoleMemberRespDeptVService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.netin.entity.OaNetin;
import com.supporter.prj.linkworks.oa.outside_account_manager.dao.OutsideAccountManagerDao;
import com.supporter.prj.linkworks.oa.outside_account_manager.dao.OutsideAccountManagerRecDao;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManager;
import com.supporter.prj.linkworks.oa.outside_account_manager.entity.OutsideAccountManagerRec;
import com.supporter.util.CommonUtil;

@Service
public class OutsideAccountManagerService {
	@Autowired
	private OutsideAccountManagerDao outsideAccountManagerDao;
	
	@Autowired
	private OutsideAccountManagerRecDao outsideAccountManagerRecDao;
	
	@Autowired
	private OutsideAccountManagerRecService outsideAccountManagerRecService;
	
	/**
	 * 根据主键id获得实体
	 * @param managerId
	 * @return
	 */
	public OutsideAccountManager get(String managerId){
		return outsideAccountManagerDao.get(managerId);
	}
	
	/**
	 * 分页查询数据
	 * @param jqGrid
	 * @param OutsideAccountManager
	 * @return
	 */
	public List<OutsideAccountManager> getGrid(JqGrid jqGrid, OutsideAccountManager OutsideAccountManager){
		return outsideAccountManagerDao.findPage(jqGrid, OutsideAccountManager);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param managerId
	 * @param user
	 * @return
	 */
	public OutsideAccountManager initEditOrViewPage(String managerId, UserProfile user){
		if (StringUtils.isBlank(managerId)){//新建
			OutsideAccountManager outsideAccountManager = new OutsideAccountManager();
			outsideAccountManager.setManagerId(com.supporter.util.UUIDHex.newId());
			outsideAccountManager.setStatus(OutsideAccountManager.DRAFT);
			if (user != null){
				outsideAccountManager.setCreatedBy(user.getName());
				outsideAccountManager.setCreatedById(user.getPersonId());
			}
			outsideAccountManager.setCreatedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			//新建即保存草稿
			this.outsideAccountManagerDao.save(outsideAccountManager);
			return outsideAccountManager;
		}else{//编辑
			return outsideAccountManagerDao.get(managerId);
		}
	}
	
	/**
	 * 查看
	 * @param managerId
	 * @return
	 */
	public OutsideAccountManager viewPage(String managerId){
		if (StringUtils.isNotBlank(managerId)){
			OutsideAccountManager outsideAccountManager = outsideAccountManagerDao.get(managerId);
			return outsideAccountManager;
		}
		return null;
	}
	
	/**
	 * 保存或更新
	 * @param user
	 * @param outsideAccountManager
	 * @return
	 */
	public OutsideAccountManager saveOrUpdate(UserProfile user, OutsideAccountManager outsideAccountManager){
		if (outsideAccountManager != null){
			if (user != null){
				outsideAccountManager.setModifiedBy(user.getName());
				outsideAccountManager.setModifiedById(user.getPersonId());
			}
			outsideAccountManager.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			this.outsideAccountManagerDao.update(outsideAccountManager);
		}
		return outsideAccountManager;
	}
	
	public void update(OutsideAccountManager entity){
		this.outsideAccountManagerDao.update(entity);
	}
	
	/**
	 * 保存并提交
	 * @param user
	 * @param outsideAccountManager
	 * @return
	 */
	public OutsideAccountManager commit(UserProfile user, OutsideAccountManager outsideAccountManager){
		if (outsideAccountManager != null){
			if (user != null){
				outsideAccountManager.setModifiedBy(user.getName());
				outsideAccountManager.setModifiedById(user.getPersonId());
			}
			outsideAccountManager.setModifiedDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			outsideAccountManager.setStatus(OaNetin.PROCESSING);
			this.outsideAccountManagerDao.update(outsideAccountManager);
		}
		return outsideAccountManager;
	}
	
	/**
	 * 删除
	 * @param managerId
	 */
	public void batchDel(String managerId){
		if (StringUtils.isNotBlank(managerId)){
			//先删除维护单下的所有人员信息
			this.outsideAccountManagerRecService.deletedInner(managerId);
			//再删除维护单
			this.outsideAccountManagerDao.delete(managerId);
		}
	}
	
	/**
	 * 获取维护单下人员所属部门ID,除重
	 * @param managerId
	 * @return
	 */
	public String getRecDeptIds(String managerId){
		StringBuffer sb = new StringBuffer();
		List<OutsideAccountManagerRec> recList = outsideAccountManagerRecDao.getOutsideAccountManagerRecsByDeptId(managerId, "all");
		HashSet<String> hashSet = new HashSet<String>();
		if (recList != null){
			for (OutsideAccountManagerRec rec : recList){
				hashSet.add(rec.getDeptId());
			}
		}
		if (hashSet != null){
			int i = 0;
			for (String set : hashSet){
				if (i == 0){
					sb.append(set);
				}else{
					sb.append(","+set);
				}
				i++;
			}
		}
		return sb.toString();
	}
	
	/**
	 * 获取相关部门管理员ID
	 * @param deptIds
	 * @return
	 */
	public String getDeptManagerIds(String managerId){
		List<AccountEntity> list = this.outsideAccountManagerDao.getRoleMemberByDeptIds(getRecDeptIds(managerId), "OUTSIDE_ACCOUNT_DEPT_MANAGER");
		HashSet<String> set = new HashSet<String>();
		if (list != null && list.size() > 0){
			for (AccountEntity resp : list){
				set.add(resp.getPersonId());
			}
			StringBuffer sb = new StringBuffer();
			int i = 0;
			for (String personId : set){
				if (i == 0){
					sb.append(personId);
				}else{
					sb.append(","+personId);
				}
				i++;
			}
			return sb.toString();
		}
		return "";
	}
}
