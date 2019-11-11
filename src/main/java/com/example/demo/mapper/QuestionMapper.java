package com.example.demo.mapper;

import com.example.demo.dto.QuestionDTO;
import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface QuestionMapper {
    @Insert("INSERT INTO question(title, description, gmt_create, gmt_modified, creator, comment_count, tag) VALUES (#{title}, #{description}, #{gmt_create}, #{gmt_modified}, #{creator}, #{comment_count}, #{tag})")
    public void create(Question question);

//    @Select("select * from question limit #{offset},#{size}")//带分页的版本
    @Select("select * from question")
//不带分页的版本
    List<Question> list(Integer offset, Integer size);

    @Select("select count(1) from question")
    Integer questionCount();

    @Select("select * from question where id =#{id}")
    Question getById(Integer id);

    @Select("UPDATE question SET title = #{title}, description = #{description}, tag = #{tag}, gmt_modified = #{gmt_modified} WHERE id = #{id}")
    void update(Question question);


    @Select("UPDATE question SET view_count = view_count+1 WHERE id = #{id}")
    void updateView(Integer id);
}
