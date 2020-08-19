package com.supporter.prj.cneec.todo.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.supporter.prj.cneec.todo.entity.base.BaseCneecHistTodo;
import com.supporter.util.CommonUtil;

/**   
 * @Title: Entity
 * @Description: eip_todo_hist.
 * @author arnoldH 
 *
 */
@Entity
@Table(name = "eip_v_todo_hist_simple", schema = "")
public class CneecHistTodo extends BaseCneecHistTodo {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 无参构造函数.
	 */
	public CneecHistTodo(){
		super();
	}
	
	/**
	 * 构造函数.
	 * @param 
	 */
	public CneecHistTodo(String id){
		super(id);
	}
	
	public String getWebNameAndId(){
		return this.getWebappName() + "_" + CommonUtil.trim(this.getId());
	}
	
	public boolean equals(Object obj) {
		if(null == obj) return false;
		if(!(obj instanceof CneecHistTodo)) return false;
		else {
			CneecHistTodo todo = (CneecHistTodo) obj;
			if(null == this.getId() || null == todo.getId()) return false;
			else return(this.getId().equals(todo.getId()));
		}
	}

    @Transient
    private int hashCode = Integer.MIN_VALUE;
	public int hashCode() {
		if(Integer.MIN_VALUE == this.hashCode) {
			if(null == this.getId()) return super.hashCode();
			else {
				String hashStr = this.getClass().getName() + ":" + this.getId().hashCode();
				this.hashCode = hashStr.hashCode();
			}
		}
		return this.hashCode;
	}
	
}
