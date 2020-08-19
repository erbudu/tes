package com.supporter.prj.ppm.ecc;

import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.bms.entity.Message;
import com.supporter.prj.eip_service.person.entity.Person;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.prj_org.base_info.constant.BaseInfoConstant;
import com.supporter.prj.ppm.util.PpmSendMessage;
import com.supporter.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @autor 颜丙超
 * @date 2019/8/16
 */
public class EccUtils {
    public static final String MODULE_ID= "PpmEcc";
    public static final String Dept_File_BusiType = "PPM_DEPT_FILES";
    public static final String File_BusiType = "wg_reprot_files";
    public static final String ROLE_JINGYINGWAIPAI_MANAGER = "BUSINESSMANAGERTOBUSINESS";
    /**
     * 根据角色发送知会
     * @param roleId
     * @param title
     * @param url
     * @param moduleId
     * @param procId
     */
    public static void sendMessages(String roleId,String title,String url ,String moduleId,String procId,String nodeName){
        //获取角色下的人员
        List<Person> persons = EIPService.getRoleService().getPersonFromRole(roleId,"");
        if (!CommonUtil.isNullOrEmpty(persons)){
            for (Person p:persons
                 ) {
                sendMessage(p.getPersonId(),title,url,moduleId,procId,nodeName);
            }
        }
    }

    /**
     * 根据人员id发送知会
     * @param personId 人员id
     * @param title 知会标题
     * @param url 知会页面地址
     * @param moduleId 应用编号
     * @param procId 流程id
     */
    public static void sendMessage(String personId,String title,String url ,String moduleId,String procId,String nodeName){
        System.out.println(personId);
        System.out.println(title);
        System.out.println(procId);
        System.out.println(moduleId);
        System.out.println(nodeName);
        Message message = EIPService.getBMSService().newMessage();// --------------------------------获取待办通知服务内容
        message.setPersonId(personId);// ------------------------------------------------------------通知人
        // String title = "知会："+purchaseDemand.getProcTitle();
        // 待办标题
        message.setEventTitle(title);// -------------------------------------------------------------通知待办标题
        message.setNotifyTime(new Date());// --------------------------------------------------------通知待办日期
        // 待办地址"/cpp/purchase_demand/demand/purchase_demand_audit_notify.html?id="+id
        message.setWebPageURL(url);// ------------------------------------------------------------------通知待办地址
        message.setModuleId(moduleId);// ------------------------------------------------------应用编号
        // 默认地指定为“待办”类型
        message.setMessageType(ITodo.MsgType.CC);// -------------------------------------------------待办类型
        message.setWebappName("BM");
        // message.setWfProcId(execContext.getProcId());
        message.setWfProcId(procId);
        //知会给哪些节点
        message.setNotifyModes(nodeName);
        // 加入待办事宜（BMS）
        PpmSendMessage.addMessage(message);
// -------------------------------------------获取待办服务发送待办
    }
}
