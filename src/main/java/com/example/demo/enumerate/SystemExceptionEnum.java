package com.example.demo.enumerate;

/**
 * 枚举描述：
 *
 * @ClassName SystemExceptionEnum
 * @Description TODO
 * @Author msi
 * @Date 2020/7/14 20:43
 * @Version 1.0
 */
public enum SystemExceptionEnum {
    DB_UPDATE_ERROR("C0101", "数据库更新失败", false),
    DB_DELETE_ERROR("C0102", "数据库删除失败", false),
    DB_INSERT_ERROR("C0103", "数据库添加数据失败", false)
    ;
    /**
     * 错误码
     * 错误码为字符串类型,共5位,分成两个部分:错误产生来源+四位数字编号。
     */
    private String code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 是否发送邮件: false不发送
     */
    private Boolean isSend;

    SystemExceptionEnum(String code, String message){
        this.code = code;
        this.message = message;
        this.isSend = false;
    }

    SystemExceptionEnum(String code, String message, Boolean isSend) {
        this.code = code;
        this.message = message;
        this.isSend = isSend;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSend() {
        return isSend;
    }
}
