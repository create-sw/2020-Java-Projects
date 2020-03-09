package com.jxlg.service.impl;

import com.github.pagehelper.PageHelper;
import com.jxlg.dao.IOrdersDao;
import com.jxlg.domain.Orders;
import com.jxlg.service.IOrdersService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements IOrdersService {

    @Autowired
    private IOrdersDao ordersDao;

    @Override
    public List<Orders> findAll(int page, int size) throws Exception {
        //分页处理，pageNum 页码值， pageSize 每页显示条数（写在之前！！！）
        PageHelper.startPage(page,size);
        return ordersDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) throws Exception {
        return ordersDao.findById(ordersId);
    }
}
