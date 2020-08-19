package com.supporter.prj.pm.delivery_construction.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.pm.delivery_construction.dao.DeliveryDrawingsDao;
import com.supporter.prj.pm.delivery_construction.entity.DeliveryDrawings;
@Service
public class DeliveryDrawingsService {
	@Autowired
	private DeliveryDrawingsDao contentDao;
	
	public List<DeliveryDrawings> getContentList(String id){
		return contentDao.getContentList(id);
	}
	
	public List<DeliveryDrawings> getContentListByDrawingId(String drawingId){
		return contentDao.getContentListByDrawingId(drawingId);
	}
	
}