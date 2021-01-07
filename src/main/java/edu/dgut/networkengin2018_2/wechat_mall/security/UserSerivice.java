package edu.dgut.networkengin2018_2.wechat_mall.security;

import edu.dgut.networkengin2018_2.wechat_mall.dao.MyUserMapper;
import edu.dgut.networkengin2018_2.wechat_mall.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserSerivice implements UserDetailsService {
    @Autowired
    private MyUserMapper myUserMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser myUser= myUserMapper.findByUserName(username);
        if(myUser==null){
            throw new UsernameNotFoundException("用户不存在");
        }

        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        GrantedAuthority sg = new SimpleGrantedAuthority(myUser.getAuthority());
        authorityList.add(sg);

        User user = new User(myUser.getUserName(),"{noop}"+myUser.getPassword(),authorityList);
        return user;
    }

}
