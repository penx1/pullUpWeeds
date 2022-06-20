package com.chl.pullUpWeeds.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chl.pullUpWeeds.entity.Article;
import com.chl.pullUpWeeds.mapper.ArticleMapper;
import com.chl.pullUpWeeds.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chl
 * @since 2022-02-08
 */
@Service
public class ArticleService extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

    @Autowired
    ArticleMapper articleMapper;

    public Article getArticleById (Integer id){
       return articleMapper.getArticleById(id);
    }

    @Override
    public <E extends IPage<Article>> E page(E page, Wrapper<Article> queryWrapper) {
        return super.page(page, queryWrapper);
    }
}
