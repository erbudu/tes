package com.supporter.prj.linkworks.oa.consignment.service;

import com.ibm.icu.text.SimpleDateFormat;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.consign.entity.WfConsignation;
import com.supporter.prj.eip.swf.consign.entity.WfConsignationSpecified;
import com.supporter.prj.eip.swf.consign.service.WfConsignationService;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinService;
import com.supporter.prj.linkworks.oa.consignment.dao.AmSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.AmSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.BmSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.BmSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.CmSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.CmSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.ConsignmentDao;
import com.supporter.prj.linkworks.oa.consignment.dao.DmSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.DmSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.OaSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.OldOaConsignmentDao;
import com.supporter.prj.linkworks.oa.consignment.dao.PcSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.PcSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.dao.PlSwfConsignationDao;
import com.supporter.prj.linkworks.oa.consignment.dao.PlSwfDefProcDao;
import com.supporter.prj.linkworks.oa.consignment.entity.AmSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.AmSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.BmSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.BmSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.CmSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.CmSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.Consignment;
import com.supporter.prj.linkworks.oa.consignment.entity.DmSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.DmSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.OaSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.OldOaConsignment;
import com.supporter.prj.linkworks.oa.consignment.entity.PcDefProc;
import com.supporter.prj.linkworks.oa.consignment.entity.PcSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.PlSwfConsignation;
import com.supporter.prj.linkworks.oa.consignment.entity.PlSwfDefProc;
import com.supporter.prj.linkworks.oa.consignment.util.CommonUtils;
import com.supporter.util.CommonUtil;
import com.supporter.util.UUIDHex;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsignmentService {
	@Autowired
	private ConsignmentDao consignmentDao;
	@Autowired
	private BulletinService bulletinService;
	@Autowired
	private WfConsignationService wfConsignationService;
	@Autowired
	private OaSwfDefProcDao swfDao;
	@Autowired
	private PcSwfDefProcDao pcSwfDao;
	@Autowired
	private AmSwfDefProcDao amSwfDao;
	@Autowired
	private PlSwfDefProcDao plSwfDao;
	@Autowired
	private BmSwfDefProcDao bmSwfDao;
	@Autowired
	private DmSwfDefProcDao dmSwfDao;
	@Autowired
	private CmSwfDefProcDao cmSwfDao;
	@Autowired
	private PcSwfConsignationDao pcSwfConsignationDao;
	@Autowired
	private AmSwfConsignationDao amSwfConsignationDao;
	@Autowired
	private PlSwfConsignationDao plSwfConsignationDao;
	@Autowired
	private BmSwfConsignationDao bmSwfConsignationDao;
	@Autowired
	private DmSwfConsignationDao dmSwfConsignationDao;
	@Autowired
	private CmSwfConsignationDao cmSwfConsignationDao;
	@Autowired
	private OldOaConsignmentDao oldOaConsignmentDao;
	public SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public List<Consignment> getGrid(UserProfile user, JqGrid jqGrid,
			String attr) {
		return this.consignmentDao.findPage(jqGrid, attr);
	}

	public Consignment initEditOrViewPage(String consignmentId, UserProfile user) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(consignmentId)) {
			Consignment consignment = new Consignment();
			consignment.setConsignerId(user.getPersonId());
			consignment.setConsignerName(user.getName());
			consignment.setDateFrom(df.format(new Date()));
			consignment.setIsNew(true);
			consignment.setConsignmentId(UUIDHex.newId());
			return consignment;
		}
		Consignment consignment = (Consignment) this.consignmentDao
				.get(consignmentId);
		consignment.setIsNew(false);
		return consignment;
	}

	public Consignment saveOrUpdate(UserProfile user, Consignment consignment,
			Map<String, Object> valueMap) {
				
		Consignment con = (Consignment) this.consignmentDao.get(consignment
				.getConsignmentId());
		
		if (con == null) {
			if (consignment.getPublishBulletin() == null) {
				consignment.setPublishBulletin("F");
			} else {
				consignment.setPublishBulletin("T");
			}
			this.consignmentDao.save(consignment);
			if (consignment.getPublishBulletin() == "T") {
				this.bulletinService.addConsignment(consignment, user);
			}
		} else {
			if (consignment.getPublishBulletin() == null) {
				consignment.setPublishBulletin("F");
			} else {
				consignment.setPublishBulletin("T");
			}
			String dateFrom = consignment.getDateFrom();
			if (dateFrom.length() > 10) {
				consignment.setDateFrom(dateFrom.substring(0, 10));
			}
			String dateTo = consignment.getDateTo();
			if (dateTo.length() > 10) {
				consignment.setDateTo(dateTo.substring(0, 10));
			}
			this.consignmentDao.update(consignment);
			if (consignment.getPublishBulletin() == "T") {
				this.bulletinService.updateConsignment(consignment, user);
			}
		}
		return consignment;
	}

	public void delete(UserProfile user, String consignIds) {
		if (StringUtils.isNotBlank(consignIds)) {
			List<Consignment> list = this.consignmentDao
					.getByConsignIds(consignIds);
			for (Consignment consignment : list) {
				String consignmentId = consignment.getConsignmentId();
				this.consignmentDao.delete(consignmentId);

				OldOaConsignment oldEntity = getOldConsignment(consignmentId);
				if (oldEntity != null) {

					Long oldConsignmentId = oldEntity.getConsignmentId();

					delPcSwfConsignation(oldConsignmentId);

					delAmSwfConsignation(oldConsignmentId);

					delPlSwfConsignation(oldConsignmentId);

					delBmSwfConsignation(oldConsignmentId);

					delDmSwfConsignation(oldConsignmentId);

					delCmSwfConsignation(oldConsignmentId);

					delOldOaSwfConsignation(oldConsignmentId);

					this.oldOaConsignmentDao.delete(oldEntity);
				}

				this.bulletinService.deleteConsignment(consignment
						.getConsignmentId(), user);
			}
		}
	}

	public static void main(String[] args) {
		System.out.println(CommonUtil.format(CommonUtil.parseDate(CommonUtils
				.getNextDay(new Date())), "yyyy-MM-dd HH:mm:ss"));
	}

	public void stop(UserProfile user, String consignIds) {
		if (StringUtils.isNotBlank(consignIds)) {
			List<Consignment> list = this.consignmentDao
					.getByConsignIds(consignIds);
			for (Consignment consignment : list) {
				consignment.setIsFailure(Consignment.VALID);
				String dateTo = CommonUtil.format(CommonUtil
						.parseDate(CommonUtils.getNextDay(new Date())),
						"yyyy-MM-dd HH:mm:ss");
				consignment.setDateTo(dateTo);
				this.consignmentDao.update(consignment);
				String consignmentId = consignment.getConsignmentId();

				OldOaConsignment oldEntity = getOldConsignment(consignmentId);
				if (oldEntity != null) {
					oldEntity.setDateTo(consignment.getDateTo());
					this.oldOaConsignmentDao.update(oldEntity);

					abortPc(oldEntity);

					abortAm(oldEntity);

					abortPl(oldEntity);

					abortBm(oldEntity);

					abortDm(oldEntity);

					abortCm(oldEntity);

					abortOldOa(oldEntity);
				}

				if (consignment.getPublishBulletin() == "T") {
					this.bulletinService.updateConsignment(consignment, user);
				}
			}
		}
	}

	private void abortPc(OldOaConsignment oldEntity) {
		List<PcSwfConsignation> entities = this.pcSwfConsignationDao.find(
				getDelHql(PcSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (PcSwfConsignation pcSwfConsignation : entities) {
				pcSwfConsignation.setDateTo(oldEntity.getDateTo());
				this.pcSwfConsignationDao.update(pcSwfConsignation);
			}
		}
	}

	private void abortAm(OldOaConsignment oldEntity) {
		List<AmSwfConsignation> entities = this.amSwfConsignationDao.find(
				getDelHql(AmSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (AmSwfConsignation amSwfConsignation : entities) {
				Date dateTo = new Date();
				try {
					dateTo = this.sdf.parse(oldEntity.getDateTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				amSwfConsignation.setDateTo(dateTo);
				this.amSwfConsignationDao.update(amSwfConsignation);
			}
		}
	}

	private void abortPl(OldOaConsignment oldEntity) {
		List<PlSwfConsignation> entities = this.plSwfConsignationDao.find(
				getDelHql(PlSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (PlSwfConsignation plSwfConsignation : entities) {
				Date dateTo = new Date();
				try {
					dateTo = this.sdf.parse(oldEntity.getDateTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				plSwfConsignation.setDateTo(dateTo);
				this.plSwfConsignationDao.update(plSwfConsignation);
			}
		}
	}

	private void abortBm(OldOaConsignment oldEntity) {
		List<BmSwfConsignation> entities = this.bmSwfConsignationDao.find(
				getDelHql(BmSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (BmSwfConsignation bmSwfConsignation : entities) {
				Date dateTo = new Date();
				try {
					dateTo = this.sdf.parse(oldEntity.getDateTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				bmSwfConsignation.setDateTo(dateTo);
				this.bmSwfConsignationDao.update(bmSwfConsignation);
			}
		}
	}

	private void abortDm(OldOaConsignment oldEntity) {
		List<DmSwfConsignation> entities = this.dmSwfConsignationDao.find(
				getDelHql(DmSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (DmSwfConsignation dmSwfConsignation : entities) {
				Date dateTo = new Date();
				try {
					dateTo = this.sdf.parse(oldEntity.getDateTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				dmSwfConsignation.setDateTo(dateTo);
				this.dmSwfConsignationDao.update(dmSwfConsignation);
			}
		}
	}

	private void abortCm(OldOaConsignment oldEntity) {
		List<CmSwfConsignation> entities = this.cmSwfConsignationDao.find(
				getDelHql(CmSwfConsignation.class.getName()),
				new Object[] { oldEntity.getConsignmentId() });
		if ((entities != null) && (entities.size() > 0)) {
			for (CmSwfConsignation cmSwfConsignation : entities) {
				Date dateTo = new Date();
				try {
					dateTo = this.sdf.parse(oldEntity.getDateTo());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				cmSwfConsignation.setDateTo(dateTo);
				this.cmSwfConsignationDao.update(cmSwfConsignation);
			}
		}
	}

	private void abortOldOa(OldOaConsignment oldEntity) {
	}

	public List<Map<String, Object>> getCheckBoxList(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<Map<String, Object>> list = this.wfConsignationService
				.getDefProcGrid(jqGrid, params, user);
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListOa(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<OaSwfDefProc> reList = this.swfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (OaSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getProcDefId());
				map.put("procName", entity.getProcName());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListPc(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<PcDefProc> reList = this.pcSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (PcDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getProcDefId());
				map.put("procName", entity.getProcName());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListAm(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<AmSwfDefProc> reList = this.amSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (AmSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getName());
				map.put("procName", entity.getProcTitle());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListPl(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<PlSwfDefProc> reList = this.plSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (PlSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getName());
				map.put("procName", entity.getProcTitle());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListBm(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<BmSwfDefProc> reList = this.bmSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (BmSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getName());
				map.put("procName", entity.getProcTitle());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListDm(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<DmSwfDefProc> reList = this.dmSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (DmSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getName());
				map.put("procName", entity.getProcTitle());
				list.add(map);
			}
		}
		return list;
	}

	public List<Map<String, Object>> getCheckBoxListCm(JqGrid jqGrid,
			Map<String, Object> params, UserProfile user) {
		List<CmSwfDefProc> reList = this.cmSwfDao.getAll();
		List<Map<String, Object>> list = new ArrayList();
		if (reList != null) {
			for (CmSwfDefProc entity : reList) {
				Map<String, Object> map = new HashMap();
				map.put("procInnerName", entity.getName());
				map.put("procName", entity.getProcTitle());
				list.add(map);
			}
		}
		return list;
	}

	public Consignment saveConsignment(Consignment consignment, UserProfile user) {
		Consignment com = this.consignmentDao.getByConsignId(consignment
				.getConsignId());	
		boolean boo = false;
		if (com == null) {
			consignment.setConsignmentId(UUIDHex.newId());
			boo = true;
		} else {
			consignment.setConsignmentId(com.getConsignmentId());
		}
		String dateFrom = consignment.getDateFrom();
		String dateTo = consignment.getDateTo();

		this.consignmentDao.saveOrUpdate(consignment);

		OldOaConsignment oldEntity = new OldOaConsignment();
		
		if (!boo) {
			oldEntity = getOldConsignment(consignment.getConsignmentId());
		} else {
			oldEntity.setConsignmentId(getEntityId(OldOaConsignment.class
					.getName(), "consignmentId"));
		}
		oldEntity.setConsigneeId(Long.valueOf(consignment.getConsigneeId()));
		oldEntity.setConsigneeName(consignment.getConsigneeName());
		oldEntity.setConsignerId(Long.valueOf(consignment.getConsignerId()));
		oldEntity.setConsignerName(consignment.getConsignerName());
		oldEntity.setConsignmentIdNew(consignment.getConsignmentId());
		oldEntity.setConsignReason(consignment.getConsignReason());
		oldEntity.setDateFrom(consignment.getDateFrom());
		oldEntity.setDateTo(consignment.getDateTo());
		oldEntity.setIsFailure(Long.valueOf(consignment.getIsFailure()
				.intValue()));

		oldEntity.setPublishBulletin("0");
		this.oldOaConsignmentDao.saveOrUpdate(oldEntity);

		savePcConsignment(consignment, user, oldEntity);

		saveAmConsignment(consignment, user, oldEntity);

		savePlConsignment(consignment, user, oldEntity);

		saveBmConsignment(consignment, user, oldEntity);

		saveDmConsignment(consignment, user, oldEntity);

		saveCmConsignment(consignment, user, oldEntity);

		saveOldOaConsignment(consignment, user, oldEntity);
		//无论是新建还是修改先在公告板删除一遍
		this.bulletinService.deleteConsignment(consignment.getConsignmentId(), user);
		//然后在根据是否发布到公告板这个参数执行以下代码
		if (consignment.getPublishBulletin().trim().equals("T")) {
			this.bulletinService.addConsignment(consignment, user);
		}
		return consignment;
	}

	public Long getEntityId(String entityName, String id) {
		Long l = (Long) this.oldOaConsignmentDao.getHibernateTemplate()
				.getSessionFactory().openSession().createQuery(
						"select max(" + id + ") from " + entityName + " ")
				.uniqueResult();
		System.out.println(entityName + "最大ID:" + l);
		return Long.valueOf(l.longValue() + 1L);
	}

	public Consignment initConsignment(String consignId) {
		Consignment consignment = null;
		if (StringUtils.isNotBlank(consignId)) {
			consignment = this.consignmentDao.getByConsignId(consignId);
		}
		return consignment;
	}

	public void savePcConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delPcSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getPcAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.pcSwfConsignationDao.save(initPcSwfConsignation(oldEntity,
					Long.valueOf(0L), Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefPcIds();
			System.out.println("pc:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.pcSwfConsignationDao.save(initPcSwfConsignation(
							oldEntity, Long.valueOf(defIds[i]), Long
									.valueOf(2L)));
				}
			}
		}
	}

	public PcSwfConsignation initPcSwfConsignation(OldOaConsignment oldEntity,
			Long defId, Long defType) {
		PcSwfConsignation pcSwfConsignation = new PcSwfConsignation();
		pcSwfConsignation.setConsigneeId(oldEntity.getConsigneeId());
		pcSwfConsignation.setConsignerId(oldEntity.getConsignerId());
		pcSwfConsignation.setConsignId(getEntityId(PcSwfConsignation.class
				.getName(), "consignId"));
		pcSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		pcSwfConsignation.setDateFrom(oldEntity.getDateFrom());
		pcSwfConsignation.setDateTo(oldEntity.getDateTo());
		pcSwfConsignation.setDefId(defId);
		pcSwfConsignation.setDefType(defType);
		return pcSwfConsignation;
	}

	public void saveAmConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delAmSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getAmAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.amSwfConsignationDao.save(initAmSwfConsignation(oldEntity,
					"全部", Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefAmIds();
			System.out.println("am:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.amSwfConsignationDao.save(initAmSwfConsignation(
							oldEntity, defIds[i], Long.valueOf(2L)));
				}
			}
		}
	}

	public AmSwfConsignation initAmSwfConsignation(OldOaConsignment oldEntity,
			String defId, Long defType) {
		AmSwfConsignation amSwfConsignation = new AmSwfConsignation();
		amSwfConsignation.setConsigneeId(oldEntity.getConsigneeId());
		amSwfConsignation.setConsignerId(String.valueOf(oldEntity
				.getConsignerId()));
		amSwfConsignation.setConsignId(getEntityId(AmSwfConsignation.class
				.getName(), "consignId"));
		amSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		try {
			amSwfConsignation.setDateFrom(this.sdf.parse(oldEntity
					.getDateFrom()));
			amSwfConsignation.setDateTo(this.sdf.parse(oldEntity.getDateTo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		amSwfConsignation.setDefId(defId);
		amSwfConsignation.setDefType(defType);
		return amSwfConsignation;
	}

	public void savePlConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delPlSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getPlAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.plSwfConsignationDao.save(initPlSwfConsignation(oldEntity,
					"全部", Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefPlIds();
			System.out.println("pl:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.plSwfConsignationDao.save(initPlSwfConsignation(
							oldEntity, defIds[i], Long.valueOf(2L)));
				}
			}
		}
	}

	public PlSwfConsignation initPlSwfConsignation(OldOaConsignment oldEntity,
			String defId, Long defType) {
		PlSwfConsignation plSwfConsignation = new PlSwfConsignation();
		plSwfConsignation.setConsigneeId(oldEntity.getConsigneeId());
		plSwfConsignation.setConsignerId(String.valueOf(oldEntity
				.getConsignerId()));
		plSwfConsignation.setConsignId(getEntityId(PlSwfConsignation.class
				.getName(), "consignId"));
		plSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		try {
			plSwfConsignation.setDateFrom(this.sdf.parse(oldEntity
					.getDateFrom()));
			plSwfConsignation.setDateTo(this.sdf.parse(oldEntity.getDateTo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		plSwfConsignation.setDefId(defId);
		plSwfConsignation.setDefType(defType);
		return plSwfConsignation;
	}

	public void saveBmConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delBmSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getBmAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.bmSwfConsignationDao.save(initBmSwfConsignation(oldEntity,
					"全部", Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefBmIds();
			System.out.println("bm:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.bmSwfConsignationDao.save(initBmSwfConsignation(
							oldEntity, defIds[i], Long.valueOf(2L)));
				}
			}
		}
	}

	public BmSwfConsignation initBmSwfConsignation(OldOaConsignment oldEntity,
			String defId, Long defType) {
		BmSwfConsignation bmSwfConsignation = new BmSwfConsignation();
		bmSwfConsignation.setConsigneeId(oldEntity.getConsigneeId());
		bmSwfConsignation.setConsignerId(String.valueOf(oldEntity
				.getConsignerId()));
		bmSwfConsignation.setConsignId(getEntityId(BmSwfConsignation.class
				.getName(), "consignId"));
		bmSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		try {
			bmSwfConsignation.setDateFrom(this.sdf.parse(oldEntity
					.getDateFrom()));
			bmSwfConsignation.setDateTo(this.sdf.parse(oldEntity.getDateTo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bmSwfConsignation.setDefId(defId);
		bmSwfConsignation.setDefType(defType);
		return bmSwfConsignation;
	}

	public void saveDmConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delDmSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getDmAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.dmSwfConsignationDao.save(initDmSwfConsignation(oldEntity,
					"全部", Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefDmIds();
			System.out.println("dm:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.dmSwfConsignationDao.save(initDmSwfConsignation(
							oldEntity, defIds[i], Long.valueOf(2L)));
				}
			}
		}
	}

	public DmSwfConsignation initDmSwfConsignation(OldOaConsignment oldEntity,
			String defId, Long defType) {
		DmSwfConsignation dmSwfConsignation = new DmSwfConsignation();
		dmSwfConsignation.setConsigneeId(String.valueOf(oldEntity
				.getConsigneeId()));
		dmSwfConsignation.setConsignerId(String.valueOf(oldEntity
				.getConsignerId()));
		dmSwfConsignation.setConsignId(getEntityId(DmSwfConsignation.class
				.getName(), "consignId"));
		dmSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		try {
			dmSwfConsignation.setDateFrom(this.sdf.parse(oldEntity
					.getDateFrom()));
			dmSwfConsignation.setDateTo(this.sdf.parse(oldEntity.getDateTo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		dmSwfConsignation.setDefId(defId);
		dmSwfConsignation.setDefType(defType);
		return dmSwfConsignation;
	}

	public void saveCmConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
		delCmSwfConsignation(oldEntity.getConsignmentId());
		String isAll = consignment.getCmAll();
		if ((StringUtils.isNotBlank(isAll)) && (isAll.equals("1"))) {
			this.cmSwfConsignationDao.save(initCmSwfConsignation(oldEntity,
					"全部", Long.valueOf(1L)));
		} else {
			String defId = consignment.getIs_ProcDefCmIds();
			System.out.println("cm:" + defId);
			if (StringUtils.isNotBlank(defId)) {
				String[] defIds = defId.split(",");
				for (int i = 0; i < defIds.length; i++) {
					this.cmSwfConsignationDao.save(initCmSwfConsignation(
							oldEntity, defIds[i], Long.valueOf(2L)));
				}
			}
		}
	}

	public CmSwfConsignation initCmSwfConsignation(OldOaConsignment oldEntity,
			String defId, Long defType) {
		CmSwfConsignation cmSwfConsignation = new CmSwfConsignation();
		cmSwfConsignation.setConsigneeId(String.valueOf(oldEntity
				.getConsigneeId()));
		cmSwfConsignation.setConsignerId(String.valueOf(oldEntity
				.getConsignerId()));
		cmSwfConsignation.setConsignId(getEntityId(CmSwfConsignation.class
				.getName(), "consignId"));
		cmSwfConsignation.setConsignmentId(oldEntity.getConsignmentId());
		try {
			cmSwfConsignation.setDateFrom(this.sdf.parse(oldEntity
					.getDateFrom()));
			cmSwfConsignation.setDateTo(this.sdf.parse(oldEntity.getDateTo()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		cmSwfConsignation.setDefId(defId);
		cmSwfConsignation.setDefType(defType);
		return cmSwfConsignation;
	}

	public List<PcSwfConsignation> getPcSwfConsignation(Long oldConsignmentId) {
		List<PcSwfConsignation> entities = this.pcSwfConsignationDao.find(
				getDelHql(PcSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public List<AmSwfConsignation> getAmSwfConsignation(Long oldConsignmentId) {
		List<AmSwfConsignation> entities = this.amSwfConsignationDao.find(
				getDelHql(AmSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public List<PlSwfConsignation> getPlSwfConsignation(Long oldConsignmentId) {
		List<PlSwfConsignation> entities = this.plSwfConsignationDao.find(
				getDelHql(PlSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public List<BmSwfConsignation> getBmSwfConsignation(Long oldConsignmentId) {
		List<BmSwfConsignation> entities = this.bmSwfConsignationDao.find(
				getDelHql(BmSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public List<DmSwfConsignation> getDmSwfConsignation(Long oldConsignmentId) {
		List<DmSwfConsignation> entities = this.dmSwfConsignationDao.find(
				getDelHql(DmSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public List<CmSwfConsignation> getCmSwfConsignation(Long oldConsignmentId) {
		List<CmSwfConsignation> entities = this.cmSwfConsignationDao.find(
				getDelHql(CmSwfConsignation.class.getName()),
				new Object[] { oldConsignmentId });
		return entities;
	}

	public void getOldOaSwfConsignation(Long oldConsignmentId) {
	}

	public void delPcSwfConsignation(Long oldConsignmentId) {
		List<PcSwfConsignation> entities = getPcSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.pcSwfConsignationDao.delete(entities);
		}
	}

	public void delAmSwfConsignation(Long oldConsignmentId) {
		List<AmSwfConsignation> entities = getAmSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.amSwfConsignationDao.delete(entities);
		}
	}

	public void delPlSwfConsignation(Long oldConsignmentId) {
		List<PlSwfConsignation> entities = getPlSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.plSwfConsignationDao.delete(entities);
		}
	}

	public void delBmSwfConsignation(Long oldConsignmentId) {
		List<BmSwfConsignation> entities = getBmSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.bmSwfConsignationDao.delete(entities);
		}
	}

	public void delDmSwfConsignation(Long oldConsignmentId) {
		List<DmSwfConsignation> entities = getDmSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.dmSwfConsignationDao.delete(entities);
		}
	}

	public void delCmSwfConsignation(Long oldConsignmentId) {
		List<CmSwfConsignation> entities = getCmSwfConsignation(oldConsignmentId);
		if ((entities != null) && (entities.size() > 0)) {
			this.cmSwfConsignationDao.delete(entities);
		}
	}

	public void delOldOaSwfConsignation(Long oldConsignmentId) {
	}

	public void saveOldOaConsignment(Consignment consignment, UserProfile user,
			OldOaConsignment oldEntity) {
	}

	public String getDelHql(String className) {
		String hql = " from " + className + " where consignmentId = ? ";
		return hql;
	}

	public String getOldConsignmentHql() {
		String hql = " from " + OldOaConsignment.class.getName()
				+ " where consignmentIdNew = ? ";
		return hql;
	}

	public OldOaConsignment getOldConsignment(String consignmentId) {
		List<OldOaConsignment> oldEntities = null;
		if (consignmentId.length() < 32) {
			oldEntities = this.oldOaConsignmentDao.find(
					"from OldOaConsignment where consignmentId = ? ",
					new Object[] { Long.valueOf(consignmentId) });
			System.out.println(oldEntities.size());
		} else {
			oldEntities = this.oldOaConsignmentDao.find(getOldConsignmentHql(),
					new Object[] { consignmentId });
		}
		if ((oldEntities != null) && (oldEntities.size() > 0)) {
			return (OldOaConsignment) oldEntities.get(0);
		}
		return null;
	}

	public Consignment initConsignmentById(String consignmentId) {
		Consignment c = (Consignment) this.consignmentDao.get(consignmentId);
		if (c != null) {
			OldOaConsignment oldEntity = getOldConsignment(consignmentId);
			if (consignmentId.length() > 31) {
				getOaDef(c);
			}
			getPcDef(c, oldEntity);

			getAmDef(c, oldEntity);

			getPlDef(c, oldEntity);

			getBmDef(c, oldEntity);

			getDmDef(c, oldEntity);

			getCmDef(c, oldEntity);
		}
		return c;
	}

	public void getOaDef(Consignment c) {
		WfConsignation wc = this.wfConsignationService.getWfConsignation(c
				.getConsignId());
		if (wc != null) {
			if (wc.getConsignType() == 0) {
				c.setOaAll("1");
			} else {
				List<WfConsignationSpecified> list = this.wfConsignationService
						.getSpecifieds(c.getConsignId());
				String defId = "";
				if ((list != null) && (list.size() > 0)) {
					for (WfConsignationSpecified wfConsignationSpecified : list) {
						defId = defId
								+ wfConsignationSpecified.getProcInnerName()
								+ ",";
					}
				}
				c.setIs_ProcDefIds(defId);
			}
		}
	}

	public void getPcDef(Consignment entity, OldOaConsignment oldEntity) {
		List<PcSwfConsignation> pc = getPcSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((pc != null) && (pc.size() > 0)) {
			for (PcSwfConsignation pcSwfConsignation : pc) {
				Long def = pcSwfConsignation.getDefId();
				if (def.longValue() == 0L) {
					entity.setPcAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefPcIds(defIds);
	}

	public void getAmDef(Consignment entity, OldOaConsignment oldEntity) {
		List<AmSwfConsignation> am = getAmSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((am != null) && (am.size() > 0)) {
			for (AmSwfConsignation amSwfConsignation : am) {
				String def = amSwfConsignation.getDefId();
				if (def.equals("全部")) {
					entity.setAmAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefAmIds(defIds);
	}

	public void getPlDef(Consignment entity, OldOaConsignment oldEntity) {
		List<PlSwfConsignation> pl = getPlSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((pl != null) && (pl.size() > 0)) {
			for (PlSwfConsignation plSwfConsignation : pl) {
				String def = plSwfConsignation.getDefId();
				if (def.equals("全部")) {
					entity.setPlAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefPlIds(defIds);
	}

	public void getBmDef(Consignment entity, OldOaConsignment oldEntity) {
		List<BmSwfConsignation> bm = getBmSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((bm != null) && (bm.size() > 0)) {
			for (BmSwfConsignation bmSwfConsignation : bm) {
				String def = bmSwfConsignation.getDefId();
				if (def.equals("全部")) {
					entity.setBmAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefBmIds(defIds);
	}

	public void getDmDef(Consignment entity, OldOaConsignment oldEntity) {
		List<DmSwfConsignation> dm = getDmSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((dm != null) && (dm.size() > 0)) {
			for (DmSwfConsignation dmSwfConsignation : dm) {
				String def = dmSwfConsignation.getDefId();
				if (def.equals("全部")) {
					entity.setDmAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefDmIds(defIds);
	}

	public void getCmDef(Consignment entity, OldOaConsignment oldEntity) {
		List<CmSwfConsignation> cm = getCmSwfConsignation(oldEntity
				.getConsignmentId());
		String defIds = "";
		if ((cm != null) && (cm.size() > 0)) {
			for (CmSwfConsignation cmSwfConsignation : cm) {
				String def = cmSwfConsignation.getDefId();
				if (def.equals("全部")) {
					entity.setCmAll("1");
				} else {
					defIds = defIds + String.valueOf(def) + ",";
				}
			}
		}
		entity.setIs_ProcDefCmIds(defIds);
	}
}
