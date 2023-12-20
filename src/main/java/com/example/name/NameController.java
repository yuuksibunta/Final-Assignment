package com.example.name;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

=======
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
>>>>>>> 1c97a1300676b342a4fce1b06f0d3e4ac5025703

@RestController
@Validated
public class NameController {
<<<<<<< HEAD
=======

>>>>>>> 1c97a1300676b342a4fce1b06f0d3e4ac5025703
    private final NameService nameService;
    private final NameMapper nameMapper;

    public NameController(NameService nameService, NameMapper nameMapper) {
        this.nameService = nameService;
        this.nameMapper = nameMapper;
    }

<<<<<<< HEAD
        @GetMapping("/names/{id}")
        public Name getName(@PathVariable("id") int id) {
            return nameService.findByld(id);
        }

        @GetMapping("/names/all")
        public List<Name> getAllNames() {
            return nameMapper.findAll();
        }
        @GetMapping("/names")
        public List<Name> findByNames(@RequestParam(required = false) String startsWith) {
            if (startsWith != null && !startsWith.isEmpty()) {
                return nameMapper.findByNameStartingWith(startsWith);
            } else {
                return nameMapper.findAll();
            }
        }

        @PostMapping("/names")
        public ResponseEntity<?> insert(@RequestBody NameRequest nameRequest) {

            if (nameRequest.getName() == null || nameRequest.getName().isEmpty()) {

                Map<String, String> errorResponse = Map.of(
                        "timestamp", ZonedDateTime.now().toString(),
                        "status", String.valueOf(HttpStatus.BAD_REQUEST.value()),
                        "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "message", "Name field cannot be null or empty",
                        "path", "/names"
                );
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
            }
            Name name = nameService.insert(nameRequest.getName(), nameRequest.getAge());
            return new ResponseEntity<>(name, HttpStatus.CREATED);
        }

        @ExceptionHandler(value = NameNotFoundException.class)
        public ResponseEntity<Map<String, String>> handleNameNotFoundException(
                NameNotFoundException e, HttpServletRequest request) {
            Map<String, String> body = Map.of(
                    "timestamp", ZonedDateTime.now().toString(),
                    "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                    "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "message", e.getMessage(),
                    "path", request.getRequestURI());
            return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
        }
=======
    @GetMapping("/names/{id}")
    public Name getName(@PathVariable("id") int id) {
        return nameService.findName(id);
    }

    @GetMapping("/names/all")
    public List<Name> getAllNames() {
        return nameMapper.findAllNames();
    }

    @GetMapping("/names")
    public List<Name> findByNames(@RequestParam(required = false) String startsWith) {
        if (startsWith != null && !startsWith.isEmpty()) {
            return nameMapper.findByNameStartingWith(startsWith);
        } else {
            return nameMapper.findAllNames();
        }
    }

    @PostMapping("/names")
    public ResponseEntity<?> insert(@RequestBody NameRequest nameRequest) {

        if (nameRequest.getName() == null || nameRequest.getName().isEmpty()) {

            Map<String, String> errorResponse = Map.of(
                    "timestamp", ZonedDateTime.now().toString(),
                    "status", String.valueOf(HttpStatus.BAD_REQUEST.value()),
                    "error", HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "message", "Name field cannot be null or empty",
                    "path", "/names"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Name name = nameService.insert(nameRequest.getName(), nameRequest.getAge());
        return new ResponseEntity<>(name, HttpStatus.CREATED);
    }

    @ExceptionHandler(value = NameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNameNotFoundException(
            NameNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
>>>>>>> 1c97a1300676b342a4fce1b06f0d3e4ac5025703

}