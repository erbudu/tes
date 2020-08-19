/**
 * 
 */
package com.supporter.prj.ppm.poa.use_seal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.ppm.poa.icc.entity.Coordination;
import com.supporter.prj.ppm.poa.use_seal.entity.UseSealFileEntity;
import com.supporter.prj.ppm.poa.use_seal.service.UseSealFileService;
import com.supporter.spring_mvc.AbstractController;

/**
 *<p>Title: 启动用印jqGrid</p>
 *<p>Description: </p>
 *<p>Company: </p>
 * @author YUEYUN
 * @date 2019年9月19日
 * 
 */
@Controller
@RequestMapping("ppm/poa/use_seal/file/")
public class UseSealFileController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private UseSealFileService useSealFileService;
	
	/**
	 * <pre>更新盖章文件</pre>
	 * @param sealFileId 用印文件业务单主键
	 * @param fuSealFileId 盖章文件ID
	 * @param fuSealFileName 盖章文件名称
	 * @return 盖章文件实体类
	 */
	@RequestMapping("updateUseSealFuFile")
	protected @ResponseBody UseSealFileEntity updateUseSealFuFile(String sealFileId,String fuSealFileId,String fuSealFileName) {
		
		UseSealFileEntity entity  = useSealFileService.updateUseSealFuFile(sealFileId,fuSealFileId,fuSealFileName);
		return entity;
	}
	/**
	 * 删除盖章文件信息
	 * @param sealFileId
	 * @param moduleName
	 * @param basiType
	 * @param entityId
	 * @param twolevelid
	 */
	
	@RequestMapping("deleteUseSealFuFile")
	public @ResponseBody void deleteUseSealFuFile(String sealFileId, String moduleName, String basiType, String entityId, String twolevelid) {
		useSealFileService.deleteUseSealFuFile(sealFileId, moduleName, basiType, entityId, twolevelid);
	}
	
	/**
	 * 根据业务单号查询用印详单列表
	 * @param businessUUID
	 * @return
	 */
	@RequestMapping("getGridData")
	public @ResponseBody List<UseSealFileEntity> getGridData(String businessUUID){
		List<UseSealFileEntity> list = useSealFileService.getGridData(businessUUID);
		return list;
	}
	
	/**
	 * 根据外键查询用印详单
	 * @param useSealId
	 * @param request
	 * @param jqGridReq
	 * @return
	 */
	
	@RequestMapping("getByUseSealId")
	@ResponseBody
	public JqGrid getByUseSealId(String useSealId,HttpServletRequest request, JqGridReq jqGridReq){
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
	    
	    this.useSealFileService.getByUseSealId(useSealId,jqGrid);
		return jqGrid;
		
	}
	
	/**
	 * 根据项目id查询用印详单
	 * @param prjId
	 * @param request
	 * @param jqGridReq
	 * @return
	 */
	
	@RequestMapping("getByBusinessId")
	@ResponseBody
	public JqGrid getByPrjId(String businessUUId,HttpServletRequest request, JqGridReq jqGridReq){
		
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
	    
	    this.useSealFileService.getByBusinessId(businessUUId,jqGrid);
		return jqGrid;
		
	}

}
