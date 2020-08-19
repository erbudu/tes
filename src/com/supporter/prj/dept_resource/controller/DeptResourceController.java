package com.supporter.prj.dept_resource.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.service.DeptResourceService;
import com.supporter.prj.dept_resource.service.DeptResourceTypeService;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.spring_mvc.AbstractController;

@Controller
@RequestMapping("util/dept_resource")
public class DeptResourceController extends AbstractController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private DeptResourceService deptResourceService;
	@Autowired
	private DeptResourceTypeService deptResourceTypeService;

	/**
	 * 返回列表. 分页表格展示数据.
	 * 
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody
	JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq,
			DeptResource deptResource) throws Exception {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.deptResourceService.getGrid(user, jqGrid, deptResource);
		return jqGrid;
	}

	/**
	 * 根据主键获取功能模块.
	 * 
	 * @param reportId 主键
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("get")
	public @ResponseBody
	DeptResource get(String resourceId) {
		UserProfile user = this.getUserProfile();
		DeptResource deptResource = deptResourceService.get(resourceId);
		return deptResource;

	}

	/**
	 * 进入新建或编辑或查看页面时加载的信息
	 * 
	 * @param resourceId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody
	DeptResource initEditOrViewPage(HttpServletRequest request,
			JqGridReq jqGridReq, String resourceId) {
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		DeptResource entity = deptResourceService.initEditOrViewPage(jqGrid,
				resourceId, this.getUserProfile());
		return entity;
	}

	/**
	 * 保存或更新数据
	 * 
	 * @param report 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody
	OperResult<DeptResource> saveOrUpdate(DeptResource deptResource,
			String resourceIdOld) {
		UserProfile user = this.getUserProfile();
		Map<String, Object> valueMap = this.getPropValues(DeptResource.class);
		DeptResource entity = this.deptResourceService.saveOrUpdate(user,
				deptResource, valueMap);
		// return OperResult.succeed(DeptResourceConstant.I18nKey.SAVE_SUCCESS,
		// null, null);
		return OperResult.succeed("saveSuccess", null, entity);
	}

	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody
	OperResult batchDel(String resourceIds) {
		UserProfile user = this.getUserProfile();
		this.deptResourceService.delete(user, resourceIds);
		// return
		// OperResult.succeed(DeptResourceConstant.I18nKey.DELETE_SUCCESS, null,
		// null);
		return OperResult.succeed("deleteSuccess", null, null);
	}

	/**
	 * 获取字典数据-用于会议地点的下拉显示
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResourceTypeCodetable")
	public Map<String, String> getDeptResourceTypeCodetable()
			throws IOException {
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<Object[]> list = deptResourceTypeService.getDeptResourceType();
		if(list!=null){
			if(list.size()>0){
				for (Object[] object : list) {
					String typeCode = (String) object[0];
					String typeName = (String) object[1];
					map.put(typeCode, typeName);
				}
			}
		}
		return map;
	}
	
	
	
	/**
	 * 获取字典数据-部门资源的下拉列表（公司公告菜单用到）
	 * 
	 * @param key
	 * @throws IOException
	 */
//	@ResponseBody
//	@RequestMapping(value = "/getDeptResourceOfBulletinCodetable")
//	public Map<String, String> getDeptResourceOfBulletinCodetable()
//		throws IOException {
//	UserProfile user = this.getUserProfile();	
//	Map<String, String> map = new LinkedHashMap<String, String>();
//	List<Object[]> list = deptResourceService.findDeptResourceOfBulletin(user);
//		if(list!=null){
//			if(list.size()>0){
//				for (Object[] object : list) {
//					String resourceId = (String) object[0];
//					String resourceName = (String) object[1];
//					map.put(resourceId, resourceName);
//				}
//			}
//		}	
//	return map;
//	}
	
	/**
	 * 获取字典数据-部门资源的下拉列表---获取资源类型为公告的部门资源（公司公告菜单编辑页面用到，这是获取的“canWrite”也就是拥有编辑权限的）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResourceOfBulletinOfCanWriteCodetable")
	public Map<String, String> getDeptResourceOfBulletinOfCanWriteCodetable()
		throws IOException {
	UserProfile user = this.getUserProfile();	
	Map<String, String> map = new LinkedHashMap<String, String>();
	String canTodo="canWrite";//编辑权限
	List<DeptResource> list = deptResourceService.findDeptResourceOfBulletinOfCanTodo(user,canTodo);
		if(list!=null){
			if(list.size()>0){
				for (DeptResource deptResource : list) {
					String resourceId = deptResource.getResourceId();
					String resourceName = deptResource.getResourceName();
					map.put(resourceId, resourceName);
				}
			}
		}	
	return map;
	}
	/**
	 * 获取字典数据-部门资源的下拉列表---获取资源类型为公告的部门资源（公司公告菜单新建页面用到，这是获取的“canNew”也就是拥有新建权限的）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResourceOfBulletinOfCanNewCodetable")
	public Map<String, String> getDeptResourceOfBulletinOfCanNewCodetable()
		throws IOException {
	UserProfile user = this.getUserProfile();	
	Map<String, String> map = new LinkedHashMap<String, String>();
	String canTodo="canNew";//新建权限
	List<DeptResource> list = deptResourceService.findDeptResourceOfBulletinOfCanTodo(user,canTodo);
		if(list!=null){
			if(list.size()>0){
				for (DeptResource deptResource : list) {
					String resourceId = deptResource.getResourceId();
					String resourceName = deptResource.getResourceName();
					map.put(resourceId, resourceName);
				}
			}
		}	
	return map;
	}	
	
	
	
	/**
	 * 获取字典数据-部门资源的下拉列表（文章栏菜单用到）
	 * 
	 * @param key
	 * @throws IOException
	 */
//	@ResponseBody
//	@RequestMapping(value = "/getDeptResourceOfArticleCodetable")
//	public Map<String, String> getDeptResourceOfArticleCodetable()
//		throws IOException {
//	Map<String, String> map = new LinkedHashMap<String, String>();
//	List<Object[]> list = deptResourceService.findDeptResourceOfArticle();
//		if(list!=null){
//			if(list.size()>0){
//				for (Object[] object : list) {
//					String resourceId = (String) object[0];
//					String resourceName = (String) object[1];
//					map.put(resourceId, resourceName);
//				}
//			}
//		}	
//	return map;
//	}
	
	
	/**
	 * 获取字典数据-部门资源的下拉列表获取资源类型为文章的部门资源（文章栏菜单编辑页面用到，这是获取的“canWrite”也就是拥有编辑权限的）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResourceOfArticleCanWriteCodetable")
	public Map<String, String> getDeptResourceOfArticleCanWriteCodetable()
		throws IOException {	
	UserProfile user = this.getUserProfile();	
	Map<String, String> map = new LinkedHashMap<String, String>();
	String canTodo="canWrite";//编辑权限
	List<DeptResource> list = deptResourceService.findDeptResourceOfArticleOfCanTodo(user,canTodo);
		if(list!=null){
			if(list.size()>0){
				for (DeptResource deptResource : list) {
					String resourceId = deptResource.getResourceId();
					String resourceName = deptResource.getResourceName();
					map.put(resourceId, resourceName);
				}
			}
		}	
	return map;
		
	}
	
	
	/**
	 * 获取字典数据-部门资源的下拉列表获取资源类型为文章的部门资源（文章栏菜单新建页面用到，这是获取的“canNew”也就是拥有新建权限的）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResourceOfArticleCanNewCodetable")
	public Map<String, String> getDeptResourceOfArticleCanNewCodetable()
		throws IOException {	
	UserProfile user = this.getUserProfile();	
	Map<String, String> map = new LinkedHashMap<String, String>();
	String canTodo="canNew";//编辑权限
	List<DeptResource> list = deptResourceService.findDeptResourceOfArticleOfCanTodo(user,canTodo);
		if(list!=null){
			if(list.size()>0){
				for (DeptResource deptResource : list) {
					String resourceId = deptResource.getResourceId();
					String resourceName = deptResource.getResourceName();
					map.put(resourceId, resourceName);
				}
			}
		}	
	return map;
		
	}
	
	
	
	
	/**
	 * 根据资源分类获取资源ID.
	 * @param typeCtblItemId
	 * @return
	 */
	@RequestMapping(value = "/getDeptResourceId")
	@ResponseBody
	public String getDeptResourceId(String typeCtblItemId){
		return deptResourceService.getDeptResourceIdByItemId(typeCtblItemId);
	}
	
	
}
