package com.supporter.prj.cneec.textSearch.controller;
import java.util.Date;

import org.quartz.JobExecutionContext;

import com.supporter.prj.cneec.textSearch.entity.CommonDocument;
import com.supporter.prj.cneec.textSearch.service.CommonDocumentService;
import com.supporter.prj.cneec.textSearch.service.LuceneServiceByNoFilebox;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CommonUtil;
import com.supporter.prj.eip_service.job_schedule.IJob;

/**
 * 定时执行建立索引.
 * @author tiansen
 *
 */
public class LuceneIndexJobByNoFilebox implements IJob{

	public LuceneServiceByNoFilebox luceneServiceByNoFilebox;
	
	public void execute(final JobExecutionContext context){
		String ls_Today = CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.out.println("excute lucene indexing time:" + ls_Today);
		
		try {
			long ll_StartTime = System.currentTimeMillis();
			
			//公司公告建立索引目录
			String index_dir = CommonDocumentService.getIndexDir(CommonDocument.BULLETIN);
			//公司公告附件存放目录
			String data_dir = CommonDocumentService.getDataDir(CommonDocument.BULLETIN);
			getLuceneServiceByNoFilebox().index(data_dir, index_dir,CommonDocument.BULLETIN);
			
			
			//专栏建立索引目录
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.ARTICLE);
			//专栏附件存放目录
			data_dir = CommonDocumentService.getDataDir(CommonDocument.ARTICLE);
			getLuceneServiceByNoFilebox().index(data_dir, index_dir,CommonDocument.ARTICLE);
			
			
			//主题新闻建立索引目录
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.NEWS);
			//主题新闻附件存放目录
			data_dir = CommonDocumentService.getDataDir(CommonDocument.NEWS);
			getLuceneServiceByNoFilebox().index(data_dir, index_dir,CommonDocument.NEWS);
//			
//			
//			//文件柜建立索引目录
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.FILEBOX);
			//文件柜附件存放目录
			data_dir = CommonDocumentService.getDataDir(CommonDocument.FILEBOX);
			getLuceneServiceByNoFilebox().index(data_dir, index_dir,CommonDocument.FILEBOX);
			
			//文件柜建立索引目录
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.ALL);
			//文件柜附件存放目录
			data_dir = CommonDocumentService.getDataDir(CommonDocument.FILEBOX);
			getLuceneServiceByNoFilebox().index(data_dir, index_dir,CommonDocument.ALL);
			
			
			long ll_EndTime = System.currentTimeMillis();
			System.out.println("建立索引耗时===="+(ll_EndTime - ll_StartTime));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return "indexing completed.";
	}
	public LuceneServiceByNoFilebox getLuceneServiceByNoFilebox(){
		if(luceneServiceByNoFilebox != null){
			return luceneServiceByNoFilebox;
		}
		luceneServiceByNoFilebox = SpringContextHolder.getBean(LuceneServiceByNoFilebox.class);
	 	return luceneServiceByNoFilebox;
	}
	
}
