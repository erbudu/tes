package com.supporter.prj.linkworks.oa.salary_pwd.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.linkworks.oa.salary_pwd.entity.base.BaseSalaryPwd;

@Entity
@Table(name="OA_SALARY_PASSWORD"
    ,schema=""
)
public class SalaryPwd  extends BaseSalaryPwd{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	 private boolean add;
		@Transient
	    public boolean isAdd() {
			return add;
		}
		public void setAdd(boolean add) {
			this.add = add;
		}
}
