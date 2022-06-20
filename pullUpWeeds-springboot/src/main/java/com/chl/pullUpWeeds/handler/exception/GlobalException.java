package com.chl.pullUpWeeds.handler.exception;

import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e){
        e.printStackTrace();
        return new Result(ResultInfo.GLOBAL_ERROR,null);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    //权限不足异常处理
    public Result handleAccessDeniedException(AccessDeniedException e){
        return new Result<User>(ResultInfo.AUTHORIZED_ERROR,null);
    }


}
