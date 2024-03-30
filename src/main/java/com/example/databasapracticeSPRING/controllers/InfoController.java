package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpSession session) {
        Table table = (Table) session.getAttribute("table");
        Map<String, Object> information = new LinkedHashMap<>(); // Changed to Object type
        information.put("name", "Table");
        information.put("version", "1.0");
        information.put("javaVersion", "21");
        information.put("author", "Sofia Ushakova");
        information.put("year", "2024");
        if (table != null) {
            information.put("Tables", table.getColumns());
            information.put("TableCount", table.size());
        }
        else {
            information.put("Tables", "[]");
            information.put("TableCount", "0");
        }
        return ResponseEntity.ok(information);
    }
}