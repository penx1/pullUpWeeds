package com.chl.pullUpWeeds.mapper;

import com.chl.pullUpWeeds.dto.FloorDTO;
import com.chl.pullUpWeeds.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-02-10
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
public FloorDTO getCommentBiggestFloor(Integer articleId);
}
