package com.chl.pullUpWeeds.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.Article;
import com.chl.pullUpWeeds.entity.Comment;
import com.chl.pullUpWeeds.entity.DetailedArticle;
import com.chl.pullUpWeeds.entity.User;
import com.chl.pullUpWeeds.service.impl.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chl
 * @since 2022-02-10
 */
@Api("评论控制器")
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    //通过id获得评论
    @ApiOperation("通过id获得评论")
    @GetMapping("/{id}")
    public Result<Comment> getComment(@PathVariable Integer id){
        Comment comment = commentService.getById(id);
        return new  Result<Comment>(ResultInfo.SUCCESS_FIND_COMMENT,comment);
    }

    //添加评论
    @ApiOperation("添加评论")
    @PostMapping
    public Result<Comment> saveComment (@RequestBody Comment comment){

        comment.setFloor(commentService.getCommentBiggestFloor(comment.getArticleId())+1);//获得最大楼层数并加一
        comment.setTime(new Date());
        commentService.save(comment);
        return new Result<>(ResultInfo.SUCCESS_ADD_COMMENT,null);
    }
    //删除评论
    @ApiOperation("删除评论")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result<User> deleteComment(@PathVariable Integer id){
        System.out.println(id);
        commentService.removeById(id);
        return new Result<>(ResultInfo.SUCCESS_REMOVE_COMMENT,null);
    }

    //更新评论
    @ApiOperation("更新评论")
    @PutMapping()
    public Result<Comment> updateComment(@RequestBody Comment comment){
        commentService.updateById(comment);
        return new Result<>(ResultInfo.SUCCESS_UPDATE_COMMENT,null);
    }

    //分页获得评论
    @ApiOperation("分页获得评论")
    @GetMapping
    public Result<Page> findPage(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam(defaultValue ="5") Integer pageSize, @RequestParam(defaultValue = "") String search ){

        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Comment::getContent,search);
        }
        Page<Comment> userPage = commentService.page(new Page<>(pageNum, pageSize),wrapper);
        return new  Result<Page>(ResultInfo.SUCCESS_FIND_COMMENT,userPage);
    }

    //获得单个文章的评论集合
    @ApiOperation("获得单个文章的评论集合")
    @GetMapping("/getArticleComments")
    public Result<Page> getArticleComments(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam(defaultValue ="5") Integer pageSize, @RequestParam Integer ArticleId){
        LambdaQueryWrapper<Comment> wrapper = Wrappers.<Comment>lambdaQuery();
        System.out.println(ArticleId);
        if(ArticleId != 0 || ArticleId != null){
            wrapper.eq(Comment::getArticleId,ArticleId);
            wrapper.orderByAsc(Comment::getFloor);//按照楼层数降序排列

        }else {
            return new  Result<Page>(ResultInfo.FAIL_FIND_COMMENT,null);
        }
        Page<Comment> userPage = commentService.page(new Page<>(pageNum, pageSize),wrapper);
        return new  Result<Page>(ResultInfo.SUCCESS_FIND_COMMENT,userPage);
    }
}

