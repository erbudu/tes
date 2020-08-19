package com.supporter.prj.cneec.textSearch.controller;

import java.util.Date;

import org.quartz.JobExecutionContext;

import com.supporter.prj.cneec.textSearch.entity.CommonDocument;
import com.supporter.prj.cneec.textSearch.service.CommonDocumentService;
import com.supporter.prj.cneec.textSearch.service.LuceneService;
import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.util.CommonUtil;
import com.supporter.prj.eip_service.job_schedule.IJob;

/**
 * 定时执行建立索引.
 * @author qy
 *
 */
public class LuceneIndexJob implements IJob{

	public LuceneService luceneService;
	
	public void execute(final JobExecutionContext context){
		String ls_Today = CommonUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		System.out.println("excute lucene indexing time:" + ls_Today);
		
		try {
			//为所有文件建立索引
//			indexAll();
			long ll_StartTime = System.currentTimeMillis();
			//为主题新闻建立索引
			String index_dir = CommonDocumentService.getIndexDir(CommonDocument.NEWS);
			String data_dir = CommonDocumentService.getDataDir(CommonDocument.NEWS);
			getLuceneService().index(data_dir, index_dir,CommonDocument.NEWS);
			
			//为公司公告建立索引
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.BULLETIN);
			data_dir = CommonDocumentService.getDataDir(CommonDocument.BULLETIN);
			getLuceneService().index(data_dir, index_dir,CommonDocument.BULLETIN);
			
			//为文章栏建立索引
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.ARTICLE);
			data_dir = CommonDocumentService.getDataDir(CommonDocument.ARTICLE);
			getLuceneService().index(data_dir, index_dir,CommonDocument.ARTICLE);
			
			//为文件柜建立索引
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.FILEBOX);
			data_dir = CommonDocumentService.getDataDir(CommonDocument.FILEBOX);
			getLuceneService().index(data_dir, index_dir,CommonDocument.FILEBOX);
			
			
			//多个文件目录建立一个索引目录
			String data_dir1 = CommonDocumentService.getDataDir(CommonDocument.NEWS);
			String data_dir2 = CommonDocumentService.getDataDir(CommonDocument.BULLETIN);
			String data_dir3 = CommonDocumentService.getDataDir(CommonDocument.ARTICLE);
			String data_dir4 = CommonDocumentService.getDataDir(CommonDocument.FILEBOX);
			String[] data_dirs = {data_dir1,data_dir2,data_dir3,data_dir4};
			index_dir = CommonDocumentService.getIndexDir(CommonDocument.ALL);//CommonDocumentService.getIndex();
			getLuceneService().index(data_dirs, index_dir);
			long ll_EndTime = System.currentTimeMillis();
			long ll_Return = (ll_EndTime - ll_StartTime)/1000;
			System.out.println("建立索引耗时===="+(ll_EndTime - ll_StartTime));
//			//为优秀项目案例建立索引
//			String index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.EXCELLENT_PRJ);
//			String data_dir = CommonDocumentService.getDataDir(index_dir);
//			getLuceneService().index(data_dir, index_dir,CommonDocumentService.EXCELLENT_PRJ);
	      
//			//为“项目相关信息维护”建立索引
//			index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.PRJ_MSG);
//			data_dir = CommonDocumentService.getDataDir(index_dir);
//			getLuceneService().index(data_dir, index_dir,CommonDocumentService.PRJ_MSG);
//			
//			//为“作业模板文档管理”建立索引
//			index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.TEMPLATE_DOC);
//			data_dir = CommonDocumentService.getDataDir(index_dir);
//			getLuceneService().index(data_dir, index_dir,CommonDocumentService.TEMPLATE_DOC);
//			
//			//为“公司管理相关信息维护”建立索引
//			index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.COMPANY_MANAGE);
//			data_dir = CommonDocumentService.getDataDir(index_dir);
//			getLuceneService().index(data_dir, index_dir,CommonDocumentService.COMPANY_MANAGE);
//			
//			//为“其他信息资源维护”建立索引
//			index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.MSG_RESOURCE);
//			data_dir = CommonDocumentService.getDataDir(index_dir);
////			System.out.println("index_dir:" + index_dir);
////			System.out.println("data_dir:" + data_dir);
//			getLuceneService().index(data_dir, index_dir,CommonDocumentService.MSG_RESOURCE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//return "indexing completed.";
	}
	
//	/**
//	 * 为所有文件建立索引。
//	 * @param as_IndexPath 索引路径
//	 * @throws IOException
//	 */
//	private void indexAll() throws IOException{
//		   try {
//			   	String index_dir = CommonDocumentService.getIndexDir(CommonDocumentService.ALL);
//			   	System.out.println("index_dir:" + index_dir);
//			   	File indexFile = new File(index_dir);
//	        	Directory dir = FSDirectory.open(indexFile);
//				//配置IndexWriterConfig 
//				IndexWriterConfig iwConfig = new IndexWriterConfig(Version.LUCENE_34 , getLuceneService().getAnalyzer()); 
//				iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
//				IndexWriter iwriter = new IndexWriter(dir , iwConfig);
//				
//				//删除原有索引
//				iwriter.deleteAll();
//				
//				//以下开始枚举
//				//将所有的文件夹下的文件加入,以下只是放优秀项目案例的。
//				String dataDir = CommonDocumentService.getDataDir(CommonDocumentService.EXCELLENT_PRJ);
//				File file_excellent = new File(dataDir);
//				iwriter  = getLuceneService().indexDirectory(iwriter,file_excellent,CommonDocumentService.EXCELLENT_PRJ);
//				
//				//放其他索引
//				
//				
//				//索引结束关闭
//				iwriter.close();
//				iwConfig = null;
//				dir = null;
//				
//				System.out.println("索引建立结束");
//			} catch (IOException e) {
//				System.out.println("硬盘上建立索引有误.");
//				e.printStackTrace();
//			}  
//	}
	
	public LuceneService getLuceneService(){
		if(luceneService != null){
			return luceneService;
		}
	 	luceneService = SpringContextHolder.getBean(LuceneService.class);
	 	return luceneService;
	}
	
}
