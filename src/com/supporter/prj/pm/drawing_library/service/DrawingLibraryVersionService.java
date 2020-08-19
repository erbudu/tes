package com.supporter.prj.pm.drawing_library.service;


import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.pm.drawing_library.dao.DrawingLibraryVersionDao;
import com.supporter.prj.pm.drawing_library.entity.DrawingLibraryVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(TransManager.APP)
public class DrawingLibraryVersionService {
	
	@Autowired
	private DrawingLibraryVersionDao versionDao;

	public DrawingLibraryVersion saveOrUpdateFile(UserProfile user, String versionId, String scanId, String scanName,
			String scanIds, String scanNames) {
		DrawingLibraryVersion dlv = versionDao.get(versionId);
		if(dlv != null) {
			if(scanIds.equals("T")) {
				dlv.setScanId(scanId);
				dlv.setScanName(scanName);
				this.versionDao.update(dlv);
			}else {
				this.versionDao.update(dlv);
			}
		}
		return null;
	}

    public DrawingLibraryVersion get(String drawingId) {
		return this.versionDao.get(drawingId);
    }

	public void update(DrawingLibraryVersion version) {
		this.versionDao.update(version);
	}

	/**
	 * 更改图纸状态
	 * @param drawingId
	 * @param status
	 */
	public void updateDrawingStatus(String drawingId, int status) {
		this.versionDao.updateDrawingStatus(drawingId,status);
	}

	/**
	 * 保存
	 * @param newDraing
	 */
    public void save(DrawingLibraryVersion newDraing) {
		this.versionDao.save(newDraing);
    }



	public List<DrawingLibraryVersion> find(String hql, String libraryId) {
    	return this.versionDao.find(hql,libraryId);
	}

//	public List<DrawingLibraryVersion> getVersionView(JqGrid jqGrid, UserProfile user,String versionId) {
//		return versionDao.getVersionView( jqGrid, user,versionId);
//	}
}
