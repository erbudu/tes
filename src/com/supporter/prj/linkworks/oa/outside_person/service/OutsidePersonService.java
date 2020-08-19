package com.supporter.prj.linkworks.oa.outside_person.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.linkworks.oa.outside_person.dao.OutsidePersonDao;
import com.supporter.prj.linkworks.oa.outside_person.entity.OutsidePerson;
import com.supporter.util.CommonUtil;

@Service
public class OutsidePersonService {
	@Autowired
	private OutsidePersonDao outsidePersonDao;
	
	/**
	 * 根据主键id获得实体
	 * @param id
	 * @return
	 */
	public OutsidePerson get(String id){
		return outsidePersonDao.get(id);
	}
	
	/**
	 * 进入新建或编辑页面需要加载的信息
	 * @param id
	 * @return
	 */
	public OutsidePerson initEditOrViewPage(String id){
		if (StringUtils.isBlank(id)){//新建
			OutsidePerson outsidePerson = new OutsidePerson();
			outsidePerson.setId(com.supporter.util.UUIDHex.newId());
			outsidePerson.setStatus("在职");
			outsidePerson.setConfirmDate(CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			outsidePerson.setIsNew(true);
			return outsidePerson;
		}else{//编辑
			return outsidePersonDao.get(id);
		}
	}
	
	/**
	 * 分页查询数据
	 * @param jqGrid
	 * @param outsidePerson
	 * @return
	 */
	public List<OutsidePerson> getGrid(JqGrid jqGrid, OutsidePerson outsidePerson){
		return outsidePersonDao.findPage(jqGrid, outsidePerson);
	}
	
	/**
	 * 查看
	 * @param id
	 * @return
	 */
	public OutsidePerson viewPage(String id){
		if (StringUtils.isNotBlank(id)){
			OutsidePerson outsidePerson = outsidePersonDao.get(id);
			return outsidePerson;
		}
		return null;
	}
	
	/**
	 * 保存或更新
	 * @param outsidePerson
	 * @return
	 */
	public OutsidePerson saveOrUpdate(OutsidePerson outsidePerson){
		if (outsidePerson.getIsNew()){
			this.outsidePersonDao.save(outsidePerson);
		}else{
			this.outsidePersonDao.update(outsidePerson);
		}
		return outsidePerson;
	}
	
	public void update(OutsidePerson outsidePerosn){
		this.outsidePersonDao.update(outsidePerosn);
	}
	
	/**
	 * 删除
	 * @param id
	 */
	public void batchDel(String id){
		if (StringUtils.isNotBlank(id)){
			this.outsidePersonDao.delete(id);
		}
	}
}
