package com.supporter.prj.eip.code_share.dao;

import org.springframework.stereotype.Repository;

import com.supporter.prj.core.orm.hibernate.dao.MainDaoSupport;
import com.supporter.prj.eip.code_share.entity.CsSerialEntityNo;
import com.supporter.prj.eip.code_share.entity.base.CsSerialEntityNoKey;

/**
 * [业务对象顺序号]对应的dao.
 * 
 * @author 康博
 * @createDate 2017-5-19
 * @since 6.0
 *
 */
@Repository
public class CsSerialEntityNoDao extends MainDaoSupport < CsSerialEntityNo, CsSerialEntityNoKey > {

}
