package com.supporter.prj.ppm.poa.inr.entity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.supporter.prj.ppm.poa.inr.entity.base.BaseNegotiationRecord;
@Entity
@Table(name = "ppm_inr",schema="")
public class NegotiationRecord extends BaseNegotiationRecord {
private static final long serialVersionUID = 1L;
private boolean isNew=true;
@Transient
public boolean getIsNew() {
	return isNew;
}

public void setNew(boolean isNew) {
	this.isNew = isNew;
}



}
