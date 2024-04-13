package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.service.WorkWithFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SaveTableController {
    private final WorkWithFileService workWithFileService;

    @Autowired
    public SaveTableController(WorkWithFileService workWithFileService) {
        this.workWithFileService = workWithFileService;
    }

    @GetMapping("/save_tables")
    public ResponseEntity<?> saveTables(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        List<Table> tables = dataBase.getTable();
        if (tables == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Данные не загружены на сайт");
        }
        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=newcolumns.txt");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        workWithFileService.writeTablesToFile(filename, tables, columnWidths);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN)
                .build();
    }

}
