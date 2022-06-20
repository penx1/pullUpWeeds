package com.chl.pullUpWeeds.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chl
 * @since 2022-02-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Comment对象", description="")
@TableName(value = "comment" ,resultMap = "commentAuthorMap")

public class Comment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "评论id")
    private Integer id;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "评论用户id")
    private Integer authorId;

    @ApiModelProperty(value = "评论文章id")
    private Integer articleId;


    @ApiModelProperty(value = "评论楼层数")
    private Integer floor;

    @ApiModelProperty(value = "评论发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;

    @TableField(exist = false)//表示该属性不为数据库表字段，但又是必须使用的
    @ApiModelProperty(value = "文章作者")
    private User user;

}
