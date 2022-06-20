package com.chl.pullUpWeeds.handler.exception.handler;

import com.alibaba.fastjson.JSON;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.User;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result<User>(ResultInfo.LOGIN_ERROR,null)));
    }
}
