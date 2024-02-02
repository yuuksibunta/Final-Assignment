package com.example.name.service;

import com.example.name.controller.NameRequest;
import com.example.name.entity.Name;
import com.example.name.exception.CustomExceptions;
import com.example.name.mapper.NameMapper;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;


import java.util.Objects;
import java.util.Optional;

@Service
public class NameService {

    private final NameMapper nameMapper;

    public NameService(NameMapper nameMapper) {
        this.nameMapper = nameMapper;
    }

    public Name findById(Integer id) throws CustomExceptions.NotFoundException {
        Optional<Name> optionalName = nameMapper.findById(id);
        return optionalName.orElseThrow(() -> new CustomExceptions.NotFoundException("指定されたIDの名前は存在しません"));
    }

    public Name insert(NameRequest nameRequest) throws CustomExceptions.BadRequestException {
        validateNameRequest(nameRequest);

        Name newName = nameRequest.convertToName();
        nameMapper.insert(newName);
        return newName;
    }
    private void validateNameRequest(NameRequest nameRequest) throws CustomExceptions.BadRequestException {
        if (StringUtils.isAnyBlank(nameRequest.getName()) || Objects.isNull(nameRequest.getAge())) {
            throw new CustomExceptions.BadRequestException("必須フィールドが入力されていません");
        }
    }

    public Name update(int id, String newName, int newAge) throws NotFoundException {
        Optional<Name> optionalExistingName = nameMapper.findById(id);

        if (optionalExistingName.isPresent()) {
            Name existingName = optionalExistingName.get();
            existingName.setName(newName);
            existingName.setAge(newAge);

            nameMapper.update(existingName.getId(), newName, newAge);

            return existingName;
        } else {
            throw new NotFoundException("指定されたIDの名前は存在しません");
        }
    }

    public void delete(int id) throws NotFoundException {
        Optional<Name> optionalExistingName = nameMapper.findById(id);

        if (optionalExistingName.isPresent()) {
            nameMapper.delete(id);
        } else {
            throw new NotFoundException("指定されたIDの名前は存在しません");
        }
    }
}
