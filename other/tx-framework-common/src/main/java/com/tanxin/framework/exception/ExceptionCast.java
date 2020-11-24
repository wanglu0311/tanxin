package com.tanxin.framework.exception;

import com.tanxin.framework.model.response.ResultCode;

/**
 * @ClassName ExceptionCast
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 18:55
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){

        throw new CustomException(resultCode);

    }

}
