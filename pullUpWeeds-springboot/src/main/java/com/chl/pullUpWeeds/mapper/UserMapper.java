package com.chl.pullUpWeeds.mapper;

import com.chl.pullUpWeeds.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chl
 * @since 2022-01-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    public User findUserById (Integer id);
}
