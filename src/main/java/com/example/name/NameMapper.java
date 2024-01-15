package com.example.name;

import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NameMapper {

    @Select("SELECT * FROM names")
    List<Name> findAll();

    @Select("SELECT * FROM names WHERE name LIKE CONCAT(#{name}, '%')")
    List<Name> findByNameStartingWith(String prefix);

    @Select("SELECT * FROM names WHERE id = #{id}")
    Optional<Name> findById(int id);

    @Insert("INSERT INTO names (name, age) VALUES (#{name}, #{age})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Name name);

    @Update("UPDATE names SET name = #{name}, age = #{age} WHERE id = #{id}")
    void update(@Param("id") int id, @Param("name") String name, @Param("age") int age);

    @Delete("DELETE FROM names WHERE id = #{id}")
    void delete(@Param("id") int id);
}
