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
import java.util.List;
import java.util.Map;

@RestController
public class InfoController {

    @GetMapping("/info")
    public ResponseEntity<?> getInfo(HttpSession session) {
//        Table table = (Table) session.getAttribute("table");
        List<Column> columns = (List<Column>) session.getAttribute("columns");
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
//        dataBase.addColumnsToTablesFromList(columns);
        Map<String, Object> information = new LinkedHashMap<>(); // Changed to Object type
        information.put("name", "Table");
        information.put("version", "1.0");
        information.put("javaVersion", "21");
        information.put("author", "Sofia Ushakova");
        information.put("year", "2024");
        if (dataBase != null) {
            information.put("Tables", dataBase.getTable());
            information.put("TableCount", dataBase.size());

        } else {
            information.put("Tables", "[]");
            information.put("TableCount", "0");
        }
        return ResponseEntity.ok(information);
    }
}