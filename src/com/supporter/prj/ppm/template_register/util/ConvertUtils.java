package com.supporter.prj.ppm.template_register.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.supporter.prj.eip.dept.entity.Company;
import com.supporter.prj.eip.dept.entity.Department;
import com.supporter.prj.eip.dept.entity.HistCompany;
import com.supporter.prj.eip.dept.entity.HistDepartment;
import com.supporter.prj.eip.dept.entity.HistPost;
import com.supporter.prj.eip.dept.entity.HistTreeNode;
import com.supporter.prj.eip.dept.entity.Item;
import com.supporter.prj.eip.dept.entity.NameEntity;
import com.supporter.prj.eip.dept.entity.Post;
import com.supporter.prj.eip.dept.entity.Tree;
import com.supporter.prj.eip.dept.entity.TreeNode;
import com.supporter.prj.eip.dept.service.ContextListenerService;
import com.supporter.prj.eip_service.dept.entity.ITreeNode;
import com.supporter.prj.ppm.template_register.entity.TemplateRegisterDetail;
import com.supporter.prj.ppm.template_register.entity.TreeVO;
import com.supporter.util.CodeTable;
import com.supporter.util.CommonUtil;
import com.supporter.util.JsonUtils;

// ~ File Information
// ====================================================================================================================
/**
 * 
 * <pre>
 * 转换工具类
 * </pre>
 * 
 * @author liyinfeng
 * @createDate 2017-03-27
 */
public class ConvertUtils {

	// ~ Static Fields
	// ================================================================================================================
	/**
	 * 
	 */
	private static Calendar calendar = null;
	private static final String PATH_SPLIT = "/";
	private static final String SPLIT = ".";
	private static final String SPACE = "  ";
	// ~ Fields
	// ================================================================================================================

	// ~ Constructors
	// ================================================================================================================

	// ~ Methods
	// ================================================================================================================
	/**
	 * 
	 * convertInt 将传入的字符串转化成整型(若字符串非法,取默认值)
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @param strInt
	 * @param defaultInt
	 * @return
	 * @返回类型 int
	 * @exception
	 * @since 1.0.0
	 */
	public static int convertInt(String strInt, int defaultInt) {
		int outInt = defaultInt;
		if ((strInt == null) || (strInt.equals(""))) {
			outInt = defaultInt;
		} else {
			try {
				outInt = Integer.parseInt(strInt);
			} catch (Exception e) {
				outInt = defaultInt;
			}
		}
		if (outInt <= 0) {
			outInt = defaultInt;
		}
		return outInt;
	}

	/**
	 * 
	 * toDate 转换日期
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @param date1
	 * @return
	 * @返回类型 java.sql.Date
	 * @exception
	 * @since 1.0.0
	 */
	public static java.sql.Date toDate(String date1) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (date1 == null)
			return null;
		try {
			return new java.sql.Date(simpleDateFormat.parse(date1).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new java.sql.Date(0L);
	}

	/**
	 * 
	 * dateStrToDate yyyy-MM-dd
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @param dateStr
	 * @return
	 * @返回类型 Date
	 * @exception
	 * @since 1.0.0
	 */
	public static Date dateStrToDate(String dateStr) {
		if (null == dateStr) {
			return null;
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = simpleDateFormat.parse(dateStr);
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	/**
	 * 
	 * getDate yyyy-MM-dd
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 Date
	 * @exception
	 * @since 1.0.0
	 */
	public static Date getDate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		calendar = Calendar.getInstance();
		try {
			Date date = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
			return date;
		} catch (ParseException pe) {
			return null;
		}
	}

	public static Date getNow() {
		calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		return now;
	}

	/**
	 * 
	 * 码表转换
	 * <pre>
	 * 	码表转换
	 * </pre>
	 * 
	 * @param ct
	 * @return
	 */
	public static List<Item> CodeTableToList(CodeTable ct) {
		List<Item> list = new ArrayList<Item>();
		if (ct != null) {
			String[] keys = ct.getDataValues();
			for (String key : keys) {
				Item item = new Item();
				item.setId(key);
				item.setName(ct.getDisplay(key));
				list.add(item);
			}
		}
		return list;
	}

	/**
	 * 
	 *  转换
	 * <pre>
	 * 	转换
	 * </pre>
	 * 
	 * @param treeNodeList
	 * @param list
	 */
	public static void convertTreeVO(List<TemplateRegisterDetail> treeNodeList,
			List<TreeVO> list) {
		Map<String, String> pIdMap = new HashMap<String, String>();

		if (treeNodeList != null && list != null) {
			for (TemplateRegisterDetail treeNode : treeNodeList) {
				if(treeNode == null){
					continue;
				}
				String parentNodeId = CommonUtil.trim(treeNode.getParentId());
				pIdMap.put(parentNodeId, parentNodeId);
			}
			for (TemplateRegisterDetail treeNode : treeNodeList) {
				if(treeNode == null){
					continue;
				}
				String nodeId = treeNode.getDetailId();
				String refEntityId = treeNode.getDetailId();//getRefEntityId指向的实体ID
				String parentNodeId = CommonUtil.trim(treeNode.getParentId());
				String treeId = "ORG_TREE";

				int nodeType = 1;//treeNode.getNodeType()节点类型单位，部门
				int displayOrder = treeNode.getDisplayOrder();
				int nodeLevel = 0;//treeNode.getDirectoryStructure();

				boolean isActive = true;//treeNode.getIsActive();
				Date disabledDate = new Date();

				if (parentNodeId.equals("0") || parentNodeId.equals("")) {
					parentNodeId = null;
				}
				TemplateRegisterDetail parentNode = null;
				//if(parentNodeId != null){
				//	parentNode = allMap.get(parentNodeId);
				//}

				String parent = parentNodeId;
				String parentName = "";
				if(parentNode != null) parentName = parentNode.getTextDisplay();
				String displayName = "";
				String icon = "";
				String companyIcon = "";
				displayName = CommonUtil.trim(treeNode.getTextDisplay());
				boolean isLeaf = true;
				if (pIdMap.containsKey(nodeId)) {
					isLeaf = false;
				}
				TreeVO treeVO = new TreeVO(nodeId, parentNodeId, displayName, nodeId, nodeType, nodeLevel, treeId,
						displayOrder, isActive, disabledDate, true, true, isLeaf, parent, parentName, refEntityId);
				if (companyIcon == null || companyIcon.length() == 0) {
					companyIcon = "";
				}
				treeVO.setCompanyIcon(companyIcon);
				treeVO.setIcon(icon);
				treeVO.setCompanyNo(treeNode.getParagraphNo());
				//treeVO.setAllCount(treeNode.getAllCount());
				//treeVO.setSelfCount(treeNode.getSelfCount());
				list.add(treeVO);
			}
		}
	}
	
	

	/** 
	 * 获取某个父节点下面的所有子节点 
	 * @param treeNodeList 
	 * @param subTreeNodeList
	 * @param pid 
	 * @return 
	 */
	public static CopyOnWriteArrayList<HistTreeNode> subHistTreeNodeList(CopyOnWriteArrayList<HistTreeNode> treeNodeList,
			CopyOnWriteArrayList<HistTreeNode> subTreeNodeList,
			String pid) {
		if (treeNodeList == null || subTreeNodeList == null || pid == null) {
			return subTreeNodeList;
		}
		for (HistTreeNode tn : treeNodeList) {
			if (pid.equals(tn.getParentNodeId())) {
				subHistTreeNodeList(treeNodeList, subTreeNodeList, tn.getNodeId());
				subTreeNodeList.add(tn);
			}
		}
		return subTreeNodeList;
	}

	/** 
	 * 获取某个节点的所有父节点 
	 * @param treeNodeList 
	 * @param subTreeNodeList
	 * @param pid 
	 * @return 
	 */
	public static CopyOnWriteArrayList<TreeNode> parentTreeNodeList(CopyOnWriteArrayList<TreeNode> treeNodeList,
			CopyOnWriteArrayList<TreeNode> parentTreeNodeList,
			TreeNode treeNode) {
		if (treeNodeList == null || parentTreeNodeList == null) {
			return parentTreeNodeList;
		}
		if (treeNode != null) {
			for (TreeNode tn : treeNodeList) {
				if (treeNode.getParentNodeId() != null && treeNode.getParentNodeId().equals(tn.getNodeId())) {
					parentTreeNodeList(treeNodeList, parentTreeNodeList, tn);
					parentTreeNodeList.add(tn);
				}
			}
		}

		return parentTreeNodeList;
	}

	/** 
	 * 获取某个父节点下面的所有父节点 
	 * @param treeNodeList 
	 * @param subTreeNodeList
	 * @param pid 
	 * @return 
	 */
	public static CopyOnWriteArrayList<HistTreeNode> parentHistTreeNodeList(CopyOnWriteArrayList<HistTreeNode> treeNodeList,
			CopyOnWriteArrayList<HistTreeNode> parentTreeNodeList,
			HistTreeNode treeNode) {
		if (treeNodeList == null || parentTreeNodeList == null) {
			return parentTreeNodeList;
		}
		if (treeNode != null) {
			for (HistTreeNode tn : treeNodeList) {
				if (treeNode.getParentNodeId() != null && tn.getNodeId().equals(treeNode.getParentNodeId())) {
					parentHistTreeNodeList(treeNodeList, parentTreeNodeList, tn);
					parentTreeNodeList.add(tn);
				}
			}
		}

		return parentTreeNodeList;
	}

	/**
	 * 
	 *  从列表中获取treeNode
	 * <pre>
	 * 	从列表中获取treeNode
	 * </pre>
	 * 
	 * @param treeNodeList
	 * @param nodeId
	 * @return
	 */
	public static TreeNode getTreeNode(CopyOnWriteArrayList<TreeNode> treeNodeList, String nodeId) {
		TreeNode treeNode = new TreeNode();
		for (TreeNode tn : treeNodeList) {
			if (tn.getNodeId().equals(nodeId)) {
				treeNode = tn;
				break;
			}
		}
		return treeNode;
	}

	

	/**
	 * 
	 *  判读是否存在
	 * <pre>
	 * 	判读是否存在
	 * </pre>
	 * 
	 * @param refId
	 * @param newId
	 * @param treeNodeMap
	 * @return
	 */
	public static boolean isHistExists(String treeId,
			String refId,
			String newParentId,
			ConcurrentHashMap<String, CopyOnWriteArrayList<HistTreeNode>> treeNodeMap) {
		boolean isExists = false;
		CopyOnWriteArrayList<HistTreeNode> list = treeNodeMap.get(treeId);
		for (HistTreeNode node : list) {
			if (node != null && newParentId.equals(node.getNodeId())) {
				isExists = true;
				return isExists;
			}
		}
		return isExists;
	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param company
	 */
	public static void setName(List<Post> list) {
		if (list == null) {
			return;
		}
		for (Post post : list) {
			setName(post);
		}
	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param company
	 * @deprecated
	 */
	public static void setName(Company company) {
		if (company == null) {
			return;
		}
		// String nameZh = "";
		// String nameEn = "";
		// String name3 = "";
		// String name4 = "";
		//
		// String displayNameZh = "";
		// String displayNameEn = "";
		// String displayName3 = "";
		// String displayName4 = "";

		// nameZh = company.getCompanyNameZh();
		// nameEn = company.getCompanyNameEn();
		// name3 = company.getCompanyName3();
		// name4 = company.getCompanyName4();
		//
		// displayNameZh = company.getDisplayNameZh();
		// displayNameEn = company.getDisplayNameEn();
		// displayName3 = company.getDisplayName3();
		// displayName4 = company.getDisplayName4();

		// NameEntity nameEntity = new NameEntity(nameZh,nameEn,name3,name4);
		// NameEntity displayNameEntity = new NameEntity(displayNameZh,displayNameEn,displayName3,displayName4);
		// String name = nameEntity.getEntityName();
		// String displayName = displayNameEntity.getEntityName();
		// company.setCompanyName(name);
		// company.setDisplayName(displayName);
	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param company
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	public static void setName(HistCompany company) {
		if (company == null) {
			return;
		}
		String nameZh = "";
		String nameEn = "";
		String name3 = "";
		String name4 = "";

		String displayNameZh = "";
		String displayNameEn = "";
		String displayName3 = "";
		String displayName4 = "";

		nameZh = company.getCompanyNameZh();
		nameEn = company.getCompanyNameEn();
		name3 = company.getCompanyName3();
		name4 = company.getCompanyName4();

		displayNameZh = company.getDisplayNameZh();
		displayNameEn = company.getDisplayNameEn();
		displayName3 = company.getDisplayName3();
		displayName4 = company.getDisplayName4();

		NameEntity nameEntity = new NameEntity(nameZh, nameEn, name3, name4);
		NameEntity displayNameEntity = new NameEntity(displayNameZh, displayNameEn, displayName3, displayName4);

		String name = nameEntity.getEntityName();
		String displayName = displayNameEntity.getEntityName();

	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param Department
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	public static void setName(Department dept) {
		if (dept == null) {
			return;
		}
		String nameZh = "";
		String nameEn = "";
		String name3 = "";
		String name4 = "";

		String displayNameZh = "";
		String displayNameEn = "";
		String displayName3 = "";
		String displayName4 = "";

		nameZh = dept.getDeptNameZh();
		nameEn = dept.getDeptNameEn();
		name3 = dept.getDeptName3();
		name4 = dept.getDeptName4();

		displayNameZh = dept.getDisplayNameZh();
		displayNameEn = dept.getDisplayNameEn();
		displayName3 = dept.getDisplayName3();
		displayName4 = dept.getDisplayName4();

		NameEntity nameEntity = new NameEntity(nameZh, nameEn, name3, name4);
		NameEntity displayNameEntity = new NameEntity(displayNameZh, displayNameEn, displayName3, displayName4);

		String name = nameEntity.getEntityName();
		String displayName = displayNameEntity.getEntityName();

		// dept.setDeptName(name);
		// dept.setDisplayName(displayName);
	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param company
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	public static void setName(HistDepartment dept) {
		if (dept == null) {
			return;
		}
		String nameZh = "";
		String nameEn = "";
		String name3 = "";
		String name4 = "";

		String displayNameZh = "";
		String displayNameEn = "";
		String displayName3 = "";
		String displayName4 = "";

		nameZh = dept.getDeptNameZh();
		nameEn = dept.getDeptNameEn();
		name3 = dept.getDeptName3();
		name4 = dept.getDeptName4();

		displayNameZh = dept.getDisplayNameZh();
		displayNameEn = dept.getDisplayNameEn();
		displayName3 = dept.getDisplayName3();
		displayName4 = dept.getDisplayName4();

		NameEntity nameEntity = new NameEntity(nameZh, nameEn, name3, name4);
		NameEntity displayNameEntity = new NameEntity(displayNameZh, displayNameEn, displayName3, displayName4);

		String name = nameEntity.getEntityName();
		String displayName = displayNameEntity.getEntityName();

	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param Post
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	public static void setName(Post post) {
		if (post == null) {
			return;
		}
		String nameZh = "";
		String nameEn = "";
		String name3 = "";
		String name4 = "";

		String displayNameZh = "";
		String displayNameEn = "";
		String displayName3 = "";
		String displayName4 = "";

		nameZh = post.getPostNameZh();
		nameEn = post.getPostNameEn();
		name3 = post.getPostName3();
		name4 = post.getPostName4();

		displayNameZh = post.getDisplayNameZh();
		displayNameEn = post.getDisplayNameEn();
		displayName3 = post.getDisplayName3();
		displayName4 = post.getDisplayName4();

		NameEntity nameEntity = new NameEntity(nameZh, nameEn, name3, name4);
		NameEntity displayNameEntity = new NameEntity(displayNameZh, displayNameEn, displayName3, displayName4);

		String name = nameEntity.getEntityName();
		String displayName = displayNameEntity.getEntityName();

		// post.setPostName(name);
		// post.setDisplayName(displayName);
	}

	/**
	 * 
	 *  多语言设置
	 * <pre>
	 * 	多语言设置
	 * </pre>
	 * 
	 * @param HistPost
	 * @deprecated
	 */
	@SuppressWarnings("unused")
	public static void setName(HistPost post) {
		if (post == null) {
			return;
		}
		String nameZh = "";
		String nameEn = "";
		String name3 = "";
		String name4 = "";

		String displayNameZh = "";
		String displayNameEn = "";
		String displayName3 = "";
		String displayName4 = "";

		nameZh = post.getPostNameZh();
		nameEn = post.getPostNameEn();
		name3 = post.getPostName3();
		name4 = post.getPostName4();

		displayNameZh = post.getDisplayNameZh();
		displayNameEn = post.getDisplayNameEn();
		displayName3 = post.getDisplayName3();
		displayName4 = post.getDisplayName4();

		NameEntity nameEntity = new NameEntity(nameZh, nameEn, name3, name4);
		NameEntity displayNameEntity = new NameEntity(displayNameZh, displayNameEn, displayName3, displayName4);

		String name = nameEntity.getEntityName();
		String displayName = displayNameEntity.getEntityName();

	}

	/**
	 * 
	 *  根据前端json格式进行转换
	 * <pre>
	 * 	根据前端json格式进行转换
	 * </pre>
	 * 
	 * @param list
	 */
	public static void setParent(List<TreeVO> list) {
		if (list == null) {
			return;
		}
		for (TreeVO treeVO : list) {
			if (treeVO != null && treeVO.getParent() != null && "0".equals(treeVO.getParent())) {
				treeVO.setParent(null);
			}
		}
	}

	/**
	 * 
	 * <pre>
	 * 	转换为boolean
	 * </pre>
	 * 
	 * @param name
	 * @return
	 */
	public static Boolean toBoolean(String name, boolean def) {
		String result = name;
		if (result != null) {
			result = result.trim().toLowerCase();
			if ((result.equals("1")) || (result.equals("true")))
				return Boolean.TRUE;
			if ((result.equals("0")) || (result.equals("false"))) {
				return Boolean.FALSE;
			}
		}
		return def;
	}

	/**
	 * 
	 *  引用节点转换
	 * <pre>
	 * 	引用节点转换
	 * </pre>
	 * 
	 * @param company
	 * @return
	 */
	public static Company toCompany(Company refCompany) {
		Company company = new Company();
		if (refCompany == null) {
			return company;
		}
		company.setCompanyNameZh(refCompany.getCompanyNameZh());
		company.setCompanyNameEn(refCompany.getCompanyNameEn());
		company.setCompanyName3(refCompany.getCompanyName3());
		company.setCompanyName4(refCompany.getCompanyName4());

		company.setDisplayNameZh(refCompany.getDisplayNameZh());
		company.setDisplayNameEn(refCompany.getDisplayNameEn());
		company.setDisplayName3(refCompany.getDisplayName3());
		company.setDisplayName4(refCompany.getDisplayName4());

		company.setCompanyIcon(refCompany.getCompanyIcon());
		company.setIsDeleted(refCompany.getIsDeleted());
		company.setIsActive(refCompany.getIsActive());
		company.setAccountingEntity(refCompany.isAccountingEntity());
		return company;
	}

	/**
	 * 
	 *  引用节点转换
	 * <pre>
	 * 	引用节点转换
	 * </pre>
	 * 
	 * @param company
	 * @return
	 */
	public static Department toDept(Company refCompany) {
		Department dept = new Department();
		if (refCompany == null) {
			return dept;
		}
		dept.setDeptNameZh(refCompany.getCompanyNameZh());
		dept.setDeptNameEn(refCompany.getCompanyNameEn());
		dept.setDeptName3(refCompany.getCompanyName3());
		dept.setDeptName4(refCompany.getCompanyName4());

		dept.setDisplayNameZh(refCompany.getDisplayNameZh());
		dept.setDisplayNameEn(refCompany.getDisplayNameEn());
		dept.setDisplayName3(refCompany.getDisplayName3());
		dept.setDisplayName4(refCompany.getDisplayName4());

		dept.setDeptIcon(refCompany.getCompanyIcon());
		dept.setIsDeleted(refCompany.getIsDeleted());
		dept.setIsActive(refCompany.getIsActive());
		dept.setAccountingEntity(refCompany.isAccountingEntity());
		return dept;
	}

	/**
	 * 
	 *  引用节点转换
	 * <pre>
	 * 	引用节点转换
	 * </pre>
	 * 
	 * @param company
	 * @return
	 */
	public static Department toDept(Post refPost) {
		Department dept = new Department();
		if (refPost == null) {
			return dept;
		}
		dept.setDeptNameZh(refPost.getPostNameZh());
		dept.setDeptNameEn(refPost.getPostNameEn());
		dept.setDeptName3(refPost.getPostName3());
		dept.setDeptName4(refPost.getPostName4());

		dept.setDisplayNameZh(refPost.getDisplayNameZh());
		dept.setDisplayNameEn(refPost.getDisplayNameEn());
		dept.setDisplayName3(refPost.getDisplayName3());
		dept.setDisplayName4(refPost.getDisplayName4());

		dept.setDeptIcon(refPost.getPostIcon());
		dept.setIsDeleted(false);
		dept.setIsActive(refPost.getIsActive());
		dept.setAccountingEntity(true);
		return dept;
	}

	/**
	 * 
	 *  引用节点转换
	 * <pre>
	 * 	引用节点转换
	 * </pre>
	 * 
	 * @param company
	 * @return
	 */
	public static Department toDept(Department refDept) {
		Department dept = new Department();
		if (refDept == null) {
			return dept;
		}
		dept.setDeptNameZh(refDept.getDeptNameZh());
		dept.setDeptNameEn(refDept.getDeptNameEn());
		dept.setDeptName3(refDept.getDeptName3());
		dept.setDeptName4(refDept.getDeptName4());

		dept.setDisplayNameZh(refDept.getDisplayNameZh());
		dept.setDisplayNameEn(refDept.getDisplayNameEn());
		dept.setDisplayName3(refDept.getDisplayName3());
		dept.setDisplayName4(refDept.getDisplayName4());

		dept.setDeptIcon(refDept.getDeptIcon());
		dept.setIsDeleted(refDept.getIsDeleted());
		dept.setIsActive(refDept.getIsActive());
		dept.setAccountingEntity(refDept.isAccountingEntity());
		return dept;
	}

	/**
	 * 
	 *  转换为json
	 * <pre>
	 * 	转换为json
	 * </pre>
	 * 
	 * @param target
	 * @param datePattern
	 * @return
	 */
	public static String toJson(Object target) {
		return JsonUtils.toJson(target, JsonUtils.DEFAULT_DATE_PATTERN_COMMON);
	}

	/**
	 * 
	 * splitList 查分列表
	 * @参数：
	 * @param list
	 *           要拆分的列表
	 * @param pageSize每个子列表条数
	 * @return
	 * @返回类型 List[]
	 * @exception
	 */
	@SuppressWarnings("rawtypes")
	public static List[] splitList(List list, int pageSize) {
		int total = list.size();
		// 总页数
		int pageCount = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		List[] result = new List[pageCount];
		for (int i = 0; i < pageCount; i++) {
			int start = i * pageSize;
			// 最后一条可能超出总数
			int end = start + pageSize > total ? total : start + pageSize;
			List subList = list.subList(start, end);
			result[i] = subList;
		}
		return result;
	}

	/**
	 * 
	 * splitList 查分列表
	 * @参数：
	 * @param list
	 *           要拆分的列表
	 * @param pageSize每个子列表条数
	 * @param pageNum 页数
	 * @return
	 * @返回类型 List[]
	 * @exception
	 */
	@SuppressWarnings("rawtypes")
	public static List splitList(List list, int pageSize, int pageNum) {
		List subList = new ArrayList();
		if (list == null) {
			return subList;
		}
		List[] result = splitList(list, pageSize);

		if (result.length == 0) {
			return subList;
		}

		if (pageNum > 0 && pageNum <= result.length) {
			subList = result[pageNum - 1];
		}
		if (pageNum <= 0) {
			subList = result[0];
		}
		if (pageNum >= result.length) {
			subList = result[result.length - 1];
		}
		return subList;
	}

	/**
	 * 
	 *  转换为map
	 * <pre>
	 * 	转换为map
	 * </pre>
	 * 
	 * @param result
	 * @param list
	 */
	public static void convertMap(LinkedHashMap<String, String> result, List<Tree> list) {
		if (list == null || result == null) {
			return;
		}
		for (Tree tree : list) {
			result.put(tree.getTreeId(), tree.getTreeName());
		}
	}

	/**
	 * 
	 * getNowMonth 获取当前月
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String getNowMonth() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
		// 获取当前月第一天：
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 0);
		String first = format.format(c.getTime());
		return first;
	}

	/**
	 * 
	 * dateString 日期串yyyyMM
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateString() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return simpleDateFormat.format(new Date());
	}

	/**
	 * 
	 * 获取最后一天
	 * <pre>
	 * 获取最后一天
	 * </pre>
	 * 
	 * @param datadate
	 * @return
	 * @throws Exception
	 */
	public static String getLastDay(String strDate) {
		Date date = null;
		String day_last = null;

		if (strDate == null || strDate.length() < 6) {
			return "";
		}
		String tmp = strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-01";

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = format.parse(tmp);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 创建日历
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1); // 加一个月
		calendar.set(Calendar.DATE, 1); // 设置为该月第一天
		calendar.add(Calendar.DATE, -1); // 再减一天即为上个月最后一天
		day_last = format.format(calendar.getTime());
		return day_last;
	}

	/**
	 * 
	 * dateString 日期串yyyyMM
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String dateString(Date date) {
		String str = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
			str = simpleDateFormat.format(date);
		} catch (Exception e) {

		}

		return str;
	}

	/**
	 * 
	 * dateString 日期串yyyyMM
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String toDateString(Date date) {
		String str = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			str = simpleDateFormat.format(date);
		} catch (Exception e) {

		}

		return str;
	}

	/**
	 * 
	 * dateString 日期串yyyyMM
	 * 
	 * @desc： (这里描述这个方法适用条件 – 可选)
	 * @参数：
	 * @return
	 * @返回类型 String
	 * @exception
	 * @since 1.0.0
	 */
	public static String toDateStringYYYMMDD(Date date) {
		String str = "";
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			str = simpleDateFormat.format(date);
		} catch (Exception e) {

		}

		return str;
	}
	
	/** 
	 * 获取某个节点的所有父节点 
	 * @param treeNodeList 
	 * @param subTreeNodeList
	 * @param pid 
	 * @return 
	 */
	public static void setFullName(CopyOnWriteArrayList<TreeNode> parentTreeNodeList, TreeNode treeNode,ConcurrentHashMap<String, Company> companyMap,ConcurrentHashMap<String, Department> deptMap,ConcurrentHashMap<String, Post> postMap,ConcurrentHashMap<String, CopyOnWriteArrayList<TreeNode>> subListMap) {
		if (treeNode == null) {
			return;
		}
		if (parentTreeNodeList == null || parentTreeNodeList.size() == 0) {
			treeNode.setFullName(treeNode.getDisplayName());
			setSelfProperty(treeNode);
			return;
		}
		int nodeType = treeNode.getNodeType();
		StringBuilder fullName = new StringBuilder();
		//StringBuilder deptFullName = new StringBuilder();							// 实时计算出来  	部门全名称
		StringBuilder deptFullNameZh = new StringBuilder();						// 实时计算出来   
		StringBuilder deptFullNameEn = new StringBuilder();						// 实时计算出来
		StringBuilder deptFullName3 = new StringBuilder();						// 实时计算出来
		StringBuilder deptFullName4 = new StringBuilder();						// 实时计算出来
		StringBuilder nodeNameIndent = new StringBuilder();						// 实时计算出来   缩进名称
		StringBuilder nodeNameIndentZh = new StringBuilder(); 				// 实时计算出来
		StringBuilder nodeNameIndentEn = new StringBuilder(); 				// 实时计算出来
		StringBuilder nodeNameIndent3 = new StringBuilder(); 					// 实时计算出来
		StringBuilder nodeNameIndent4 = new StringBuilder(); 					// 实时计算出来
		StringBuilder nodeDisplayNameIndent = new StringBuilder();		// 实时计算出来			 缩进显示名称
		StringBuilder nodeDisplayNameIndentZh = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndentEn = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndent3 = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndent4 = new StringBuilder(); 	// 实时计算出来
		boolean isLeafDept = true;																		// 实时计算出来    部门时计算
		
		for (TreeNode parentNode : parentTreeNodeList) {
			fullName.append(parentNode.getDisplayName()).append(PATH_SPLIT);
			if(nodeType == ITreeNode.NodeType.DEPARTMENT){
				deptFullNameZh.append(parentNode.getDisplayNameZh()).append(PATH_SPLIT);
				deptFullNameEn.append(parentNode.getDisplayNameEn()).append(PATH_SPLIT);
				deptFullName3.append(parentNode.getDisplayName3()).append(PATH_SPLIT);
				deptFullName4.append(parentNode.getDisplayName4()).append(PATH_SPLIT);
				
				nodeNameIndent.append(SPACE).append(SPLIT);
			}
		}

		fullName.append(treeNode.getDisplayName());
		deptFullNameZh.append(treeNode.getDisplayNameZh());
		deptFullNameEn.append(treeNode.getDisplayNameEn());
		deptFullName3.append(treeNode.getDisplayName3());
		deptFullName4.append(treeNode.getDisplayName4());
		treeNode.setFullName(fullName.toString());
		if(nodeType == ITreeNode.NodeType.DEPARTMENT){
			Department dept = null;
			if(deptMap != null && deptMap.containsKey(treeNode.getRefEntityId())){
				dept = deptMap.get(treeNode.getRefEntityId());
			}
			
			if(dept != null){
				String temp = nodeNameIndent.toString();
				nodeNameIndent.append(dept.getDeptName());
				nodeNameIndentZh.append(temp).append(dept.getDeptNameZh());
				nodeNameIndentEn.append(temp).append(dept.getDeptNameEn());
				nodeNameIndent3.append(temp).append(dept.getDeptName3());
				nodeNameIndent4.append(temp).append(dept.getDeptName4());
				
				nodeDisplayNameIndent.append(temp).append(treeNode.getDisplayName());
				nodeDisplayNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeDisplayNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeDisplayNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeDisplayNameIndent4.append(temp).append(treeNode.getDisplayName4());
			}else{
				String temp = nodeNameIndent.toString();
				nodeNameIndent.append(treeNode.getDisplayName());
				nodeNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeNameIndent4.append(temp).append(treeNode.getDisplayName4());
				
				nodeDisplayNameIndent.append(temp).append(treeNode.getDisplayName());
				nodeDisplayNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeDisplayNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeDisplayNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeDisplayNameIndent4.append(temp).append(treeNode.getDisplayName4());
			}
			
			//判断 isLeafDept
			if(subListMap != null){
				CopyOnWriteArrayList<TreeNode> subList = subListMap.get(treeNode.getTreeId()+ContextListenerService.CacheKey.SPLIT+treeNode.getNodeId());
				if(subList != null){
					for(TreeNode sub : subList){
						if(sub != null && (sub.getNodeType() == ITreeNode.NodeType.COMPANY || sub.getNodeType() == ITreeNode.NodeType.DEPARTMENT)){
							isLeafDept = false;
							break;
						}
					}
				}
			}
			
			treeNode.setDeptFullName(treeNode.getFullName());
			treeNode.setDeptFullNameZh(deptFullNameZh.toString());
			treeNode.setDeptFullNameEn(deptFullNameEn.toString());
			treeNode.setDeptFullName3(deptFullName3.toString());
			treeNode.setDeptFullName4(deptFullName4.toString());
			treeNode.setNodeNameIndent(nodeNameIndent.toString());
			treeNode.setNodeNameIndentZh(nodeNameIndentZh.toString());
			treeNode.setNodeNameIndentEn(nodeNameIndentEn.toString());
			treeNode.setNodeNameIndent3(nodeNameIndent3.toString());
			treeNode.setNodeNameIndent4(nodeNameIndent4.toString());
			treeNode.setNodeDisplayNameIndent(nodeDisplayNameIndent.toString());
			treeNode.setNodeDisplayNameIndentZh(nodeDisplayNameIndentZh.toString());
			treeNode.setNodeDisplayNameIndentEn(nodeDisplayNameIndentEn.toString());
			treeNode.setNodeDisplayNameIndent3(nodeDisplayNameIndent3.toString());
			treeNode.setNodeDisplayNameIndent4(nodeDisplayNameIndent4.toString());
			treeNode.setLeafDept(isLeafDept);
		}
	}
	
	/** 
	 * 获取某个节点的所有父节点 
	 * @param treeNodeList 
	 * @param subTreeNodeList
	 * @param pid 
	 * @return 
	 */
	public static void setHistFullName(CopyOnWriteArrayList<HistTreeNode> parentTreeNodeList, HistTreeNode treeNode,ConcurrentHashMap<String, HistCompany> companyMap,ConcurrentHashMap<String, HistDepartment> deptMap,ConcurrentHashMap<String, HistPost> postMap,ConcurrentHashMap<String, CopyOnWriteArrayList<HistTreeNode>> subListMap,String versionId) {
		if (treeNode == null) {
			return;
		}
		if (parentTreeNodeList == null || parentTreeNodeList.size() == 0) {
			treeNode.setFullName(treeNode.getDisplayName());
			setSelfProperty(treeNode);
			return;
		}
		int nodeType = treeNode.getNodeType();
		StringBuilder fullName = new StringBuilder();
		//StringBuilder deptFullName = new StringBuilder();						// 实时计算出来  部门全名称
		StringBuilder deptFullNameZh = new StringBuilder();						// 实时计算出来   
		StringBuilder deptFullNameEn = new StringBuilder();						// 实时计算出来
		StringBuilder deptFullName3 = new StringBuilder();						// 实时计算出来
		StringBuilder deptFullName4 = new StringBuilder();						// 实时计算出来
		StringBuilder nodeNameIndent = new StringBuilder();						// 实时计算出来   缩进名称
		StringBuilder nodeNameIndentZh = new StringBuilder(); 				// 实时计算出来
		StringBuilder nodeNameIndentEn = new StringBuilder(); 				// 实时计算出来
		StringBuilder nodeNameIndent3 = new StringBuilder(); 					// 实时计算出来
		StringBuilder nodeNameIndent4 = new StringBuilder(); 					// 实时计算出来
		StringBuilder nodeDisplayNameIndent = new StringBuilder();		// 实时计算出来   缩进显示名称
		StringBuilder nodeDisplayNameIndentZh = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndentEn = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndent3 = new StringBuilder(); 	// 实时计算出来
		StringBuilder nodeDisplayNameIndent4 = new StringBuilder(); 	// 实时计算出来
		boolean isLeafDept = true;																		// 实时计算出来   部门时计算
		
		for (HistTreeNode parentNode : parentTreeNodeList) {
			fullName.append(parentNode.getDisplayName()).append(PATH_SPLIT);
			if(nodeType == ITreeNode.NodeType.DEPARTMENT){
				deptFullNameZh.append(parentNode.getDisplayNameZh()).append(PATH_SPLIT);
				deptFullNameEn.append(parentNode.getDisplayNameEn()).append(PATH_SPLIT);
				deptFullName3.append(parentNode.getDisplayName3()).append(PATH_SPLIT);
				deptFullName4.append(parentNode.getDisplayName4()).append(PATH_SPLIT);
				
				nodeNameIndent.append(SPACE).append(SPLIT);
			}
		}

		fullName.append(treeNode.getDisplayName());
		deptFullNameZh.append(treeNode.getDisplayNameZh());
		deptFullNameEn.append(treeNode.getDisplayNameEn());
		deptFullName3.append(treeNode.getDisplayName3());
		deptFullName4.append(treeNode.getDisplayName4());
		treeNode.setFullName(fullName.toString());
		if(nodeType == ITreeNode.NodeType.DEPARTMENT){
			HistDepartment dept = null;
			if(deptMap != null && deptMap.containsKey(treeNode.getRefEntityId())){
				dept = deptMap.get(treeNode.getRefEntityId());
			}
			
			if(dept != null){
				String temp = nodeNameIndent.toString();
				nodeNameIndent.append(dept.getDeptName());
				nodeNameIndentZh.append(temp).append(dept.getDeptNameZh());
				nodeNameIndentEn.append(temp).append(dept.getDeptNameEn());
				nodeNameIndent3.append(temp).append(dept.getDeptName3());
				nodeNameIndent4.append(temp).append(dept.getDeptName4());
				
				nodeDisplayNameIndent.append(temp).append(treeNode.getDisplayName());
				nodeDisplayNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeDisplayNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeDisplayNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeDisplayNameIndent4.append(temp).append(treeNode.getDisplayName4());
			}else{
				String temp = nodeNameIndent.toString();
				nodeNameIndent.append(treeNode.getDisplayName());
				nodeNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeNameIndent4.append(temp).append(treeNode.getDisplayName4());
				
				nodeDisplayNameIndent.append(temp).append(treeNode.getDisplayName());
				nodeDisplayNameIndentZh.append(temp).append(treeNode.getDisplayNameZh());
				nodeDisplayNameIndentEn.append(temp).append(treeNode.getDisplayNameEn());
				nodeDisplayNameIndent3.append(temp).append(treeNode.getDisplayName3());
				nodeDisplayNameIndent4.append(temp).append(treeNode.getDisplayName4());
			}
			
			//判断 isLeafDept
			if(subListMap != null){
				CopyOnWriteArrayList<HistTreeNode> subList = subListMap.get(treeNode.getTreeId()+ContextListenerService.CacheKey.SPLIT+treeNode.getNodeId());
				if(subList != null){
					for(HistTreeNode sub : subList){
						if(sub != null && (sub.getNodeType() == ITreeNode.NodeType.COMPANY || sub.getNodeType() == ITreeNode.NodeType.DEPARTMENT)){
							isLeafDept = false;
							break;
						}
					}
				}
			}
			
			treeNode.setDeptFullName(treeNode.getFullName());
			treeNode.setDeptFullNameZh(deptFullNameZh.toString());
			treeNode.setDeptFullNameEn(deptFullNameEn.toString());
			treeNode.setDeptFullName3(deptFullName3.toString());
			treeNode.setDeptFullName4(deptFullName4.toString());
			treeNode.setNodeNameIndent(nodeNameIndent.toString());
			treeNode.setNodeNameIndentZh(nodeNameIndentZh.toString());
			treeNode.setNodeNameIndentEn(nodeNameIndentEn.toString());
			treeNode.setNodeNameIndent3(nodeNameIndent3.toString());
			treeNode.setNodeNameIndent4(nodeNameIndent4.toString());
			treeNode.setNodeDisplayNameIndent(nodeDisplayNameIndent.toString());
			treeNode.setNodeDisplayNameIndentZh(nodeDisplayNameIndentZh.toString());
			treeNode.setNodeDisplayNameIndentEn(nodeDisplayNameIndentEn.toString());
			treeNode.setNodeDisplayNameIndent3(nodeDisplayNameIndent3.toString());
			treeNode.setNodeDisplayNameIndent4(nodeDisplayNameIndent4.toString());
			treeNode.setLeafDept(isLeafDept);
		}
	}
	
	/**
	 * 
	 *  计算动态属性
	 * <pre>
	 * 	计算动态属性
	 * </pre>
	 * 
	 * @param treeNode
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param subListMap
	 */
	private static void setSelfProperty(TreeNode treeNode){
		if(treeNode == null || treeNode.getNodeType() != ITreeNode.NodeType.DEPARTMENT){
			return;
		}
		
		String deptFullName = "";			// 实时计算出来  	部门全名称
		String deptFullNameZh = "";		// 实时计算出来   
		String deptFullNameEn = "";		// 实时计算出来
		String deptFullName3 = "";		// 实时计算出来
		String deptFullName4 = "";		// 实时计算出来
		String nodeNameIndent = "";		// 实时计算出来   缩进名称
		String nodeNameIndentZh = ""; // 实时计算出来
		String nodeNameIndentEn = ""; // 实时计算出来
		String nodeNameIndent3 = ""; 	// 实时计算出来
		String nodeNameIndent4 = ""; 	// 实时计算出来
		String nodeDisplayNameIndent = "";		// 实时计算出来			 缩进显示名称
		String nodeDisplayNameIndentZh = ""; 	// 实时计算出来
		String nodeDisplayNameIndentEn = ""; 	// 实时计算出来
		String nodeDisplayNameIndent3 = ""; 	// 实时计算出来
		String nodeDisplayNameIndent4 = ""; 	// 实时计算出来
		boolean isLeafDept;										// 实时计算出来    部门时计算

		deptFullName = treeNode.getDisplayName();			// 实时计算出来  	部门全名称
		deptFullNameZh = treeNode.getDisplayNameZh();		// 实时计算出来   
		deptFullNameEn = treeNode.getDisplayNameEn();		// 实时计算出来
		deptFullName3 = treeNode.getDisplayName3();	// 实时计算出来
		deptFullName4 = treeNode.getDisplayName4();		// 实时计算出来
		nodeNameIndent = deptFullName;		// 实时计算出来   缩进名称
		nodeNameIndentZh = deptFullNameZh; // 实时计算出来
		nodeNameIndentEn = deptFullNameEn; // 实时计算出来
		nodeNameIndent3 = deptFullName3; 	// 实时计算出来
		nodeNameIndent4 = deptFullName4; 	// 实时计算出来
		nodeDisplayNameIndent = deptFullName;		// 实时计算出来			 缩进显示名称
		nodeDisplayNameIndentZh = treeNode.getDisplayNameZh(); 	// 实时计算出来
		nodeDisplayNameIndentEn = treeNode.getDisplayNameEn(); 	// 实时计算出来
		nodeDisplayNameIndent3 = treeNode.getDisplayName3(); 	// 实时计算出来
		nodeDisplayNameIndent4 = treeNode.getDisplayName4(); 	// 实时计算出来
		isLeafDept = true;										// 实时计算出来    部门时计算
		
		treeNode.setDeptFullName(CommonUtil.trim(deptFullName));
		treeNode.setDeptFullNameZh(CommonUtil.trim(deptFullNameZh));
		treeNode.setDeptFullNameEn(CommonUtil.trim(deptFullNameEn));
		treeNode.setDeptFullName3(CommonUtil.trim(deptFullName3));
		treeNode.setDeptFullName4(CommonUtil.trim(deptFullName4));
		treeNode.setNodeNameIndent(CommonUtil.trim(nodeNameIndent));
		treeNode.setNodeNameIndentZh(CommonUtil.trim(nodeNameIndentZh));
		treeNode.setNodeNameIndentEn(CommonUtil.trim(nodeNameIndentEn));
		treeNode.setNodeNameIndent3(CommonUtil.trim(nodeNameIndent3));
		treeNode.setNodeNameIndent4(CommonUtil.trim(nodeNameIndent4));
		treeNode.setNodeDisplayNameIndent(CommonUtil.trim(nodeDisplayNameIndent));
		treeNode.setNodeDisplayNameIndentZh(CommonUtil.trim(nodeDisplayNameIndentZh));
		treeNode.setNodeDisplayNameIndentEn(CommonUtil.trim(nodeDisplayNameIndentEn));
		treeNode.setNodeDisplayNameIndent3(CommonUtil.trim(nodeDisplayNameIndent3));
		treeNode.setNodeDisplayNameIndent4(CommonUtil.trim(nodeDisplayNameIndent4));
		treeNode.setLeafDept(isLeafDept);
	}

	/**
	 * 
	 *  计算动态属性
	 * <pre>
	 * 	计算动态属性
	 * </pre>
	 * 
	 * @param treeNode
	 * @param companyMap
	 * @param deptMap
	 * @param postMap
	 * @param subListMap
	 */
	private static void setSelfProperty(HistTreeNode treeNode){
		if(treeNode == null || treeNode.getNodeType() != ITreeNode.NodeType.DEPARTMENT){
			return;
		}
		
		String deptFullName = "";			// 实时计算出来  	部门全名称
		String deptFullNameZh = "";		// 实时计算出来   
		String deptFullNameEn = "";		// 实时计算出来
		String deptFullName3 = "";		// 实时计算出来
		String deptFullName4 = "";		// 实时计算出来
		String nodeNameIndent = "";		// 实时计算出来   缩进名称
		String nodeNameIndentZh = ""; // 实时计算出来
		String nodeNameIndentEn = ""; // 实时计算出来
		String nodeNameIndent3 = ""; 	// 实时计算出来
		String nodeNameIndent4 = ""; 	// 实时计算出来
		String nodeDisplayNameIndent = "";		// 实时计算出来			 缩进显示名称
		String nodeDisplayNameIndentZh = ""; 	// 实时计算出来
		String nodeDisplayNameIndentEn = ""; 	// 实时计算出来
		String nodeDisplayNameIndent3 = ""; 	// 实时计算出来
		String nodeDisplayNameIndent4 = ""; 	// 实时计算出来
		boolean isLeafDept;										// 实时计算出来    部门时计算

		deptFullName = treeNode.getDisplayName();			// 实时计算出来  	部门全名称
		deptFullNameZh = treeNode.getDisplayNameZh();		// 实时计算出来   
		deptFullNameEn = treeNode.getDisplayNameEn();		// 实时计算出来
		deptFullName3 = treeNode.getDisplayName3();	// 实时计算出来
		deptFullName4 = treeNode.getDisplayName4();		// 实时计算出来
		nodeNameIndent = deptFullName;		// 实时计算出来   缩进名称
		nodeNameIndentZh = deptFullNameZh; // 实时计算出来
		nodeNameIndentEn = deptFullNameEn; // 实时计算出来
		nodeNameIndent3 = deptFullName3; 	// 实时计算出来
		nodeNameIndent4 = deptFullName4; 	// 实时计算出来
		nodeDisplayNameIndent = deptFullName;		// 实时计算出来			 缩进显示名称
		nodeDisplayNameIndentZh = treeNode.getDisplayNameZh(); 	// 实时计算出来
		nodeDisplayNameIndentEn = treeNode.getDisplayNameEn(); 	// 实时计算出来
		nodeDisplayNameIndent3 = treeNode.getDisplayName3(); 	// 实时计算出来
		nodeDisplayNameIndent4 = treeNode.getDisplayName4(); 	// 实时计算出来
		isLeafDept = true;										// 实时计算出来    部门时计算
		
		treeNode.setDeptFullName(CommonUtil.trim(deptFullName));
		treeNode.setDeptFullNameZh(CommonUtil.trim(deptFullNameZh));
		treeNode.setDeptFullNameEn(CommonUtil.trim(deptFullNameEn));
		treeNode.setDeptFullName3(CommonUtil.trim(deptFullName3));
		treeNode.setDeptFullName4(CommonUtil.trim(deptFullName4));
		treeNode.setNodeNameIndent(CommonUtil.trim(nodeNameIndent));
		treeNode.setNodeNameIndentZh(CommonUtil.trim(nodeNameIndentZh));
		treeNode.setNodeNameIndentEn(CommonUtil.trim(nodeNameIndentEn));
		treeNode.setNodeNameIndent3(CommonUtil.trim(nodeNameIndent3));
		treeNode.setNodeNameIndent4(CommonUtil.trim(nodeNameIndent4));
		treeNode.setNodeDisplayNameIndent(CommonUtil.trim(nodeDisplayNameIndent));
		treeNode.setNodeDisplayNameIndentZh(CommonUtil.trim(nodeDisplayNameIndentZh));
		treeNode.setNodeDisplayNameIndentEn(CommonUtil.trim(nodeDisplayNameIndentEn));
		treeNode.setNodeDisplayNameIndent3(CommonUtil.trim(nodeDisplayNameIndent3));
		treeNode.setNodeDisplayNameIndent4(CommonUtil.trim(nodeDisplayNameIndent4));
		treeNode.setLeafDept(isLeafDept);
	}
	
	/**
	 * 
	 *  生成fullorder
	 * <pre>
	 * 	生成fullorder
	 * </pre>
	 * 根为1 ，则其直接下级为1001，1002，1003，而1001的下级为1001001，1001002
	 * @param level
	 * @param count
	 * @return
	 */
	public static String getFullOrder(int level, int count) {
		StringBuilder sb = new StringBuilder();
		if (level == 0) {
			sb.append("1");
		} else {
			sb.append("1");
			while (level-- > 0) {
				sb.append("001");
			}
		}
		String strCount = String.valueOf(count);
		String no = sb.toString();
		if (no.length() >= strCount.length()) {
			no = no.substring(0, no.length() - strCount.length());
			no += strCount;
		}
		return no;
	}

	public static void main(String[] arg) {
		System.out.println(getFullOrder(1, 1221));
	}
}
