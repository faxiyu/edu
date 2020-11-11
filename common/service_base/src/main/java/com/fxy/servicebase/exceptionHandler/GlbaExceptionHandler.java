package com.fxy.servicebase.exceptionHandler;

import com.fxy.commonutils.Rsponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class GlbaExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rsponse error(Exception e){
        e.printStackTrace();
        log.error(e.getMessage());
        return Rsponse.error().message("执行异常处理");
    }
    //自定义异常
    @ExceptionHandler(FxyException.class)
    @ResponseBody
    public Rsponse fxyerror(FxyException e){
        log.error(e.getMessage());

        return Rsponse.error().code(e.getCode()).message(e.getMsg());
    }
}
