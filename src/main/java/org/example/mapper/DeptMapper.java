package org.example.mapper;

import org.apache.ibatis.annotations.*;
import org.example.pojo.Dept;

import java.util.List;

@Mapper
public interface DeptMapper {

    // 封装字段名和对象属性名不一致的映射关系（配置文件中开启驼峰命名后，不需要配置下划线分割和驼峰标准的映射关系）
//    @Results(id = "deptMap",value = {
//            @Result(id = true,column = "id",property = "id"), // id = true 表示主键
//            @Result(column = "create_time",property = "createTime"), // column = "update_time" 列名
//            @Result(column = "update_time",property = "updateTime")
//    })
    @Select("select d.id ,d.name ,d.create_time ,d.update_time  from dept d ORDER by d.update_time desc;")
    public List<Dept> findAll();

    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    @Insert("insert into dept(name,create_time,update_time) values(#{name},#{createTime},#{updateTime})")
    void add(Dept dept);

    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept findById(Integer id);

    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
