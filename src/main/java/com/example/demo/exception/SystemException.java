package com.example.demo.exception;


import com.example.demo.enumerate.SystemExceptionEnum;

/**
 * 类描述：
 *
 * @ClassName SystemException
 * @Description TODO
 * @Author msi
 * @Date 2020/7/14 20:40
 * @Version 1.0
 */
public class SystemException extends BaseException{

    public SystemException(String message) {
        super(message);
    }
    public SystemException(SystemExceptionEnum systemExceptionEnum) {
        super(systemExceptionEnum.getCode(), systemExceptionEnum.getMessage(), systemExceptionEnum.getSend());

    }
    public SystemException(String code, String message, Boolean isSend) {
        super(code, message, isSend);
    }

    @Override
    public String getCode() {
        return super.getCode();
    }

    @Override
    public Boolean getSend() {
        return super.getSend();
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
