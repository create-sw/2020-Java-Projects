package com.jxlg.service.impl;

import com.jxlg.dao.IProductDao;
import com.jxlg.domain.Product;
import com.jxlg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ProductServiceImpl implements IProductService {
//查询产品所有信息
    @Autowired
    private IProductDao productDao;
    @Override
    public List<Product> findAll() throws Exception {

        return   productDao.findAll();
    }
//保存产品信息
    @Override
    public void save(Product product) {
        productDao.save(product);
    }
}
