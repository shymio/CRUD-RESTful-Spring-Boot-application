package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.model.DataBase;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

public class ShowController {

    @PostMapping("/show")
    public ResponseEntity<Column> showService(@RequestParam("uuid") String uuidString, HttpSession session) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            Table table = (Table) session.getAttribute("table");
            ResponseEntity<Column> response = table.showColumn(uuid);

            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                return response;
            } else {
                return response;
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
