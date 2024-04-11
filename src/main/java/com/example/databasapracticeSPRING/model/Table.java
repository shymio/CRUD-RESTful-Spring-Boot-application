package com.example.databasapracticeSPRING.model;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.UUID;

import com.example.databasapracticeSPRING.model.Column;
import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Table {
    private String phisical_name;
    private String description;
    private String creationDate;
    private List<Column> columns = new ArrayList<>();

    public Table(String phisical_name, String description, String date_time) {
        this.phisical_name = phisical_name;
        this.description = description;
        this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));
        this.columns = new ArrayList<>();
    }

    public Table() {this.columns = new ArrayList<>();}

    public void setPhisical_name(String phisical_name) {this.phisical_name = phisical_name;}
    public String getPhisical_name() {return phisical_name;}

    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}

    public void setCreationDate() {this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));}
    public String getCreationDate() {return creationDate;}

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date);
    }

    public int size() {
        return columns.size();
    }

    public void setColumn(List<Column> columns) {this.columns = columns;}

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public List<Column> getColumn() {
        return columns;
    }

    public Column findColumn(UUID uuid) {
        for (Column column : columns) {
            if (column.getUuid().equals(uuid)) {
                return column;
            }
        }
        return null;
    }

    public ResponseEntity<Column> showColumn(UUID uuid) {
        try {
            Column foundService = findColumn(uuid);
            if (foundService != null) {
                return ResponseEntity.ok(foundService);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    public Column getColumn(int index) {
        if (index >= 0 && index < columns.size()) {
            return columns.get(index);
        } else {
            System.out.println("Индекс за пределами диапазона");
            return null;
        }
    }

    public void removeColumn(int index) {
        if (index >= 0 && index < columns.size()) {
            columns.remove(index);
            System.out.println("Колонка " + (index + 1) + " успешно удалена.");
        } else {
            System.out.println("Указанный индекс выходит за границы списка.");
        }
    }

    public void removeColumn(String columnName) {
        for (int i = 0; i < columns.size(); i++) {
            if (columns.get(i).getName().equals(columnName)) {
                columns.remove(i);
                break;
            }
        }
    }

    public String toString() {
        return "\n" + "Имя таблицы = " + phisical_name + ' '
                + "Описание = " + description + " "
                + "Дата и время создания = " + creationDate + " "
                + "Колонки " + columns;
    }
}