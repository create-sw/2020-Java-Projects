package com.jxlg.dao;

import com.jxlg.domain.Member;
import com.jxlg.domain.Orders;
import com.jxlg.domain.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface IOrdersDao {
    @Select("select * from orders")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.jxlg.dao.IProductDao.findById")),



    })
    public List<Orders> findAll() throws Exception;
    //多表操作
    @Select("select * from orders where id=#{ordersId}")
    @Results({
            @Result(id = true,property = "id",column = "id"),
            @Result(property = "orderNum",column = "orderNum"),
            @Result(property = "orderTime",column = "orderTime"),
            @Result(property = "orderStatus",column = "orderStatus"),
            @Result(property = "peopleCount",column = "peopleCount"),
            @Result(property = "payType",column = "payType"),
            @Result(property = "orderDesc",column = "orderDesc"),
            @Result(property = "product",column = "productId",javaType = Product.class,one = @One(select = "com.jxlg.dao.IProductDao.findById")),
            @Result(property = "member" ,column = "memberId",javaType = Member.class,one = @One(select = "com.jxlg.dao.IMemberDao.findById")),
            @Result(property = "travellers", column = "id" ,javaType = java.util.List.class,many = @Many(select = "com.jxlg.dao.ITravellerDao.findByOrdersId"))
    })
    Orders findById(String ordersId) throws Exception;
}
