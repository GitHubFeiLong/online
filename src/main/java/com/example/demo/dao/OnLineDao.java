package com.example.demo.dao;

import com.example.demo.entry.dto.DWZDTO;

import java.util.List;

/**
 * 接口描述：
 *
 * @ClassName OnLineDao
 * @Description TODO
 * @Author msi
 * @Date 2020/8/20 13:52
 * @Version 1.0
 */
public interface OnLineDao {

    Integer insertDWZ(DWZDTO dwzdto);

    List<DWZDTO> listDWZ();

    Integer deleteCwz(Integer id);
}
