package com.jxlg.controller;

import com.jxlg.domain.Permission;
import com.jxlg.domain.Role;
import com.jxlg.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/role")
@Controller
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/findAll.do")
    public ModelAndView findAll() throws Exception {

        ModelAndView mv = new ModelAndView();
        List<Role> roles= roleService.findAll();
        mv.addObject("roleList",roles);
        mv.setViewName("role-list");
        return  mv;
    }

    @RequestMapping("/save.do")
    public String save(Role role) throws Exception {
        roleService.save(role);
        return "redirect:findAll.do";
    }

    //根据roleId查询role，并查询陈可以添加的权限
    @RequestMapping("/findByIdAndAllPermission")
    public ModelAndView  findByIdAndAllPermission(@RequestParam(name = "id",required = true)String roleId) throws Exception {
        //根据roleId查询role
        Role role= roleService.findById(roleId);
        //根据roleId查询可以添加的权限
        List<Permission> otherPermission= roleService.findOtherPermissions(roleId);
        ModelAndView mv = new ModelAndView();
        mv.addObject("role",role);
        mv.addObject("permissionList",otherPermission);
        mv.setViewName("role-permission-add");
        return mv;
    }

    //给角色添加权限
    @RequestMapping("/addPermissionToRole")
    public String  addPermissionToRole(@RequestParam(name = "roleId",required = true)String roleId,@RequestParam(name = "ids",required = true)String[] permissionIds ) throws Exception {

        roleService.addPermissionToRole(roleId,permissionIds);

        return "redirect:findAll.do";

    }
}
