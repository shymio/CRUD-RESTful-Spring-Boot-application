package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AddColumnController {

    @PostMapping("/add_column")
    public ResponseEntity<?> addColumn(@ModelAttribute Column column, HttpSession session) {
        List<Column> columns = (List<Column>) session.getAttribute("columns");
        if (columns != null) {
            columns.add(column);
        } else {
            columns = new ArrayList<>();
            columns.add(column);
            session.setAttribute("columns", columns);
        }
        return ResponseEntity.ok(columns);
    }
}