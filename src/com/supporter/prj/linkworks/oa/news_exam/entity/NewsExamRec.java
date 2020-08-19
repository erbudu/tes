package com.supporter.prj.linkworks.oa.news_exam.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip.busi_entity.IBusiEntity;
import com.supporter.prj.eip_service.codetable.entity.ICodeTable;
import com.supporter.prj.linkworks.oa.news_exam.entity.base.BaseNewsExamRec;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: OA_NEWS_EXAM_REC
 * @author linda
 * @date 2017-11-14 13:46:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "OA_NEWS_EXAM_REC", schema = "")
public class NewsExamRec extends BaseNewsExamRec implements IBusiEntity {

	private static final long serialVersionUID = 1L;
	
	//扩展数据库以外的其他属性，注意属性前加@Transient注解标示非数据库字段
	
	/**
	 * 构造方法.
	 */
	public NewsExamRec() {
		
	}
	
	@Transient
	public String getPublishToDisp(){
		String ids = CommonUtil.trim(this.getPublishTo());
		if (ids.length() == 0)return "";
		String[] idArray = ids.split(",");
		ICodeTable ctbl = PublishTo.getCodeTable();
		
		String disp = "";
		for (int i = 0; i < idArray.length; i++){
			String id = CommonUtil.trim(idArray[i]);
			if (id.length() > 0){
				if (disp.length() > 0)disp += ",";
				if (id.equals(PublishTo.INNER_WEB)){
					String inner = getInnerPublishToDisp();
					if (inner.length() == 0)inner = ctbl.getDisplay(id);
					disp += inner;
				} else {
					disp += ctbl.getDisplay(id);
				}
				
			}
		}
		return disp;
	}
	@Transient
	public String getInnerPublishToDisp(){
		String ids = CommonUtil.trim(this.getInnerPublishTo());
		if (ids.length() == 0)return "";
		String[] idArray = ids.split(",");
		ICodeTable ctbl = PublishTo.getInnerCodeTable();
		
		String disp = "";
		for (int i = 0; i < idArray.length; i++){
			String id = CommonUtil.trim(idArray[i]);
			if (id.length() > 0){
				if (disp.length() > 0)disp += ",";
				disp += ctbl.getDisplay(id);
			}
		}
		return disp;
	}
	@Transient
	public String getSubmitToDisp(){
		String ids = CommonUtil.trim(this.getSubmitTo());
		if (ids.length() == 0)return "";
		
		String[] idArray = ids.split(",");
		ICodeTable ctbl = SubmitTo.getCodeTable();
		
		String disp = "";
		for (int i = 0; i < idArray.length; i++){
			String id = CommonUtil.trim(idArray[i]);
			if (id.length() > 0){
				if (disp.length() > 0)disp += ",";
				disp += ctbl.getDisplay(id);
			}
		}
		return disp;
	}
	
	@Transient
	public String getEditTypeDisp(){
		if (this.getEditType() <= 0)return "";
		return EditType.getCodeTable().getDisplay(this.getEditType());
	}
	
	@Transient
	public String getRecStatusDisp(){
		return Status.getCodeTable().getDisplay(this.getRecStatus());
	}
	
	@Transient
	public String getNeedConSignDisp(){
		if (this.getNeedConSign()){
			return "是";
		} else {
			return "否";
		}
	}
	
	@Transient
	public boolean getZongcai(){
		String procId = CommonUtil.trim(this.getProcId());
		if (procId.length() == 0){
			return false;
		}
		
		NewsExamRecService newsService = SpringContextHolder.getBean(NewsExamRecService.class);
		return newsService.isCompLeader(this);
	}
	
	@Transient
	public boolean isSignByOtherDepts(){
		return CommonUtil.trim(this.getSignerIds()).length() > 0;
	}
	
	/**
	 * 发布意向.
	 * @author linda
	 *
	 */
	public static final class PublishTo {
		public static final String INNER_WEB = "INNER_WEB"; //公司内网
		public static final String WECHAT = "WECHAT"; //公司微信
		public static final String OUTER_WEB = "OUTER_WEB"; //公司外网
		public static final String TOPIC_NEWS = "TOPIC_NEWS"; //主题新闻
		public static final String SCROLL_NEWS = "SCROLL_NEWS"; //滚动新闻
		
		public static ICodeTable getCodeTable(){
			ICodeTable ctbl = new CodeTable();
			ctbl.insertItem(INNER_WEB, "公司内网");
			ctbl.insertItem(WECHAT, "公司微信");
			ctbl.insertItem(OUTER_WEB, "公司外网");
			return ctbl;
		}
		public static ICodeTable getInnerCodeTable(){
			ICodeTable ctbl = new CodeTable();
			ctbl.insertItem(TOPIC_NEWS, "主题新闻");
			ctbl.insertItem(SCROLL_NEWS, "滚动新闻");
			return ctbl;
		}
	}
	
	/**
	 * 报送.
	 * @author linda
	 *
	 */
	public static final class SubmitTo {
		public static final String GUOJI_COMP = "GUOJI_COMP"; //国机集团
		public static final String CMEC = "CMEC"; //CMEC
		public static final String OUTERE_MEDIA = "OUTERE_MEDIA"; //其它外部媒体
		
		public static ICodeTable getCodeTable(){
			ICodeTable ctbl = new CodeTable();
			ctbl.insertItem(GUOJI_COMP, "国机集团");
			ctbl.insertItem(CMEC, "CMEC");
			ctbl.insertItem(OUTERE_MEDIA, "其它外部媒体");
			return ctbl;
		}
	}
	
	/**
	 * 编辑情况.
	 * @author linda
	 *
	 */
	public static final class EditType {
		public static final int NONE = 1; //未做修改
		public static final int WORD_PIC = 2; //文字润色图片编辑
		public static final int MORE_DIFF = 3; //改动较大
		public static final ICodeTable getCodeTable(){
			ICodeTable ctbl = new CodeTable();
			ctbl.insertItem(NONE, "未做修改");
			ctbl.insertItem(WORD_PIC, "文字润色图片编辑");
			ctbl.insertItem(MORE_DIFF, "改动较大");
			return ctbl;
		}
	}
	
	/**
	 * 状态.
	 * @author linda
	 *
	 */
	public static final class Status {
		public static final int DRAFT = 1; //草稿
		public static final int EXAM = 2; //正在审批
		public static final int RECEIVED = 3; //审批完成
		public static final ICodeTable getCodeTable(){
			ICodeTable ctbl = new CodeTable();
			ctbl.insertItem(DRAFT, "草稿");
			ctbl.insertItem(EXAM, "正在审批");
			ctbl.insertItem(RECEIVED, "审批完成");
			return ctbl;
		}
	}

	@Transient
	@Override
	public String getEntityName() {
		return this.getClass().getName();
	}

	@Transient
	@Override
	public String getKeyProps() {
		return "recId";
	}

	@Transient
	@Override
	public String getKeyValues() {
		return this.getRecId();
	}
}
