package com.supporter.prj.linkworks.oa.news_exam.handler;


import com.supporter.prj.core.spring.SpringContextHolder;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.workflow.AbstractExecHandler;
import com.supporter.prj.eip_service.workflow.ExecContext;
import com.supporter.prj.eip_service.workflow.ISharedVar;
import com.supporter.prj.linkworks.oa.news_exam.entity.NewsExamRec;
import com.supporter.prj.linkworks.oa.news_exam.service.NewsExamRecService;


/**
 * 判断并设置是否总裁的Handler.
 * @author linda
 *
 */
public class JudgeCompLeaderHandler extends AbstractExecHandler {
	private static final long serialVersionUID = 1L;

	@Override
    public String getDesc() {
        return null;
    }

    @Override
    public Object execute(ExecContext execContext) {
        NewsExamRecService newsService = SpringContextHolder.getBean(NewsExamRecService.class);
        String recId = (String) execContext.getProcVar("recId");
        NewsExamRec rec = newsService.getNewsExamRec(recId);
        if (rec != null) {
        	execContext.setProcVarsUpdatedToDb(true);
        	if (newsService.isCompLeader(rec)){
        		execContext.setProcVar(ISharedVar.EXT_VAR_1, 1);
        	} else {
        		execContext.setProcVar(ISharedVar.EXT_VAR_1, 0);
        	}
        } else {
            EIPService.getLogService().error("无效的recId:" + recId);
        }
        return null;
    }
}
