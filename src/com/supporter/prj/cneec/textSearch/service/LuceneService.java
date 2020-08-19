package com.supporter.prj.cneec.textSearch.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;

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
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.HWPFOldDocument;
import org.apache.poi.hwpf.OldWordFileFormatException;
import org.apache.poi.hwpf.extractor.Word6Extractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.BodyElementType;
import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.supporter.prj.cneec.textSearch.dao.DataFileDao;
import com.supporter.prj.cneec.textSearch.entity.DataFile;
import com.supporter.prj.eip.file_upload.entity.FileUpload;
import com.supporter.prj.eip.file_upload.service.FileUploadService;
import com.supporter.util.CommonUtil;

/**
 * lucene的类(lucene就是个工具，一个工具类即可).
 * @author lyf
 *
 */
@Service
public class LuceneService {
	
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private DataFileDao dataFileDao;
	
	//做判断的数组长度
	private static  int ARRAY_LENGTH = 10;
	
	//doc格式的Word文档的字节数组的头十个值
	private static  byte [] WORD_FILE = {-48,-49,17,-32,-95,-79,26,-31,0,0};
	
	//索引文件内容
	public   String INDEX_CONTENT = "content";
	//索引文件名称
	public  String INDEX_NAME = "canonicalPath";
	//缩索引路径
	public  String INDEX_PATH = "path";
	
	//文件柜ID
	public static  String FILE_ID = "fileId";
	
	// lucene版本
    private static final Version VERSION = Version.LUCENE_40;//Version.LUCENE_34
	
	//中文分词器(待改进) 正向最大全切分分词器 适合用户输入检索时使用
	public Analyzer ANALYZER;
	
	public Analyzer getAnalyzer(){
		if(ANALYZER != null)return ANALYZER;
		ANALYZER = new IKAnalyzer(false); //最细粒度切分
		return ANALYZER;
	}
	
	private FileUpload getFileUpload(String id) {
		// FileUploadService fileUploadService =
		// SpringContextHolder.getBean(FileUploadService.class);
		FileUpload upload = fileUploadService.get(id);
		return upload;
	}

//	public void indexDocs(IndexWriter writer, File file) throws IOException {
//		// file可以读取
//		if (file.canRead()) {
//			// 如果file是一个目录(该目录下面可能有文件、目录文件、空文件三种情况)
//			if (file.isDirectory()) {
//				// 获取file目录下的所有文件(包括目录文件)File对象，放到数组files里
//				String[] files = file.list();
//				if (files != null) {// 如果files!=null
//					// 对files数组里面的File对象递归索引，通过广度遍历
//					for (int i = 0; i < files.length; i++) {
//						indexDocs(writer, new File(file, files[i]));
//					}
//				}
//			} else { // 到达叶节点时，说明是一个File，而不是目录，则建立索引
//				System.out.println("adding " + file);
//				try {
//					writer.addDocument(FileDocument.Document(file));
//				} catch (FileNotFoundException fnfe) {
//					;
//				}
//			}
//		}
//	}
	

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
		File lfile_DataDir = null;
		try {
			lfile_DataDir = new File(as_DataDir);
			File lfile_IndexDir = new File(as_IndexDir);
			index(lfile_DataDir,lfile_IndexDir,indexType);
			
			lfile_DataDir = null;
			lfile_IndexDir = null;
		} catch (RuntimeException e) {
//			System.out.println("as_DataDir:" + as_DataDir);
//			System.out.println("lfile_DataDir:" + lfile_DataDir);
			e.printStackTrace();
		}
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
	public void index(String[] as_DataDirs,String as_IndexDir){
		File[] lfile_DataDir = new File[as_DataDirs.length];
		try {
			
			for(int i=0;i<as_DataDirs.length;i++){
				lfile_DataDir[i] = new File(as_DataDirs[i]);
			}
			
			File lfile_IndexDir = new File(as_IndexDir);
			index(lfile_DataDir,lfile_IndexDir);
			
			lfile_DataDir = null;
			lfile_IndexDir = null;
		} catch (RuntimeException e) {
//			System.out.println("as_DataDir:" + as_DataDir);
//			System.out.println("lfile_DataDir:" + lfile_DataDir);
			e.printStackTrace();
		}
	}
	/**
	 * 硬盘上建立索引.
	 * @param afile_DataDir 数据文件目录
	 * @param afile_IndexDir 索引文件目录
	 * @throws IOException 
	 */
	public void index(File[] afile_DataDirs,File afile_IndexDir){
//		for(File afile_DataDir : afile_DataDirs){
//			if(!afile_DataDir.exists() || !afile_DataDir.isDirectory()){
//				System.out.println(afile_DataDir + " does not exists or is not a directory!");
//				//return;
//			}
//		}
//		System.out.println("开始建立索引"+afile_IndexDir);
		
		//索引目录 
        try {
        	Directory dir = FSDirectory.open(afile_IndexDir);
			//配置IndexWriterConfig 
			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
			IndexWriter iwriter = new IndexWriter(dir , iwConfig);
			
			//删除原有索引
			iwriter.deleteAll();
			for(File afile_DataDir : afile_DataDirs){
				if(!afile_DataDir.exists() || !afile_DataDir.isDirectory()){
					System.out.println(afile_DataDir + " does not exists or is not a directory!");
					//return;
				}else{
					indexDirectory(iwriter, afile_DataDir);
				}
			}
			
			iwriter.close();
			iwConfig = null;
			dir = null;
			
//			System.out.println("索引建立结束");
		} catch (IOException e) {
			System.out.println("硬盘上建立索引有误.");
			e.printStackTrace();
		}  
	}
	/**
	 * 内存上建立索引.
	 * @param as_DataDir 数据文件目录
	 * @throws IOException
	 */
	public void index(String as_DataDir,int indexType){
		File lifle_Data = new File(as_DataDir);
		index(lifle_Data,indexType);
	}
	
	/**
	 * 硬盘上建立索引.
	 * @param afile_DataDir 数据文件目录
	 * @param afile_IndexDir 索引文件目录
	 * @throws IOException 
	 */
	public void index(File afile_DataDir,File afile_IndexDir,int indexType){
		if(!afile_DataDir.exists() || !afile_DataDir.isDirectory()){
			System.out.println(afile_DataDir + " does not exists or is not a directory!");
			return;
		}
		
//		System.out.println("开始建立索引"+afile_IndexDir);
		
		//索引目录 
        try {
        	Directory dir = FSDirectory.open(afile_IndexDir);
			//配置IndexWriterConfig 
			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
			IndexWriter iwriter = new IndexWriter(dir , iwConfig);
			
			//删除原有索引
			iwriter.deleteAll();
			indexDirectory(iwriter, afile_DataDir);
			
			iwriter.close();
			iwConfig = null;
			dir = null;
			
//			System.out.println("索引建立结束");
		} catch (IOException e) {
			System.out.println("硬盘上建立索引有误.");
			e.printStackTrace();
		}  
	}
	
	/**
	 * 内存上建立索引.
	 * @param afile_DataDir 数据文件目录
	 * @throws IOException
	 */
	public void index(File afile_DataDir,int indexType){
		if(!afile_DataDir.exists() || !afile_DataDir.isDirectory()){
			System.out.println(afile_DataDir + " does not exists or is not a directory!");
			return;
		}
		try {
			//配置IndexWriterConfig 
			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
			IndexWriter iwriter = new IndexWriter(new RAMDirectory() , iwConfig);
			indexDirectory(iwriter, afile_DataDir);
			iwriter.close();
		} catch (IOException e) {
			System.out.println("内存上建立索引有误");
			e.printStackTrace();
		}
	}
	public DataFile getDataFile(String id){
		return this.dataFileDao.get(id);
	}
	
	/**
	 * 遍历数据文件目录.
	 * @param aindexwriter_IW 
	 * @param afile_F 数据文件目录
	 * @throws IOException 
	 */
	public IndexWriter indexDirectory(IndexWriter aindexwriter_IW,File afile_F){
		File [] larrfile_Files = afile_F.listFiles();
		if(larrfile_Files != null){
			for (File lfile_F : larrfile_Files){
				if(lfile_F.isDirectory()){
					indexDirectory(aindexwriter_IW,lfile_F);
				} else{
					FileUpload upload = getUpload(lfile_F.getName());
					if(upload != null){
						String fileType = CommonUtil.trim(upload.getFileExt());
						String fileRealName = CommonUtil.trim(upload.getFileName());
//						System.out.println(fileRealName);
						 if(fileType.equals("doc")){//格式为doc的Word文档
								if(fileRealName != null)indexWordFile(aindexwriter_IW,lfile_F,fileRealName);
							} else if(fileType.equals(".pdf")){//pdf文档
								if(fileRealName != null)indexPdfFile(aindexwriter_IW,lfile_F,fileRealName);
							}else if(fileType.equals(".txt")){//txt文档
								if(fileRealName != null)indexTxt(aindexwriter_IW,lfile_F,fileRealName);
							}else if(fileType.equals(".docx")){//pdf文档
								if(fileRealName != null)indexWordDocx(aindexwriter_IW,lfile_F,fileRealName);
							}
					} else {
//						System.out.println("开始文件柜内容：：");
						System.out.println("lfile_F.getName()="+lfile_F.getName()+"==path="+lfile_F.getPath());
						if(lfile_F.getPath().indexOf("remot_foxbox_data")>-1){
							String file_id = lfile_F.getName();
							if(lfile_F.getName().indexOf(".")>-1){
								file_id = lfile_F.getName().substring(0,lfile_F.getName().indexOf("."));
							}
//							System.out.println("file_id="+file_id);
							DataFile dataFile = dataFileDao.get(file_id);
							if (dataFile != null) {
								String fileType = CommonUtil.trim(dataFile
										.getFileExt());
								String fileRealName = CommonUtil.trim(dataFile
										.getFileName());
//								System.out.println(fileRealName);
								if (fileType.equals("doc")) {// 格式为doc的Word文档
									if (fileRealName != null)
										indexWordFile(aindexwriter_IW, lfile_F,
												fileRealName);
								} else if (fileType.equals(".pdf")) {// pdf文档
									if (fileRealName != null)
										indexPdfFile(aindexwriter_IW, lfile_F,
												fileRealName);
								} else if (fileType.equals(".txt")) {// txt文档
									if (fileRealName != null)
										indexTxt(aindexwriter_IW, lfile_F,
												fileRealName);
								} else if (fileType.equals(".docx")) {// pdf文档
									if (fileRealName != null)
										indexWordDocx(aindexwriter_IW, lfile_F,
												fileRealName);
								}
							}
						}
					}
				}
			}
		}else{
			System.out.println("文件夹下没有文件！");
		}
		return aindexwriter_IW;
	}
	
	public String getRealName(String lastName,String fileName,int indexType){
		String[] fileNamePart = fileName.split(lastName);
		String[] name = fileNamePart[0].split("_");
		String id = name[0];
		int index = Integer.parseInt(name[1]);
		String realNames = getRealName(id);
		if(realNames != null && CommonUtil.trim(realNames).length()  > 0 && realNames.split(",").length > index){
			String realName = CommonUtil.trim(realNames.split(",")[index]);
			return realName;
		}else return null;
		
	}
	
	/**
	 * 文件名称
	 * @param id
	 * @param indexType
	 * @return
	 */
	private String getRealName(String id){
		String realName = "";
		if(id != null && id.length() > 0){
			FileUpload upload = getFileUpload(id);
			if(upload != null){
				realName = upload.getFileName();
			}
		}
		return realName;
	}
	
//	/**
//	 * 文件类型
//	 * @param id
//	 * @param indexType
//	 * @return
//	 */
//	private String getFileType(String id){
//		String fileType = "";
//		if(id != null && id.length() > 0){
//			FileUpload upload = getFileUpload(id);
//			if(upload != null && upload.getFileExt() != null){
//				fileType = upload.getFileExt();
//			}
//		}
//		return fileType;
//	}
	
	/**
	 * 返回文件.
	 * @param id
	 * @return
	 */
	private FileUpload getUpload(String id){
		if(id != null && id.length() > 0){
			return getFileUpload(id);
		}else return null;
	}
	
	
	
	/**
	 * 读取格式为doc的Word文档建立索引.
	 * @param aindexwriter_IW
	 * @param afile_F 格式为doc的Word文件
	 * @throws IOException 
	 */
	public void indexWordFile(IndexWriter aindexwriter_IW,File afile_F,String realName){
		if(!afile_F.exists() || afile_F.isHidden() || !afile_F.canRead()){
			return;
		}
		if(afile_F.length() == 0){//判断文件是否为0字节
			System.out.println("The length of " + afile_F + " is 0.");
			return;
		}
		String ls_WordText = getWordText(afile_F, true);
		if(ls_WordText == null)return;
		StringReader lstringreader_SR = new StringReader(ls_WordText);
		try {
			Document ldocument_D = new Document();
			ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));//索引文件内容 分词 不存储
			
			ldocument_D.add(new Field(INDEX_NAME,realName,
					Field.Store.YES,Field.Index.ANALYZED));//索引文件名称 不分词 存储
			
			ldocument_D.add(new Field(INDEX_PATH,afile_F.getCanonicalPath(),
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引路径 不分词 存储
			aindexwriter_IW.addDocument(ldocument_D);
			
		} catch (IOException e) {
			System.out.println("indexWordFile() is error!");
			e.printStackTrace();
		}
		lstringreader_SR.close();
		ls_WordText = null;
	}
	
	public void indexWordDocx(IndexWriter aindexwriter_IW,File afile_F,String realName){
		if(!afile_F.exists() || afile_F.isHidden() || !afile_F.canRead()){
			return;
		}
		if(afile_F.length() == 0){//判断文件是否为0字节
			System.out.println("The length of " + afile_F + " is 0.");
			return;
		}
		String ls_WordText = getWordDocx(afile_F);
		if(ls_WordText == null)return;
		StringReader lstringreader_SR = new StringReader(ls_WordText);
		
		try {
			Document ldocument_D = new Document();
			ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));//索引文件内容 分词 不存储
			
			ldocument_D.add(new Field(INDEX_NAME,realName,
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
			ldocument_D.add(new Field(INDEX_PATH,afile_F.getCanonicalPath(),
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引路径 不分词 存储
			aindexwriter_IW.addDocument(ldocument_D);
		} catch (IOException e) {
			System.out.println("indexWordFile() is error!");
			e.printStackTrace();
		}
		lstringreader_SR.close();
		ls_WordText = null;
	}
	
	
	public void indexTxt(IndexWriter aindexwriter_IW,File afile_F,String realName){
		if(!afile_F.exists() || afile_F.isHidden() || !afile_F.canRead()){
			return;
		}
		String ls_WordText = getTxtText(afile_F);
		 
		if(ls_WordText == null)return;
		StringReader lstringreader_SR = new StringReader(ls_WordText.toString());
		
		try {
			Document ldocument_D = new Document();
			ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));//索引文件内容 分词 不存储
			ldocument_D.add(new Field(INDEX_NAME,realName,
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
			ldocument_D.add(new Field(INDEX_PATH,afile_F.getCanonicalPath(),
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引路径 不分词 存储
			aindexwriter_IW.addDocument(ldocument_D);
		} catch (IOException e) {
			System.out.println("indexTxt() eroror");
			e.printStackTrace();
		}
		lstringreader_SR.close();
	}
	
	/**
	 * 获得word文档的内容(应建立在独立的方法类POIBO下).
	 * @param afile_F Word文档
	 * @return 文档内容
	 * @throws IOException
	 */
	public String getWordText(File afile_F, boolean flag){
		InputStream linputstream_IS;
		try {
			linputstream_IS = new FileInputStream(afile_F);
//			System.out.println("linputstream_IS:" + linputstream_IS);
			byte [] larrbyte_InputStream = new byte [LuceneService.ARRAY_LENGTH];//接受前十个字节码的数组
			
			try {
				linputstream_IS.read(larrbyte_InputStream,0,LuceneService.ARRAY_LENGTH);
				linputstream_IS = new FileInputStream(afile_F);
				if(!Arrays.equals(larrbyte_InputStream, WORD_FILE)){//判断是否为标准Word文档
					System.out.println(afile_F + " is not a standard Microsoft Word.");
					return null;
				}
				String wordText = "";
				if(flag){
					wordText = getWordText(linputstream_IS, afile_F);
				}else{
					wordText = getWordTextOld(linputstream_IS);
				}
				return wordText;
				
			} catch (IOException e) {
				System.out.println("输入输出流有误!");
				e.printStackTrace();
				return null;
			}
			
		} catch (Exception e) {
			System.out.println("文件未找到getWordText()");
			e.printStackTrace();
			return null;
		}
	}
	
	public  String getWordDocx(File afile_F){
		try {
//			System.out.println(afile_F.getPath());
			OPCPackage openPackage = POIXMLDocument.openPackage(afile_F.getPath());
			XWPFWordExtractor docx = new XWPFWordExtractor(openPackage);
			String text = docx.getText();
			docx = null;
			openPackage = null;
			System.gc();
			return text; 
		} catch (XmlException e) {
			e.printStackTrace();
			System.out.println("Error: getWordDocx()");
			return null;
		} catch (OpenXML4JException e) {
			e.printStackTrace();
			System.out.println("Error: getWordDocx()");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error: getWordDocx()");
			return null;
		}
	}
	/**
	 * 获得word文档的内容(应建立在独立的方法类POIBO下)(在确认为正确的word文档下).
	 * @param ainputstream_IS
	 * @return
	 * @throws IOException 
	 */
	public  String getWordText(InputStream ainputstream_IS, File afile_F) throws Exception{
		String ls_WordText = null;
		try {
//			WordExtractor lwordextractor_WE;
			try {
//				System.out.println("读取word"+afile_F.getName());
				HWPFDocument hwpf=new HWPFDocument(ainputstream_IS);  
				Range range = hwpf.getRange();
				ls_WordText = range.text();
				ainputstream_IS.close();
//				lwordextractor_WE = new WordExtractor(ainputstream_IS);
//				ls_WordText = lwordextractor_WE.getText();
//				System.out.println(ls_WordText);
// 				System.out.println("读取word结束");
			} catch (Exception e) {
				if(e instanceof OldWordFileFormatException){
					ls_WordText = getWordText(afile_F, false);
					return ls_WordText;
				}else{
					System.out.println("getWordText(InputStream ainputstream_IS) IOException");
					e.printStackTrace();
					return null;
				}
			}
		} catch (Exception e) {
			System.out.println("getWordText(InputStream ainputstream_IS) error");
			e.printStackTrace();
		}
		return ls_WordText;
	}
	public void getTable(InputStream ainputstream_IS){
		try{
			HWPFDocument hwpf=new HWPFDocument(ainputstream_IS);  
			Range range = hwpf.getRange();
			TableIterator it = new TableIterator(range);
			System.out.println("it :"+it);
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				System.out.println("tb :"+tb.numRows());
				for (int i = 0; i < tb.numRows(); i++) {
					TableRow tr = tb.getRow(i);
					System.out.println("numCells :"+tr.numCells());
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);
						for (int k = 0; k < td.numParagraphs(); k++) {
							Paragraph para = td.getParagraph(k);
							System.out.println(para.text());
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	/**
	 * 处理docx读取带表格的word文档。
	 * @param in
	 * @throws Exception
	 */
	public String getWordXAndStyle(InputStream in) throws Exception {
	    XWPFDocument docx = new XWPFDocument(in);  
	    Iterator<IBodyElement> iBody = docx.getBodyElementsIterator();
	    int curT = 0;//当前操作对象的索引
	    int curP = 0;//当前操作对象的索引
	    //htmlText = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title></title></head><body>";
	    String htmlText = "";
	    while(iBody.hasNext()){
	        IBodyElement body = iBody.next();
	        if(BodyElementType.TABLE.equals(body.getElementType())){//处理表格
	            XWPFTable table = body.getBody().getTableArray(curT);
	            List<XWPFTable> tables = body.getBody().getTables();
	            table = tables.get(curT);
	            if(table != null){
	                htmlText = htmlText+readTableX(table);
	                curT++;
	            }
	        }else if(BodyElementType.PARAGRAPH.equals(body.getElementType())){//处理段落
	            XWPFParagraph ph = body.getBody().getParagraphArray(curP);
	            if(ph != null){
	                htmlText = htmlText+readParagraphX(ph);
	                curP++;
	            }
	        }
	    }
	    //htmlText = htmlText + "</body></html>";
	    System.out.println("htmlText==="+htmlText);
	    //writeFile(htmlText,fileName,path,type);
	    return htmlText;
	}

	public String readTableX(XWPFTable tb) throws Exception {
	    //tblExist=true;
	    String htmlTextTbl="";

	    List<XWPFTableRow> rows = tb.getRows();
	    //遍历行
	    for(XWPFTableRow row:rows){
	        //int rowHight = row.getHeight();
	        String tr = "";
	        List<XWPFTableCell> cells = row.getTableCells();
	        //遍历列
	        for(XWPFTableCell cell:cells){
	            String text = "";
	            List<XWPFParagraph> graphs = cell.getParagraphs();
	            //遍历段落
	            for(XWPFParagraph pg:graphs){
	                text = text+pg.getText()+"<br/>";
	            }
	            tr += "<td>"+text+"</td>";
	        }
	        htmlTextTbl += "<tr>"+tr+"</tr>";
	    }
	    htmlTextTbl = "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" class=\"tbl2\">"+htmlTextTbl+"</table><br/>";
	    return htmlTextTbl;
	}

	public String readParagraphX(XWPFParagraph p) throws Exception {
	    String tempStr = "";
	    String text = p.getText();
	    if(CommonUtil.isEmpty(text)){
	        tempStr = tempStr + "<br/>";
	    }else{
	        tempStr = tempStr+"<span>"+text+"</span><br/>";
	    }
	    return tempStr;
	}

	/**
	 * 获得word文档的内容(应建立在独立的方法类POIBO下)(在确认为正确的word文档下).
	 * 用于低版本word(例如word95)
	 * @param ainputstream_IS
	 * @return
	 * @throws IOException 
	 */
	public  String getWordTextOld(InputStream ainputstream_IS){
		String ls_WordText = null;
		try {
			try {
				// 获取文件编码格式
//			    String code = this.getFileEncode("E:\\\\workspace\\cneec_eip6.2\data\supp\remot_foxbox_data\filebox\25631\44327.DOC");
//			 // 根据编码格式解析文件
//			      if("asci".equals(code)){
//			        // 这里采用GBK编码，而不用环境编码格式，因为环境默认编码不等于操作系统编码 
//			        // code = System.getProperty("file.encoding");
//			        code = "GBK";
//			      }
				
				POIFSFileSystem fileSys = HWPFOldDocument.verifyAndBuildPOIFS(ainputstream_IS);//new POIFSFileSystem(ainputstream_IS);
				//POIFSFileSystem fileSys = HWPFOldDocument.verifyAndBuildPOIFS(ainputstream_IS);
				HWPFOldDocument doc = new HWPFOldDocument(fileSys);
				Range rang = doc.getRange();
				ls_WordText = rang.text();
//				ls_WordText = doc.getDocumentText();
//				String uncod=URLDecoder.decode(ls_WordText,"iso-8859-1");
//				String name1 = new String(ls_WordText.getBytes("iso-8859-1"), "utf-8");
//				System.out.println("name1="+name1);
//				name1 = new String(ls_WordText.getBytes("GBK"), "utf-8");
//				System.out.println("name2="+name1);
//				name1 = new String(ls_WordText.getBytes("iso-8859-1"), "GBK");
//				System.out.println("name3="+name1);
//				name1 = new String(ls_WordText.getBytes("utf-8"), "GBK");
//				System.out.println("name4="+name1);
//				name1 = new String(ls_WordText.getBytes("GBK"), "iso-8859-1");
//				System.out.println("name5="+name1);
//				name1 = new String(ls_WordText.getBytes("utf-8"), "iso-8859-1");
//				System.out.println("name6="+name1);
				String name1 = toChinese(ls_WordText);
				System.out.println("name7="+name1);
				System.out.println("ls_WordText="+ls_WordText);
				ainputstream_IS.close();
			} catch (Exception e) {
				System.out.println("getWordText(InputStream ainputstream_IS) IOException");
				e.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			System.out.println("getWordText(InputStream ainputstream_IS) error");
			e.printStackTrace();
		}
		return ls_WordText;
	}
	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为乱码
	 * 
	 * @param strName
	 *            传入需要判断的字符串
	 * @return 如果为乱码则返回true，否则返回false
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = 0;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
				chLength++;
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断字符串是否为乱码并返回
	 * 
	 * @param msg
	 *            传入乱码字符串
	 * @return 返回转换后的汉字数据
	 */
	public static String toChinese(String msg) {
		System.out.println("isMessyCode(msg)==="+isMessyCode(msg));
		if (isMessyCode(msg)) {
			
			try {
				return new String(msg.getBytes("ISO8859-1"), "UTF-8");
			} catch (Exception e) {
			}
		}
		return msg;
	}  

	public static String getFileEncode(String path) {
	    String charset ="asci";
	    byte[] first3Bytes = new byte[3];
	    BufferedInputStream bis = null;
	    try {
	      boolean checked = false;
	      bis = new BufferedInputStream(new FileInputStream(path));
	      bis.mark(0);
	      int read = bis.read(first3Bytes, 0, 3);
	      if (read == -1)
	        return charset;
	      if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
	        charset = "Unicode";//UTF-16LE
	        checked = true;
	      } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
	        charset = "Unicode";//UTF-16BE
	        checked = true;
	      } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
	        charset = "UTF8";
	        checked = true;
	      }
	      bis.reset();
	      if (!checked) {
	        int len = 0;
	        int loc = 0;
	        while ((read = bis.read()) != -1) {
	          loc++;
	          if (read >= 0xF0)
	            break;
	          if (0x80 <= read && read <= 0xBF) //单独出现BF以下的，也算是GBK
	            break;
	          if (0xC0 <= read && read <= 0xDF) {
	            read = bis.read();
	            if (0x80 <= read && read <= 0xBF) 
	            //双字节 (0xC0 - 0xDF) (0x80 - 0xBF),也可能在GB编码内
	              continue;
	            else
	              break;
	          } else if (0xE0 <= read && read <= 0xEF) { //也有可能出错，但是几率较小
	            read = bis.read();
	            if (0x80 <= read && read <= 0xBF) {
	              read = bis.read();
	              if (0x80 <= read && read <= 0xBF) {
	                charset = "UTF-8";
	                break;
	              } else
	                break;
	            } else
	              break;
	          }
	        }
	        //TextLogger.getLogger().info(loc + " " + Integer.toHexString(read));
	      }
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (bis != null) {
	        try {
	          bis.close();
	        } catch (IOException ex) {
	        }
	      }
	    }
	    return charset;
	  }
	  
	  private static String getEncode(int flag1, int flag2, int flag3) {
	    String encode="";
	    // txt文件的开头会多出几个字节，分别是FF、FE（Unicode）,
	    // FE、FF（Unicode big endian）,EF、BB、BF（UTF-8）
	    if (flag1 == 255 && flag2 == 254) {
	      encode="Unicode";
	    }
	    else if (flag1 == 254 && flag2 == 255) {
	      encode="UTF-16";
	    }
	    else if (flag1 == 239 && flag2 == 187 && flag3 == 191) {
	      encode="UTF8";
	    }
	    else {
	      encode="asci";// ASCII码
	    }
	    return encode;
	  }

	/**
	 * 获得word文档的内容(应建立在独立的方法类POIBO下).
	 * @param as_WordDir Word文档目录
	 * @return 文档内容
	 * @throws IOException 
	 */
	public String getWordText(String as_WordDir){
		File lfile_WordDir = new File(as_WordDir);
		if(!lfile_WordDir.exists() || lfile_WordDir.isDirectory() || !lfile_WordDir.canRead()){
			System.out.println(as_WordDir + "getWordText() does not exists or is a directory or can not be read.");
			return null;
		}
		return getWordText(lfile_WordDir, true);
	}
	
	public String getWordDocx(String wordPath){
		try {
			XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(wordPath));
			//提取.docx正文文本 
			String text = docx.getText(); 
			return text;
		} catch (XmlException e) {
			e.printStackTrace();
			return null;
		} catch (OpenXML4JException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 根据路径返回文件内容
	 * @param as_WordDir
	 * @return
	 */
	public String getAllText(String as_WordDir,String realName){
		as_WordDir = CommonUtil.trim(as_WordDir);
		if(realName != null && realName.length() > 0){
			if(realName.endsWith(".doc")){
				return getWordText(as_WordDir);
			}else if(realName.endsWith(".txt")){
				return getTxtText(as_WordDir);
			}else if(realName.endsWith(".docx")){
				return getWordDocx(as_WordDir);
			}else if(realName.endsWith(".pdf")){
				return getPdfText(as_WordDir);
			}
		}
		return null;
	}
	
	public String getTxtText(String as_WordDir){
		File lfile_WordDir = new File(as_WordDir);
		if(!lfile_WordDir.exists() || lfile_WordDir.isDirectory() || !lfile_WordDir.canRead()){
			System.out.println("getTxtText() file is not exist");
			return null;
		}
		return getTxtText(lfile_WordDir);
	}
	
	public  String getTxtText(File file){
		if(!file.exists() || file.isDirectory() || !file.canRead()){
			return null;
		}
		StringBuffer ls_WordText = new StringBuffer();
		InputStreamReader read;
		try {
			read = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(read);   
			try {
				String lineTXT = null;  
				while ((lineTXT = bufferedReader.readLine()) != null){
					ls_WordText.append(lineTXT);
				}
			} catch (IOException e) {
				System.out.println("getTxtText(File file) is error");
				e.printStackTrace();
			}  
			
		} catch (FileNotFoundException e1) {
			System.out.println("getTxtText(File file) FileNotFoundException");
			e1.printStackTrace();
		}  
		
		return ls_WordText.toString();
	}
	
	/**
	 * 追加格式为doc的Word文档索引.
	 * @param as_IndexDir 索引目录
	 * @param as_WordDir 格式为doc的Word文件
	 * @throws IOException
	 */
	public void indexNewWordFile(String as_WordDir,String as_IndexDir,String realName){
		File lfile_IndexDir = new  File(as_IndexDir);
		if(!lfile_IndexDir.exists() || !lfile_IndexDir.isDirectory()){
			System.out.println(as_IndexDir + " does not exists or is not a directory!");
			return;
		}
		if(!as_WordDir.endsWith(".doc")){
			System.out.println(as_WordDir + " is not a Word document.");
			return;
		}
		File lfile_WordDir = new File(as_WordDir);
		
		indexNewClearWordFile(lfile_WordDir,lfile_IndexDir,realName);
	}
	
	
	/**
	 * 追加明确的Word文档索引.
	 * @param afile_WordDir 索引目录
	 * @param afile_IndexDir 格式为doc的Word文件
	 * @param as_FileId
	 * @throws IOException
	 */
	public void indexNewClearWordFile(File afile_WordDir,File afile_IndexDir,String realName){
		
//		//追加新的Word文档索引 不可清空目录
//		IndexWriter lindexwriter_IW = new IndexWriter(afile_IndexDir,CHN_IK_ANALYZER,false);
//		lindexwriter_IW.setUseCompoundFile(false);
//		indexWordFile(lindexwriter_IW,afile_WordDir,as_FileId);
//		System.out.println("Add " + afile_WordDir + " index into " + afile_IndexDir);
//		lindexwriter_IW.optimize();//优化
//		lindexwriter_IW.close();
		
		//索引目录  
        try {
			Directory dir=FSDirectory.open(afile_IndexDir);  
			
			//配置IndexWriterConfig 
			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
			IndexWriter iwriter = new IndexWriter(dir , iwConfig);
			indexWordFile(iwriter,afile_WordDir,realName);
			iwriter.close();
		} catch (IOException e) {
			System.out.println("indexNewClearWordFile() IOException");
			e.printStackTrace();
		}
	}
	
	/**
	 * 追加Word文档索引(专为文件柜).
	 * @param as_IndexDir
	 * @param afis_Word
	 * @param as_FileId
	 * @param as_FileName
	 */
//	public void indexNewClearWordFile(String as_IndexDir,InputStream afis_Word,
//			String as_FileId,String as_FileName){
//		//追加新的Word文档索引 不可清空目录
//		File afile_IndexDir = new File(as_IndexDir);
////		boolean lb_Rebuild = false;
////		if(!afile_IndexDir.exists() || isFileNull(afile_IndexDir)) lb_Rebuild = true;
////		IndexWriter lindexwriter_IW = new IndexWriter(afile_IndexDir,CHN_IK_ANALYZER,lb_Rebuild);
////		lindexwriter_IW.setUseCompoundFile(false);//是否生成复合文件
////		indexWordFile(lindexwriter_IW,afis_Word,as_FileId,as_FileName);
////		System.out.println("Add " + as_FileName + " index into " + afile_IndexDir);
////		lindexwriter_IW.optimize();//优化
////		lindexwriter_IW.close();
//		
//		try {
//			//索引目录  
//			Directory dir=FSDirectory.open(afile_IndexDir);  
//			//配置IndexWriterConfig Version.LUCENE_CURRENT
//			IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
//			iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
//			IndexWriter iwriter = new IndexWriter(dir , iwConfig);
//			indexWordFile(iwriter,afis_Word,as_FileId,as_FileName);
//			iwriter.close();
//		} catch (IOException e) {
//			System.out.println("indexNewClearWordFile(专为文件柜) IOException");
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 判断文件夹是否为空.
	 * @param afile_F
	 * @return
	 */
	private boolean isFileNull(File afile_F){
		File [] larrfile_Files = afile_F.listFiles();
		return larrfile_Files.length == 0;
	}
	
	/**
	 * 读取格式为doc的Word文档建立索引(专为文件柜).
	 * @param aindexwriter_IW
	 * @param afis_Word
	 * @param as_FileId
	 * @param as_FileName
	 * @throws IOException
	 */
//	public void indexWordFile(IndexWriter aindexwriter_IW,InputStream afis_Word,
//			String as_FileId,String as_FileName){
//		String ls_WordText = null;
//		try{
//			ls_WordText = getWordText(afis_Word, true);
//		}catch(Exception e){
//			if(e instanceof OldWordFileFormatException){
//				ls_WordText = getWordTextOld(afis_Word);
//			}else{
//				System.out.println("getWordText(InputStream ainputstream_IS) IOException");
//			}
//		}
//		if(ls_WordText == null)return;
//		StringReader lstringreader_SR = new StringReader(ls_WordText);
//		Document ldocument_D = new Document();
//		ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));//索引文件内容 分词 不存储
//		ldocument_D.add(new Field(INDEX_NAME,as_FileName,
//				Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
//		try {
//			aindexwriter_IW.addDocument(ldocument_D);
//		} catch (IOException e) {
//			System.out.println("indexWordFile() IOException");
//			e.printStackTrace();
//		}
//		lstringreader_SR.close();
//	}
	
	/**
	 * 追加Word文档索引(专为文件柜).
	 * @param as_WordDir
	 * @param as_IndexDir
	 * @param as_FileId
	 * @param as_FileName
	 * @param ab_NeedRebuild
	 * @throws IOException 
	 */
	public void indexNewClearWordFile(String as_WordDir,String as_IndexDir,
			String as_FileId,String as_FileName,boolean ab_NeedRebuild){
		//追加新的Word文档索引 不可清空目录
		File afile_IndexDir = new File(as_IndexDir);
//		IndexWriter lindexwriter_IW = new IndexWriter(afile_IndexDir,ANALYZER,ab_NeedRebuild);
//		lindexwriter_IW.setUseCompoundFile(false);
//		indexWordFile(lindexwriter_IW,as_WordDir,as_FileId,as_FileName);
//		//System.out.println("Add " + as_FileName + " index into " + afile_IndexDir);
//		lindexwriter_IW.optimize();//优化
//		lindexwriter_IW.close();
		
		 try {
			Directory dir=FSDirectory.open(afile_IndexDir);  
				//配置IndexWriterConfig Version.LUCENE_CURRENT
				IndexWriterConfig iwConfig = new IndexWriterConfig(VERSION , getAnalyzer()); 
				iwConfig.setOpenMode(OpenMode.CREATE_OR_APPEND); //首次创建时生效
				IndexWriter iwriter = new IndexWriter(dir , iwConfig);
				indexWordFile(iwriter,as_WordDir,as_FileId,as_FileName);
				iwriter.close();
		} catch (IOException e) {
			System.out.println("indexNewClearWordFile() IOException");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 读取格式为doc的Word文档建立索引(专为文件柜).
	 * @param aindexwriter_IW
	 * @param as_WordDir
	 * @param as_FileId
	 * @param as_FileName
	 * @throws IOException
	 */
	public void indexWordFile(IndexWriter aindexwriter_IW,String as_WordDir,
			String as_FileId,String as_FileName){
		String ls_WordText = getWordText(as_WordDir);
		if(ls_WordText == null)return;
		StringReader lstringreader_SR = new StringReader(ls_WordText);
		
		Document ldocument_D = new Document();
		ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));//索引文件内容 分词 不存储
		ldocument_D.add(new Field(INDEX_NAME,as_FileName,
				Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
		ldocument_D.add(new Field(INDEX_PATH,as_WordDir,
				Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
		try {
			aindexwriter_IW.addDocument(ldocument_D);
		} catch (IOException e) {
			System.out.println("indexWordFile()");
			e.printStackTrace();
		}
		lstringreader_SR.close();
	}
	
	/**
	 * 读取pdf文档建立索引.
	 * @param aindexwriter_IW
	 * @param afile_F pdf文件
	 * @throws IOException 
	 */
	public void indexPdfFile(IndexWriter aindexwriter_IW,File afile_F,String realName){
		if(!afile_F.exists() || afile_F.isHidden() || !afile_F.canRead()){
			return;
		}
		if(afile_F.length() == 0){//判断文件是否为0字节
			System.out.println("The length of " + afile_F + " is 0.");
			return;
		}
		String ls_PdfText = getPdfText(afile_F);
		if(ls_PdfText == null || ls_PdfText.length() == 0)return;
		StringReader lstringreader_SR = new StringReader(ls_PdfText);
		try {
			Document ldocument_D = new Document();
			ldocument_D.add(new Field(INDEX_CONTENT,lstringreader_SR));
			ldocument_D.add(new Field(INDEX_NAME,realName,
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引文件名称 不分词 存储
			ldocument_D.add(new Field(INDEX_PATH,afile_F.getCanonicalPath(),
					Field.Store.YES,Field.Index.NOT_ANALYZED));//索引lujing 不分词 存储
			aindexwriter_IW.addDocument(ldocument_D);
		} catch (IOException e) {
			System.out.println("indexPdfFile() IOException");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得pdf文档的内容(应建立在独立的方法类XpdfBO下).
	 * @param afile_Pdf pdf文档
	 * @param afile_Convertor pdf转换器路径
	 * @return 文档内容
	 * @throws IOException
	 */
	public  String getPdfText(File afile_Pdf){
		String result = null;;
		try {
			PDDocument doc = PDDocument.load(afile_Pdf.getPath()); 
			PDFTextStripper stripper=new PDFTextStripper();
			result = stripper.getText(doc);
			result = result.replace(" ", "");
			doc.close();
		} catch (IOException e) {
			System.out.println(" ERROR: getPdfText()");
			e.printStackTrace();
		} 
		return result;
	}
	
	/**
	 * 获得pdf文档的内容(应建立在独立的方法类PdfBO下).
	 * @param as_Pdf pdf文档路径
	 * @param as_Convertor 转化器路径
	 * @return 文档内容
	 * @throws IOException
	 */
	public String getPdfText(String as_Pdf){
		File lfile_Pdf = new File(as_Pdf);
		if(!lfile_Pdf.exists() || lfile_Pdf.isDirectory() || !lfile_Pdf.canRead()){
			System.out.println(as_Pdf + ":getPdfText() does not exists or is a directory or can not be read.");
			return null;
		}
		return getPdfText(lfile_Pdf);
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
			
			 Sort sort = new Sort(new SortField[]{new SortField("date", SortField.Type.STRING, true)});
			if(lindexsearcher_IS != null){
				
				//索引名字
				Term term=new Term(INDEX_NAME, as_Query);  
			    TermQuery query=new TermQuery(term);  
			    
			    TopDocs topdocs=lindexsearcher_IS.search(query, 1000, sort);  //搜索前100条记录
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
