package com.example.databasapracticeSPRING.model;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataBase {
    private List<Table> tables = new ArrayList<>();

    public DataBase() {
        this.tables = new ArrayList<>();
    }

    public List<Table> getTable() {
        return tables;
    }

    public int size() {
        return tables.size();
    }

    public void removeColumnFromTable(String tableName, String columnName) {
        // Поиск таблицы по имени
        Table foundTable = null;
        for (Table table : tables) {
            if (table.getPhisical_name().equals(tableName)) {
                foundTable = table;
                break;
            }
        }
        // Проверка, найдена ли таблица
        if (foundTable == null) {
            throw new IllegalArgumentException("Таблица " + tableName + " не найдена.");
        }
        if (foundTable.getColumn(columnName) == null) {
            throw new IllegalArgumentException("Колонка " + columnName + " не найдена в таблице " + tableName + ".");
        }
        // Удаление колонки
        foundTable.removeColumn(columnName);
    }

    public Table findTable(String tableName) {
        for (Table table : tables) {
            if (table.getPhisical_name().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    public void removeTableByName(String tableName) {
        Table tableToRemove = findTable(tableName);
        if (tableToRemove != null) {
            tables.remove(tableToRemove);
        }
    }

    public boolean hasTableWithName(Column column) {
        for (Table table : tables) {
            if (column.getBelongsToTable().equals(table.getPhisical_name()))
                return true;
        } return false;
    }

    // добавить одну колонку из списка в таблицу по имени
    public void addOneColumnToTable(Column column) {
        if (findTable(column.getBelongsToTable()) != null)
            findTable(column.getBelongsToTable()).addColumn(column);
    }

    public void addColumnsToTablesFromList(List<Column> columns) {
        for (Column column : columns) {
            for (Table table : tables) {
                if (table.getPhisical_name().equals(column.getBelongsToTable())) {
                    table.addColumn(column);
                    break;
                }
            }
        }
    }

//    public ResponseEntity<Table> showTable(String tableName) {
//        try {
//            Table foundService = findTable(tableName);
//            if (foundService != null) {
//                return ResponseEntity.ok(foundService);
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//    }

    public void setTable(List<Table> tables) {this.tables = tables;}

//    public Table getTable (int index) {
//        if (index >= 0 && index < tables.size()) {
//            return tables.get(index);
//        } else {
//            System.out.println("Индекс за пределами диапазона");
//            return null;
//        }
//    }

//    public int getTableIndexByName(String tableName) {
//        for (int i = 0; i < tables.size(); i++) {
//            if (tables.get(i).getPhisical_name().equals(tableName)) {
//                return i;
//            }
//        }
//        return -1; // Возвращаем -1, если таблица с таким именем не найдена
//    }

    public String toString() {
        return "База данных {Таблицы: " + tables + "}";
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }

//    public void removeTable(String tableName) {
//        for (int i = 0; i < tables.size(); i++) {
//            if (tables.get(i).getPhisical_name().equals(tableName)) {
//                tables.remove(i);
//                break;
//            }
//        }
//    }

}
