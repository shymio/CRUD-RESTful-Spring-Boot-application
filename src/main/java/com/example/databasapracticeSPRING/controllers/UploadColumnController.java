
package com.example.databasapracticeSPRING.controllers;

import com.example.databasapracticeSPRING.model.DataBase;
import com.example.databasapracticeSPRING.service.TableService;
import com.example.databasapracticeSPRING.model.Column;
import com.example.databasapracticeSPRING.model.Table;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;


import java.util.Enumeration;
import java.util.List;

@RestController
public class UploadColumnController {

    private final TableService tableService;

    @Autowired
    public UploadColumnController(TableService columnService) {
        this.tableService = columnService;
    }

    @GetMapping("/columns")
    public ResponseEntity<String> getColumnsFromFile(@RequestParam String filename, @RequestParam int[] columnWidths, HttpSession session) {
        List<Column> columns = tableService.readFromFileColumn(filename, columnWidths);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
        session.setAttribute("columns", columns);
        return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body("Колонки добавлены в сессию");
    }
}
//        DataBase dataBase = (DataBase) session.getAttribute("dataBase");
//        if (dataBase.size() > 0) {
//            dataBase.addColumnsToTablesFromList(columns);
//            session.setAttribute("dataBase", dataBase);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.TEXT_PLAIN)
//                    .body(dataBase.toString());
//
//        } else {
//            session.setAttribute("columns", columns);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.TEXT_PLAIN)
//                    .body("Колонки добавлены в сессию, но не добавлены в базу данных");
//        }
//    }
//}


// такая ссылка для считывания из файла, но пока не работает
// localhost:8080/columns?filename=columns.txt&columnWidths=3,10,10,3,8,20
