package com.supporter.prj.ppm.template_register.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.collections.CollectionUtils;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.swf.runtime.entity.WfExam;
import com.supporter.prj.eip.swf.runtime.service.WfExamService;
import com.supporter.prj.eip.transaction.TransManager;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.com_codetable.entity.IComCodeTableItem;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.dept.entity.Dept;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.security.entity.UserProfile;

import com.supporter.prj.log4j.XLogger;
import com.supporter.prj.ppm.service.ITemplateRegisterService;
import com.supporter.prj.ppm.service.PPMService;
import com.supporter.prj.ppm.template_register.dao.TemplateKeyPointsExamDao;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDataDao;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDetailDao;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterDao;
import com.supporter.prj.ppm.template_register.dao.TemplateRegisterTableDao;
import com.supporter.prj.ppm.template_register.entity.TableGroupHeadersVO;
import com.supporter.prj.ppm.template_register.entity.TableVO;
import com.supporter.prj.ppm.template_register.entity.TemplateKeyPointsExam;
import com.supporter.prj.ppm.template_register.entity.TemplateRegister;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterData;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterTable;
import com.supporter.prj.ppm.template_register.entity.TreeVO;
import com.supporter.prj.ppm.template_register.util.ConvertUtils;
import com.supporter.prj.ppm.template_register.util.JsonUtil;
import com.supporter.prj.ppm.tool_tips.service.ToolTipsService;
import com.supporter.util.UUIDHex;

/**   
 * @Title: Service
 * @Description: 模板注册
 * @author liyinfeng
 * @date 2017-7-05 10:25:07
 * @version V1.0   
 *
 */
@Service
@Transactional(TransManager.APP)
public class TemplateRegisterService implements ITemplateRegisterService {
	@Autowired
	private TemplateRegisterDao templateRegisterDao;
	@Autowired
	private TemplateRegisterDetailDao templateRegisterDetailDao;
	@Autowired
	private TemplateRegisterTableDao templateRegisterTableDao;
	@Autowired
	private TemplateRegisterDataDao templateRegisterDataDao;
	@Autowired
	private TemplateKeyPointsExamDao templateKeyPointsExamDao;
	@Autowired
	private ToolTipsService toolTipsService;
	@Autowired
	private WfExamService wfExamService;

	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public TemplateRegister get(String templateRegisterId) {
		return templateRegisterDao.get(templateRegisterId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public TemplateRegister initEditOrViewPage(String templateId, UserProfile user) {
		if (StringUtils.isBlank(templateId)) {// 新建
			TemplateRegister templateRegister = newTemplateRegister(user);
			templateRegister.setIsActive("T");
			templateRegister.setAdd(true);
			return templateRegister;
		} else {// 编辑
			TemplateRegister templateRegister =  templateRegisterDao.get(templateId);
			templateRegister.setAdd(false);
			return templateRegister;
		}
	}
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param detailId
	 * @return
	 */
	public TemplateRegisterDetail initEditOrViewPageDetail(String detailId, String templateId, UserProfile user) {
		if (StringUtils.isBlank(detailId)) {// 新建
			TemplateRegisterDetail templateRegisterDetail = newTemplateRegisterDetail(user);
			templateRegisterDetail.setTemplateId(templateId);
			templateRegisterDetail.setDirectoryStructure(10);
			templateRegisterDetail.setIsActive("T");
			templateRegisterDetail.setIsLineFeed("T");
			templateRegisterDetail.setAdd(true);
			templateRegisterDetail.setDisplayOrder(getDisplayOrder(templateId));
			return templateRegisterDetail;
		} else {// 编辑
			TemplateRegisterDetail templateRegisterDetail =  templateRegisterDetailDao.get(detailId);
			templateRegisterDetail.setAdd(false);
			return templateRegisterDetail;
		}
	}
	
	private int getDisplayOrder(String templateId){
		String hql = "select max(displayOrder) from TemplateRegisterDetail where templateId = ?";
		List<Integer> displayOrders = this.templateRegisterDetailDao.find(hql, templateId);
		int displayOrder = 0;
		if(displayOrders != null && !displayOrders.isEmpty() && displayOrders.get(0) != null){
			displayOrder = displayOrders.get(0);
		}
		displayOrder += 5;
		return displayOrder;
	}
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param detailId
	 * @return
	 */
	public TemplateRegisterDetail initEditOrViewPageTable(String detailId, String templateId, UserProfile user) {
		TemplateRegisterDetail templateRegisterDetail = templateRegisterDetailDao.get(detailId);
		if (templateRegisterDetail == null) {// 新建
			templateRegisterDetail = newTemplateRegisterDetail(user);
			templateRegisterDetail.setTextDisplay("表格");
			templateRegisterDetail.setIsActive("T");
			templateRegisterDetail.setDirectoryStructure(10);
			templateRegisterDetail.setTemplateId(templateId);
			templateRegisterDetail.setAdd(true);
			templateRegisterDetail.setDisplayOrder(getDisplayOrder(templateId));
			return templateRegisterDetail;
		} else {// 编辑
			templateRegisterDetail =  templateRegisterDetailDao.get(detailId);
			String hql = "from TemplateRegisterTable where detailId = ? ";
			List<TemplateRegisterTable> tableList = this.templateRegisterTableDao.find(hql, detailId);
			templateRegisterDetail.setTableList(tableList);
			templateRegisterDetail.setAdd(false);
			return templateRegisterDetail;
		}
	}
	
	/**
	 * 获取模板数据
	 * 
	 * @param templateId 
	 * @return List
	 */
	public String getDataByAuto(Map<String, String> paramId, String templateId, String paragraphNo, UserProfile user) {
		List<TemplateRegisterDetail> detailList = templateRegisterDetailDao.getDetailListByAuto(templateId, paragraphNo);
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("{");
		if(detailList != null && !detailList.isEmpty()){
			for(TemplateRegisterDetail detail : detailList){
				//自动识别
				if("AUTO".equals(detail.getContentType())){
					
				}else{
					//数据读入
					if(StringUtils.isNotBlank(paramId.get(detail.getEntityId()))){
						String dataReadIn = toolTipsService.replaceVarBy(detail.getDataReadIn(), 
								paramId.get(detail.getEntityId()), detail.getModuleId(), detail.getEntityId(), 
								detail.getEntityName(), detail.getDomainObjectId(), detail.getBusiTypeField(), 
								detail.getBusiType());
						//System.out.println("dataReadIn="+dataReadIn);
						//System.out.println("detail.getListSelect()="+detail.getSelectType());
						
						//通过码表再获取文字
						if(StringUtils.isNotBlank(dataReadIn) && StringUtils.isNotBlank(detail.getSelectType())){
							Map<String, String> map = getCommonCodeTableSelectCodeTables(detail.getSelectType());
							if(map != null){
								dataReadIn = map.get(dataReadIn);
							}
						}else{
							EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
									user, "获取数据读入",detail.getTextDisplay()+" 没有获取到数据读入的码表类别参数：" + detail.getSelectType()+",内容编码："+detail.getContentNo(),
									null, null);
						}
						if(dataReadIn == null){
							dataReadIn = "";
						}
						if(jsonStr.toString().length() > 1){
							jsonStr.append(",");
						}
						jsonStr.append("\""+detail.getContentNo()+"\": \"").append(dataReadIn).append("\"");
					}else{
						EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
								user, "获取数据读入失败",detail.getTextDisplay()+",没有获取到读入数据的参数：" + detail.getEntityId()+",内容编码："+detail.getContentNo(),
								null, null);
					}
				}
			}
		}
		jsonStr.append("}");
		return jsonStr.toString();
	}
	
	/**
	 * 码表公共方法
	 * 通过码表分类编码获取对应的码表信息
	 * @return
	 */
	public Map<String, String> getCommonCodeTableSelectCodeTables(String param) {
		//System.out.println("param="+param);
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(param)){
			List<IComCodeTableItem> items = EIPService.getComCodeTableService().getCodeTableItems(param);
			if(items != null && !items.isEmpty()) {
				for(IComCodeTableItem item : items) {
					//map.put(item.getExtField1(), item.getDisplayName());
					map.put(item.getItemId(), item.getDisplayName());
				}
			}
		}
		return map;
	}
	
	/**
	 * 加载模板数据显示
	 * @param contractId
	 * @return
	 */
	public String loadByDetail(String dataId, String templateId, String paragraphNo) {
		//System.out.println("dataId="+dataId);
		String hql = " from "+TemplateRegisterData.class.getName() + " where dataId = ? order by keyName";
		List<TemplateRegisterData> list = new ArrayList<TemplateRegisterData>();
		if(StringUtils.isNotBlank(paragraphNo)) {
			hql = " from "+TemplateRegisterData.class.getName() + " where dataId = ? and paragraphNo = ? order by keyName";
			list = this.templateRegisterDataDao.find(hql, dataId, paragraphNo);
		}else {
			list = this.templateRegisterDataDao.find(hql, dataId);
		}
		StringBuffer jsonStr = new StringBuffer();
		jsonStr.append("{");
		//StringBuffer jsonTable = new StringBuffer();
		//String templateId = "";
		String i = "0";
		//需要先获取表格编码
		Map<String, String> map = getDetailListByTable(templateId);
		Map<String, String> jsonMap = new HashMap<String, String>();
		for(TemplateRegisterData detail : list) {
			//templateId = detail.getTemplateId();
			String key = detail.getKeyName();
			//System.out.println("key"+key);
			String value = detail.getValueText();
	        if(value == null || "".equals(value)) {
	        	value = detail.getValueClob();
	        }
	        if(value == null) {
	        	value = "";
	        }
			//if(detail.getKeyName().indexOf("table") > -1){
	        	//String tempName = key.substring(key.indexOf("_")+1, key.lastIndexOf("_"));
	        	//String tempI = key.substring(key.lastIndexOf("_")+1);
	        boolean flag = true;
	        for (Map.Entry<String, String> entry : map.entrySet()) { 
	        	//System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	        	String temp = jsonMap.get(entry.getKey());
	        	if(temp == null){
	        		temp = "";
	        	}
	        	StringBuffer jsonTableTemp = new StringBuffer(temp);
	        	if(detail.getKeyName().indexOf(entry.getKey()+".") > -1) {
	        		flag = false;
	        		String tempName = key.substring(key.lastIndexOf(".")+1);
					String tempI = key.substring(key.indexOf(".")+1, key.lastIndexOf("."));
					if(i.equals(tempI)) {
						if(jsonTableTemp.length() != 0) {
							jsonTableTemp.append(",");
						}
						jsonTableTemp.append("\""+tempName+"\": \"").append(value).append("\"");
					}else {
						if(jsonTableTemp.length() != 0) {
							jsonTableTemp.append("},{");
						}
						i = tempI;
						jsonTableTemp.append("\""+tempName+"\": \"").append(value).append("\"");
					}
					jsonMap.put(entry.getKey(), jsonTableTemp.toString());
				}
	        }
			if(flag){
				jsonStr.append("\""+detail.getKeyName()+"\": \"").append(value).append("\",");
			}
	       /* if(detail.getKeyName().indexOf("].") > -1){
				String tempName = key.substring(key.indexOf(".")+1);
				String tempI = key.substring(key.indexOf("[")+1, key.indexOf("]"));
				if(i.equals(tempI)) {
					if(jsonTable.length() != 0) {
						jsonTable.append(",");
					}
					jsonTable.append("\""+tempName+"\": \"").append(value).append("\"");
				}else {
					i = tempI;
					jsonTable.append("},{").append("\""+tempName+"\": \"").append(value).append("\"");
				}
			}else {
				jsonStr.append("\""+detail.getKeyName()+"\": \"").append(value).append("\",");
			}*/
		}
		jsonStr.append("\"templateId\": \"").append(templateId).append("\",");
		jsonStr.append("\"paragraphNo\": \"").append(paragraphNo).append("\",");
		jsonStr.append("\"dataId\": \"").append(dataId).append("\"");
		jsonStr.append("}");
		//System.out.println("jsonStr="+jsonStr.toString());
		//System.out.println("jsonTable="+"[{"+jsonTable.toString()+"}]");
		//String tableStr = "[]";
		String tableStr = "";
		/*if(jsonTable.toString().length() > 0) {
			tableStr = "[{"+jsonTable.toString()+"}]";
		}*/
		for (Map.Entry<String, String> entry : jsonMap.entrySet()) { 
	        //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	        if(tableStr.length() > 0){
	        	tableStr += ",";
	        }
	        tableStr += "\""+entry.getKey()+"\""+": [{"+ entry.getValue()+"}]";
		}
		//System.out.println("tableStr="+tableStr);
		return jsonStr.toString()+"@{"+tableStr+"}";
	}
	
	/**
	 * 进入查看页面.
	 * @param moduleId
	 * @return
	 */
	public TemplateRegister viewPage(String templateRegisterId, UserProfile user) {
			TemplateRegister templateRegister =  templateRegisterDao.get(templateRegisterId);
			templateRegister.setTemplateRegisterDetail(templateRegisterDetailDao.get(templateRegisterId));
			return templateRegister;
	}
	
	/**
	 * 封装详情页附件下载部分
	 * @param bulletinId
	 * @return
	 */
	public String getFiles(TemplateRegister templateRegister){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAREPORT", "OAREPORT", templateRegister.getTemplateId());
		for(IFile file:list){
			sb.append("<a href=\"javascript:downloadFile('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public TemplateRegister newTemplateRegister(UserProfile auserprf_U){
        TemplateRegister ltemplateRegister_N = new TemplateRegister();
        
        ltemplateRegister_N.setCreatedById(auserprf_U.getPersonId());
        ltemplateRegister_N.setCreatedBy(auserprf_U.getName());
        ltemplateRegister_N.setCreatedDate(new  Date());
        
        ltemplateRegister_N.setModifiedById(auserprf_U.getPersonId());
        ltemplateRegister_N.setModifiedBy(auserprf_U.getName());
        ltemplateRegister_N.setModifiedDate(new Date());
        
        Dept dept = auserprf_U.getDept();
        if(dept != null){
	        ltemplateRegister_N.setDeptId(dept.getDeptId());
	        ltemplateRegister_N.setDeptName(dept.getName());
        }
        //ltemplateRegister_N.setStatus(TemplateRegister.DRAFT);
        ltemplateRegister_N.setTemplateId(com.supporter.util.UUIDHex.newId());
        return ltemplateRegister_N;
    }
    
    /**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public TemplateRegisterDetail newTemplateRegisterDetail(UserProfile auserprf_U){
        TemplateRegisterDetail ltemplateRegister_N = new TemplateRegisterDetail();
        
        ltemplateRegister_N.setCreatedById(auserprf_U.getPersonId());
        ltemplateRegister_N.setCreatedBy(auserprf_U.getName());
        ltemplateRegister_N.setCreatedDate(new  Date());
        
        ltemplateRegister_N.setModifiedById(auserprf_U.getPersonId());
        ltemplateRegister_N.setModifiedBy(auserprf_U.getName());
        ltemplateRegister_N.setModifiedDate(new Date());
        
        Dept dept = auserprf_U.getDept();
        if(dept != null){
	        ltemplateRegister_N.setDeptId(dept.getDeptId());
	        ltemplateRegister_N.setDeptName(dept.getName());
        }
        ltemplateRegister_N.setDetailId(com.supporter.util.UUIDHex.newId());
        return ltemplateRegister_N;
    }
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<TemplateRegister> getGrid(UserProfile user, JqGrid jqGrid, TemplateRegister templateRegister) {
		return templateRegisterDao.findPage(user, jqGrid, templateRegister);
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<TemplateRegisterDetail> getDetailGrid(UserProfile user, JqGrid jqGrid, String templateId, String parentId, TemplateRegisterDetail templateRegisterDetail) {
		return templateRegisterDetailDao.findPage(user, jqGrid, templateId, parentId, templateRegisterDetail);
	}

	/**
	 * 
	 * 	获取目录树
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<TreeVO> getTreeData(Map<String, Object> paramMap) {
		List<TreeVO> list = new ArrayList<TreeVO>();
		try {
			List<TemplateRegisterDetail> cMap = templateRegisterDetailDao.getAll(paramMap);
			ConvertUtils
					.convertTreeVO(cMap, list);
		} catch (Exception e) {
			XLogger.getLogger().myWarn("[执行getTreeData异常： " + e.toString() + "]");
		}
		return list;
	}
	
	/**
	 * 
	 * 	获取目录树
	 * 	用于显示评审要点
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<TreeVO> getTreeDataByReviewKeyPoint(Map<String, Object> paramMap) {
		List<TreeVO> list = new ArrayList<TreeVO>();
		try {
			List<TemplateRegisterDetail> cMap = templateRegisterDetailDao.getAllByReviewKeyPoint(paramMap);
			ConvertUtils.convertTreeVO(cMap, list);
		} catch (Exception e) {
			XLogger.getLogger().myWarn("[getTreeDataByReviewKeyPoint： " + e.toString() + "]");
		}
		return list;
	}
	
	/**
	 * 获取模板数据
	 * 
	 * @param templateId 
	 * @return List
	 */
	public List<TemplateRegisterDetail> getDetailList(String templateId, String paragraphNo) {
		return templateRegisterDetailDao.getDetailList(templateId, paragraphNo);
	}
	
	/**
	 * 获取评审要点
	 * 带审批意见
	 * @param templateId 
	 * @return List
	 */
	public List<TemplateRegisterDetail> getDetailListByReviewKeyPoint(String templateId, String procId, UserProfile user) {
		//获取评审要点
		List<TemplateRegisterDetail> list = templateRegisterDetailDao.getDetailListByReviewKeyPoint(templateId);
		//获取评审要点的审批意见
		String hql = "from TemplateKeyPointsExam where templateId = ? and empId = ? and procId = ? ";
		List<TemplateKeyPointsExam> examList = this.templateKeyPointsExamDao.find(hql, templateId, user.getPersonId(), procId);
		Map<String, TemplateKeyPointsExam> map = new HashMap<String, TemplateKeyPointsExam>();
		if(examList != null && !examList.isEmpty()){
			for(TemplateKeyPointsExam exam : examList){
				//信息项ID已经存在
				if(map.containsKey(exam.getDetailId())) {
					
				}else{
					map.put(exam.getDetailId(), exam);
				}
			}
		}
		if(list != null && !list.isEmpty()){
			for(TemplateRegisterDetail detail : list){
				TemplateKeyPointsExam exam = map.get(detail.getDetailId());
				if(exam != null){
					detail.setExamResult(exam.getExamResult());
					detail.setOpinionDesc(exam.getOpinionDesc());
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取评审要点
	 * 
	 * @param templateId 
	 * @return List
	 */
	public List<TemplateRegisterDetail> getDetailListByReviewKeyPoint(String templateId) {
		templateRegisterDetailDao.getDetailListByReviewKeyPoint(templateId);
		return templateRegisterDetailDao.getDetailListByReviewKeyPoint(templateId);
	}
	
	/**
	 * 获取评审要点的段落
	 * 
	 * @param templateId 
	 * @return List
	 */
	public List<TemplateRegisterDetail> getDetailListByParagraphNo(String templateId) {
		return templateRegisterDetailDao.getDetailListByParagraphNo(templateId);
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<TemplateRegisterTable> getTableGrid(UserProfile user, JqGrid jqGrid, String detailId, TemplateRegisterTable templateRegisterTable) {
		return templateRegisterTableDao.findPage(user, jqGrid, detailId, templateRegisterTable);
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<TemplateKeyPointsExam> getExamGridByEmpId(UserProfile user, JqGrid jqGrid, String empId, String templateId, String procId) {
		return templateKeyPointsExamDao.findPage(user, jqGrid, empId, templateId, procId);
	}
	
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<WfExam> getExamGridByProcId(UserProfile user, JqGrid jqGrid, String procId) {
		List < WfExam > recs = wfExamService.getHisExamList(procId, true);
		jqGrid.setRows(recs);
		return recs;
	}
	/**
	 * 根据流程Id获取评审要点审批有条件通过的要点以及审批意见等
	 * @param user
	 * @param jqGrid
	 * @param procId
	 * @return
	 */
	public List<TemplateKeyPointsExam> getPointsExamGridByProcId(
			UserProfile user, JqGrid jqGrid, String procId) {
		List<TemplateKeyPointsExam> recs = templateKeyPointsExamDao
				.findBy("procId", procId);
		List<TemplateKeyPointsExam> pointRecs = new ArrayList<TemplateKeyPointsExam>();
		for (TemplateKeyPointsExam wfExam : recs) {
			String textDisplay = "";
			String opinionDesc = "";
			if (wfExam.getExamResult().equals("F")) {
				textDisplay = wfExam.getTextDisplay();
				opinionDesc = wfExam.getOpinionDesc();
				wfExam.setTextDisplay(textDisplay);
				wfExam.setOpinionDesc(opinionDesc);
				pointRecs.add(wfExam);
			}
		}
		return pointRecs;
	}	
	public List<TemplateKeyPointsExam> getPointsExamGridByProcId(String procId) {
		List<TemplateKeyPointsExam> recs = templateKeyPointsExamDao
				.findBy("procId", procId);
		List<TemplateKeyPointsExam> pointRecs = new ArrayList<TemplateKeyPointsExam>();
		for (TemplateKeyPointsExam wfExam : recs) {
			String textDisplay = "";
			String opinionDesc = "";
			if (wfExam.getExamResult().equals("F")) {
				textDisplay = wfExam.getTextDisplay();
				opinionDesc = wfExam.getOpinionDesc();
				wfExam.setTextDisplay(textDisplay);
				wfExam.setOpinionDesc(opinionDesc);
				pointRecs.add(wfExam);
			}
		}
		return pointRecs;
	}
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public String getTableEditGrid(UserProfile user, JqGrid jqGrid, String detailId, TemplateRegisterTable templateRegisterTable) {
		List<TemplateRegisterTable> list = templateRegisterTableDao.findPage(user, jqGrid, detailId, templateRegisterTable);
		List<TableVO> tableVOList = new ArrayList<TableVO>();
		List<TableGroupHeadersVO> headers = new ArrayList<TableGroupHeadersVO>();
		if(list != null && !list.isEmpty()){
			TableVO tableIdVO = new TableVO();
			tableIdVO.setLabel("主键");
			tableIdVO.setName("id");
			tableIdVO.setKey(true);
			tableIdVO.setHidden(true);
			tableVOList.add(tableIdVO);
			for(TemplateRegisterTable table : list){
				TableVO tableVO = new TableVO();
				tableVO.setLabel(table.getTextDisplay());
				tableVO.setName(table.getContentNo());
				tableVO.setIndex(table.getContentNo());
				tableVO.setSortable(false);
				tableVOList.add(tableVO);
				if(table.getNumberOfColumns() > 1 && StringUtils.isNotBlank(table.getTitleText())){
					TableGroupHeadersVO header = new TableGroupHeadersVO();
					header.setStartColumnName(table.getContentNo());
					header.setNumberOfColumns(table.getNumberOfColumns());
					header.setTitleText(table.getTitleText());
					headers.add(header);
				}
			}
		}
		String s1 = JsonUtil.list2json(tableVOList);
		String s2 = JsonUtil.list2json(headers);
		String s3 = JsonUtil.list2json(list);
		//System.out.println("s1==="+s1);
		//System.out.println("s2==="+s2);
		//System.out.println("s3==="+s3);
		//jqGrid.setRows(tableVOList);
		//return tableVOList;
		return s1+"@"+s2+"@"+s3;
	}
	/**
	 * 将评审中的评审要点数据复制到评审验证下
	 * @param user
	 * @param dataId
	 * @param reviewVerId
	 * @return
	 */
	public OperResult<TemplateRegister> getDataForReviewVer(UserProfile user, String dataId1, String dataId2) {
//		List<TemplateRegisterData> verDataList = templateRegisterDataDao.findBy("dataId", dataId2);
//		if(verDataList!=null&&verDataList.size()>0) {
//			return null;
//		}
		List<TemplateRegisterData> dataList = templateRegisterDataDao.findBy("dataId", dataId1);
		for(TemplateRegisterData data: dataList) {
			saveDataForUpdate(user,data,dataId2);
		}
		return null;
	}
	public void saveDataForUpdate(UserProfile user, TemplateRegisterData data1,String dataId2) {
		TemplateRegisterData verData = new TemplateRegisterData();
		verData.setDetailId(UUIDHex.newId());
		verData.setTemplateId(data1.getTemplateId());
		verData.setDataId(dataId2);
		verData.setKeyName(data1.getKeyName());
		verData.setValueText(data1.getValueText());
		verData.setValueClob(data1.getValueClob());
		verData.setFieldType(data1.getFieldType());
		verData.setRemark(data1.getRemark());
		verData.setCreatedBy(data1.getCreatedBy());
		verData.setCreatedById(data1.getCreatedById());
		verData.setCreatedDate(data1.getCreatedDate());
		verData.setModifiedBy(user.getName());
		verData.setModifiedById(user.getPersonId());
		verData.setModifiedDate(new Date());
		verData.setDeptId(data1.getDeptId());
		verData.setDeptName(data1.getDeptName());
		verData.setParagraphNo(data1.getParagraphNo());
		templateRegisterDataDao.save(verData);
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<TemplateRegister> saveOrUpdate(UserProfile user, TemplateRegister templateRegister, Map< String, Object > valueMap) {
		TemplateRegister ret = templateRegisterDao.get(templateRegister.getTemplateId());
		templateRegister.setTemplateId(templateRegister.getTemplateNo());
		if (ret == null) {// 新建
			this.templateRegisterDao.save(templateRegister);
			TemplateRegisterDetail con = templateRegister.getTemplateRegisterDetail();
			if(con != null){
				templateRegister.getTemplateRegisterDetail().setTemplateId(templateRegister.getTemplateId());
				this.templateRegisterDetailDao.save(con);
			}
		} else {// 编辑
			this.templateRegisterDao.clear();
			this.templateRegisterDao.update(templateRegister);
			TemplateRegisterDetail con = templateRegister.getTemplateRegisterDetail();
			if(con != null){
				this.templateRegisterDetailDao.update(con);
			}
		}
		// 记录日志
		EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
				user, "保存成功","ID：" + templateRegister.getTemplateId(),
				templateRegister, null);
		return OperResult.succeed("saveSuccess", null,
				templateRegister);

	}
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<TemplateRegisterDetail> saveOrUpdateDetail(UserProfile user, 
			TemplateRegisterDetail templateRegisterDetail, Map< String, Object > valueMap) {
		if (templateRegisterDetail.getAdd()) {// 新建
			templateRegisterDetail.setParentId(getParentId(templateRegisterDetail.getDirectoryStructure(),
					templateRegisterDetail.getTemplateId(), templateRegisterDetail.getDetailId(), templateRegisterDetail.getDisplayOrder()));
			this.templateRegisterDetailDao.save(templateRegisterDetail);
		} else {// 编辑
			templateRegisterDetail.setParentId(getParentId(templateRegisterDetail.getDirectoryStructure(),
					templateRegisterDetail.getTemplateId(), templateRegisterDetail.getDetailId(), templateRegisterDetail.getDisplayOrder()));
			//将后续一段的段落名称都修改成这个
			if(StringUtils.isNotBlank(templateRegisterDetail.getParagraphNo())){
				setParagraphNo(templateRegisterDetail.getTemplateId(), templateRegisterDetail.getDirectoryStructure(), 
						templateRegisterDetail.getDisplayOrder(), templateRegisterDetail.getParagraphNo(), templateRegisterDetail.getDetailId());
			}
			this.templateRegisterDetailDao.update(templateRegisterDetail);
		}
		// 记录日志
		EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
				user, "保存成功","ID：" + templateRegisterDetail.getDetailId(),
				templateRegisterDetail, null);
		return OperResult.succeed("saveSuccess", null,
				templateRegisterDetail);
	}
	
	/**
	 * 更新一个段落编码
	 * @param templateId
	 * @param directoryStructure
	 * @param displayOrderStart
	 * @param paragraphNo
	 */
	private void setParagraphNo(String templateId, int directoryStructure, int displayOrderStart, 
			String paragraphNo, String detailId){
		//先获取与设置段落名称相同大纲级别的信息项的顺序值
		String hql = "select displayOrder from TemplateRegisterDetail where templateId = ?  and directoryStructure = ? " +
				"and detailId <> ? and displayOrder > ? order by displayOrder";
		List<Integer> displayOrderList = this.templateRegisterDetailDao.find(hql, templateId, directoryStructure, 
				detailId, displayOrderStart);
		int displayOrder = 0;
		if(displayOrderList != null && !displayOrderList.isEmpty() && displayOrderList.get(0) != null){
			displayOrder = displayOrderList.get(0);
		}
		//如果没找到，获取下最大值
		if(displayOrder == 0){
			String maxHql = "select max(displayOrder) from TemplateRegisterDetail where templateId = ? ";
			List<Integer> maxDisplayOrderList = this.templateRegisterDetailDao.find(maxHql, templateId);
			if(maxDisplayOrderList != null && !maxDisplayOrderList.isEmpty() && maxDisplayOrderList.get(0) != null){
				displayOrder = maxDisplayOrderList.get(0);
			}
		}
		//更新这一段的段落编码
		String updateHql = "UPDATE TemplateRegisterDetail SET paragraphNo = ? "
				+ "WHERE templateId = ? and displayOrder > ? and displayOrder < ?";
		this.templateRegisterDetailDao.execUpdate(updateHql, paragraphNo, templateId, displayOrderStart, displayOrder);
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public OperResult<TemplateRegisterDetail> saveOrUpdateTable(UserProfile user, 
			TemplateRegisterDetail templateRegisterDetail) {
		TemplateRegisterDetail ret = templateRegisterDetailDao.get(templateRegisterDetail.getDetailId());
		templateRegisterDetail.setIsLineFeed("T");
		if (ret == null) {// 新建
			templateRegisterDetail.setParentId(getParentId(templateRegisterDetail.getDirectoryStructure(),
					templateRegisterDetail.getTemplateId(), templateRegisterDetail.getDetailId(), templateRegisterDetail.getDisplayOrder()));
			this.templateRegisterDetailDao.save(templateRegisterDetail);
		} else {// 编辑
			this.templateRegisterDetailDao.clear();
			// 删除预算明细
			deleteItem(templateRegisterDetail.getDelItemIds());
			templateRegisterDetail.setParentId(getParentId(templateRegisterDetail.getDirectoryStructure(),
					templateRegisterDetail.getTemplateId(), templateRegisterDetail.getDetailId(), templateRegisterDetail.getDisplayOrder()));
			this.templateRegisterDetailDao.update(templateRegisterDetail);
		}
		// 保存或更新表格明细
		List<TemplateRegisterTable> tableList = templateRegisterDetail.getTableList();
		//System.out.println("tableList="+tableList);
		if(CollectionUtils.isNotEmpty(tableList)){
			saveOrUpdateTableList(templateRegisterDetail.getDetailId(), templateRegisterDetail.getContentNo(), tableList);
		}
		// 记录日志
		EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
				user, "保存成功","ID：" + templateRegisterDetail.getDetailId(),
				templateRegisterDetail, null);
		return OperResult.succeed("saveSuccess", null,
				templateRegisterDetail);
	}
	
	/**
	 * 删除表格明细
	 * @param delStageIds
	 */
	public void deleteItem(String delItemIds) {
		if (StringUtils.isNotBlank(delItemIds)) {
			for (String itemId : delItemIds.split(",")) {
				this.templateRegisterTableDao.deleteByProperty("lineId", itemId);
			}
		}
	}
	
	/**
	 * 保存或更新预算明细
	 * @param id
	 * @param itemList
	 */
	public void saveOrUpdateTableList(String detailId, String tableNo, List<TemplateRegisterTable> itemList) {
		for (TemplateRegisterTable item : itemList) {
			item.setDetailId(detailId);
			item.setTableNo(tableNo);
			if (StringUtils.isBlank(item.getLineId()) || item.getAdd()) {
				item.setLineId(com.supporter.util.UUIDHex.newId());
				this.templateRegisterTableDao.save(item);
			} else {
				this.templateRegisterTableDao.update(item);
			}
		}
	}
	
	/**
	 * 获取模板里所有的表格
	 * @param templateId
	 * @return
	 */
	private Map<String, String> getDetailListByTable(String templateId){
		//需要先获取表格编码
		List<TemplateRegisterDetail> detailList = new ArrayList<TemplateRegisterDetail>();
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtils.isNotBlank(templateId)) {
			String hql = "from "+ TemplateRegisterDetail.class.getName()+" where templateId = ? and textDisplay = '表格' and isActive = 'T'";
			detailList = this.templateRegisterDetailDao.find(hql, templateId);
			if(detailList != null && !detailList.isEmpty()){
				for(TemplateRegisterDetail detail : detailList){
					map.put(detail.getContentNo(), detail.getContentNo());
				}
			}
		}
		return map;
	}
	
	private List<TemplateRegisterDetail> getDetailListByTableList(String templateId){
		//需要先获取表格编码
		List<TemplateRegisterDetail> detailList = new ArrayList<TemplateRegisterDetail>();
		if(StringUtils.isNotBlank(templateId)) {
			String hql = "from "+ TemplateRegisterDetail.class.getName()+" where templateId = ? and textDisplay = '表格' ";
			detailList = this.templateRegisterDetailDao.find(hql, templateId);
		}
		return detailList;
	}
	
	public String delTemplateDataByDataId(String dataId){
		String warning = "";
		if(StringUtils.isNotBlank(dataId)) {
			String hql = "from "+ TemplateRegisterData.class.getName()+" where dataId = ?";
			try{
				List<TemplateRegisterData> list = this.templateRegisterDataDao.find(hql, dataId);
				templateRegisterDataDao.delete(list);
				warning = "删除模板数据成功";
			}catch(Exception e){
				e.printStackTrace();
				warning = "删除模板数据时报错："+e.getMessage();
			}
		}else{
			warning = "dataId值为空";
		}
		return warning;
	}
	
	/**
	 * 保存或修改模板对象
	 * @param user
	 * @param map
	 * @return
	 */
	public void saveOrUpdateByDetailAndData(UserProfile user, Map<String, String[]> map) {
		for (Map.Entry<String, String[]> entry : map.entrySet()) { 
			System.out.println("keyName::"+entry.getKey()+"::value::"+entry.getValue()[0]);
		}
		String dataId = (String)(map.get("dataId")[0]);
		String templateId = (String)(map.get("templateId")[0]);
		String paragraphNo = (String)(map.get("paragraphNo")[0]);
		if(StringUtils.isBlank(dataId)){
			dataId = (String)(map.get("dataId")[1]);
		}
		if(StringUtils.isBlank(templateId)){
			templateId = (String)(map.get("templateId")[1]);
		}
		if(StringUtils.isBlank(paragraphNo)){
			if(map.get("paragraphNo").length == 2) {
				paragraphNo = (String)(map.get("paragraphNo")[1]);
			}
		}
		System.out.println("dataId::"+dataId);
		System.out.println("templateId::"+templateId);
		System.out.println("paragraphNo::"+paragraphNo);
		if(StringUtils.isNotBlank(dataId) && StringUtils.isNotBlank(templateId)){
			//删除之前的数据
			if(StringUtils.isNotBlank(paragraphNo)) {
				String hql = "from "+ TemplateRegisterData.class.getName()+" where dataId = ? and paragraphNo = ?";
				List<TemplateRegisterData> list = this.templateRegisterDataDao.find(hql, dataId, paragraphNo);
				templateRegisterDataDao.delete(list);
			}else {
				String hql = "from "+ TemplateRegisterData.class.getName()+" where dataId = ? ";
				List<TemplateRegisterData> list = this.templateRegisterDataDao.find(hql, dataId);
				templateRegisterDataDao.delete(list);
			}
		//需要先获取表格编码
		List<TemplateRegisterDetail> detailList = getDetailListByTableList(templateId);
		
		for (Map.Entry<String, String[]> entry : map.entrySet()) { 
			System.out.println("keyName::"+entry.getKey()+"::value::"+entry.getValue()[0]);
			// 字段名
			String keyName = entry.getKey();
			String value = entry.getValue()[0];
			String valueText = "";
			String valueClob = "";
			int fieldType = 1; 
			if(!"templateId".equals(keyName) && !"dataId".equals(keyName) && !"paragraphNo".equals(keyName)) {
				// 装配基础信息
				TemplateRegisterData data = new TemplateRegisterData();
				loadTemplateDetail(data, user);
				//对于选项或输入的情况，当时是输入是value值为inputValue，只能取编码加Name的值
				if("inputValue".equals(value)) {
					value = map.get(keyName+"Name")[0];
				}
				// 字段值
				if(value.length() > 64) {
					valueClob = value;
					fieldType = 2;
				}else {
					valueText = value;
				}
				//判断是否是表格信息
				boolean flag = false;
				if(detailList != null && !detailList.isEmpty()){
					for(TemplateRegisterDetail detail : detailList){
						if(keyName.indexOf(detail.getContentNo()+"[") > -1) {
							String tempName = keyName.substring(keyName.indexOf(".")+1);
							
							String i = keyName.substring(keyName.indexOf("[")+1, keyName.indexOf("]"));
							data.setKeyName(detail.getContentNo()+"."+i+"."+tempName);
							flag = true;
						}
					}
				}
				/*if(keyName.indexOf("tableList[") > -1) {
					String tempName = keyName.substring(keyName.indexOf(".")+1);
					String i = keyName.substring(keyName.indexOf("[")+1, keyName.indexOf("]"));
					data.setKeyName("table_"+tempName+"_"+i);
				}else {
					data.setKeyName(keyName);
				}*/
				if(!flag){
					data.setKeyName(keyName);
				}
				System.out.println("getKeyName"+data.getKeyName());
				System.out.println("getValueText"+valueText);
				System.out.println("getValueText"+valueClob);
				data.setValueText(valueText);
				data.setValueClob(valueClob);
				data.setFieldType(fieldType);
				data.setTemplateId(templateId);
				data.setParagraphNo(paragraphNo);
				data.setDataId(dataId);
				templateRegisterDataDao.save(data);
			}
		}
		}else{
			EIPService.getLogService(TemplateRegister.LOG_TYPE).info(user, "保存模板数据错误：", 
					"dataId或templateId为空", null, null);
		}
	}
	
	/**
	 * 保存或修改
	 * 评审要点审批意见
	 * @param user
	 * @param map
	 * @return
	 */
	public void saveOrUpdateByKeyPointsExam(UserProfile user, Map<String, String[]> map) {
		//整理detailId
		Map<String, String> detailIdMap = new HashMap<String, String>();
		for (Map.Entry<String, String[]> entry : map.entrySet()) { 
			// 字段名
			String keyName = entry.getKey();
			String detailId = keyName.split("_")[0];
			if(detailId.length() == 32){
				if(!detailIdMap.containsKey(detailId)){
					detailIdMap.put(detailId,detailId);
				}
			}
		}
		//保存审批意见
		// 装配基础信息
		for (Map.Entry<String, String> entry : detailIdMap.entrySet()) { 
			String keyName = entry.getKey();
			TemplateKeyPointsExam data = new TemplateKeyPointsExam();
			loadTemplateExam(data, user);
			data.setExamResult(map.get(keyName+"_examResult")[0]);
			data.setOpinionDesc(map.get(keyName+"_opinionDesc")[0]);
			data.setDetailId(keyName);
			data.setTextDisplay(map.get(keyName+"_textDisplay")[0]);
			data.setTemplateId(map.get("templateId")[0]);
			data.setProcId(map.get("procId")[0]);
			templateKeyPointsExamDao.save(data);
		}
	}
	
	/**
	 * 模板信息项表
	 * 添加基础信息
	 * @return
	 */
	public TemplateRegisterData loadTemplateDetail(TemplateRegisterData templateDetail, UserProfile user) {
		templateDetail.setDetailId(UUIDHex.newId());
		templateDetail.setCreatedBy(user.getName());
		templateDetail.setCreatedById(user.getPersonId());
		templateDetail.setCreatedDate(new Date());
		
		templateDetail.setModifiedBy(user.getName());
		templateDetail.setModifiedById(user.getPersonId());
		templateDetail.setModifiedDate(new Date());
		Dept dept = user.getDept();
		if (dept != null) {
			templateDetail.setDeptName(dept.getName());
			templateDetail.setDeptId(dept.getDeptId());
		}
		return templateDetail;
	}
	
	/**
	 * 模板评审要点审批意见
	 * 添加基础信息
	 * @return
	 */
	public TemplateKeyPointsExam loadTemplateExam(TemplateKeyPointsExam templateExam, UserProfile user) {
		templateExam.setRecordId(UUIDHex.newId());
		templateExam.setCreatedDate(new Date());
		templateExam.setEmpId(user.getPersonId());
		templateExam.setEmpName(user.getName());
		return templateExam;
	}
	
	/**
	 * 查找最近的上级，获取ID
	 * @return
	 */
	private String getParentId(int level, String templateId, String detailId, int displayOrder){
		String hql = "from " + TemplateRegisterDetail.class.getName() + " where directoryStructure < ? " +
				"and templateId = ? and isActive = 'T' and detailId <> ? and displayOrder < ? order by displayOrder desc ";
		List<TemplateRegisterDetail> list = this.templateRegisterDetailDao.find(hql,level,templateId, detailId, displayOrder);
		String parentId = "";
		if(list != null && !list.isEmpty()){
			parentId = list.get(0).getDetailId();
		}
		return parentId;
	}
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public TemplateRegister update(TemplateRegister templateRegister) {
			this.templateRegisterDao.update(templateRegister);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return templateRegister;

	}
	/**
	 * 判断编码是否重复
	 * 
	 * @param templateId
	 * @param templateNo
	 * @return
	 */
	public boolean checkTemplateNoIsValid(String templateId, String templateNo) {
		return this.templateRegisterDao.checkTemplateNoIsValid(templateId, templateNo);
	}
	
	/**
	 * 判断信息项编码是否重复
	 * 
	 * @param detailId
	 * @param contentNo
	 * @return
	 */
	public boolean checkContentNoIsValid(String detailId, String contentNo) {
		return this.templateRegisterDetailDao.checkContentNoIsValid(detailId, contentNo);
	}
	
	/**
	 * 判断分段显示编码是否重复
	 * 
	 * @param detailId
	 * @param paragraphNo
	 * @param templateId
	 * @return
	 */
	public boolean checkParagraphNoIsValid(String detailId, String paragraphNo, String templateId) {
		return this.templateRegisterDetailDao.checkParagraphNoIsValid(detailId, paragraphNo, templateId);
	}

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String templateRegisterIds) {
		if (StringUtils.isNotBlank(templateRegisterIds)) {
			for (String templateRegisterId : templateRegisterIds.split(",")) {
				TemplateRegister templateRegister = get(templateRegisterId);
				//ReportAuthUtil.canExecute(user, TemplateRegister.MODULE_ID, templateRegisterId, templateRegister);
				this.templateRegisterDetailDao.deleteByProperty("templateId", templateRegisterId);
				this.templateRegisterDao.delete(templateRegister);
			}
			// 记录日志
			/*String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, templateRegisterIds);
			EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					null, null);*/
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void detailDel(UserProfile user, String ids) {
		if (StringUtils.isNotBlank(ids)) {
			for (String detailId : ids.split(",")) {
				//System.out.println("detailId="+detailId);
				TemplateRegisterDetail templateRegisterDetail = templateRegisterDetailDao.get(detailId);
				templateRegisterTableDao.deleteByProperty("detailId", detailId);
				if(templateRegisterDetail != null)
					this.templateRegisterDetailDao.delete(detailId);
			}
			// 记录日志
			/*String logMessage = MessageFormat.format(
					LogConstant.PUBLISH_REPORT_LOG_MESSAGE, ids);
			EIPService.getLogService(TemplateRegister.LOG_TYPE).info(
					user, LogConstant.PUBLISH_REPORT_LOG_ACTION, logMessage,
					null, null);*/
		}
	}

}
