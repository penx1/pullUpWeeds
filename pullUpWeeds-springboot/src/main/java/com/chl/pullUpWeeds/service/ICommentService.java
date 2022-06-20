package com.chl.pullUpWeeds.service;

import com.chl.pullUpWeeds.dto.FloorDTO;
import com.chl.pullUpWeeds.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chl
 * @since 2022-02-10
 */
public interface ICommentService extends IService<Comment> {
    public Integer getCommentBiggestFloor(Integer articleId);
}
