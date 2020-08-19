package com.supporter.prj.cneec.tpc.invoice_no_contract.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.tpc.invoice_no_contract.constant.InvoiceNoContractConstant;
import com.supporter.prj.cneec.tpc.invoice_no_contract.entity.base.BaseInvoiceNoContractEntity;
import com.supporter.prj.cneec.tpc.register_project.entity.RegisterProject;
import com.supporter.prj.cneec.tpc.register_project.service.RegisterProjectService;
import com.supporter.prj.core.spring.SpringContextHolder;

/**
 * 非合同付款收发票实体类
 * @Title: InvoiceNoContractEntity
 * @author CHENHAO
 *
 */

@Entity
@Table(name = "TPC_INVOICE_NOCONTRACT", schema = "")
public class InvoiceNoContractEntity extends BaseInvoiceNoContractEntity{
	
	private static final long serialVersionUID = 1L;
	
	@Transient
	public String getPrjDeptId() {//项目所属部门ID
		RegisterProjectService prjService = (RegisterProjectService) SpringContextHolder.getBean(RegisterProjectService.class);
		RegisterProject project = prjService.get(this.getPrjId());
		return project != null ? project.getProjectDeptId() : "";
	}

	@Transient
	public int getDbYear(){
		return 0;
	}
	
	@Transient
	public String getModuleId(){//应用编号
		return InvoiceNoContractConstant.MODULE_NAME;
	}
	
	@Transient
	public String getDomainObjectId(){//业务对象
		return InvoiceNoContractConstant.DOMAIN_OBJECT_ID;
		
	}
	
	@Transient
	public String getEntityId(){//业务主键
		return this.getId();
	}
	
	@Transient
	public String getId() {//业务主键
		return this.getInvoiceId();
	}
	
	@Transient
	public String getCompanyNo(){//公司编码
		
		return this.getDeptId();
	}
	
	@Transient
	public String getModuleBusiType(){//应用业务类型
		return "";
	}
	
	@Transient
	public String getEntityName(){//业务实体类名称
		return this.getClass().getName();
	}
	/**
	 * 获取状态的显示名称
	 * @return
	 */
	@Transient
	public String getStatusDisplayName() {
		return InvoiceNoContractConstant.getProcStatus().get(this.getStatus());
	}
	
}
