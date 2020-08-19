package com.supporter.prj.ppm.poa.icc.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.icc.dao.CoordinationDao;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.role.entity.Role;

@Service
public class CoordinationService {
	@Autowired
	private CoordinationDao coordinationDao;
	//public static final String COTYPE_CODETABLE="CorType";
    @Autowired
	private PrjInfo PrjInfo;

	public List<Coordination> getGrid(UserProfile user, JqGrid jqGrid, Coordination coordination,String prjId) {
		List<Coordination> list = coordinationDao.findPage(user, jqGrid, coordination,prjId);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Coordination coordinations = list.get(i);
				coordinations.setLinkManAndPhone(coordinations.getLinkmanName() + coordinations.getLinkmanTel());
				list.set(i, coordinations);
				//prjInfo.PrjInformation(prjId)
			}
			jqGrid.setRows(list);
		}
		return list;
	}
	public Coordination initData(String iccId ,String prjId,UserProfile user) {
		Coordination coordination = new Coordination();
		if (StringUtils.isBlank(iccId)) {
			coordination.setIccId(com.supporter.util.UUIDHex.newId());
			coordination.setNew(true);
			coordination.setStatus(0);
			
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = PrjInfo.PrjInformation(prjId);
			coordination.setPrjId(prj.getPrjId());
			coordination.setPrjCName(prj.getPrjCName());
			coordination.setPrjEName(prj.getPrjEName());
			coordination.setProNo(prj.getPrjNo());
			coordination.setPrjSrcDept(prj.getDeptName());
			coordination.setPrjSrcDeptNo(prj.getDeptId());
			coordination.setPrjSrcNo(prj.getCreatedById());
			coordination.setLinkmanName(user.getName());
			return coordination;
		} else {
			coordination = this.coordinationDao.get(iccId);
			coordination.setNew(false);
			return coordination;
		}
	}

	public Coordination saveOrUpdate(Coordination coordination, UserProfile user) {
		 if (coordination == null) {
				return null;
			}
		 Coordination isInDb=coordinationDao.get(coordination.getIccId());
		 Coordination ret = null;
			if (isInDb==null) {
				coordination.setCreatedById(user.getPersonId());
				coordination.setCreatedBy(user.getName());
				coordination.setCreatedDate(new Date());
				if (user.getDept() != null) {
					coordination.setCreateDeptId(user.getDept().getDeptId());
					coordination.setCreatedByDept(user.getDept().getName());
				} else {
					coordination.setCreateDeptId("");
					coordination.setCreatedByDept("");
				}
				coordination.setNew(false);
				String cotypeId = coordination.getCoTypeId();
				if(StringUtils.isNotBlank(cotypeId)) {
					String cotypeName = EIPService.getComCodeTableService().getCodetableItem(cotypeId).getItemValue();
					coordination.setCoTypeName(cotypeName);
					}
				coordinationDao.save(coordination);
				ret = coordination;
			} else {
				coordination.setCreatedById(user.getPersonId());
				coordination.setCreatedBy(user.getName());
				coordination.setCreatedDate(new Date());
				if (user.getDept() != null) {
					coordination.setCreateDeptId(user.getDept().getDeptId());
					coordination.setCreatedByDept(user.getDept().getName());
				} else {
					coordination.setCreateDeptId("");
					coordination.setCreatedByDept("");
				}
				coordination.setModifiedDate(new Date());
				coordination.setModifiedName(user.getName());
				coordination.setModifiedId(user.getPersonId());
				String cotypeId = coordination.getCoTypeId();
				if(StringUtils.isNotBlank(cotypeId)) {
					String cotypeName = EIPService.getComCodeTableService().getCodetableItem(cotypeId).getItemValue();
					coordination.setCoTypeName(cotypeName);
					}
				coordinationDao.update(coordination);
				ret = coordination;
			}
			return ret;

	}
	public Coordination edit(Coordination coordination, UserProfile user) {// System.out.println(negotiationRecord.getIsNew());
	
			Coordination coordinations=this.coordinationDao.get(coordination.getIccId());
			coordinations.setStatus(Integer.valueOf(0));
			coordinations.setModifiedDate(new Date());
			coordinations.setModifiedName(user.getName());
			coordinations.setModifiedId(user.getPersonId());
			coordinations.setCoDesc(coordination.getCoDesc());
			coordinations.setCoTitle(coordination.getCoTitle());
			coordinations.setCoTypeId(coordination.getCoTypeId());
			coordinations.setLinkmanName(coordination.getLinkmanName());
			coordinations.setLinkmanTel(coordination.getLinkmanTel());
			coordinations.setPrjSrcDept(coordination.getPrjSrcDept());
			coordinations.setPrjSrcDeptNo(coordination.getPrjSrcDeptNo());
			coordinations.setPrjCName(coordination.getPrjCName());
			coordinations.setPrjTrgDept(coordination.getPrjTrgDept());
			coordinations.setProNo(coordination.getProNo());
			coordinations.setPrjEName(coordination.getPrjEName());
			coordinations.setPrjId(coordination.getPrjId());
			//coordination.setCoDesc(c);
			String cotypeId = coordinations.getCoTypeId();
			if(StringUtils.isNotBlank(cotypeId)) {
				String cotypeName = EIPService.getComCodeTableService().getCodetableItem(cotypeId).getItemValue();
				coordinations.setCoTypeName(cotypeName);
			}
			this.coordinationDao.update(coordinations);
		
		
		return coordination;
	}
	public void delete(UserProfile user, String CoordinationId) {
		if (StringUtils.isNotBlank(CoordinationId)) {
			for (String CoordinationIds : CoordinationId.split(",")) {
				this.coordinationDao.delete(CoordinationIds);
				// EIPService.getLogService("CPP_SUPPLIER").info(user, "ɾ����Ӧ��", "ɾ����Ӧ�̳ɹ�" ,
				// null, null);
			}
		}
	}
	public void update(Coordination coordination) {
		this.coordinationDao.update(coordination);
		
	}
	public Coordination get(String iccId) {
		Coordination coordination = this.coordinationDao.get(iccId);
		return coordination;
	}
	public Coordination getById(String coordinationId, UserProfile user) {
		Coordination coordination = this.coordinationDao.get(coordinationId);
		if (coordination == null) {
			Coordination coordinations = new Coordination();
			return coordinations;
		} else {
			return coordination;
		}
	}
	public Coordination checkInProc(String prjId, UserProfile user) {
		List<Coordination> coordination = this.coordinationDao.getByPrjId(prjId,user);
		Coordination coordinations = new Coordination();
		if (coordination != null) {
			for(int i=0;i<coordination.size();i++) {
				if(coordination.get(i).getStatus()==1) {
					coordinations.setIccId("F");
					break;
					
				}
				else {coordinations.setIccId("T");
				}
			}
			return coordinations;	
		} else {
			coordinations.setIccId("T");
			return coordinations;
		}
	}
	public Coordination commit(UserProfile user, Coordination coordination, Map<String, Object> valueMap) {
		
				String logMessage = MessageFormat.format("提交内部协调", "(" + coordination.getPrjId()+ ")" + coordination.getModifiedDate());
				//EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS_REVIEW).info(user, LogConstant.PUBLISH_LOG_ACTION_REVIEW, logMessage, coordination, null);
				System.out.println(logMessage);
				return coordination;
	
	}
	public List<Map<String, String>> getNoticePerson() {
		
		String department = Coordination.MANAGEMENT_DEPARTMENT;//经营管理部:部门编号
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Role role1 = EIPService.getRoleService().getRole(Coordination.ROLE_RECORDER);//角色：经营管理部-项目信息记录员
		//Role role2 = EIPService.getRoleService().getRole(BaseInfoConstant.ROLE_BIDFILING);//角色：经营管理部-投议标备案及许可人员
		Role role3 = EIPService.getRoleService().getRole(Coordination.ROLE_ExpatriateManagers);//角色：经营管理部-经营管理部外派事业部经理
		
		/*角色*/
		Map<String,String> mapRole = new HashMap<String, String>();
		mapRole.put("role1", role1.getName());
		//mapRole.put("role2", role2.getName());
		mapRole.put("role3", role3.getName());
		
		/*角色下人员*/
		String role1Person = "";
		List<Person> role1PersList = getPersonListByRole(department,role1.getRoleId());
		for (int i = 0; i < role1PersList.size(); i++) {
			role1Person += role1PersList.get(i).getName()+"、";
		}
		mapRole.put("role1Person",role1Person);
		
		
		/*String role2Person = "";
		List<Person> role2PersList = getPersonListByRole(department,role2.getRoleId());
		for (int i = 0; i < role2PersList.size(); i++) {
			role2Person += role2PersList.get(i).getName()+"、";
		}
		mapRole.put("role2Person",role2Person);*/
		
		/*角色下人员*/
		String role3Person = "";
		List<Person> role3PersList = getPersonListByRole(department,role3.getRoleId());
		for (int i = 0; i < role3PersList.size(); i++) {
			role3Person += role3PersList.get(i).getName()+"、";
		}
		mapRole.put("role3Person",role3Person);
		list.add(mapRole);
		return list;
	}
private List<Person> getPersonListByRole(String deptId,String roleId) {
		
		Dept dept = EIPService.getDeptService().getDept(deptId);
		Role role = EIPService.getRoleService().getRole(roleId);
		List<Person> personList = EIPService.getRoleService().getPersonsForDept(role, dept);
		return personList;
		
	}
}
