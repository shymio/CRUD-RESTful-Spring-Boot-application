package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.service.TableService;
import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.util.List;

@RestController
public class UploadController {

    private final TableService tableService;

    @Autowired
    public UploadController(TableService columnService) {
        this.tableService = columnService;
    }

    @GetMapping("/columns")
    public ResponseEntity<String> getColumnsFromFile(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session) {
        List<Column> columns = tableService.readFromFile(filename, columnWidths);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data.txt");
        Table table = new Table();
        for (Column column : columns) {
            table.addColumn(column);
        }

        session.setAttribute("table", table);

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN)
                .body(table.toString());
    }
}

// такая ссылка для считывания из файла, но пока не работает
// localhost:8080/columns?filename=columns.txt&columnWidths=3,10,10,3,8,20


