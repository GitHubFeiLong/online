package com.example.demo.entry.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 类描述：
 *
 * @ClassName DWZDTO
 * @Description TODO
 * @Author msi
 * @Date 2020/8/20 13:14
 * @Version 1.0
 */
@Data
public class DWZDTO implements Serializable {

    private static final long serialVersionUID = -6688776847289709670L;
    private Integer id;
    private String cwz;
    private String dwz;
    private Long minute;
    private String createTime;
    private String modifiedTime;

}
