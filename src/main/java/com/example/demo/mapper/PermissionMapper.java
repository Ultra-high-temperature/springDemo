package com.example.demo.mapper;

import com.example.demo.model.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface PermissionMapper {

    @Select("SELECT * FROM permission WHERE id = #{id}")
    Permission findPermissionById(int id);

    @Update("UPDATE permission SET bantime = #{bantime} WHERE id = #{id}")
    void updataUserBeanTime(Permission userPermission);
}
