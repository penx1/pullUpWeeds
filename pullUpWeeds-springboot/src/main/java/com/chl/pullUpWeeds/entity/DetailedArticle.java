package com.chl.pullUpWeeds.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DetailedArticle extends Article{

    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的
    @ApiModelProperty(value = "文章评论")
    List<Comment> articleComments;
}
