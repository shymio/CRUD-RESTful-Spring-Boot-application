package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.model.Table;
import com.example.databasapracticeSPRING.service.WorkWithFileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UploadController {

    private final WorkWithFileService workWithFileService;
    private final int[] columnWidthsForColumns;
    private final int[] columnWidthsForTables;

    @Autowired
    public UploadController(int[] columnWidthsForColumns, int[] columnWidthsForTables, WorkWithFileService workWithFileService) {
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
}
