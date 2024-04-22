package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StatisticController {
    @GetMapping("/statistics")
    public ResponseEntity<String> calculateStatistics(HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        if (dataBase == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("База данных не добавлена");
        }

        List<Table> tables = dataBase.getTable();
        List<Integer> fieldLengths = new ArrayList<>();
        for (Table table : tables) {
            List<Column> columns = table.getColumn();
            for (Column column : columns) {
                fieldLengths.add(column.getField_length());
            }
        }

        // Вычисление математического ожидания
        double sum = 0;
        for (int length : fieldLengths) {
            sum += length;
        }

        double mean = sum / fieldLengths.size();

        // Вычисление дисперсии
        double variance = 0;
        for (int length : fieldLengths) {
            variance += Math.pow(length - mean, 2);
        }
        variance /= fieldLengths.size();

        return ResponseEntity.ok().body("Математическое ожидание: " + mean + ",\nДисперсия: " + variance);
    }
}
