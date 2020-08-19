package com.supporter.prj.linkworks.oa.article.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.linkworks.oa.article.entity.Article;
import com.supporter.prj.linkworks.oa.article.service.ArticleService;
import com.supporter.spring_mvc.AbstractController;

/**   
 * @Title: Controller
 * @Description: 新闻公告.
 * @author linxiaosong
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("oa/article")
public class ArticleController extends AbstractController{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ArticleService articleService;
	
	/**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGrid")
	public @ResponseBody JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, String attr,String dateFrom,String dateTo,String deptResourceId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.articleService.getGrid(user,jqGrid,attr,dateFrom,dateTo,deptResourceId);
		return jqGrid;
	}
	
	
	
	/**
     * 返回列表.
	 * 分页表格展示数据.
	 * @param jqGrid
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("getGridNotAuth")
	public @ResponseBody JqGrid getGridNotAuth(HttpServletRequest request, JqGridReq jqGridReq, String attr,String dateFrom,String dateTo,String deptResourceId) throws Exception  {
		UserProfile user = this.getUserProfile();
		JqGrid jqGrid = jqGridReq.initJqGrid(request);
		this.articleService.getGridNotAuth(user,jqGrid,attr,dateFrom,dateTo,deptResourceId);
		return jqGrid;
	}
	
	
	/**
	 * 获取分组专栏记录.
	 * @param recCount 每组最大记录数
	 * @return
	 */
	@RequestMapping("/getResourceArticles")
	@ResponseBody
	public List < Map < String, Object > > getResourceArticles(){
		return articleService.getResourceArticles();
	}
	
	/**
	 * 进入查看页面时加载的信息.
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getInView")
	public @ResponseBody Article getInView(String articleId) {
		Article entity = articleService.getInView(articleId,this.getUserProfile());
		return entity;
	}
	
	/**
	 * 进入新建或编辑时加载的信息
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("initEditOrViewPage")
	public @ResponseBody Article initEditOrViewPage(String articleId) {
		Article entity = articleService.initEditOrViewPage(articleId,this.getUserProfile());
		return entity;
	}
	
	
	/**
	 * 保存或更新数据.
	 * 
	 * @param news 页面传递参数自动绑定成的实体类
	 * @return
	 */
	@RequestMapping("saveOrUpdate")
	public @ResponseBody OperResult<Article> saveOrUpdate(Article article) {
		UserProfile user = this.getUserProfile();
		Article entity = this.articleService.saveOrUpdate(user, article);
		if(article.getPublishStatus() == Article.PUBLISHED){
			return OperResult.succeed("submitSuccess", null, entity);
		}else{
			return OperResult.succeed("saveSuccess", null, entity);
		}
		
	}
	
	
	/**
	 * 删除操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("batchDel")
	public @ResponseBody OperResult batchDel(String articleIds) {
		UserProfile user = this.getUserProfile();
		this.articleService.delete(user, articleIds);
		return OperResult.succeed("deleteSuccess");
	}
	
	/**
	 * 取消发布操作
	 * 
	 * @param reportIds 主键集合，多个以逗号分隔
	 * @return
	 */
	@RequestMapping("cancelSubmit")
	public @ResponseBody OperResult cancelSubmit(String articleId) {
		UserProfile user = this.getUserProfile();
		this.articleService.cancelSubmit(user, articleId);
		return OperResult.succeed("cancelSuccess");
	}
	
	/**
	 * 高级查询获取字典数据-部门资源的下拉列表（文章栏菜单用到）
	 * 
	 * @param key
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getDeptResource")
	public Map<String, String> getDeptResource()throws IOException {
		UserProfile user = this.getUserProfile();
	Map<String, String> map = articleService.getDeptResource(user,"canRead");
	return map;
	}
	
	/**
	 * 通过员工id获取部门名称
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getDeptName")
	public @ResponseBody String getDeptName(String emp) {
		String deptName = articleService.getDeptName(emp);
		return deptName;
	}

	/**
	 * 获取首页展示
	 * 
	 * @param newsId 主键
	 * @return
	 */
	@RequestMapping("getTopList")
	public @ResponseBody String getTopList() {
		UserProfile user = this.getUserProfile();
		String attr = articleService.getTopList();
		return attr;
	}
	
	
	/**
	 * 判断有没有权限（该权限在部门资源管理模块设置）操作
	 * 
	 * @param bulletinIds 主键集合，多个以逗号分隔
	 * @param canTodo 操作类型（包括 canNew、canWrite、canDelete）
	 * @return String (yes/no)
	 */
	@RequestMapping("isCanOperate")
	public @ResponseBody String isCanOperate(String articleId,String canTodo) {
		UserProfile user = this.getUserProfile();
		String isCanOperate=this.articleService.isCanOperate(user,articleId,canTodo);
		return isCanOperate;
	}
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("extractFiles")
	public @ResponseBody String extractFiles(String articleId){
		return articleService.extractFiles(articleId,this.getUserProfile());
	}
	
	/**
	 * 提取历史数据文件.
	 * @param report
	 * @return
	 */
	@RequestMapping("batchExtractFiles")
	public @ResponseBody String batchExtractFiles(){
		return articleService.batchExtractFiles(this.getUserProfile());
	}
}
