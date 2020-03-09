package com.jxlg.service.impl;

import com.jxlg.dao.IPermissionDao;
import com.jxlg.domain.Permission;
import com.jxlg.service.IPermissionService;
import com.jxlg.service.IProductService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PermissionServiceImpl implements IPermissionService {


    @Autowired
    private IPermissionDao permissionDao;
    @Override
    public List<Permission> findAll() throws Exception {
        return permissionDao.findAll();
    }

    @Override
    public void save(Permission permission) throws Exception {
      permissionDao.save(permission);
    }
}
