package com.tanxin.framework.exception;

import com.tanxin.framework.model.response.ResultCode;

/**
 * @ClassName CustomException
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 18:52
 */
public class CustomException extends RuntimeException {

    ResultCode resultCode;

    public CustomException(ResultCode resultCode){

        this.resultCode = resultCode;

    }

    public ResultCode getResultCode(){

        return resultCode;

    }


}
