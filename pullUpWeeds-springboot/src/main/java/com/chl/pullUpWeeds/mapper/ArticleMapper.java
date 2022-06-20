package com.chl.pullUpWeeds.mapper;

import com.chl.pullUpWeeds.entity.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-08
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

    public Article getArticleById(Integer id);

}
