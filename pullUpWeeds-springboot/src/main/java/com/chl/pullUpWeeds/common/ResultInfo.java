package com.chl.pullUpWeeds.common;

import lombok.Getter;

@Getter
public enum ResultInfo {
    NOT_FOUND(false,"404","找不到网页"),
    GLOBAL_ERROR(false,"555","服务器繁忙"),
    LOGIN_ERROR(false,"501","登陆失败，账号或者密码错误"),
    AUTHORIZED_ERROR(false,"502","权限不足"),
    NOT_LOG(false,"503","未登录"),

    FAIL_FIND_COMMENT(false,"525","查找评论失败"),


    LOGIN_SUCCESS(true,"201","登陆成功"),
    SUCCESS_ADD_USER(true,"202","添加用户成功"),
    SUCCESS_REMOVE_USER(true,"203","删除用户成功"),
    SUCCESS_UPDATE_USER(true,"204","修改用户成功"),
    SUCCESS_FIND_USER(true,"205","查找用户成功"),
    SUCCESS_UPLOAD_PHOTO(true,"206","图片上传成功"),
    SUCCESS_CHECK_USER(true,"207","用户名无重复"),
    FAIL_CHECK_USER(false,"507","用户名有重复"),



    SUCCESS_ADD_ARTICLE(true,"212","添加文章成功"),
    SUCCESS_REMOVE_ARTICLE(true,"213","删除文章成功"),
    SUCCESS_UPDATE_ARTICLE(true,"214","修改文章成功"),
    SUCCESS_FIND_ARTICLE(true,"215","查找文章成功"),

    SUCCESS_ADD_COMMENT(true,"222","添加评论成功"),
    SUCCESS_REMOVE_COMMENT(true,"223","删除评论成功"),
    SUCCESS_UPDATE_COMMENT(true,"224","修改评论成功"),
    SUCCESS_FIND_COMMENT(true,"225","查找评论成功"),
    SUCCESS(true,"200","成功");

    private Boolean flag;
    private String code;
    private String message;

    ResultInfo(Boolean flag, String code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
