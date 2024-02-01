package com.example.name.controller;

import com.example.name.entity.Name;
import com.example.name.exception.CustomExceptions;
import com.example.name.mapper.NameMapper;
import com.example.name.service.NameService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class NameController {
    private final NameService nameService;
    private final NameMapper nameMapper;

    public NameController(NameService nameService, NameMapper nameMapper) {
        this.nameService = nameService;
        this.nameMapper = nameMapper;
    }

    @GetMapping("/names/{id}")
    public ResponseEntity<Name> getName(@PathVariable("id") Integer id) throws CustomExceptions.NotFoundException {
        Name name = nameService.findById(id);
        return new ResponseEntity<>(name, HttpStatus.OK);
    }

    @GetMapping("/names")
    public List<Name> findByNames(@RequestParam(required = false) String startsWith) {
        if (startsWith != null && !startsWith.isEmpty()) {
            return nameMapper.findByNameStartingWith(startsWith);
        } else {
            return nameMapper.findAll();
        }
    }

    @ExceptionHandler(value = {CustomExceptions.NotFoundException.class, CustomExceptions.BadRequestException.class,})
    public ResponseEntity<Map<String, String>> handleCustomExceptions(
            RuntimeException e, HttpServletRequest request) {
        HttpStatus status = determineHttpStatus(e);
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(status.value()),
                "error", status.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity<>(body, status);
    }
    private HttpStatus determineHttpStatus(RuntimeException e) {
        if (e instanceof CustomExceptions.NotFoundException) {
            return HttpStatus.NOT_FOUND;
        } else if (e instanceof CustomExceptions.BadRequestException) {
            return HttpStatus.BAD_REQUEST;
        }
        return null;
    }

    @PostMapping("/names")
    public ResponseEntity<Name> insert(@RequestBody NameRequest nameRequest) {
        Name name = nameService.insert(nameRequest);
        return new ResponseEntity<>(name, HttpStatus.CREATED);
    }

    @PatchMapping("/names/{id}")
    public ResponseEntity<Name> updateName(
            @PathVariable("id") int id,
            @RequestBody NameRequest nameRequest) throws NotFoundException {
        Name updatedName = nameService.update(id, nameRequest.getName(), nameRequest.getAge());
        return new ResponseEntity<>(updatedName, HttpStatus.OK);
    }

    @DeleteMapping("/names/{id}")
    public ResponseEntity<NameResponse> deleteName(@PathVariable int id) throws NotFoundException {
        nameService.delete(id);

        String message = "name deleted";
        NameResponse responseBody = new NameResponse(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
