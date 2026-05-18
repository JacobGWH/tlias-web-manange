package org.example.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.pojo.Result;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result handleException(Exception e) {
        log.info("执行出错了...", e);
        return Result.error("出错了,请联系管理员！");
    }

    @ExceptionHandler
    public Result handlerDuplicateKeyException(DuplicateKeyException e){

        log.info("程序执行出错了",e);
       String message = e.getMessage();
       // 定位到 Duplicate entry，然后将后面重复的数据截取出来
        return Result.error("数据重复，" + message.substring(message.indexOf("Duplicate entry") + 16, message.indexOf("for key")));

    }
}
