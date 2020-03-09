package com.jxlg.service;

import com.jxlg.domain.Role;
import com.jxlg.domain.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface IUserService extends UserDetailsService {


    List<UserInfo> findAll() throws  Exception;

    void save(UserInfo userInfo) throws Exception;

    UserInfo findById(String id) throws Exception;

    List<Role> findOtherRoles(String userId) throws Exception;

    void addRoleTOUser(String userId, String[] roleIds) throws Exception;


}
