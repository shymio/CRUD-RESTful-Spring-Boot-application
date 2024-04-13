package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DeleteColumnController {
    @DeleteMapping("/delete_column")
    public ResponseEntity<String> deleteService(@RequestParam String uuidString, HttpSession session) {
        try {
            UUID uuid = UUID.fromString(uuidString);
            List<Column> columns = (List<Column>) session.getAttribute("columns");
            ResponseEntity<String> responseEntity = columns.deleteColumn(String.valueOf(uuid));

            if (responseEntity.getStatusCode() == HttpStatus.NOT_FOUND) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - Колонка не найдена");
            } else if (responseEntity.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 - Некорректные данные");
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(responseEntity.getBody());
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 - Некорректный формат UUID");
        }
    }

}


//    @PostMapping("/delete_column")
//    public ResponseEntity<String> deleteService(@RequestParam String uuidString, HttpSession session) {
//        try {
//            UUID uuid = UUID.fromString(uuidString);
//            Table columns = (Table) session.getAttribute("columns");
//
//            if (columns == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 - Колонк не найдена");
//            }
//
//            columns.deleteColumn(String.valueOf(uuid));
//
//            return ResponseEntity.ok("Колонка удалена успешно");
//
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("400 - Некорректный формат UUID");
//        }
//    }
