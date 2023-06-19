package com.example.fussballmannschaft;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class AbstractRestController {

    protected static <T> ResponseEntity<T> getRespond(T result) {
        return ResponseEntity.ok(result);
    }

    protected static <T> ResponseEntity<T> postRespond() {
        return ResponseEntity.ok().build();
    }
}

