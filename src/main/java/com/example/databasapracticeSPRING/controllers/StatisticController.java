package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatisticController {
//    @GetMapping("/statistics")
//    public ResponseEntity<String> calculateStatistics(HttpSession session) {
//        List<Column> columns = (List<Column>) session.getAttribute("columns");
//        if (columns == null || columns.isEmpty())
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Список колонок пуст");
//
//        List<Integer> field_lengths = new ArrayList<>();
//
//    }
}
