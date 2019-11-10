package com.example.demo.service;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdata(User user) {
        User db_user=userMapper.findByAccount_id(user.getAccount_id());
        if (db_user==null){ //判断数据库内是否存在对应的用户记录
            //不存在则插入
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }
        else {
            //存在则更新对应的用户记录
            db_user.setGmt_modified(System.currentTimeMillis());
            db_user.setLogin_name(user.getLogin_name());
            db_user.setToken(user.getToken());
            userMapper.update(db_user);
        }
    }
}
