package com.supporter.prj.ppm.poa.inr.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.inr.dao.NegotiationRecordDao;
import com.supporter.prj.ppm.poa.inr.entity.NegotiationRecord;
import com.supporter.prj.ppm.prj_org.base_info.common.PrjInfo;
import com.supporter.prj.ppm.prj_org.base_info.entity.Prj;

@Service
public class NegotiationRecordService {
	@Autowired
	private NegotiationRecordDao negotiationRecordDao;
	@Autowired
	private PrjInfo PrjInfo;

	public List<NegotiationRecord> getGrid(UserProfile user, JqGrid jqGrid, NegotiationRecord negotiationRecord ,String prjId) {
		return this.negotiationRecordDao.findPage(user, jqGrid, negotiationRecord,prjId);
	}

	public NegotiationRecord saveOrUpdate(NegotiationRecord negotiationRecord, UserProfile user) {
		if(negotiationRecord==null)
		{
			return negotiationRecord;
		}	
		NegotiationRecord isIn = this.negotiationRecordDao.get(negotiationRecord.getInrId());
		NegotiationRecord ret=new NegotiationRecord();
		if (isIn==null) {
			negotiationRecord.setStatus(Integer.valueOf(0));
			negotiationRecord.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				negotiationRecord.setCreatedByDept(user.getDept().getDeptId());
			} else {
				negotiationRecord.setCreatedByDept("");
			}

			negotiationRecord.setCreatedById(user.getPersonId());
			negotiationRecord.setCreatedDate(new Date());
			negotiationRecord.setModifiedDate(new Date());
			negotiationRecord.setModifiedName(user.getAccount().getName());
			negotiationRecord.setModifiedId(user.getAccount().getAccountId());
			negotiationRecord.setNew(false);
			this.negotiationRecordDao.save(negotiationRecord);
			ret=negotiationRecord;
		} else {
			
			negotiationRecord.setCreatedDate(new Date());
			negotiationRecord.setModifiedDate(new Date());
			negotiationRecord.setModifiedName(user.getAccount().getName());
			negotiationRecord.setModifiedId(user.getAccount().getAccountId());
			negotiationRecord.setStatus(Integer.valueOf(0));
			
			this.negotiationRecordDao.update(negotiationRecord);
			ret=negotiationRecord;
		}
		return ret;
	}
	public NegotiationRecord initData(String inrId ,String prjId,UserProfile user) {
		NegotiationRecord negotiationRecord = new NegotiationRecord();
		if (StringUtils.isBlank(inrId)) {
			negotiationRecord.setInrId(com.supporter.util.UUIDHex.newId());
			negotiationRecord.setNew(true);
			negotiationRecord.setStatus(0);
			
			// 获取左侧项目信息栏选中项目的信息
			Prj prj = PrjInfo.PrjInformation(prjId);
			negotiationRecord.setPrjId(prj.getPrjId());
			
			return negotiationRecord;
		} else {
			negotiationRecord = this.negotiationRecordDao.get(inrId);
			negotiationRecord.setNew(false);
			return negotiationRecord;
		}
	}

	public NegotiationRecord edit(NegotiationRecord negotiationRecord, UserProfile user) {// System.out.println(negotiationRecord.getIsNew());
		String negotiationRecordId = negotiationRecord.getInrId();
		NegotiationRecord negotiationRecords = this.negotiationRecordDao.get(negotiationRecordId);
		if (negotiationRecords != null) {
			negotiationRecords.setInrTitle(negotiationRecord.getInrTitle());
			negotiationRecords.setInrDate(negotiationRecord.getInrDate());
			negotiationRecords.setInrAddress(negotiationRecord.getInrAddress());
			negotiationRecords.setInrPicName(negotiationRecord.getInrPicName());
			negotiationRecords.setInrDesc(negotiationRecord.getInrDesc());
			negotiationRecords.setRecorderName(negotiationRecord.getRecorderName());
			negotiationRecords.setInrOtherUnits(negotiationRecord.getInrOtherUnits());
			if(negotiationRecords.getStatus()==1) {
				negotiationRecords.setStatus(Integer.valueOf(1));
			}
			else {
			negotiationRecords.setStatus(Integer.valueOf(0));}
			negotiationRecords.setModifiedDate(new Date());
			negotiationRecords.setModifiedName(user.getName());
			negotiationRecords.setModifiedId(user.getPersonId());
			this.negotiationRecordDao.update(negotiationRecords);
			return negotiationRecord;
		} else {
			NegotiationRecord negotiationRecord1 = new NegotiationRecord();
			return negotiationRecord1;
		}
	}

	public NegotiationRecord changeStatus(NegotiationRecord negotiationRecord, UserProfile user) {
		String negotiationRecordId = negotiationRecord.getInrId();
		NegotiationRecord negotiationRecords = this.negotiationRecordDao.get(negotiationRecordId);
		if (negotiationRecords != null) {
			negotiationRecords.setInrTitle(negotiationRecord.getInrTitle());
			negotiationRecords.setInrDate(negotiationRecord.getInrDate());
			negotiationRecords.setInrAddress(negotiationRecord.getInrAddress());
			negotiationRecords.setInrPicName(negotiationRecord.getInrPicName());
			negotiationRecords.setInrDesc(negotiationRecord.getInrDesc());
			negotiationRecords.setRecorderName(negotiationRecord.getRecorderName());
			negotiationRecords.setInrOtherUnits(negotiationRecord.getInrOtherUnits());
			if(negotiationRecords.getStatus()==1) {
				negotiationRecords.setStatus(Integer.valueOf(1));
			}
			else {
			negotiationRecords.setStatus(Integer.valueOf(1));}
			negotiationRecords.setModifiedDate(new Date());
			negotiationRecords.setModifiedName(user.getName());
			negotiationRecords.setModifiedId(user.getPersonId());
			this.negotiationRecordDao.update(negotiationRecords);
			return negotiationRecord;
		} else {
			negotiationRecord.setStatus(Integer.valueOf(1));
			negotiationRecord.setCreatedBy(user.getName());
			if (user.getDept() != null) {
				negotiationRecord.setCreatedByDept(user.getDept().getDeptId());
			} else {
				negotiationRecord.setCreatedByDept("");
			}

			negotiationRecord.setCreatedById(user.getPersonId());
			negotiationRecord.setCreatedDate(new Date());
			negotiationRecord.setModifiedDate(new Date());
			negotiationRecord.setModifiedName(user.getAccount().getName());
			negotiationRecord.setModifiedId(user.getAccount().getAccountId());
			negotiationRecord.setNew(false);
			this.negotiationRecordDao.save(negotiationRecord);
			return  negotiationRecord;
		}
	}

	public NegotiationRecord getById(String negotiationRecordId, UserProfile user) {
		NegotiationRecord negotiationRecord = this.negotiationRecordDao.get(negotiationRecordId);
		if (negotiationRecord == null) {
			NegotiationRecord negotiationRecords = new NegotiationRecord();
			return negotiationRecords;
		} else {
			return negotiationRecord;
		}
	}

	public void delete(UserProfile user, String negotiationRecordIds) {
		if (StringUtils.isNotBlank(negotiationRecordIds)) {
			for (String negotiationRecordId : negotiationRecordIds.split(",")) {
				this.negotiationRecordDao.delete(negotiationRecordId);
				// EIPService.getLogService("CPP_SUPPLIER").info(user, "ɾ����Ӧ��", "ɾ����Ӧ�̳ɹ�" ,
				// null, null);
			}
		}
	}
}
