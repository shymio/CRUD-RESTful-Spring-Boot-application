package com.example.databasapracticeSPRING.controllers;

import org.springframework.ui.Model;
import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AddColumnController {


    @PostMapping("/add_column")
    public ResponseEntity<?> addService(@ModelAttribute Column column,  HttpSession session) {
        Table table = (Table) session.getAttribute("table");
        table.addColumn(column);
        return ResponseEntity.ok(table.getColumns());
    }
}