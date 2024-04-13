package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class DeleteColumnController {

    @DeleteMapping("/delete_column")
    public ResponseEntity<String> deleteColumn(@RequestParam String uuidString, HttpSession session) {
        List<Column> columns = (List<Column>) session.getAttribute("columns");
        if (columns == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Список колонок не найден в сессии");
        }

        try {
            UUID uuid = UUID.fromString(uuidString);
            Column column = Column.findColumn(uuid, columns);
            if (column == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Колонка с указанным UUID не найдена");
            }

            Column.deleteColumn(uuidString, columns);
            return ResponseEntity.ok("Колонка успешно удалена");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Некорректный формат UUID");
        }
    }
}