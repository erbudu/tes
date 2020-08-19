package com.supporter.prj.ppm.getInform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supporter.prj.eip.todo.dao.HistTodoDao;
import com.supporter.prj.eip.todo.entity.HistTodo;
import com.supporter.prj.eip_service.EIPService;
import com.supporter.prj.eip_service.todo.entity.ITodo;
import com.supporter.prj.ppm.getInform.constant.GetInformConstant;
import com.supporter.prj.ppm.getInform.dao.GetInformDao;

@Service
public class GetInformService {
    @Autowired
    private GetInformDao getInformDao;
    @Autowired
    private HistTodoDao histTodoDao;

    public static final String noticMsg = "（知会）";

    public Map<Integer, List<Map<String, String>>> searchByProcId(String procId) {
        /** map存放查阅状态以及信息*/
        Map<Integer, List<Map<String, String>>> map = new HashMap<Integer, List<Map<String, String>>>();
        Set<Map<String, String>> setRead = new HashSet<Map<String, String>>();
        Set<Map<String, String>> setNotRead = new HashSet<Map<String, String>>();
        int message;
        if (histTodoDao.findBy("procId", procId).size() > 0) {    //查询已办信息
            List<HistTodo> listRead = getInformDao.getByProcId(procId);
            for (int i = 0; i < listRead.size(); i++) {
                if (listRead.get(i).getMsgType().equals("CC")) {    //只去查找MSG_TYPE是CC的数据
                    /** mapsmall存放知会人Id、名字，角色名*/
                    Map<String, String> mapsmall = new HashMap<String, String>();
                    message = GetInformConstant.HAVETODO;//"已阅"
                    String nodeName = listRead.get(i).getMsgTitle();
                    String[] arr = nodeName.split("：");
                    if (arr.length > 1) {
                        nodeName = arr[0];
                        nodeName = nodeName.replaceAll(noticMsg, "");
                    }
                    if (StringUtils.isBlank(nodeName)) {
                        nodeName = "";
                    }
                    mapsmall.put("nodeName", nodeName);
                    mapsmall.put("personId", listRead.get(i).getPersonId());
                    String personName = listRead.get(i).getPersonName();
                    if (StringUtils.isBlank(personName)) {
                        personName = EIPService.getEmpService().getEmp(listRead.get(i).getPersonId()).getName();
                    }
                    System.out.println(personName);
                    mapsmall.put("personName", personName);
                    setRead.add(mapsmall);
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>(setRead);
                    map.put(message, list);
                }
            }
        }
        List<ITodo> listNotRead = EIPService.getTodoService().getProcTodos(procId);
        if (listNotRead != null && listNotRead.size() > 0) {
            for (int i = 0; i < listNotRead.size(); i++) {
                if (listNotRead.get(i).getMsgType().equals("CC")) {
                    Map<String, String> mapsmall = new HashMap<String, String>();
                    message = GetInformConstant.BLACKLOG;//"未阅"
                    String nodeName = listNotRead.get(i).getMsgTitle();
                    String[] arr = nodeName.split("：");
                    if (arr.length > 1) {
                        nodeName = arr[0];
                        nodeName = nodeName.replaceAll(noticMsg, "");
                    }
                    if (StringUtils.isBlank(nodeName)) {
                        nodeName = "";
                    }
                    mapsmall.put("nodeName", nodeName);
                    mapsmall.put("personId", listNotRead.get(i).getPersonId());
                    String personName = listNotRead.get(i).getPersonName();
                    if (StringUtils.isBlank(personName)) {
                        personName = EIPService.getEmpService().getEmp(listNotRead.get(i).getPersonId()).getName();
                    }
                    mapsmall.put("personName", personName);
                    setNotRead.add(mapsmall);
                    List<Map<String, String>> list = new ArrayList<Map<String, String>>(setNotRead);
                    map.put(message, list);
                }
            }
        }
        for (Entry<Integer, List<Map<String, String>>> s : map.entrySet()) {
            System.out.println("键值对:" + s);
        }
        return map;
    }
}