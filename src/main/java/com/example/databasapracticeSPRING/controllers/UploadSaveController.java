package com.example.databasapracticeSPRING.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.service.WorkWithFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UploadSaveController {
    private final WorkWithFileService workWithFileService;
    private final int[] columnWidthsForColumns;
    private final int[] columnWidthsForTables;

    @Autowired
    public UploadSaveController(int[] columnWidthsForColumns, int[] columnWidthsForTables, WorkWithFileService workWithFileService) {
        this.columnWidthsForColumns = columnWidthsForColumns;
        this.columnWidthsForTables = columnWidthsForTables;
        this.workWithFileService = workWithFileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<DataBase> uploadFromFiles(@RequestParam String fileNameColumn, @RequestParam String fileNameTable, HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        List<Column> columns = workWithFileService.readFromFileColumn(fileNameColumn, columnWidthsForColumns);
        List<Table> tables = workWithFileService.readFromFileTable(fileNameTable, columnWidthsForTables);
        if (dataBase != null) {
            for (Table table : tables)
                dataBase.addTable(table);
        } else {
            dataBase = new DataBase();
            for (Table table : tables)
                dataBase.addTable(table);
        }
        dataBase.addColumnsToTablesFromList(columns);
        session.setAttribute("dataBase", dataBase);
        return ResponseEntity.ok().body(dataBase);
    }

    @GetMapping("/save")
    public ResponseEntity<?> saveInFile(@RequestParam String fileNameColumn, @RequestParam String fileNameTable, HttpSession session) {
        if (fileNameColumn == null || fileNameTable == null) {
            // Проверка наличия хотя бы одного параметра в запросе
            if (fileNameColumn == null && fileNameTable == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: не указаны необходимые параметры в URL запросе.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ошибка: не указаны все необходимые параметры в URL запросе.");
            }
        }

        // Проверка наличия объекта базы данных в сессии
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        if (dataBase == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка: объект базы данных не найден в сессии.");
        }

        List<Column> allColumns = new ArrayList<>();
        for (Table table : dataBase.getTable()) {
            List<Column> columns = table.getColumn();
            allColumns.addAll(columns);
        }

        try {
            workWithFileService.writeColumnsToFile(fileNameColumn, allColumns, columnWidthsForColumns);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при записи колонок в файл: " + e.getMessage());
        }

        try {
            workWithFileService.writeTablesToFile(fileNameTable, dataBase.getTable(), columnWidthsForTables);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка при записи таблиц в файл: " + e.getMessage());
        }
        return ResponseEntity.ok("Данные успешно сохранены");
    }
}
