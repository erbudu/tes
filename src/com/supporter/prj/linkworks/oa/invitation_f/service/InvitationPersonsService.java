package com.supporter.prj.linkworks.oa.invitation_f.service;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.invitation_f.dao.InvitationPersonsDao;
import com.supporter.prj.linkworks.oa.invitation_f.entity.InvitationPersons;


@Service
public class InvitationPersonsService {
	@Autowired
	private InvitationPersonsDao codeDao;
	public List<InvitationPersons> getGrid(UserProfile user, JqGrid jqGrid,
			String invitationId,String pagetype) {
		List<InvitationPersons> persons= codeDao.findPage(jqGrid, invitationId);
		if(pagetype!=null&&pagetype.equals("view")){
			return persons;
		}
		int maxPersonCount = 5;
		int canAddCount = maxPersonCount - persons.size();
		int a = canAddCount > 0 ? canAddCount : 0;
		if(persons!=null){
			for (int i = 0; i < a ;i++) {
				InvitationPersons person = new InvitationPersons();
				person.setRecordId(com.supporter.util.UUIDHex.newId());
				person.setInvitationId(invitationId);
				persons.add(person);
			}		
		}
		jqGrid.setRows(persons);
		jqGrid.setRowCount(jqGrid.getRowCount()+a);
		return persons;
	}

	
	public void deleteRec(String invitationId, String delIds) {
		if (StringUtils.isNotBlank(delIds)) {
			for (String delId : delIds.split(",")) {
				InvitationPersons rpnoDb = (InvitationPersons) this.codeDao
						.get(delId);
				if (rpnoDb != null) {
					this.codeDao.delete(delId);
				}
			}
		}
	}
	//删除主表信息是删除从表
	public void deleteRecByPlcId(String id) {
		List<InvitationPersons> rec = codeDao
				.getPersonsByApply(id);
		codeDao.delete(rec);
	}
}
