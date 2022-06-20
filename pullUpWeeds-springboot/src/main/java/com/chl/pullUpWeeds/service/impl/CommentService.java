package com.chl.pullUpWeeds.service.impl;

import com.chl.pullUpWeeds.dto.FloorDTO;
import com.chl.pullUpWeeds.entity.Comment;
import com.chl.pullUpWeeds.mapper.CommentMapper;
import com.chl.pullUpWeeds.service.ICommentService;
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
 * @since 2022-02-10
 */
@Service
public class CommentService extends ServiceImpl<CommentMapper, Comment> implements ICommentService {


    @Autowired
    CommentMapper commentMapper;
    @Override
    public Integer getCommentBiggestFloor(Integer articleId) {
        FloorDTO commentBiggestFloor = commentMapper.getCommentBiggestFloor(articleId);
        if (commentBiggestFloor == null){
            return 0;
        }else {
            return commentBiggestFloor.getFloor();
        }

    }
}
