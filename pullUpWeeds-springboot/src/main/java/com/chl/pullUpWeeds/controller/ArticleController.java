package com.chl.pullUpWeeds.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.Article;
import com.chl.pullUpWeeds.entity.DetailedArticle;
import com.chl.pullUpWeeds.entity.User;
import com.chl.pullUpWeeds.service.impl.ArticleService;
import com.chl.pullUpWeeds.service.impl.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
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
 * @since 2022-02-08
 */
@Api("文章控制器")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;


    //通过id获得文章
    @ApiOperation("通过id获得文章")
    @GetMapping("/{id}")
    public Result<Article> getArticle(@PathVariable Integer id){
//        Article article = articleService.getArticleById(id);
        Article article = articleService.getById(id);
        return new  Result<Article>(ResultInfo.SUCCESS_FIND_ARTICLE,article);
    }

    //添加文章
    @ApiOperation("添加文章")
    @PostMapping
    public Result<Article> saveArticle (@RequestBody Article article){

        article.setTime(new Date());
        articleService.save(article);
        return new Result<>(ResultInfo.SUCCESS_ADD_ARTICLE,null);
    }
    //删除文章
    @ApiOperation("删除文章")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result<User> deleteArticle(@PathVariable Integer id){
        System.out.println(id);
        articleService.removeById(id);
        return new Result<>(ResultInfo.SUCCESS_REMOVE_ARTICLE,null);
    }

    //更新文章
    @ApiOperation("更新文章")
    @PutMapping()
    public Result<Article> updateArticle(@RequestBody Article article){
        articleService.updateById(article);
        return new Result<>(ResultInfo.SUCCESS_UPDATE_ARTICLE,null);
    }

    //分页获得文章
    @ApiOperation("分页获得文章")
    @GetMapping
    public Result<Page> findPage(@RequestParam(defaultValue = "1") Integer pageNum , @RequestParam(defaultValue ="5") Integer pageSize, @RequestParam(defaultValue = "") String search ){

        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Article::getTitle,search);
        }
        Page<Article> userPage = articleService.page(new Page<>(pageNum, pageSize),wrapper);

        return new  Result<Page>(ResultInfo.SUCCESS_FIND_ARTICLE,userPage);
    }


}

