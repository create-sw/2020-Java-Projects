package com.jxlg.controller;

import com.jxlg.domain.Role;
import com.jxlg.domain.UserInfo;
import com.jxlg.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    //查询所有用户
    @RequestMapping("findAll.do")
    public ModelAndView findAll() throws Exception {

        ModelAndView mv = new ModelAndView();
        List<UserInfo> userInfos = userService.findAll();
        mv.addObject("userList",userInfos);
        mv.setViewName("user-list");
        return mv;
    }

    //用户添加
    @RequestMapping("/save.do")
    public String  save(UserInfo userInfo) throws Exception {
        userService.save(userInfo);
        return "redirect:findAll.do";
    }


    //查询指定id的用户
    @RequestMapping("/findById")
    public ModelAndView findById( String id) throws Exception {
        ModelAndView mv = new ModelAndView();
         UserInfo userInfo= userService.findById(id);
         mv.addObject("user",userInfo);
         mv.setViewName("user-show");
        return mv;
    }

    //查询用户以及用户可以添加的角色
    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView  findUserByIdAndAllRole(@RequestParam(name = "id",required = true)String  userId) throws Exception {
    //根据用户id查询用户
        UserInfo userInfo =userService.findById(userId);
        //根据用户id查询可以添加的角色
        List<Role> otherRoles= userService.findOtherRoles(userId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("user",userInfo);
        mv.addObject("roleList",otherRoles);
        mv.setViewName("user-role-add");
        return mv;
    }

    //给用户添加角色
    @RequestMapping("/addRoleToUser.do")
    public String  addRoleToUser(@RequestParam(name = "userId",required = true)String userId,@RequestParam(name = "ids",required = true)String[] roleIds) throws Exception {

        userService.addRoleTOUser(userId,roleIds);

        return "redirect:findAll.do";

    }
}
