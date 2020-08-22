package com.example.demo.service;

import com.example.demo.entry.dto.DWZDTO;
import com.example.demo.exception.SystemException;
import com.github.pagehelper.PageInfo;

/**
 * 类描述：
 * 短网址 业务处理接口
 * @ClassName OnLineService
 * @Description TODO
 * @Author msi
 * @Date 2020/8/20 13:44
 * @Version 1.0
 */
public interface OnLineService {

    /**
     * 添加一天短网址
     * @param dwzdto
     * @return
     */
    Integer saveDWZ(DWZDTO dwzdto) throws SystemException;

    /**
     * 查询网址
     * @return
     */
    PageInfo<DWZDTO> selectCwz();

    /**
     * 删除网址
     * @param id
     * @return
     */
    Integer removeCwz(Integer id) throws SystemException;
}
