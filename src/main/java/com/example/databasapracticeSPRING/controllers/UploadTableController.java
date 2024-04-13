
package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.service.WorkWithFileService;
import com.example.databasapracticeSPRING.model.Table;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
public class UploadTableController {

    private final WorkWithFileService workWithFileService;

    @Autowired
    public UploadTableController(WorkWithFileService workWithFileService) {
        this.workWithFileService = workWithFileService;
    }

    @PostMapping("/tables")
    public ResponseEntity<String> getColumnsFromFile(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session) {
        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
        List<Table> tables = workWithFileService.readFromFileTable(filename, columnWidths);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");

        if (dataBase != null) {
            for (Table table : tables) {
                dataBase.addTable(table);
            }
        } else {
            dataBase = new DataBase();
            for (Table table : tables) {
                dataBase.addTable(table);
            }
        }
        session.setAttribute("dataBase", dataBase);
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.TEXT_PLAIN)
                .body(dataBase.toString());


//        List<Table> tables = tableService.readFromFileTable(filename, columnWidths);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
//        DataBase dataBase = new DataBase();
//        for (Table table : tables) {
//            dataBase.addTable(table);
//        }
//        session.setAttribute("dataBase", dataBase);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentType(MediaType.TEXT_PLAIN)
//                .body(dataBase.toString());
    }
}

// такая ссылка для считывания из файла, но пока не работает
// localhost:8080/columns?filename=columns.txt&columnWidths=3,10,10,3,8,20
