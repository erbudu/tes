package com.supporter.prj.cneec.textSearch.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.supporter.prj.cneec.textSearch.entity.CommonDocument;
import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.linkworks.oa.article.entity.Article;
import com.supporter.prj.linkworks.oa.article.entity.ArticleContent;
import com.supporter.prj.linkworks.oa.article.service.ArticleServiceByFulltextSearch;
import com.supporter.prj.linkworks.oa.bulletin.entity.Bulletin;
import com.supporter.prj.linkworks.oa.bulletin.entity.BulletinContent;
import com.supporter.prj.linkworks.oa.bulletin.service.BulletinServiceByFulltextSearch;
import com.supporter.prj.linkworks.oa.news.service.NewsServiceByFulltextSearch;
import com.supporter.prj.meip_service.news.entity.News;
import com.supporter.prj.meip_service.news.entity.NewsContent;
import com.supporter.util.CommonUtil;

/**
 * lucene的类(lucene就是个工具，一个工具类即可).
 * @author lyf
 *
 */
@Service
public class LuceneServiceByNoFilebox {
	
	@Autowired
	private BulletinServiceByFulltextSearch bulletinServiceByFulltextSearch;
	@Autowired
	private ArticleServiceByFulltextSearch articleServiceByFulltextSearch;
	@Autowired
	private NewsServiceByFulltextSearch newsServiceByFulltextSearch;
	@Autowired
	private FileboxServiceByFulltextSearch fileboxServiceByFulltextSearch;
	
	//索引文件内容
	public   String INDEX_CONTENT = "content";
	//索引文件名称
	public  String INDEX_NAME = "canonicalPath";
	//索引路径
	public  String INDEX_PATH = "path";
	//索引附件名称
	public  String INDEX_FILE_NAME = "fileName";
	//索引ID
	public  String INDEX_BUSSNIS_ID = "bussnisId";
	//索引日期
	public  String INDEX_Date = "date";
	// lucene版本
    private static final Version VERSION = Version.LUCENE_40;//Version.LUCENE_34
	
	//中文分词器(待改进) 正向最大全切分分词器 适合用户输入检索时使用
	public Analyzer ANALYZER;
	
	public Analyzer getAnalyzer(){
		if(ANALYZER != null)return ANALYZER;
		ANALYZER = new IKAnalyzer(false); //最细粒度切分
		return ANALYZER;
	}
	

	

	/**
	 * 硬盘上建立索引.
	 * 
	 * @param as_DataDir
	 *            数据文件目录
	 * @param as_IndexDir
	 *            索引文件目录
	 * @throws IOException
	 */
	public void index(String as_DataDir,String as_IndexDir,int indexType){
		try{
			File lfile_DataDir = null;
			lfile_DataDir = new File(as_DataDir);
			File lfile_IndexDir = new File(as_IndexDir);
			if(!lfile_DataDir.exists() || !lfile_DataDir.isDirectory()){
				System.out.println(lfile_DataDir + " does not exists or is not a directory!");
				return;
			}
        	Directory dir = FSDirectory.open(lfile_IndexDir);
			//配置IndexWriterConfig 
			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
			IndexWriter iwriter = new IndexWriter(dir , iwConfig);
			//删除原有索引
			iwriter.deleteAll();
			//新建索引(公司公告)
			if(indexType == CommonDocument.BULLETIN){
				createIndexByBulletin(iwriter);
			}
			//新建索引(主题新闻)
			if(indexType == CommonDocument.NEWS){
				createIndexByNews(iwriter);
			}
			//新建索引(专栏)
			if(indexType == CommonDocument.ARTICLE){
				createIndexByArticle(iwriter);
			}
			//新建索引(文件柜)
			if(indexType == CommonDocument.FILEBOX){
				createIndexByFilebox(iwriter);
			}
			//新建索引(全部)
			if(indexType == CommonDocument.ALL){
				createIndexByFilebox(iwriter);
				createIndexByBulletin(iwriter);
				createIndexByNews(iwriter);
				createIndexByArticle(iwriter);
			}
			
			iwriter.close();
		} catch (IOException e) {
			System.out.println("硬盘上建立索引有误.");
			e.printStackTrace();
		}
	}
	/**
     * 创建文件柜索引
     * Description：
     * @author dennisit@163.com Apr 3, 2013
	 * @throws IOException 
     * @throws Exception
     */
    public void createIndexByFilebox(IndexWriter iwriter) throws IOException{
    	List <DataFile> newsList = fileboxServiceByFulltextSearch.getFileboxList(News.Status.published.getKey());
//    	System.out.println("size="+newsList.size());
        for(DataFile dataFile : newsList){
        	String newsId = dataFile.getId();
        	//标题
        	String titile = CommonUtil.trim(dataFile.getFileName());
        	//内容
        	String content = "";
        	//String content = "主题新闻：(" + dataFile.getPublisherName() + CommonUtil.format(dataFile.getPublishDate(),"yyyy年MM月dd日") +" 发布)";
        	String fileName = fileboxServiceByFulltextSearch.getFiles(dataFile);
//        	System.out.println("titile: " + titile +  " content:" + ReduceHtmlText.removeHtmlTag(content));
//        	System.out.println("articleId: " + newsId);
        	if(content == null || content.equals("")){
        		content += "";
        	}
        	//业务单路径路径（通过业务参数设置:FULLTEXT_SEARCH_BULLETIN）
        	String newsViewUrl = EIPService.getRegistryService().get("FULLTEXT_SEARCH_FILEBOX")+"?id="+newsId;
//        	System.out.println("newsViewUrl: " + newsViewUrl);
            Document document = addDocument(newsId,titile,ReduceHtmlText.removeHtmlTag(content),fileName,"",newsViewUrl);
            iwriter.addDocument(document);
        }
    }
	/**
     * 创建主题新闻索引
     * Description：
     * @author dennisit@163.com Apr 3, 2013
	 * @throws IOException 
     * @throws Exception
     */
    public void createIndexByNews(IndexWriter iwriter) throws IOException{
    	List <News> newsList = newsServiceByFulltextSearch.getNewsList(News.Status.published.getKey());
        for(News news : newsList){
        	String newsId = news.getNewsId();
        	//标题
        	String titile = CommonUtil.trim(news.getTitle());
        	NewsContent newsContent = newsServiceByFulltextSearch.getNewsContent(news);
        	//内容
        	//String content = "";
        	String publisherName = news.getPublisherName() == null ? "" : news.getPublisherName();
        	String content = "主题新闻：(" + publisherName + CommonUtil.format(news.getPublishDate(),"yyyy年MM月dd日") +" 发布)";
        	if(newsContent != null){
        		content += CommonUtil.trim(newsContent.getContent());
        		if(content == null || content.equals("") ){
        			content += newsServiceByFulltextSearch.getFiles(news);
            	}
        	}else{
        		content += newsServiceByFulltextSearch.getFiles(news);
        	}
        	String fileName = newsServiceByFulltextSearch.getFiles(news);
//        	System.out.println("titile: " + titile +  " content:" + ReduceHtmlText.removeHtmlTag(content));
//        	System.out.println("newsId: " + newsId);
        	if(content == null || content.equals("")){
        		content += "";
        	}
        	//业务单路径路径（通过业务参数设置:FULLTEXT_SEARCH_BULLETIN）
        	String newsViewUrl = EIPService.getRegistryService().get("FULLTEXT_SEARCH_NEWS")+"?newsId="+newsId;
//        	System.out.println("newsViewUrl: " + newsViewUrl);
        	String date = CommonUtil.format(news.getPublishDate(),"yyyy-MM-dd");
            Document document = addDocument(newsId,titile,ReduceHtmlText.removeHtmlTag(content),fileName,date,newsViewUrl);
            iwriter.addDocument(document);
        }
    }
	/**
     * 创建专栏索引
     * Description：
     * @author dennisit@163.com Apr 3, 2013
	 * @throws IOException 
     * @throws Exception
     */
    public void createIndexByArticle(IndexWriter iwriter) throws IOException{
    	List <Article>   articleList = articleServiceByFulltextSearch.getArticleList(Article.PUBLISHED);
        for(Article article : articleList){
        	String articleId = article.getArticleId();
        	//标题
        	String titile = CommonUtil.trim(article.getArticleTitle());
        	ArticleContent articleContent = articleServiceByFulltextSearch.getArticleContent(article);
        	String PublisherName = article.getPublisherName();
        	if(PublisherName == null){
        		PublisherName = "";
        	}
        	//内容
        	//String content = "";
        	String content = "专栏：(" + article.getPublisherName() + CommonUtil.format(article.getPublishDate(),"yyyy年MM月dd日") +" 发布)";
        	if(articleContent != null){
        		content += CommonUtil.trim(articleContent.getArticleContent())==null ? "" : CommonUtil.trim(articleContent.getArticleContent());
        		if(content == null || content.equals("") ){
        			content += article.getFiles();
            	}
        	}else{
        		content += article.getFiles();
        	}
        	String fileName = articleServiceByFulltextSearch.getFiles(article);
//        	System.out.println("titile: " + titile +  " content:" + ReduceHtmlText.removeHtmlTag(content));
//        	System.out.println("articleId: " + articleId);
//        	System.out.println("fileName: " + fileName);
        	if(content == null || content.equals("")){
        		content += "";
        	}
        	
        	//业务单路径路径（通过业务参数设置:FULLTEXT_SEARCH_BULLETIN）
        	String articleViewUrl = EIPService.getRegistryService().get("FULLTEXT_SEARCH_ARTICLE")+"?articleId="+articleId;
//        	System.out.println("articleViewUrl: " + articleViewUrl);
        	String date = CommonUtil.format(article.getPublishDate(),"yyyy-MM-dd");
            Document document = addDocument(articleId,titile,ReduceHtmlText.removeHtmlTag(content),fileName,date,articleViewUrl);
            iwriter.addDocument(document);
        }
    }
	/**
     * 创建索引
     * Description：
     * @author dennisit@163.com Apr 3, 2013
	 * @throws IOException 
     * @throws Exception
     */
    public void createIndexByBulletin(IndexWriter iwriter) throws IOException{
    	List <Bulletin>   bulletinList = bulletinServiceByFulltextSearch.getBulletinList(Bulletin.PUBLISHED);
        for(Bulletin bulletion:bulletinList){
        	String bussnisId = bulletion.getBulletinId();
        	//标题
        	String titile = CommonUtil.trim(bulletion.getBulletinTitle());
        	BulletinContent bulletinContent = bulletinServiceByFulltextSearch.getBulletinContent(bulletion);
        	//内容
        	String publisherName = bulletion.getPublisherName() == null ? "" : bulletion.getPublisherName();
        	String content = "公司公告：(" + publisherName + CommonUtil.format(bulletion.getMessageDate(),"yyyy年MM月dd日") +" 发布)";
        	if(bulletinContent != null){
        		content += CommonUtil.trim(bulletinContent.getBulletinContent()) == null ? "" : CommonUtil.trim(bulletinContent.getBulletinContent());
//        		if(content == null || content.equals("") ){
//        			content += bulletion.getFiles();
//            	}
        	}
//        	else{
//        		content += bulletion.getFiles();
//        	}
//        	System.out.println("titile: " + titile +  " content:" + ReduceHtmlText.removeHtmlTag(content));
//        	System.out.println("bussnisId: " + bussnisId);
        	if(content == null || content.equals("")){
        		content += "";
        	}
        	String fileName = bulletinServiceByFulltextSearch.getFiles(bulletion);
        	//业务单路径路径（通过业务参数设置:FULLTEXT_SEARCH_BULLETIN）
        	String bulletinViewUrl = EIPService.getRegistryService().get("FULLTEXT_SEARCH_BULLETIN")+"?bulletinId="+bussnisId;
//        	System.out.println("bulletinViewUrl: " + bulletinViewUrl);
        	String date = CommonUtil.format(bulletion.getMessageDate(),"yyyy-MM-dd");
        	Document document = addDocument(bussnisId,titile,ReduceHtmlText.removeHtmlTag(content),fileName,date,bulletinViewUrl);
            iwriter.addDocument(document);
        }
    }
	/**
	 * 
	 * @param indexName
	 * @param indexContent
	 * @param indexBussninessPath
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Document addDocument(String bussnisId,String indexName,String indexContent,String fileName,String date,String indexBussninessPath){
		 //Field.Index.NO 表示不索引         
         //Field.Index.ANALYZED 表示分词且索引         
         //Field.Index.NOT_ANALYZED 表示不分词且索引
			Document ldocument_D = new Document();
			ldocument_D.add(new Field(INDEX_BUSSNIS_ID,bussnisId,
					Field.Store.YES,Field.Index.NOT_ANALYZED));
			ldocument_D.add(new Field(INDEX_NAME,indexName,
					Field.Store.YES,Field.Index.ANALYZED));//索引文件名称 不分词 存储
			ldocument_D.add(new Field(INDEX_CONTENT,indexContent,Field.Store.YES,Field.Index.ANALYZED));//索引文件内容 分词 不存储
			ldocument_D.add(new Field(INDEX_FILE_NAME,fileName,Field.Store.YES,Field.Index.ANALYZED));//索引附件名称 分词 不存储
			ldocument_D.add(new Field(INDEX_PATH,indexBussninessPath,
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引路径 不分词 存储
			ldocument_D.add(new Field(INDEX_Date, date, Field.Store.NO, Field.Index.NOT_ANALYZED_NO_NORMS));
			return ldocument_D;
	}
	
	/**
	 * 简单的关键字查询索引.
	 * @param as_IndexDir 索引目录
	 * @param as_Query 查询关键字
	 * @return Document的list
	 * @throws IOException
	 */
	public List < Document > simpleSearch(String as_IndexDir,String as_Query){
		
		Map<String, String> mapKey = new HashMap<String, String>();
		List < Document > llist_Document = new ArrayList < Document >();
		File indexDir = new File(as_IndexDir);  
		
		try {
			Directory dir = FSDirectory.open(indexDir);   
			//根据索引目录创建读索引对象  
			IndexReader reader = IndexReader.open(dir);
			 //搜索对象创建  
			IndexSearcher lindexsearcher_IS = new IndexSearcher(reader);
			
			
			if(lindexsearcher_IS != null){
				
				//索引名字
				Term term=new Term(INDEX_NAME, as_Query);  
			    TermQuery query=new TermQuery(term);  
			    
			    TopDocs topdocs=lindexsearcher_IS.search(query, 1000);  //搜索前100条记录
			    ScoreDoc[] scoreDocs = topdocs.scoreDocs;  
				
			    for(int i=0; i < scoreDocs.length; i++){  
			        int doc = scoreDocs[i].doc;
			        Document document = lindexsearcher_IS.doc(doc);  
			        //放弃重复的文档
			        String docName = CommonUtil.trim(document.get(INDEX_NAME));
			        if(!mapKey.containsKey(docName)){
			        	mapKey.put(docName, "");
			        	llist_Document.add(document);
			        }
			    }
			    
				term=new Term(INDEX_CONTENT, as_Query);  
			    query=new TermQuery(term);  
			    
			    topdocs=lindexsearcher_IS.search(query, 1000);  //搜索前100条记录
			    scoreDocs = topdocs.scoreDocs;
			    
			    for(int i=0; i < scoreDocs.length; i++){  
			        int doc = scoreDocs[i].doc;  
			        Document document = lindexsearcher_IS.doc(doc);  
			        //放弃重复的文档
			        String docName = CommonUtil.trim(document.get(INDEX_NAME));
			        if(!mapKey.containsKey(docName)){
			        	mapKey.put(docName, "");
			        	llist_Document.add(document);
			        }
			    }
			}
			reader.close();
		} catch (IOException e) {
			System.out.println("simpleSearch() IOException");
			e.printStackTrace();
		}
		
		return llist_Document;
	}
}
