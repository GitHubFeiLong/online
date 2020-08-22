package com.example.demo.exception;

/**
 * 类描述：
 * 自定义异常的基类，其它模块的异常继承进行扩展
 * @ClassName BaseException
 * @Description TODO
 * @Author msi
 * @Date 2020/6/10 19:41
 * @Version 1.0
 */
public class BaseException extends Exception{

    /**
     * 错误代码
     */
    protected String code;
    /**
     * 错误信息
     */
    protected String message;

    /**
     * 是否发送错误邮件，false不发送
     */
    protected Boolean isSend;


    public BaseException(String message) {
        super(message);
    }

    public BaseException(String code, String message, Boolean isSend) {
        this.code = code;
        this.message = message;
        this.isSend = isSend;
    }

    public String getCode() {
        return code;
    }

    public Boolean getSend() {
        return isSend;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
