package com.jxlg.controller;


import com.jxlg.domain.Product;
import com.jxlg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("product/")
public class ProductController {

    @Autowired
    private IProductService productService;

    //查询所有产品信息
    @RequestMapping("findAll.do")
    @RolesAllowed("ADMIN")
    public ModelAndView findAll() throws Exception {
        ModelAndView mv=new ModelAndView();
        List<Product> products = productService.findAll();
        mv.addObject("productList",products);
        mv.setViewName("product-list");
        return mv;
    }

    //产品添加
    @RequestMapping("/save.do")
    public String save(Product product) throws Exception {
        productService.save(product);
        return "redirect:findAll.do";
    }



}
