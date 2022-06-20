package com.chl.pullUpWeeds.auth;

import com.chl.pullUpWeeds.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyUserDetails implements UserDetails {
    private User user;
    private String password;
    private  String username;


    //权限集合
    private List<GrantedAuthority> authorities;
    //账户是否失效
    private  boolean accountNonExpired = true;
//账户是否锁定
    private  boolean accountNonLocked = true;
//密码是否失效
    private  boolean credentialsNonExpired = true;
//是否可用
    private  boolean enabled = true;


}
