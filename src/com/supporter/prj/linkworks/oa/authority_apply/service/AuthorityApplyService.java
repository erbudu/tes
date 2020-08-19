package com.supporter.prj.linkworks.oa.authority_apply.service;
 
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.cneec.tpc.prj_contract_settlement.util.CommonUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip.todo.entity.Todo;
import com.supporter.prj.linkworks.oa.ExtractFiles;
import com.supporter.prj.linkworks.oa.authority_apply.dao.AuthorityApplyBoardDao;
import com.supporter.prj.linkworks.oa.authority_apply.dao.AuthorityApplyContentDao;
import com.supporter.prj.linkworks.oa.authority_apply.dao.AuthorityApplyDao;
import com.supporter.prj.linkworks.oa.authority_apply.dao.AuthorityApplyPersonDao;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApply;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyBoard;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyContent;
import com.supporter.prj.linkworks.oa.authority_apply.entity.AuthorityApplyPerson;
import com.supporter.prj.linkworks.oa.authority_apply.util.AuthConstant;
import com.supporter.prj.linkworks.oa.authority_apply.util.AuthUtil;
import com.supporter.prj.linkworks.oa.authority_apply.util.LogConstant;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.file_upload.entity.IFile;
import com.supporter.prj.eip_service.file_upload.service.IFileUploadService;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.util.CommonUtil;

@Service
public class AuthorityApplyService {
	@Autowired
	private AuthorityApplyDao authorityApplyDao;
	@Autowired
	private AuthorityApplyBoardDao authorityApplyBoardDao;	
	@Autowired
	private AuthorityApplyContentDao authorityApplyContentDao;
	@Autowired
	private AuthorityApplyPersonDao authorityApplyPersonDao;
	
	@Autowired
	private ExtractFiles extractFiles;


	public AuthorityApply get(String moduleId) {
		return authorityApplyDao.get(moduleId);
	}
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public AuthorityApply initEditOrViewPage(JqGrid jqGrid,String applyId, UserProfile user,String isEnglish) {
		if (StringUtils.isBlank(applyId)) {// 新建
			AuthorityApply authorityApply = newAuthorityApply(user,null);
			authorityApply.setAdd(true);
			return authorityApply;
		} else {// 编辑
			//获得主表
			AuthorityApply authorityApply =  authorityApplyDao.get(applyId);
			
			if(authorityApply!=null){
				//获得从表AuthorityApplyContent
				AuthorityApplyContent authorityApplyContent=authorityApplyContentDao.get(applyId);
				if(authorityApplyContent!=null&&!authorityApplyContent.equals("")){
					if(authorityApply.getType()!=null){
						if(authorityApply.getType().equals("1")||(isEnglish!=null&&isEnglish.equals("no"))){//中文授权书
							authorityApply.setAuthorityApplyReasonZHDesc(authorityApplyContent.getAuthorityApplyReason());
						}else if(authorityApply.getType().equals("2")||(isEnglish!=null&&isEnglish.equals("yes"))){//英文授权书
							authorityApply.setAuthorityApplyReasonEDesc(authorityApplyContent.getAuthorityApplyReasonE());
						}else{
							authorityApply.setAuthorityApplyReasonZHDesc(authorityApplyContent.getAuthorityApplyReason());
							authorityApply.setAuthorityApplyReasonEDesc(authorityApplyContent.getAuthorityApplyReasonE());
						}
					}				
					authorityApply.setAuthorityApplyNoteDesc(authorityApplyContent.getAuthorityApplyNote());
				}
				
				//获得从表AuthorityApplyPerson
				List<AuthorityApplyPerson> personList=authorityApplyPersonDao.getByApplyId(applyId);
				String personName="";
				String personNameDesc="";
				String personNameCardDesc="";
				if(personList!=null&&personList.size()>0){
					
					for(AuthorityApplyPerson authorityApplyPerson:personList){					
						if(CommonUtil.trim(authorityApply.getType()).equals("1")||(isEnglish!=null&&isEnglish.equals("no"))){//中文授权书
							personName= personName+authorityApplyPerson.getAuthName()+",";
							if(authorityApplyPerson.getGender()!=null){							
								if(authorityApplyPerson.getGender().equals("男")){
									personNameDesc = personNameDesc+authorityApplyPerson.getAuthName()+"先生,";
									personNameCardDesc=personNameCardDesc+authorityApplyPerson.getAuthName()+"先生（身份证/护照号码："+authorityApplyPerson.getPassportNo()+"）,";
								}else if(authorityApplyPerson.getGender().equals("女")){
									personNameDesc = personNameDesc+authorityApplyPerson.getAuthName()+"女士,";
									personNameCardDesc=personNameCardDesc+authorityApplyPerson.getAuthName()+"女士（身份证/护照号码："+authorityApplyPerson.getPassportNo()+"）,";

								}							
							}
						}else if(CommonUtil.trim(authorityApply.getType()).equals("2")||(isEnglish!=null&&isEnglish.equals("yes"))){//英文授权书
							personName = personName+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+",";
							if(authorityApplyPerson.getGender()!=null){
								if(authorityApplyPerson.getGender().equals("男")){
									personNameDesc = personNameDesc+"Mr "+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+",";
									personNameCardDesc=personNameCardDesc+"Mr "+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+" whose ID card /Passport No. is: "+authorityApplyPerson.getPassportNo()+",";

								}else if(authorityApplyPerson.getGender().equals("女")){
									personNameDesc = personNameDesc+"Mrs "+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+",";
									personNameCardDesc=personNameCardDesc+"Mrs "+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+" whose ID card /Passport No. is:"+authorityApplyPerson.getPassportNo()+",";

								}							
							}
						}else if(CommonUtil.trim(authorityApply.getType()).equals("3")||CommonUtil.trim(authorityApply.getType()).equals("4")){
							personName = personName+authorityApplyPerson.getAuthName()+","+authorityApplyPerson.getAuthNameE()+" "+authorityApplyPerson.getAuthNameF()+",";
						}
						
					}
					
					if(personName.length()>0){
						int endIndex=personName.length()-1;
						authorityApply.setPersonName(personName.substring(0, endIndex));
					}

					if(personNameDesc.length()>0){
						int endIndex=personNameDesc.length()-1;
						authorityApply.setPersonNameDesc(personNameDesc.substring(0, endIndex));
					}
					if(personNameCardDesc.length()>0){
						int endIndex=personNameCardDesc.length()-1;
						authorityApply.setPersonNameCardDesc(personNameCardDesc.substring(0, endIndex));
					}
					
				}
			    String dateFrom = CommonUtil.format(authorityApply.getDateFrom(),"yyyy年MM月dd日");
			    String dateTo = CommonUtil.format(authorityApply.getDateTo(),"yyyy年MM月dd日");
		  	    if(CommonUtil.trim(authorityApply.getType()).equals("1")||(isEnglish!=null&&isEnglish.equals("no"))){ //中文授权书
					
					authorityApply.setDatesFrom(dateFrom);
					authorityApply.setDatesTo(dateTo);
		  	    	
		  	    }else if(CommonUtil.trim(authorityApply.getType()).equals("2")||(isEnglish!=null&&isEnglish.equals("yes"))){  //英文授权书
		  	    //语言环境设置为英文
					Locale l = new Locale("en");
					//日期格式
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
					//处理开始日期
					Date dateF=null;
					try {
						dateF = sdf.parse(dateFrom);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String yearFrom = String.format("%tY",dateF);
					String monthFrom = String.format(l,"%tB",dateF);
					String dayFrom = String.format("%td",dateF);
					//处理结束日期
					Date dateT=null;
					try {
						dateT = sdf.parse(dateTo);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String yearTo = String.format("%tY",dateT);
					String monthTo = String.format(l,"%tB",dateT);
					String dayTo = String.format("%td",dateT);
					
					
					authorityApply.setDatesFrom(monthFrom+" "+dayFrom+", "+yearFrom);
					authorityApply.setDatesTo(monthTo+" "+dayTo+", "+yearTo );
					
		  	    }
							
				authorityApply.setLanguage(authorityApply.getType());
				authorityApply.setNotifierIds(authorityApply.getCompanyLeaderId());
				authorityApply.setNotifierNames(authorityApply.getCompanyLeaderName());
				authorityApply.setFiles(getFiles(authorityApply));
				authorityApply.setAdd(false);
				return authorityApply;
			}else{
				AuthorityApply authorityApplyZ = newAuthorityApply(user,applyId);
				authorityApplyZ.setAdd(true);
				return authorityApplyZ;
			}
			
		}
	}
	
	
	
	/**
	 * 进入新建或编辑或查看页面需要加载的信息.
	 * @param moduleId
	 * @return
	 */
	public AuthorityApply initSignNo(JqGrid jqGrid,String applyId, UserProfile user) {
		AuthorityApply authorityApply =  authorityApplyDao.get(applyId);
		if(authorityApply.getSignNo()==null||authorityApply.equals("")){
			authorityApply.setSignNo(this.getCurrentNo());
			authorityApplyDao.update(authorityApply);
		}		
		return authorityApply;
	}
	
	/**
	 * 保存授权书编号
	 * @param applyId
	 * @param signNo
	 * @return
	 */
	public AuthorityApply saveSignNo(String applyId, String signNo) {
		AuthorityApply authorityApply =  authorityApplyDao.get(applyId);
		if(StringUtils.isNotBlank(signNo) && authorityApply!= null){
			if (!this.authorityApplyDao.isRepeatBySignNo(applyId,signNo)){
				authorityApply.setSignNo(signNo);
				authorityApplyDao.update(authorityApply);
			}else {
				authorityApply.setSignNo("isRepeat");
			}
		}
		return authorityApply;
	}
	
	
	
	/**
     * 新建实例,并初始化必要的属性.
     * @param auserprf_U
     * @return
     */
    public AuthorityApply newAuthorityApply(UserProfile auserprf_U,String applyId){
    	AuthorityApply lauthorityApply_N = new AuthorityApply();
    	if(applyId!=null){
    		lauthorityApply_N.setApplyId(applyId);
    	}else{
    		lauthorityApply_N.setApplyId(com.supporter.util.UUIDHex.newId());
    	}
    	
    	lauthorityApply_N.setCreatedById(auserprf_U.getPersonId());
    	lauthorityApply_N.setCreatedBy(auserprf_U.getName());
        String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
    	lauthorityApply_N.setCreatedDate(date);
    	lauthorityApply_N.setAuthorityApplyTime(date);
    	lauthorityApply_N.setApplyDeptId(auserprf_U.getDeptId());
	    if(auserprf_U.getPersonId().equals("1")){
	    	lauthorityApply_N.setApplyDept("管理员所属部门");
	    }else{
	    	lauthorityApply_N.setApplyDept(auserprf_U.getDept().getName());
	    }
    	
    	lauthorityApply_N.setApplyStatus(Long.valueOf(AuthorityApply.DRAFT+""));
    	//编号
		//lauthorityApply_N.setSignNo(getCurrentNo());			
        return lauthorityApply_N;
    }
    
    
   
    
    
    
	/**
	 * 分页表格展示数据.
	 * 
	 * @param user 用户信息
	 * @param jqGridReq jqgrid请求对象
	 * @param moduleIds 多个逗号分隔
	 * @return JqGrid
	 */
	public List<AuthorityApply> getGrid(UserProfile user, JqGrid jqGrid, AuthorityApply authorityApply) {
		List<AuthorityApply> listZ=new ArrayList<AuthorityApply>();
		List<AuthorityApply> list=this.authorityApplyDao.findPage(user,jqGrid, authorityApply);		
		if(list.size()>0){
			for (AuthorityApply authorityApplyZ : list) {
				AuthorityApplyContent authorityApplyContent=this.authorityApplyContentDao.get(authorityApplyZ.getApplyId());				
				if(authorityApplyContent!=null&&!authorityApplyContent.equals("")){
					if(authorityApplyZ.getType()!=null&&!authorityApplyZ.getType().equals("2")){//不是英文授权书
						authorityApplyZ.setAuthorityApplyReasonDesc(authorityApplyContent.getAuthorityApplyReason());
					}else{
						authorityApplyZ.setAuthorityApplyReasonDesc(authorityApplyContent.getAuthorityApplyReasonE());
					}
					listZ.add(authorityApplyZ);
			  }	
			}
		}
		
		return listZ;	
		
	}
	
	/**
	 * 分页表格展示权限数据.
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityApplyPerson> getPersonInnerGrid(UserProfile user,JqGrid jqGrid,String applyId) {
		List<AuthorityApplyPerson> list=authorityApplyPersonDao.getPersonGrid(jqGrid,applyId,"inner");	
		jqGrid.setRows(list);
		return list;
	}
	
	
	/**
	 * 分页表格展示权限数据.
	 * @param jqGrid
	 * @param flag
	 * @param recordId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AuthorityApplyPerson> getPersonOuterGrid(UserProfile user,JqGrid jqGrid,String applyId) {
		List<AuthorityApplyPerson> list=authorityApplyPersonDao.getPersonGrid(jqGrid,applyId,"outer");
		jqGrid.setRows(list);
		return list;
	}
	
	
	
	
	

	/**
	 * 获取留言板
	 * @param applyId
	 * @return
	 */
	public String getMessageBoard(String applyId) {
		StringBuffer sb = new StringBuffer();
		List<AuthorityApplyBoard> list = this.authorityApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			for(AuthorityApplyBoard board :list){
				sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 13px\">");
				sb.append(board.getContent()+"</div>");
				sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
				sb.append(board.getApplyPersonName()+"</div>");
				sb.append("<div style=\"height: 15px;font-size: 11px; font-weight: bold\" align=\"right\">");
				sb.append(board.getAuthorityApplyTime()+ "</div>");
			}
		}
	
		return sb.toString();
	}
	
	/**
	 * 获取留言板
	 * @param applyId
	 * @return
	 */
	public String getMessageBoardForIphone(String applyId) {
		StringBuffer sb = new StringBuffer();
		List<AuthorityApplyBoard> list = this.authorityApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			for(AuthorityApplyBoard board :list){
				sb.append("<div style=\"border-top: 1px solid rgb(0, 0, 0);font-size: 32px;text-align:left;padding-left:10px\">");
				sb.append(board.getContent()+"</div>");
				sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold;text-align:right;padding-right:60px;padding-bottom:10px\" align=\"right\">");
				sb.append(board.getApplyPersonName()+"</div>");
				sb.append("<div style=\"height: 30px;font-size: 25px; font-weight: bold\" align=\"right\">");
				sb.append(board.getAuthorityApplyTime()+ "</div>");
			}
		}
	
		return sb.toString();
	}
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public AuthorityApply saveOrUpdate(UserProfile user, AuthorityApply authorityApply, Map< String, Object > valueMap) {
	
		AuthorityApply ret = null;
		
		//获取上传文件名称
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAAUTHAPPLY", "authorityApplyFile", authorityApply.getApplyId());
		if(list !=null && list.size()>0){
			for(IFile file:list){
					sb.append(file.getFileName() +",");
			}
			if(sb!=null&&sb.length()>0){
				sb.deleteCharAt(sb.length() - 1);
			}			
		}
		authorityApply.setOtherFile(sb.toString());
		
		if (authorityApply.getAdd()) {// 新建
			String dateFrom = CommonUtil.format(authorityApply.getDateFrom(),"yyyy-MM-dd");
			String dateTo = CommonUtil.format(authorityApply.getDateTo(),"yyyy-MM-dd");
			authorityApply.setDateFrom(dateFrom);
			authorityApply.setDateTo(dateTo);
			//保存主表
			this.authorityApplyDao.save(authorityApply);
			//保存从表AuthorityApplyboard
			//保存从表AuthorityApplyContent
			AuthorityApplyContent authorityApplyContent=new AuthorityApplyContent();
			authorityApplyContent.setApplyId(authorityApply.getApplyId());
			authorityApplyContent.setAuthorityApplyReason(authorityApply.getAuthorityApplyReasonZHDesc());
			//暂时没有用到
//			authorityApplyContent.setAuthorityApplyContent(authorityApply.getAuthorityApplyContent().getAuthorityApplyContent());
			authorityApplyContent.setAuthorityApplyNote(authorityApply.getAuthorityApplyNoteDesc());
			authorityApplyContent.setAuthorityApplyReasonE(authorityApply.getAuthorityApplyReasonEDesc());
			this.authorityApplyContentDao.save(authorityApplyContent);			
			ret = authorityApply;
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.ADD_AUTHORITY_APPLY_LOG_MESSAGE, authorityApply.getApplyTitle());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.ADD_AUTHORITY_APPLY_LOG_ACTION, logMessage,
					authorityApply, null);
		} else {// 编辑
			
			//权限验证(判断是不是有修改授权书的权限)
			AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHORITYAPPLYOGBTN, authorityApply.getApplyId(), authorityApply);			
			String dateFrom = CommonUtil.format(authorityApply.getDateFrom(),"yyyy-MM-dd");
			String dateTo = CommonUtil.format(authorityApply.getDateTo(),"yyyy-MM-dd");
			authorityApply.setDateFrom(dateFrom);
			authorityApply.setDateTo(dateTo);			
			authorityApply.setModifiedBy(user.getName());
			authorityApply.setModifiedById(user.getPersonId());
		    String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			authorityApply.setModifiedDate(date);
			//编辑之后保存主表
			this.authorityApplyDao.update(authorityApply);
			//保存从表AuthorityApplyContent
			AuthorityApplyContent authorityApplyContent=this.authorityApplyContentDao.get(authorityApply.getApplyId());
			//authorityApplyContent.setApplyId(authorityApply.getApplyId());
			if(authorityApply.getType().equals("1")){//中文授权书
				authorityApplyContent.setAuthorityApplyReason(authorityApply.getAuthorityApplyReasonZHDesc());
				authorityApplyContent.setAuthorityApplyReasonE("");
			}else if(authorityApply.getType().equals("2")){//英文授权书
				authorityApplyContent.setAuthorityApplyReason("");
				authorityApplyContent.setAuthorityApplyReasonE(authorityApply.getAuthorityApplyReasonEDesc());
			}else{
				authorityApplyContent.setAuthorityApplyReason(authorityApply.getAuthorityApplyReasonZHDesc());
				authorityApplyContent.setAuthorityApplyReasonE(authorityApply.getAuthorityApplyReasonEDesc());
			}	
			//暂时没有用到
//			authorityApplyContent.setAuthorityApplyContent(authorityApply.getAuthorityApplyContent().getAuthorityApplyContent());
			authorityApplyContent.setAuthorityApplyNote(authorityApply.getAuthorityApplyNoteDesc());
			this.authorityApplyContentDao.update(authorityApplyContent);
			//编辑之后保存从表AuthorityApplyContent
			ret = authorityApply;			
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EDIT_AUTHORITY_APPLY_LOG_MESSAGE, authorityApply.getApplyTitle());
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.EDIT_AUTHORITY_APPLY_LOG_ACTION, logMessage,
					authorityApply, null);		
		}
		return ret;

	}
	
	/**
	 * 保存会签人
	 * @param 
	 * @param 
	 */
	public void saveSigner(String applyId,UserProfile user) {
		AuthorityApply authorityApply = this.authorityApplyDao.get(applyId);
		if(authorityApply != null){
				if(StringUtils.isNotBlank(authorityApply.getRelatedIds())){
					if(authorityApply.getRelatedIds().indexOf(user.getPersonId())==-1){
						authorityApply.setRelatedIds(authorityApply.getRelatedIds() +"," +user.getPersonId());
						authorityApply.setRelatedNames(authorityApply.getRelatedNames() + "," + user.getName());
					}
				}
			}
			this.authorityApplyDao.update(authorityApply);
		}
	
	
	//保存留言板意见的从表
	public void saveBoard(UserProfile user, AuthorityApply authorityApply){
		AuthorityApplyBoard authorityApplyBoard=new AuthorityApplyBoard();
		authorityApplyBoard.setBoardId(com.supporter.util.UUIDHex.newId());
		authorityApplyBoard.setReportId(authorityApply.getApplyId());
		authorityApplyBoard.setApplyDeptId(user.getDeptId());
		if(user.getDept()!=null){
			authorityApplyBoard.setApplyDept(user.getDept().getName());
		}else{
			authorityApplyBoard.setApplyDept("");
		}
		authorityApplyBoard.setApplyPersonId(user.getPersonId());
		authorityApplyBoard.setApplyPersonName(user.getName());
	    String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
		authorityApplyBoard.setAuthorityApplyTime(date);
		authorityApplyBoard.setContent(authorityApply.getAuthorityApplyBoardDesc());
		this.authorityApplyBoardDao.save(authorityApplyBoard);	
	}
	
	
	
	//保存从表人员
	public AuthorityApplyPerson saveOrUpdatePerson(UserProfile user, AuthorityApplyPerson authorityApplyPerson, Map< String, Object > valueMap) {
		AuthorityApplyPerson ret = null;
		if(authorityApplyPerson.getAuthorityApplyPersonId()==null||authorityApplyPerson.getAuthorityApplyPersonId().equals("")){//人员从表新建
			authorityApplyPerson.setAuthorityApplyPersonId(com.supporter.util.UUIDHex.newId());
			if(authorityApplyPerson.getPoleId()==10){
				authorityApplyPerson.setPoleId(10L);
			}else if(authorityApplyPerson.getPoleId()==20){
				authorityApplyPerson.setPoleId(20L);
			}			
			authorityApplyPersonDao.save(authorityApplyPerson);
			ret=authorityApplyPerson;
		}else{//人员从表编辑
			if(authorityApplyPerson.getPoleId()==10){
				authorityApplyPerson.setPoleId(10L);
			}else if(authorityApplyPerson.getPoleId()==20){
				authorityApplyPerson.setPoleId(20L);
			}	
			authorityApplyPersonDao.update(authorityApplyPerson);			
			ret=authorityApplyPerson;
		}
	//	this.deletePerson(delIds);
      
		return  ret;
	}
	/**
	 * 删除从表数据
	 * @param delIds
	 */
	public void deletePerson(UserProfile user, String authorityApplyPersonId) {
		if (StringUtils.isNotBlank(authorityApplyPersonId)) {			
			AuthorityApplyPerson person = this.authorityApplyPersonDao.get(authorityApplyPersonId);
			if (person!= null) {
				this.authorityApplyPersonDao.delete(authorityApplyPersonId);
			}
		}
	}

	// 得到数据库中数据的条数（以年为单位）
/*	public Integer getCount() {
		int displayOrder = authorityApplyDao.getCount();
		return displayOrder+1;
	}*/
	
	 public  String getCurrentNo(){
	 		String no=authorityApplyDao.getSignNo();
		    SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
			String year=sdf.format(new Date());
		if(year.equals(no.substring(5,9))){
			//印000012号（2017）
			//中电授字【2017】第0010号
			String nm = no.substring(11,15);
			String number = String.valueOf((Integer.parseInt(nm)+1));
			if(number.length()<nm.length()){
				no = "";
				for (int i = 0; i < nm.length()-number.length(); i++) {
						no += "0";
					}
				no += number;
			}else{
				no = number;
			}
			no = "中电授字【"+year+"】第"+no+"号";
		}else{
			no = "中电授字【"+year+"】第"+no+"号";
		}
	return no;
}
	
	
    /**
	 * 保存提交
	 * 
	 * @param user 用户信息
	 * @param apply 实体类
	 * @return
	 */
	public AuthorityApply commit(UserProfile user, AuthorityApply authorityApply, Map< String, Object > valueMap) {
		AuthorityApply ret = null;
		boolean isNew=authorityApply.getAdd();
		if(isNew){
			String dateFrom = CommonUtil.format(authorityApply.getDateFrom(),"yyyy-MM-dd");
			String dateTo = CommonUtil.format(authorityApply.getDateTo(),"yyyy-MM-dd");
			authorityApply.setDateFrom(dateFrom);
			authorityApply.setDateTo(dateTo);
			authorityApply.setApplyStatus(Long.valueOf(AuthorityApply.PROCESSING+""));
			this.authorityApplyDao.save(authorityApply);	
			
			//保存从表AuthorityApplyboard
			//保存从表AuthorityApplyContent
			AuthorityApplyContent authorityApplyContent=new AuthorityApplyContent();
			authorityApplyContent.setApplyId(authorityApply.getApplyId());
			authorityApplyContent.setAuthorityApplyReason(authorityApply.getAuthorityApplyContent().getAuthorityApplyReason());
			authorityApplyContent.setAuthorityApplyContent(authorityApply.getAuthorityApplyContent().getAuthorityApplyContent());
			authorityApplyContent.setAuthorityApplyNote(authorityApply.getAuthorityApplyContent().getAuthorityApplyNote());
			authorityApplyContent.setAuthorityApplyReasonE(authorityApply.getAuthorityApplyContent().getAuthorityApplyReasonE());
			this.authorityApplyContentDao.save(authorityApplyContent);
			//保存从表AuthorityApplyPerson			
			ret = authorityApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{AddApplication : " + apply + "}", null, null);
		} else {// 编辑
			String dateFrom = CommonUtil.format(authorityApply.getDateFrom(),"yyyy-MM-dd");
			String dateTo = CommonUtil.format(authorityApply.getDateTo(),"yyyy-MM-dd");
			authorityApply.setDateFrom(dateFrom);
			authorityApply.setDateTo(dateTo);
			authorityApply.setModifiedBy(user.getName());
			authorityApply.setModifiedById(user.getPersonId());		
		    String date = CommonUtil.formatDate(new Date(),"yyyy-MM-dd HH:mm:ss");
			authorityApply.setModifiedDate(date);
			authorityApply.setApplyStatus(Long.valueOf(AuthorityApply.PROCESSING+""));
			this.authorityApplyDao.update(authorityApply);
			ret = authorityApply;
			//日志
			//EIPService.getLogService("PURCHASE_APPLY").info(user, Application.LogOper.MODULE_ADD.getOperName(), "{EditApplication : " + apply + "}", null, null);
		}
		return ret;
	}
	

	/**
	 * 删除
	 * 
	 * @param user 用户信息
	 * @param moduleIds 主键集合，多个以逗号分隔
	 */
	public void delete(UserProfile user, String applyIds) {
		if (StringUtils.isNotBlank(applyIds)) {
			for (String applyId : applyIds.split(",")) {
				
				AuthorityApply authorityApply=authorityApplyDao.get(applyId);
				if(authorityApply==null){
					continue;
				}
				//权限验证(判断是不是有删除授权书的权限)
				AuthUtil.canExecute(user, AuthConstant.AUTH_OPER_NAME_AUTHORITYAPPLYOGBTN, applyId, authorityApply);			

				//删除授权书主表
				this.authorityApplyDao.delete(authorityApply);
				//删除留言板信息
				deleteAuthorityApplyBoard(applyId);
				
				//删除content
				AuthorityApplyContent authorityApplyContent=authorityApplyContentDao.get(applyId);
				if(authorityApplyContent!=null&&!authorityApplyContent.equals("")){
					this.authorityApplyContentDao.delete(applyId);
				}			
				//删除授权人员
				List<AuthorityApplyPerson> list=this.authorityApplyPersonDao.getByApplyId(applyId);
				if(list!=null){
					if(list.size()>0){
						this.authorityApplyPersonDao.delete(list);
					}
				}
				
				
			}
			// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.DELETE_AUTHORITY_APPLY_LOG_MESSAGE, "删除授权书的主键为："+applyIds);
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					user, LogConstant.DELETE_AUTHORITY_APPLY_LOG_ACTION, logMessage,
					null, null);		
		}
	}
	
	/**
	 * 删除从表数据(留言板从表)
	 * @param delIds
	 */
	public void deleteAuthorityApplyBoard(String applyId) {
		//首先根据applyId获取与之关联的所有从表
		List<AuthorityApplyBoard> list=this.authorityApplyBoardDao.getMessageByApplyId(applyId);
		if(list!=null&&list.size()>0){
			this.authorityApplyBoardDao.delete(list);
		}
	}
	/**
	 * 重置流程状态
	 * 
	 */
	public void updateStatus(String applyId ,Integer applyStatus) {
		if (StringUtils.isNotBlank(applyId)) {
			AuthorityApply authorityApply = this.get(applyId);
			authorityApply.setApplyStatus(Long.valueOf(applyStatus));
			this.authorityApplyDao.update(authorityApply);
		}
	}
	
	/**
	 * 附件下载部分
	 * @param maintenance
	 * @return
	 */
	public String getFiles(AuthorityApply authorityApply){
		StringBuffer sb = new StringBuffer();
		IFileUploadService fileUploadService = EIPService.getFileUploadService();
		List<IFile> list  = fileUploadService.getFileList("OAAUTHAPPLY", "authorityApplyFile", authorityApply.getApplyId());
		for(IFile file:list){
			sb.append("<a onclick=\"downloads('"+ file.getFileId() +"');\">" + file.getFileName() +"</a><br/>");
		}
		return sb.toString();
	}
	
	/**
	 * 保存或更新
	 * 
	 * @param user 用户信息
	 * @param module 实体类
	 * @return
	 */
	public AuthorityApply update(AuthorityApply authorityApply) {
			this.authorityApplyDao.update(authorityApply);
			//记录日志
//			ModuleUtils.saveModuleOperateLog(user, module, Contract.LogOper.MODULE_EDIT.getOperName(), null);

		return authorityApply;

	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String extractFiles(String id, UserProfile userProfile){
		AuthorityApply report =  this.get(id);
		return extractFiles.extractFiles(report.getApplyId(), report.getOtherFile(),
				"/oa/authority_apply/attachment/", "OAAUTHAPPLY", "authorityApplyFile", userProfile);
	}
	
	/**
	 * 单一报告提取文件.
	 * @param report
	 * @return
	 */
	public String batchExtractFiles(UserProfile userProfile){
		String returnStr = "success";
		List <AuthorityApply> reportList= authorityApplyDao.getAuthorityApplyList();
		for(AuthorityApply report:reportList){
			returnStr = extractFiles.extractFiles(report.getApplyId(), report.getOtherFile(),
					"/oa/authority_apply/attachment/", "OAAUTHAPPLY", "authorityApplyFile", userProfile);
			
		/*	// 记录日志
			String logMessage = MessageFormat.format(
					LogConstant.EXTRACT_FILES_LOG_MESSAGE+":"+returnStr, CommonUtil.trim(report.getReportTitle()));
			EIPService.getLogService(LogConstant.INFO_TYPE_BUSINESS).info(
					userProfile, LogConstant.EXTRACT_FILES_LOG_ACTION, logMessage,
					report, null);*/
		}
		return returnStr;
		//return reportDao.batchExtractFiles(userProfile);
	}	
	
	/**
	 * 给合同管理员发送知会
	 * @param authorityApply
	 */
	public void sendMessageToContractManager(AuthorityApply authorityApply) {
		String applyTile = authorityApply.getApplyTitle();
		applyTile = applyTile.length() <= 30 ? applyTile : applyTile.substring(0, 30) + "...";
		String title = "知会（" + applyTile + "的授权书审批完成）部门：" + authorityApply.getApplyDept();
		List <Person > DJHDS_admins = EIPService.getRoleService().getPersonFromRole("CONTRACT_MANAGEMENT", "");//获取经营部合同管理员角色成员
		for (Person emp : DJHDS_admins) {
			Message messageCreat =new Todo();
    	    messageCreat.setPersonId(emp.getPersonId());
    		messageCreat.setEventTitle(title);
    		messageCreat.setNotifyTime(new Date());
    		messageCreat.setWebPageURL("/oa/authority_apply/authorityApply_overall_view.html?isSwf=zh&applyId="
    				+ CommonUtil.trim(authorityApply.getApplyId()));
    		messageCreat.setMessageType(ITodo.MsgType.CC);	
    		messageCreat.setRelatedRecordTable(EIPService.getWebappService().getWebappName());
    		EIPService.getBMSService().addMessage(messageCreat);		
		}
	}
	
}
