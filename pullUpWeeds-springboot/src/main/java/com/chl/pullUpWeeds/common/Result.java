package com.chl.pullUpWeeds.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    /**
     * 返回 状态 成功 或者 失败
     */

    private boolean flag;
    /**
     * 返回状态码 如404
     */
    private String code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;

    public Result(ResultInfo resultInfo, T data) {
        this.flag = resultInfo.getFlag();
        this.code = resultInfo.getCode();
        this.message = resultInfo.getMessage();
        this.data = data;
    }
}
