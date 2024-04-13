package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.service.WorkWithFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.example.databasapracticeSPRING.model.Column;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SaveColumnController {
    private final WorkWithFileService workWithFileService;

    @Autowired
    public SaveColumnController(WorkWithFileService workWithFileService) {
        this.workWithFileService = workWithFileService;
    }

    @GetMapping("/save_columns")
    public ResponseEntity<?> saveColumns(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session) {
        List<Column> columns = (List<Column>) session.getAttribute("columns");
        if (columns == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Данные не загружены на сайт");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        workWithFileService.writeColumnsToFile(filename, columns, columnWidths);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN)
                .build();
    }
}
