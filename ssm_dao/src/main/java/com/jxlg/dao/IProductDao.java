package com.jxlg.dao;

import com.jxlg.domain.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;


import java.util.List;

public interface IProductDao {
    //查询所有的产品信息
    @Select("select * from product")
    public List<Product> findAll() throws Exception;
    //保存产品信息
    @Insert("insert into product(productNum,productName,cityName,departureTime,productPrice,productDesc,productStatus) values(#{productNum},#{productName},#{cityName},#{departureTime},#{productPrice},#{productDesc},#{productStatus})")
    void save(Product product);

    //根据id查询产品
    @Select("select * from product where id=#{id}")
    public Product findById(String id) throws Exception;
}
