package com.jxlg.dao;

import com.jxlg.domain.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ISysLogDao {
    @Insert("insert into sysLog(visitTime,username,ip,url,executionTime,method) values(#{visitTime},#{username},#{ip},#{url},#{executionTime},#{method})")
    public void save(SysLog sysLog)throws Exception;

    @Select("select * from sysLog")
    List<SysLog> findAll() throws Exception;
}
