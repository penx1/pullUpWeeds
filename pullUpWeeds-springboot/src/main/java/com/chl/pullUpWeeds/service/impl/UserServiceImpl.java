package com.chl.pullUpWeeds.service.impl;

import com.chl.pullUpWeeds.entity.User;
import com.chl.pullUpWeeds.mapper.UserMapper;
import com.chl.pullUpWeeds.service.IUserService;
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
 * @since 2022-01-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

//    @Autowired
//    UserMapper userMapper;
//    public int addUser(User user){
//        return userMapper.insert(user);
//    }
}
