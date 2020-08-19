package com.supporter.prj.cneec.hr.gpms_interface;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.supporter.prj.core.orm.hibernate.HibernateDao;
import com.supporter.prj.eip.transaction.TransManager;

/**
 * 中电专用HR库（GPMS2000库）的Dao祖先类.
 * @author TianSen 
 * @param <T>
 * @param <PK>
 */
//@Transactional(transactionManager = TransManager.SUPP_APP)
public class HrAppDaoSupport < T, PK extends Serializable > extends HibernateDao < T, PK > {
	
	@Override
	@Autowired
	@Qualifier(value = "cneec_hrSessionFactory")
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	protected String getSessionFactoryBeanId() {
		return "cneec_hrSessionFactory";
	}
	
	/**
	 * 注: 由于@Transactional注解对从祖先继承而得的方法无效，因此需要重写一下.
	 */
	@Override
	public void save(final T entity) {
		super.save(entity);
	}
	 
	@Override
	public void saveOrUpdate(final T entity) {
		super.saveOrUpdate(entity);
	}
	
	@Override
	public void update(final T entity) {
		super.update(entity);
	}
	
	@Override
	public Object merge(final T entity) {
		return super.merge(entity);
	}
	 
	@Override
	public void delete(final T entity) {
		super.delete(entity);
	}

	@Override
	public void delete(final PK id) {
		super.delete(id);
	}
	
	@Override
	public void update(final List < T > entities) {
		super.update(entities);
	}
	
	@Override
	public void save(final List < T > entities) {
		super.save(entities);
	}
}
