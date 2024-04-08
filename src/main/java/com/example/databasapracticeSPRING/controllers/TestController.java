package com.example.databasapracticeSPRING.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
//    @GetMapping("/age/{yearOfBirth}")
//    ResponseEntity<String> age(@RequestParam("yearOfBirth") int yearOfBirth) {
//        if (yearOfBirth == 0) {
//            return ResponseEntity.badRequest()
//                    .body("Year of birth cannot be in the future");
//        }
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body("Your age is " + yearOfBirth);
//    }

    @GetMapping("/api/foos")
    @ResponseBody
    public String getFoos(@RequestParam String id) {
        return "ID: " + id;
    }
}
