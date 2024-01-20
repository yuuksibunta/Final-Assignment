package com.example.name.service;


import com.example.name.entity.Name;
import com.example.name.mapper.NameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class NameServiceTest {

    @InjectMocks
    NameService nameService;

    @Mock
    NameMapper nameMapper;

    @Test
    public void 存在するユーザーのIDを指定したときに正常にユーザーが返されること() {
        doReturn(Optional.of(new Name(1, "yuki", 29))).when(nameMapper).findById(1);
        Name actual = nameService.findById(1);
        assertThat(actual).isEqualTo(new Name(1, "yuki", 29));
    }
}
