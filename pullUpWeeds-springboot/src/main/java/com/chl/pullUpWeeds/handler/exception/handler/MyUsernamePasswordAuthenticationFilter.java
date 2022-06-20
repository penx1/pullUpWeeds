package com.chl.pullUpWeeds.handler.exception.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Override
    protected String obtainPassword(HttpServletRequest request) {
        //通过request中的jason来获取密码
        Map<String,String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try( ServletInputStream is = request.getInputStream()) {

            map = objectMapper.readValue(is,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map.get("password");
    }

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        //通过request中的jason来获取用户名
        Map<String,String> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try( ServletInputStream is = request.getInputStream()) {

            map = objectMapper.readValue(is,Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return map.get("username");
    }
}
