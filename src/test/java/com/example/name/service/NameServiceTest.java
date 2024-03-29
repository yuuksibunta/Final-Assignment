package com.example.name.service;

import com.example.name.controller.NameRequest;
import com.example.name.entity.Name;
import com.example.name.exception.NameBadRequestException;
import com.example.name.exception.NameNotFoundException;
import com.example.name.mapper.NameMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class NameServiceTest {

    @InjectMocks
    NameService nameService;

    @Mock
    NameMapper nameMapper;

    @Test
    public void 存在するユーザーのIDを指定したときに正常にユーザーが返されること() throws NameNotFoundException {
        doReturn(Optional.of(new Name(1, "yuki", 29))).when(nameMapper).findById(1);
        Name actual = nameService.findById(1);
        assertThat(actual).isEqualTo(new Name(1, "yuki", 29));
        verify(nameMapper).findById(1);
    }

    @Test
    public void 存在しないユーザーのIDを指定したときにエラーが返されること() {
        doReturn(Optional.empty()).when(nameMapper).findById(10);
        assertThrows(NameNotFoundException.class, () -> {
            nameService.findById(10);
        });
    }

    @Test
    public void 存在しないユーザーのIDを指定したときに正しいエラーメッセージが返されること() {
        doReturn(Optional.empty()).when(nameMapper).findById(10);
        Throwable exception = assertThrows(NameNotFoundException.class, () -> {
            nameService.findById(10);
        });
        assertEquals("指定されたIDの名前は存在しません", exception.getMessage());
    }

    @Test
    public void 正常に新規のユーザーが登録できること() {
        NameRequest newNameRequest = new NameRequest("kazushi", 33);
        Mockito.doNothing().when(nameMapper).insert(newNameRequest.convertToName());
        nameService.insert(newNameRequest);
        verify(nameMapper).insert(newNameRequest.convertToName());
    }

    @Test
    public void 登録に必要なデータが不足している場合はエラーが返されること() {
        assertThrows(NameBadRequestException.class, () -> nameService.insert(new NameRequest("", 33)));
        assertThrows(NameBadRequestException.class, () -> nameService.insert(new NameRequest("kazushi", null)));
        assertThrows(NameBadRequestException.class, () -> nameService.insert(new NameRequest("", null)));
    }

    @Test
    public void 登録に必要なデータが不足している場合は正しいエラーメッセージが返されること() {
        NameRequest nameRequest = new NameRequest("", null);
        NameBadRequestException exception = assertThrows(NameBadRequestException.class, () -> {
            nameService.insert(nameRequest);
        });
        assertEquals("必須フィールドが入力されていません", exception.getMessage());
    }
}
