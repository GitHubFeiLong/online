package com.example.demo.controller;


import com.example.demo.annotation.RepeatSubmitAnnotation;
import com.example.demo.config.MemberVariable;
import com.example.demo.constant.MapKeyConsts;
import com.example.demo.entry.dto.DWZDTO;
import com.example.demo.exception.SystemException;
import com.example.demo.service.OnLineService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 类描述：
 *
 * @ClassName OnLineController
 * @Description TODO
 * @Author msi
 * @Date 2020/8/20 13:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/online")
public class OnLineController {

    @Autowired
    private OnLineService onLineService;

    /**
     * 查询
     * @return
     */
    @RequestMapping("/selectCwz")
    public Map<String, Object> selectCwz() {
        Map<String, Object> controllerMap = new HashMap<>();
        controllerMap.put(MapKeyConsts.CODE, MemberVariable.CODE);
        PageInfo<DWZDTO> dwzdtoPageInfo = this.onLineService.selectCwz();
        controllerMap.put("total",dwzdtoPageInfo.getTotal());
        controllerMap.put("rows",dwzdtoPageInfo.getList());
        return controllerMap;
    }

    /**
     * 添加
     * @param dwzdto
     * @return
     * @throws SystemException
     */
    @RequestMapping("/addCwz")
    @RepeatSubmitAnnotation
    public Map<String, Object> addCwz (DWZDTO dwzdto) throws SystemException {
        Map<String, Object> controllerMap = new HashMap<>();
        controllerMap.put(MapKeyConsts.CODE, MemberVariable.CODE);
        Integer count = onLineService.saveDWZ(dwzdto);
        return controllerMap;
    }

    /**
     * 删除网站
     * @param id
     * @return
     * @throws SystemException
     */
    @RequestMapping("/removeCwz")
    public Map<String, Object> removeCwz (Integer id) throws SystemException {
        Map<String, Object> controllerMap = new HashMap<>();
        controllerMap.put(MapKeyConsts.CODE, MemberVariable.CODE);
        Integer count = onLineService.removeCwz(id);
        return controllerMap;
    }
}
