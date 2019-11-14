package com.example.demo.mapper;

import com.example.demo.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper {

    @Insert("INSERT INTO comment(parent_id, type, commentator, gmt_create, gmt_modified, like_count,content) VALUES (#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{like_count},#{content})")
  //@Insert("INSERT INTO question(title, description, gmt_create, gmt_modified, creator, comment_count, tag) VALUES (#{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{comment_count}, #{tag})")
    void insert(Comment comment);

    @Select("select * from comment where parent_id=#{parent_id}")
    Comment findByParentId(Long parent_id);
    //private Long id;
    //    private Long parent_id;
    //    private int type;
    //    private int commentator;
    //    private Long gmt_create;
    //    private Long gmt_modified;
    //    private int like_count;
    //    private String content;
}
