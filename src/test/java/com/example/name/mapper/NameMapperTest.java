package com.example.name.mapper;


import com.example.name.entity.Name;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NameMapperTest {

    @Autowired
    NameMapper nameMapper;

    @Test
    @Sql(
            scripts = {"classpath:/sqlannotation/delete-names.sql", "classpath:/sqlannotation/insert-names.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
    )
    @Transactional
    void すべてのユーザーが取得できること() {
        List<Name> names = nameMapper.findAll();
        assertThat(names)
                .hasSize(3)
                .contains(
                        new Name(1, "morita", 20),
                        new Name(2, "suzuki", 30),
                        new Name(3, "ida", 40)
                );

    }
}
