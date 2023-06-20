package com.example.fussballmannschaft;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class PlayerRestController {

    protected static <T> ResponseEntity<T> getRespond(T result) {
        return ResponseEntity.ok(result);
    }

    protected static <T> ResponseEntity<T> postRespond() {
        return ResponseEntity.ok().build();
    }

    protected static <T> ResponseEntity<T> updateRespond(){
        return ResponseEntity.ok().build();
    }

    protected static<T> ResponseEntity<T> deleteRespond(){
        return ResponseEntity.ok().build();
    }


}

