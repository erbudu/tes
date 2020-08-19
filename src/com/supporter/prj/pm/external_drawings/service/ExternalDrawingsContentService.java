package com.supporter.prj.pm.external_drawings.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.pm.external_drawings.dao.ExternalDrawingsContentDao;
import com.supporter.prj.pm.external_drawings.entity.ExternalDrawingsContent;
@Service
public class ExternalDrawingsContentService {
	@Autowired
	private ExternalDrawingsContentDao contentDao;
	
	public List<ExternalDrawingsContent> getContentList(String id){
		return contentDao.getContentList(id);
	}
	
	public List<ExternalDrawingsContent> getContentListByDrawingId(String drawingId){
		return contentDao.getContentListByDrawingId(drawingId);
	}
	
}