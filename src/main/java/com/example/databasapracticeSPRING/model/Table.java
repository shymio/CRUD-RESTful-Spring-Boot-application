package com.example.databasapracticeSPRING.model;

import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.List;

public class Table {
    private String phisical_name;
    private String description;
    private String creationDate;
    private List<Column> columns = new ArrayList<>();

    public Table(String phisical_name, String description, String creationDate) {
        this.phisical_name = phisical_name;
        this.description = description;
        this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));
        this.columns = new ArrayList<>(); }

    public Table() {this.columns = new ArrayList<>();}

    public void setPhisical_name(String phisical_name) {this.phisical_name = phisical_name;}
    public String getPhisical_name() {return phisical_name;}

    public void setDescription(String description) {this.description = description;}
    public String getDescription() {return description;}

    public void setCreationDate() {this.creationDate = formatDate(DateUtils.truncate(new Date(), Calendar.MINUTE));}
    public String getCreationDate() {return creationDate;}

    public String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return dateFormat.format(date); }

    public int size() {return columns.size();}

    public void setColumn(List<Column> columns) {this.columns = columns;}

    public void addColumn(Column column) {this.columns.add(column);}

    public void removeColumn(String columnName) {columns.removeIf(column -> column.getName().equals(columnName));}

    public List<Column> getColumn() {return columns;}

    public Column findColumn(UUID uuid) {
        for (Column column : columns) {
            if (column.getUuid().equals(uuid)) {
                return column;}
        } return null;
    }

    public Column getColumn(String columnName) {
        for (Column column : columns) {
            if (column.getName().equals(columnName)) {
                return column; }
        } return null;
    }

    public String toString() {
        return "\n" + "Имя таблицы = " + phisical_name + ' '
                + "Описание = " + description + " "
                + "Дата и время создания = " + creationDate + " "
                + "Колонки " + columns;
    }
}