package com.supporter.prj.eip.code_share.code.dao;

import org.springframework.stereotype.Repository;
import com.supporter.prj.eip.code_share.code.entity.CodeContractNo;
import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.eip_service.security.entity.UserProfile;

/**
 * @Title: 编号表.合同编号表
 * @Description: CS_CODE_CONTRACT_NO.
 * @author Administrator
 * @date 2019-07-17 16:46:44
 * @version V1.0
 *
 */
@Repository
public class CodeContractNoDao extends MainDaoSupport<CodeContractNo, String> {
	/**
	 * 分页查询
	 * @param user 用户
	 * @param jqGrid 表格
	 * @param codeContractNo 合同编号
	 * @return List<CodeContractNo>
	 */
	public List<CodeContractNo> findPage(UserProfile user, JqGrid jqGrid, CodeContractNo codeContractNo) {
		if (codeContractNo != null) {
			if (codeContractNo.isCodeLocked() != null) {
				String hql = "codeLocked=?";
				jqGrid.addHqlFilter(hql, codeContractNo.isCodeLocked());
			}
			if (codeContractNo.getCodeType() > 0) {
				String hql = "codeType=?";
				jqGrid.addHqlFilter(hql, codeContractNo.getCodeType());
			}
			if (StringUtils.isNotBlank(codeContractNo.getPrjLib())) {
				String hql = "prjLib=?";
				jqGrid.addHqlFilter(hql, codeContractNo.getPrjLib());
			}
			if (StringUtils.isNotBlank(codeContractNo.getContractId())) {
				String hql = "contractId=?";
				jqGrid.addHqlFilter(hql, codeContractNo.getContractId());
			}
			// 搜索关键字
			String keyword = codeContractNo.getKeyword();
			if (StringUtils.isNotBlank(keyword)) {
				String likeKeyword = "%" + keyword + "%";
				String hql = "shareCode like ?";
				jqGrid.addHqlFilter(hql, likeKeyword);
			}
			
		}
		jqGrid.addSortPropertyDesc("displayOrder");
		return this.retrievePage(jqGrid);
	}
	
	/**
	 * 获取未锁定的编号列表，正排序
	 * @param saleContractIdOrNo 销售合同ID或编号
	 * @param prjLib 项目库
	 * @param codeType 编码类型
	 * @return List<CodeContractNo>
	 */
	public List<CodeContractNo> getUnLockedNoList(String prjLib, String saleContractIdOrNo, int codeType) {
		String hql = "from " + CodeContractNo.class.getName()
				+ " where codeType=? and codeLocked=false and prjLib=? and (contractId=? or contractNo=?) order by displayOrder";
		return find(hql, codeType, prjLib, saleContractIdOrNo, saleContractIdOrNo);
	}
	/**
	 * 获取未锁定的编号列表(附属合同)，正排序
	 * @param saleContractIdOrNo 销售合同ID或编号
	 * @param prjLib 项目库
	 * @param parentCode 父对象的编号，用于对附属合同等类型的下级对象进行编号
	 * @param codeType 编码类型
	 * @return List<CodeContractNo>
	 */
	public List<CodeContractNo> getUnLockedNoList(String prjLib, String saleContractIdOrNo,
			String parentCode, int codeType) {
		String hql = "from " + CodeContractNo.class.getName()
				+ " where codeType=? and codeLocked=false and prjLib=? and parentCode=? and (contractId=? or contractNo=?) order by displayOrder";
		return find(hql, codeType, prjLib, parentCode, saleContractIdOrNo, saleContractIdOrNo);
	}
	/**
	 * 获取待释放的编号对象
	 * @param saleContractIdOrNo 销售合同ID或编号
	 * @param codeType 编码类型
	 * @param prjLib 项目库
	 * @param code 待释放的编号
	 * @return CodeContractNo
	 */
	public CodeContractNo getCodeContractNoFair(String prjLib, String saleContractIdOrNo, int codeType, String code) {
		String hql = "from " + CodeContractNo.class.getName()
				+ " where codeType=? and codeLocked=true and prjLib=? and shareCode=? and (contractId=? or contractNo=?)";
		List <CodeContractNo> list = find(hql, codeType, prjLib, code, saleContractIdOrNo, saleContractIdOrNo);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}
	/**
	 * 获取待释放的编号对象
	 * @param saleContractIdOrNo 销售合同ID或编号
	 * @param parentCode 父对象的编号，用于对附属合同等类型的下级对象进行编号
	 * @param codeType 编码类型
	 * @param prjLib 项目库
	 * @param code 待释放的编号
	 * @return CodeContractNo
	 */
	public CodeContractNo getCodeContractNoFair(String prjLib, String saleContractIdOrNo, String parentCode, int codeType, String code) {
		String hql = "from " + CodeContractNo.class.getName()
				+ " where codeType=? and codeLocked=true and prjLib=? and parentCode=? and shareCode=? and (contractId=? or contractNo=?)";
		List <CodeContractNo> list = find(hql, codeType, prjLib, parentCode, code, saleContractIdOrNo, saleContractIdOrNo);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}
	
	/**
	 * 获取指定类型编码的最大顺序号
	 * @param codeType 编码类型
	 * @param prjLib 项目库
	 * @param saleContractIdOrNo  销售合同ID或编号
	 * @return int
	 */
	public int getMaxCodeDisplayOrder(String prjLib, String saleContractIdOrNo, int codeType) {
		String hql = "select max(displayOrder) as maxOrder from " + CodeContractNo.class.getName()
				+ " where codeType=? and prjLib=? and (contractId=? or contractNo=?)";
		Object obj = this.retrieveFirst(hql, codeType, prjLib, saleContractIdOrNo, saleContractIdOrNo);
		if (obj == null) {
			return 0;
		} else {
			return (Integer) obj;
		}
	}
	/**
	 * 获取指定类型编码的最大顺序号(附属合同之类的子编码)
	 * @param codeType 编码类型
	 * @param prjLib 项目库
	 * @param saleContractIdOrNo  销售合同ID或编号
	 * @param parentCode 父编码
	 * @return int
	 */
	public int getMaxCodeDisplayOrder(String prjLib, String saleContractIdOrNo, String parentCode, int codeType) {
		String hql = "select max(displayOrder) as maxOrder from " + CodeContractNo.class.getName()
				+ " where codeType=? and prjLib=? and parentCode=? and (contractId=? or contractNo=?)";
		Object obj = retrieveFirst(hql, codeType, prjLib, parentCode, saleContractIdOrNo, saleContractIdOrNo);
		if (obj == null) {
			return 0;
		} else {
			return (Integer) obj;
		}
	}
	
	/**
	 * 根据编号获取编号记录
	 * @param saleContractIdOrNo 销售合同ID或编号
	 * @param prjLib 项目库
	 * @param codeType 编码类型
	 * @param code 一般合同编号
	 * @return CodeContractNo
	 */
	public CodeContractNo getCodeContractNoByShareCode(String prjLib, String saleContractIdOrNo,
			int codeType, String code) {
		String hql = "from " + CodeContractNo.class.getName()
				+ " where codeType=? and prjLib=? and shareCode=? and (contractId=? or contractNo=?)";
		List <CodeContractNo> list = find(hql, codeType, prjLib, code, saleContractIdOrNo, saleContractIdOrNo);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		} else {
			return list.get(0);
		}
	}

}
