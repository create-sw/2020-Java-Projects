package com.jxlg.service.impl;

import com.jxlg.dao.IUserDao;
import com.jxlg.domain.Role;
import com.jxlg.domain.UserInfo;
import com.jxlg.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfo userInfo = null;
        try {
             userInfo= userDao.findByUsername(username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        User user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus()==0 ? false:true,true,true,true,getAuthority(userInfo.getRoles()));

        return user;
    }

    private List<SimpleGrantedAuthority> getAuthority(List<Role> roles) {

        List<SimpleGrantedAuthority> list =new ArrayList<>();
        for (Role role:roles)
        list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        return list;
    }

    @Override
    public List<UserInfo> findAll() throws Exception {
        return userDao.findAll();
    }

    @Override
    public void save(UserInfo userInfo) throws Exception {
        //对密码进行加密
       userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
         userDao.save(userInfo);
    }

    @Override
    public UserInfo findById(String id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<Role> findOtherRoles(String userId) throws Exception {
        return userDao.findOtherRoles(userId);
    }

    @Override
    public void addRoleTOUser(String userId, String[] roleIds) throws Exception {
        for (String roleId:roleIds){
            userDao.addRoleToUser(userId,roleId);
        }
    }


}
