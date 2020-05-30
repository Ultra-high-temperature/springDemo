package com.example.demo.service;

import com.example.demo.mapper.PermissionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PermissionService {

    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    UserMapper userMapper;
    public int banUser(int adminId, int userId , Date bean){
        Permission adminPermission = permissionMapper.findPermissionById(adminId);
        Permission userPermission = permissionMapper.findPermissionById(userId);
        if(adminPermission.getPermissions()>userPermission.getPermissions()){
            userPermission.setBantime(bean);
            permissionMapper.updataUserBeanTime(userPermission);
            return 1;
        }
        return 0;
    }

    public Permission findPermissionInfo(int userId){
        Permission permission = permissionMapper.findPermissionById(userId);
        return permission;
    }
}
