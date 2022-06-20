package com.chl.pullUpWeeds.controller;


import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chl.pullUpWeeds.common.Result;
import com.chl.pullUpWeeds.common.ResultInfo;
import com.chl.pullUpWeeds.entity.User;
import com.chl.pullUpWeeds.service.IUserService;
import com.chl.pullUpWeeds.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since
 */
@Api("用户控制器")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    //添加用户
    @ApiOperation("添加用户")
    @PostMapping
    public Result<User> saveUser (@RequestBody User user){

        if (user.getAvatar()==null){
            user.setAvatar("http://localhost:8090/avatar/default_avatar.jpg");
        }
        userService.save(user);
        return new Result<>(ResultInfo.SUCCESS_ADD_USER,null);
    }

    //删除用户
    @ApiOperation("删除用户")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result<User> deleteUser(@PathVariable Integer id){
        System.out.println(id);
        userService.removeById(id);

        return new Result<>(ResultInfo.SUCCESS_REMOVE_USER,null);
    }
    //修改用户信息
    @ApiOperation("修改用户")
    @PutMapping()
    public Result<User> updateUser(@RequestBody User user){
        userService.updateById(user);
        return new Result<>(ResultInfo.SUCCESS_UPDATE_USER,null);
    }
    //通过id获得用户
    @ApiOperation("获得用户")
    @GetMapping("/{id}")
    public Result<User> getUser(@PathVariable Integer id){
        User user = userService.getById(id);
        return new  Result<User>(ResultInfo.SUCCESS_FIND_USER,user);
    }


    //分页获得用户
    @ApiOperation("获得用户")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('admin')")
    public Result<Page> findPage(@RequestParam(defaultValue = "1") Integer pageNum ,@RequestParam(defaultValue ="5") Integer pageSize,@RequestParam(defaultValue = "") String search ){

        LambdaQueryWrapper<User> wrapper = Wrappers.<User>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(User::getNickname,search);
        }
        Page<User> userPage = userService.page(new Page<>(pageNum, pageSize),wrapper);
        return new  Result<Page>(ResultInfo.SUCCESS_FIND_USER,userPage);
    }

    //添加用户StrUtil.isNotBlank(search)
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public Result<User> registerUser ( User user){
        if (user.getAvatar()==null){
            user.setAvatar("http://localhost:8090/avatar/default_avatar.jpg");
        }
        user.setManager(0);

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername,user.getUsername());
        int count = userService.count(wrapper);
        if (count>=1){
            return new Result<>(ResultInfo.FAIL_CHECK_USER,null);
        }else {
            userService.save(user);
            return new Result<>(ResultInfo.SUCCESS_ADD_USER,null);
        }

    }

    //通过id获得用户
    @PostMapping("/checkUser")
    public Result<User> CheckUser(@RequestParam String username){

        LambdaQueryWrapper<User> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(User::getUsername,username);
        int count = userService.count(wrapper);
        if (count>=1){
            return new Result<>(ResultInfo.FAIL_CHECK_USER,null);
        }else {
            return new Result<>(ResultInfo.SUCCESS_CHECK_USER,null);
        }
    }
}
