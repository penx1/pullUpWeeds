package com.chl.pullUpWeeds.handler.exception.handler;
import com.alibaba.fastjson.JSON;
import com.chl.pullUpWeeds.auth.MyUserDetails;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {

        httpServletResponse.setContentType("application/json;charset=UTF-8");
        Object principal = authentication.getPrincipal();
        User user = null;
        if (principal instanceof UserDetails){
            MyUserDetails userDetails = (MyUserDetails) principal;
             user = userDetails.getUser();
        }
        httpServletResponse.getWriter().write(JSON.toJSONString(new Result<User>(ResultInfo.LOGIN_SUCCESS,user )));

    }
}
