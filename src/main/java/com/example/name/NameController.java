package com.example.name;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Name> getName(@PathVariable("id") int id) throws ResourceNotFoundException {
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

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(
            ResourceNotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/names")
    public ResponseEntity<Name> insert(@RequestBody NameRequest nameRequest) {
        Name name = nameService.insert(nameRequest.getName(), nameRequest.getAge());
        return new ResponseEntity<>(name, HttpStatus.CREATED);
    }

    @PatchMapping("/names/{id}")
    public ResponseEntity<Name> updateName(
            @PathVariable("id") int id,
            @RequestBody NameRequest nameRequest) {
        Name updatedName = nameService.update(id, nameRequest.getName(), nameRequest.getAge());
        return new ResponseEntity<>(updatedName, HttpStatus.OK);
    }

    @DeleteMapping("/names/{id}")
    public ResponseEntity<NameResponse> deleteName(@PathVariable int id) {
        nameService.delete(id);

        String message = "name deleted";
        NameResponse responseBody = new NameResponse(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(responseBody, headers, HttpStatus.OK);
    }
}
