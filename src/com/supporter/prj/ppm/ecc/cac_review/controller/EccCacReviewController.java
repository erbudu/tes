package com.supporter.prj.ppm.ecc.cac_review.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.supporter.prj.ppm.ecc.OperResultConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supporter.spring_mvc.AbstractController;
import com.supporter.prj.ppm.ecc.cac_review.service.EccCacReviewService;
import com.supporter.prj.ppm.ecc.cac_review.entity.EccCacReview;
import com.supporter.prj.core.orm.hibernate.limit.JqGrid;
import com.supporter.prj.core.orm.hibernate.limit.JqGridReq;
import com.supporter.prj.eip_service.security.entity.UserProfile;
import com.supporter.prj.eip_service.authority.annotation.AuthCheck;
import com.supporter.prj.eip_service.common.entity.OperResult;
import com.supporter.prj.eip_service.exception.ExceptionCode;

/**
 * @author YAN
 * @version V1.0
 * @Title: Controller
 * @Description: 出口管制委员会审核表.
 * @date 2019-08-16 18:45:21
 */
@Controller
@RequestMapping("ecc/cac_review/eccCacReview")
public class EccCacReviewController extends AbstractController {

    private static final long serialVersionUID = 1L;

    @Autowired
    private EccCacReviewService eccCacReviewService;

    /**
     * 进入新建或编辑或查看页面时加载的信息
     *
     * @param eccCacId 主键
     * @return
     */
    @RequestMapping("initEditOrViewPage")
    public @ResponseBody
    EccCacReview initEditOrViewPage(String prjId) {
        EccCacReview entity = eccCacReviewService.initEditOrViewPage(this.getUserProfile(),prjId);
        return entity;
    }
    @RequestMapping("initEditOrViewPageById")
    public @ResponseBody
    EccCacReview initEditOrViewPageById(String eccCacId) {
        EccCacReview entity = eccCacReviewService.initEditOrViewPageById(eccCacId);
        return entity;
    }
    /**
     * 分页表格展示数据.
     *
     * @param jqGrid
     * @return
     * @throws Exception
     */
    @RequestMapping("getGrid")
    public @ResponseBody
    JqGrid getGrid(HttpServletRequest request, JqGridReq jqGridReq, EccCacReview eccCacReview) throws Exception {
        UserProfile user = this.getUserProfile();
        JqGrid jqGrid = jqGridReq.initJqGrid(request);
        this.eccCacReviewService.getGrid(user, jqGrid, eccCacReview);
        return jqGrid;
    }

    /**
     * 保存或更新数据.
     *
     * @param eccCacReview 页面传递参数自动绑定成的实体类
     * @return
     */
    @RequestMapping("saveOrUpdate")
    @AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
    public @ResponseBody
    OperResult<?> saveOrUpdate(EccCacReview eccCacReview) {
        UserProfile user = this.getUserProfile();
        EccCacReview entity = this.eccCacReviewService.saveOrUpdate(user, eccCacReview);
        return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, entity);
    }

    /**
     * 删除操作
     *
     * @param eccCacIds 主键集合，多个以逗号分隔
     * @return
     */
    @RequestMapping("batchDel")
    @AuthCheck(module = "", oper = "", failCode = ExceptionCode.Auth.NOT_ACCESSIBLE)
    public @ResponseBody
    OperResult<?> batchDel(String eccCacIds) {
        UserProfile user = this.getUserProfile();
        this.eccCacReviewService.delete(user, eccCacIds);
        return OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, null, null);
    }

    /**
     * 判断名字是否重复
     *
     * @param eccCacId
     * @param eccCacName
     * @return
     */
    @RequestMapping("nameIsValid")
    public @ResponseBody
    Boolean nameIsValid(String eccCacId, String eccCacName) {
        return this.eccCacReviewService.nameIsValid(eccCacId, eccCacName);
    }

    @RequestMapping("validEcc")
    public @ResponseBody
    OperResult<?> validEcc(String prjId) {
        String message = "";
        boolean result = false;
        Map<String, Object> map = this.eccCacReviewService.validEcc(prjId, message, result);
        message = (String) map.get("msg");
        result = (Boolean) map.get("result");
        OperResult<?> o = OperResult.succeed(OperResultConstant.CODE_OPER_SUCCESS, message, result);
        String nextPage = (String) map.get("nextPage");
        o.setNextPage(nextPage);
        return o;
    }
}
