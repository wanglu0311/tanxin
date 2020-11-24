package com.tanxin.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.tanxin.framework.model.response.CommonCode;
import com.tanxin.framework.model.response.ResponseResult;
import com.tanxin.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName ExceptionCatch
 * @Description TODO
 * @Author 禄
 * @Date 2020/11/11 18:58
 */
@ControllerAdvice  // 控制器增强
public class ExceptionCatch {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    // 定义Map 配置异常类型所对应的错误代码
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;

    // 定义map 的bulider对象  去构建ImmutableMap
    private static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    // 捕获customException此类异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult custoException(CustomException custoException){

        // 记录日志
        LOGGER.error("catch exception:{}",custoException.getMessage());
        ResultCode resultCode = custoException.getResultCode();
        return new ResponseResult(resultCode);

    }

    // 捕获Exception此类异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){

        // 记录日志
        LOGGER.error("catch exception:{}",exception.getMessage());

        if(EXCEPTIONS == null){
            EXCEPTIONS = builder.build();
        }

        // 从EXCEPTIONS中找异常类型所有对应的错误代码响应给用户,如果找不到给用户响应99999异常
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            //找不到就99999
            return new ResponseResult(CommonCode.SERVER_ERROR );
        }



//        return new ResponseResult(CommonCode.SERVER_ERROR);

    }


    static {

        // 定义异常类型所对应的错误代码
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);

    }


}
