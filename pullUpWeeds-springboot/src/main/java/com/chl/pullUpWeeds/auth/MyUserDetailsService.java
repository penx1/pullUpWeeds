package com.chl.pullUpWeeds.auth;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chl.pullUpWeeds.entity.User;

import com.chl.pullUpWeeds.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    IUserService iUserService;

    @Autowired
    HttpSession session;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        QueryWrapper<User> wrapper = new QueryWrapper<User>();

        wrapper.eq("username",s);
        User user = iUserService.getOne(wrapper);
        System.out.println(user);
        if (user == null){
             throw new  UsernameNotFoundException("用户名不存在");
        }

        MyUserDetails myUserDetails = new MyUserDetails();

        if (user.getManager()==1){
            List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
            myUserDetails.setAuthorities(auths);
        }

        myUserDetails.setUser(user);
//        myUserDetails.getAuthorities().add();
        myUserDetails.setUsername(user.getUsername());
        //
        myUserDetails.setPassword("{noop}"+user.getPassword());

        session.setAttribute("logUsername",user.getUsername());
        return myUserDetails;


    }
}
