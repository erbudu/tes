package com.supporter.prj.dept_resource.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.dept_resource.dao.DeptResourceAuthDao;
import com.supporter.prj.dept_resource.dao.DeptResourceDao;
import com.supporter.prj.dept_resource.dao.DeptResourceTypeDao;
import com.supporter.prj.dept_resource.entity.DeptResource;
import com.supporter.prj.dept_resource.entity.DeptResourceAuth;
import com.supporter.prj.dept_resource.entity.DeptResourceType;
import com.supporter.prj.dept_resource.util.LogConstant;
import com.supporter.prj.eip.dept.entity.TreeNode;
import com.supporter.prj.eip.dept.service.TreeNodeService;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.emp.entity.IDeptPostEmp;
import com.supporter.prj.eip_service.role.entity.Role;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.util.CommonUtil;

@Service
public class DeptResourceService {
	@Autowired
	private DeptResourceDao deptResourceDao;
	@Autowired
	private TreeNodeService treeNodeService;
	@Autowired
	private DeptResourceTypeDao deptResourceTypeDao;
	@Autowired
	private DeptResourceAuthDao deptResourceAuthDao;
	@Autowired
	private DeptResourceAuthService deptResourceAuthService;

	public DeptResource get(String moduleId) {
		return deptResourceDao.get(moduleId);
	}

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * 
	 * @param moduleId
	 * @return
	 */
	public DeptResource initEditOrViewPage(JqGrid jqGrid, String resourceId,
			UserProfile user) {
		if (StringUtils.isBlank(resourceId)) {// 新建
			DeptResource deptResource = newDeptResource(user);
			deptResource.setAdd(true);
			return deptResource;
		} else {// 编辑
			// 获得主表
			DeptResource deptResource = deptResourceDao.get(resourceId);
			String deptId = deptResource.getDeptId();
			// 根据部门id取出部门名称
			String deptName = "";
			TreeNode dept = null;
			dept = treeNodeService.getById(deptId);
			if (dept != null) {
				deptName = dept.getDisplayNameZh();
			}
			deptResource.setDeptName(deptName);
			
			String deptNameAndResourceName = deptName + "-"
					+ deptResource.getResourceName();
			deptResource.setDeptNameAndResourceName(deptNameAndResourceName);
			if(deptResource.getResourceTypeCode()!=null&&!deptResource.getResourceTypeCode().equals("")){
				DeptResourceType entity = deptResourceTypeDao.get(deptResource
						.getResourceTypeCode());
				String deptResourceTypeName = entity.getTypeName();
				deptResource.setDeptResourceTypeName(deptResourceTypeName);
			}
			
			
			deptResource.setAdd(false);
			return deptResource;
		}
	}
	
	
	//根据部门资源ID获取 部门和资源名的拼接字符串(公司公告列表和文章栏列表用到)
	public String getDeptNameAndResourceName(String resourceId){
		Object[] nameDesc= deptResourceDao.getDeptIdAndResourceName(resourceId);
		// 根据部门id取出部门名称
		String deptName = "";
		String deptNameAndResourceName="";
		TreeNode dept = null;
		if(nameDesc!=null){
				if(nameDesc[1]!=null){
					dept = treeNodeService.getById(nameDesc[1].toString());
				}				
				if (dept != null) {
					deptName = dept.getDisplayNameZh();
				}else{
					deptName = "总公司";
				}
				if(nameDesc.length>0){
					deptNameAndResourceName=deptName+"."+nameDesc[0];
				}
			}
		return deptNameAndResourceName;
	}
	

	/**
	 * 新建实例,并初始化必要的属性.
	 * 
	 * @param auserprf_U
	 * @return
	 */
	public DeptResource newDeptResource(UserProfile auserprf_U) {
		DeptResource ldeptResource_N = new DeptResource();
		ldeptResource_N.setResourceId(com.supporter.util.UUIDHex.newId());
		ldeptResource_N.setCreatedById(auserprf_U.getPersonId());
		ldeptResource_N.setCreatedBy(auserprf_U.getName());
		String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		ldeptResource_N.setCreatedDate(date);
		ldeptResource_N.setDisplayOrder(getCount());
		
		ldeptResource_N.setManagerId(auserprf_U.getPersonId());
		ldeptResource_N.setManagerName(auserprf_U.getName());
		//因为管理员没有所在部门。所以需要判断一下
    	if(!auserprf_U.getPersonId().equals("1")){
    		ldeptResource_N.setDeptId(auserprf_U.getDeptId());
    		ldeptResource_N.setDeptName(auserprf_U.getDept().getName());
    	}else{
    		ldeptResource_N.setDeptName("管理员所在部门");
    	}
		
		return ldeptResource_N;
	}

	/**
	 * 分页表格展示数据.
	 * 
	 * @param user
	 *            用户信息
	 * @param jqGridReq
	 *            jqgrid请求对象
	 * @param moduleIds
	 *            多个逗号分隔
	 * @return JqGrid
	 */
	public List<DeptResource> getGrid(UserProfile user, JqGrid jqGrid,
			DeptResource deptResource) {
		List<DeptResource> list1 = new ArrayList<DeptResource>();
		List<DeptResource> list = this.deptResourceDao.findPage(jqGrid,
				deptResource);
		if (list != null) {
			for (DeptResource deptResourceZ : list) {
				// 获得资源类型名
				if(deptResourceZ.getResourceTypeCode()!=null&&!deptResourceZ.getResourceTypeCode().equals("")){
					DeptResourceType deptResourceType = deptResourceTypeDao
							.get(deptResourceZ.getResourceTypeCode());
					if(deptResourceType!=null){
						String deptResourceTypeName = deptResourceType.getTypeName();
						deptResourceZ.setDeptResourceTypeName(deptResourceTypeName);
					}					
					
				}
				
				// 获得被授权者 和 授权类型
				// 部门资源下内容的授权
				List<Object[]> objList = deptResourceAuthDao.getByResourceId(
						"RESOURCE_CONTENT", deptResourceZ.getResourceId());
				String resourceContentDesc = "";
				if (objList != null) {
					if (objList.size() > 0) {
						for (Object[] object : objList) {
							String authorizeeType = (String) object[0];
							String authorizeeId = (String) object[1];
							String canRead = (String) object[2];
							String canWrite = (String) object[3];
							String canDelete = (String) object[4];
							String fullAccess = (String) object[5];
							String canNew = (String) object[6];
							String authDesc = deptResourceAuthService
									.getAuthDesc(fullAccess, canRead, canNew,
											canWrite, canDelete);
							// String
							// nameDesc=deptResourceAuthService.getNameDesc(authorizeeId,
							// authorizeeType);
							String authorizeeIdDesc = deptResourceAuthService
									.getNameListDesc(authorizeeId,
											authorizeeType);
							resourceContentDesc += authorizeeIdDesc + ":"
									+ authDesc + ";";
						}
						deptResourceZ
								.setResourceContentDesc(resourceContentDesc
										.substring(0, (resourceContentDesc
												.length() - 1)));
					} else {
						resourceContentDesc = "";
						deptResourceZ
								.setResourceContentDesc(resourceContentDesc);
					}

				} else {
					resourceContentDesc = "";
					deptResourceZ.setResourceContentDesc(resourceContentDesc);
				}

				// 部门资源设置的授权
				List<Object[]> objList1 = deptResourceAuthDao.getByResourceId(
						"DEFINETION", deptResourceZ.getResourceId());
				String finetionDesc = "";
				if (objList1 != null) {
					if (objList1.size() > 0) {
						for (Object[] object : objList1) {
							String authorizeeType = (String) object[0];
							String authorizeeId = (String) object[1];
							String canRead = (String) object[2];
							String canWrite = (String) object[3];
							String canDelete = (String) object[4];
							String fullAccess = (String) object[5];
							String canNew = (String) object[6];
							
							String authDesc = deptResourceAuthService
									.getAuthDesc(fullAccess, canRead, canNew,
											canWrite, canDelete);
							String authorizeeIdDesc = deptResourceAuthService
									.getNameListDesc(authorizeeId,
											authorizeeType);
							finetionDesc += authorizeeIdDesc + ":" + authDesc
									+ ";";
						}
						deptResourceZ.setFinetionDesc(finetionDesc.substring(0,
								(finetionDesc.length() - 1)));
					} else {
						finetionDesc = "";
						deptResourceZ.setFinetionDesc(finetionDesc);
					}
				} else {
					finetionDesc = "";
					deptResourceZ.setFinetionDesc(finetionDesc);
				}

				list1.add(deptResourceZ);
			}
		}
		jqGrid.setRows(list1);
		return list1;

	}

	/**
	 * 保存或更新
	 * 
	 * @param user
	 *            用户信息
	 * @param module
	 *            实体类
	 * @return
	 */
	public DeptResource saveOrUpdate(UserProfile user,
			DeptResource deptResource, Map<String, Object> valueMap) {
		DeptResource ret = null;
		if (deptResource.getAdd()) {// 新建
			// 保存主表
			this.deptResourceDao.save(deptResource);
			ret = deptResource;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_DEPTRE_LOG_MESSAGE, deptResource.getResourceName());
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTRE_BUSINESS).info(
					user, LogConstant.ADD_DEPTRE_LOG_ACTION, logMessage,
					deptResource, null);
		} else {// 编辑
			// 编辑之后保存主表
			this.deptResourceDao.update(deptResource);
			ret = deptResource;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_DEPTRE_LOG_MESSAGE, deptResource.getResourceName());
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTRE_BUSINESS).info(
					user, LogConstant.EDIT_DEPTRE_LOG_ACTION, logMessage,
					deptResource, null);
		}
		return ret;

	}

	// 得到数据库中数据的条数（以年为单位）
	public long getCount() {
		int displayOrder = deptResourceDao.getCount();
		return displayOrder + 1;
	}

	/**
	 * 删除
	 * 
	 * @param user
	 *            用户信息
	 * @param moduleIds
	 *            主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String resourceIds) {
		if (StringUtils.isNotBlank(resourceIds)) {
			for (String resourceId : resourceIds.split(",")) {
			    //1. 删除主表之前先删除DeptResourceAuth从表				
				List<DeptResourceAuth> DeptResourceAuthList=deptResourceAuthDao.getListByResourceId(resourceId);
				if(DeptResourceAuthList!=null){
					if(DeptResourceAuthList.size()>0){
						for(DeptResourceAuth entity :DeptResourceAuthList){
							deptResourceAuthDao.delete(entity);							
						}
					}
				}
				 //2. 删除主表之前先删除DeptResourceCategory从表		
				// 删除部门资源管理主表
				this.deptResourceDao.delete(deptResourceDao.get(resourceId));
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_DEPTRE_LOG_MESSAGE, "删除的部门资源主键为"+resourceIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_DEPTRE_BUSINESS).info(
					user, LogConstant.DELETE_DEPTRE_LOG_ACTION, logMessage,
					null, null);
		}
	}
	
	// 获取资源类型为公告的部门资源（公司公告菜单用到）
//	public List<Object[]> findDeptResourceOfBulletin(UserProfile user) {	
//		List<Object[]> list = this.deptResourceDao.findDeptResourceOfBulletin(user);		
//		return list;
//	}
	
	// 获取资源类型为公告的部门资源（公司公告菜单编辑页面用到）
	public List<DeptResource> findDeptResourceOfBulletinOfCanTodo(UserProfile user,String canTodo) {
		boolean canAccess=false;
		//1、获取所有公告类型的资源
		List<DeptResource> listDeptResource = this.deptResourceDao.findDeptResourceOfBulletin();
		List<DeptResource> listToWrite=new ArrayList<DeptResource>();
		//2、判断资源下内容的权限还是资源设置的权限（这是肯定是资源内容的权限）
		if(listDeptResource!=null){
			for(DeptResource deptResource :listDeptResource){
				if(deptResource.getManagerId()!=null){//说明该资源设置了管理员
					if(user.getPersonId().equals(deptResource.getManagerId())){//说明当前登录人是该资源的管理员
						canAccess=true;						
					}else{
						canAccess=this.isHaveAuth(user,deptResource.getResourceId(),canTodo);//不是该资源管理员
					}
				}								
				if(canAccess){
					listToWrite.add(deptResource);
				}
			}
		}		
		return listToWrite;
	}
	
	
	/**
	 * 
	 * @param user 当前用户
	 * @param 部门资源id
	 * @return
	 */
	public boolean isHaveAuth(UserProfile user,String resourceId,String canTodo) {
		boolean isHaveAuth=false;
		//首先先判断用户（系统管理员、普通用户）
		//1.超级管理员admin(超级管理员组)
		//2.后台被赋予权限成为系统管理员的（比如角色：系统维护人员，系统管理员或者某个用户组）！！！暂时没有加入筛选
		if(user.getPersonId().equals("1")){//说明是管理员
			isHaveAuth=true;
		}
//		else if(){//通过角色名判断 比如系统管理员
//			isHaveAuth=true;
//		}
		else{//说明是普通用户
			
			DeptResource deptResource=this.deptResourceDao.get(resourceId);
			if(deptResource!=null&&deptResource.getManagerId()!=null){//说明该资源设置了管理员
				if(user.getPersonId().equals(deptResource.getManagerId())){//说明当前登录人是该资源的管理员
					isHaveAuth=true;						
				}
			}
			
			
			//根据当前登录人获取所属部门id
			String deptId=user.getDeptId();
			//根据当前登录人获取所属岗位id
			List<IDeptPostEmp> listOfPost=EIPService.getEmpAssignService().getDeptPostEmps(user.getAccount().getAccountId());
			String postIds="";
			if(listOfPost!=null){
				for(IDeptPostEmp post:listOfPost){
					if(post!=null){
						if(post.getPostNo()!=null){
							postIds=postIds+"'"+post.getPostNo()+"',";
						}
						
					}
				}
			}	
			//根据当前登录人获取所属角色id
			Role[] roles=user.getRoles();
			String roleIds="";
			if(roles.length>0){
				for(int i=0;i<roles.length;i++){
					roleIds=roleIds+"'"+roles[i].getRoleId()+"',";
				}
			}
			//根据当前登录人获取PersonId
			String personId=user.getPersonId();
			String authorizeeId="";
			if(deptId!=null&&!deptId.equals("")){
				authorizeeId=authorizeeId+"'"+deptId+"',";
			}
			if(postIds!=null&&!postIds.equals("")){
				authorizeeId=authorizeeId+postIds;
			}
			if(roleIds!=null&&!roleIds.equals("")){
				authorizeeId=authorizeeId+roleIds;
			}
			if(personId!=null&&!personId.equals("")){
				authorizeeId=authorizeeId+"'"+personId+"',";
			}
			//获取所有可能给给用户赋权限的方式（通过用户所属部门、岗位、角色，还有就是直接给当前人赋值）
			String authorizeeIds="";
			if(!authorizeeId.equals("")){
				authorizeeIds=authorizeeId.substring(0, (authorizeeId.length()-1));
			}
			//authorizeeIds就是当前用户所属部门、岗位、角色的id以及自己的personId组成的
			//(authorizeeIds===='20118','OAWEIHU','QIANBAOGUIDANG','SalaryManagerOfGroup','ZONGCAIMISHU','ZhiKaAdmin','ALLUSER','1000000764')
			List<DeptResourceAuth> listOfDeptResourceAuth=this.deptResourceAuthDao.getListByResourceIdOfContent(resourceId,authorizeeIds,canTodo);
			if(listOfDeptResourceAuth!=null&&listOfDeptResourceAuth.size()>0){
				isHaveAuth=true;
			}
		}
		
		return isHaveAuth;
	}
	
	
	/**
	 * 
	 * @param user 当前用户
	 * @param 部门资源id
	 * @return
	 */
	public String resourcehaveAuth(UserProfile user,String canTodo) {
		String ResourcehaveAuth="";		
		//首先先判断用户（系统管理员、普通用户）
		//1.超级管理员admin(超级管理员组)
		//2.后台被赋予权限成为系统管理员的（比如角色：系统维护人员，系统管理员或者某个用户组）！！！暂时没有加入筛选
		if(user.getPersonId().equals("1")){//说明是管理员
			ResourcehaveAuth="";
		}
//		else if(){//通过角色名判断 比如系统管理员
//			isHaveAuth=true;
//		}
		else{//说明是普通用户
			//普通用户通过两种方式获得相应的权限
			//1.当前登录人是该资源的管理员
				//获得所有以当前登录人为管理员的部门资源的id
			List<Object> resourceIdList=this.deptResourceDao.getDeptResourceIdByManagerId(user.getPersonId());
			
			if(resourceIdList!=null&&resourceIdList.size()>0){//说明当前登录人是某些部门资源的管理员

					int count=1;
					for(Object object:resourceIdList){
						if(object==null)continue;
						if(count==resourceIdList.size()){							
							ResourcehaveAuth+="'"+object.toString()+"'";
						}else{
							ResourcehaveAuth+="'"+object.toString()+"',";
						}
						count++;
					}
			}

			//2.当前登录人被部门资源的管理员授予相应的权限（通过部门、人员、岗位、和用户组（这里相当有角色）四种方式）
			//根据当前登录人获取所属部门id
			String deptId=user.getDeptId();
			//根据当前登录人获取所属岗位id
			List<IDeptPostEmp> listOfPost=EIPService.getEmpAssignService().getDeptPostEmps(user.getAccount().getAccountId());
			String postIds="";
			if(listOfPost!=null){
				for(IDeptPostEmp post:listOfPost){
					if(post!=null){
						if(post.getPostNo()!=null){
							postIds=postIds+"'"+post.getPostNo()+"',";
						}
						
					}
				}
			}	
			//根据当前登录人获取所属角色id
			Role[] roles=user.getRoles();
			String roleIds="";
			if(roles.length>0){
				for(int i=0;i<roles.length;i++){
					roleIds=roleIds+"'"+roles[i].getRoleId()+"',";
				}
			}
			//根据当前登录人获取PersonId
			String personId=user.getPersonId();
			String authorizeeId="";
			if(deptId!=null&&!deptId.equals("")){
				authorizeeId=authorizeeId+"'"+deptId+"',";
			}
			if(postIds!=null&&!postIds.equals("")){
				authorizeeId=authorizeeId+postIds;
			}
			if(roleIds!=null&&!roleIds.equals("")){
				authorizeeId=authorizeeId+roleIds;
			}
			if(personId!=null&&!personId.equals("")){
				authorizeeId=authorizeeId+"'"+personId+"',";
			}
			//获取所有可能给给用户赋权限的方式（通过用户所属部门、岗位、角色，还有就是直接给当前人赋值）
			String authorizeeIds="";
			if(!authorizeeId.equals("")){
				authorizeeIds=authorizeeId.substring(0, (authorizeeId.length()-1));
			}
			//authorizeeIds就是当前用户所属部门、岗位、角色的id以及自己的personId组成的
			//(authorizeeIds===='20118','OAWEIHU','QIANBAOGUIDANG','SalaryManagerOfGroup','ZONGCAIMISHU','ZhiKaAdmin','ALLUSER','1000000764')
			//System.out.println("authorizeeIds=="+authorizeeIds);
			List<DeptResourceAuth> listOfDeptResourceAuth=this.deptResourceAuthDao.getListByResourceIdOfContent("",authorizeeIds,canTodo);
			if(listOfDeptResourceAuth!=null&&listOfDeptResourceAuth.size()>0){
				
				if(ResourcehaveAuth!=null&&!ResourcehaveAuth.equals("")){
					ResourcehaveAuth+=",";
				}
				
				int count=1;
				for(DeptResourceAuth deptResourceAuth:listOfDeptResourceAuth){					
					if(count==listOfDeptResourceAuth.size()){
						ResourcehaveAuth+="'"+deptResourceAuth.getResourceId()+"'";
					}else{
						ResourcehaveAuth+="'"+deptResourceAuth.getResourceId()+"',";
					}
					count++;
				}
			}
		}
		
		return ResourcehaveAuth;
	}
	
	
	
	
	
	
	
	// 获取资源类型为文章栏的部门资源（公司公告菜单用到）
	public List<Object[]> findDeptResourceOfArticle() {
		List<Object[]> list = this.deptResourceDao.findDeptResourceOfArticleOfTop();
		return list;
	}
	
	// 获取资源类型为文章栏的部门资源（文章栏菜单用到）
	public List<DeptResource> findDeptResourceOfArticleOfCanTodo(UserProfile user,String canTodo) {
		boolean canAccess=false;
		
		List<DeptResource> listDeptResource = this.deptResourceDao.findDeptResourceOfArticle();
		
		List<DeptResource> listToWrite=new ArrayList<DeptResource>();
		//2、资源下内容的权限
		if(listDeptResource!=null){
			for(DeptResource deptResource :listDeptResource){
				if(deptResource.getManagerId()!=null){//说明该资源设置了管理员
					if(user.getPersonId().equals(deptResource.getManagerId())){//说明当前登录人是该资源的管理员
						canAccess=true;						
					}else{
						canAccess=this.isHaveAuth(user,deptResource.getResourceId(),canTodo);//不是该资源管理员
					}
				}								
				if(canAccess){
					listToWrite.add(deptResource);
				}
			}
		}		
		return listToWrite;
	}
	
	
	// 根据资源名称获取list（公司公告菜单用到）
	public List<DeptResource> getListByResourceName(String resourceName) {
		List<DeptResource> list = this.deptResourceDao.getListByResourceName(resourceName);
		return list;
	}
	
	/**
	 * 根据资源分类获取资源ID.
	 * @param typeCtblItemId
	 * @return
	 */
	public String getDeptResourceIdByItemId(String typeCtblItemId){
		return deptResourceDao.getDeptResourceIdByItemId(typeCtblItemId);
	}
	
	
}
