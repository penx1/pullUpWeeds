package com.chl.pullUpWeeds.utils;


import com.chl.pullUpWeeds.entity.Log;
import com.chl.pullUpWeeds.entity.User;
import com.chl.pullUpWeeds.mapper.LogMapper;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.Date;

@Aspect
@Controller
public class logUtil {

    @Autowired
    LogMapper logMapper;

    public void IntoData(String msg){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        String  username = (String)session.getAttribute("logUsername");


        long time = new Date().getTime() ;
        logMapper.addLog(new Log(username,msg,new Timestamp(time)));
    }


    @Pointcut("execution(* com.chl.pullUpWeeds.controller.ArticleController.getArticle(..))")
    public void getArticlePointCut(){}

    @After("getArticlePointCut()")
    public void AfterGetArticle() {
        IntoData("查找文章");
    }
    @Pointcut("execution(* com.chl.pullUpWeeds.controller.ArticleController.findPage(..))")
    public void findPagePointCut(){}

    @After("findPagePointCut()")
    public void AfterFindPage() {
        IntoData("按页查找文章");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.ArticleController.deleteArticle(..))")
    public void deleteArticlePointCut(){}

    @After("deleteArticlePointCut()")
    public void AfterDeleteArticlePointCut() {

        IntoData("删除文章");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.ArticleController.saveArticle(..))")
    public void saveArticlePointCut(){}

    @After("saveArticlePointCut()")
    public void AfterSaveArticle() {

        IntoData("增加文章");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.ArticleController.updateArticle(..))")
    public void updateArticlePointCut(){}

    @After("updateArticlePointCut()")
    public void AfterUpdateArticle() {

        IntoData("更新文章");
    }

    //用户

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.UserController.getUser(..))")
    public void getUserPointCut(){}

    @After("getUserPointCut()")
    public void AfterGetUser() {
        IntoData("查找用户");
    }
    @Pointcut("execution(* com.chl.pullUpWeeds.controller.UserController.findPage(..))")
    public void findUserPagePointCut(){}

    @After("findUserPagePointCut()")
    public void AfterFindUserPage() {
        IntoData("按页查找用户");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.UserController.deleteUser(..))")
    public void deleteUserPointCut(){}

    @After("deleteUserPointCut()")
    public void AfterDeleteUser() {

        IntoData("删除用户");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.UserController.saveUser(..))")
    public void saveUserPointCut(){}

    @After("saveUserPointCut()")
    public void SaveUserPointCut() {

        IntoData("增加用户");
    }

    @Pointcut("execution(* com.chl.pullUpWeeds.controller.UserController.updateUser(..))")
    public void updateUserPointCut(){}

    @After("updateUserPointCut()")
    public void AfterUpdateUserPointCut() {

        IntoData("更新用户信息");
    }
}
