package com.example.databasapracticeSPRING;
import org.apache.commons.lang3.time.DateUtils;
import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private String databaseName;
    private List<Table> tables = new ArrayList<>();
    public DataBase(String databaseName) {
        this.databaseName = databaseName;
        this.tables = new ArrayList<>();
    }

    public DataBase() {
        this.tables = new ArrayList<>();
    }

    public int getTableIndexByName(String tableName) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getPhisical_name().equals(tableName)) {
                return i;
            }
        }
        return -1; // Возвращаем -1, если таблица с таким именем не найдена
    }

    public void setDatabaseName(String databaseName)
    {
        this.databaseName = databaseName;
    }

    public String getDatabaseName()
    {
        return databaseName;
    }

    public ArrayList<Table> getTable() {
        return tables;
    }

    public Table getTable (int index) {
        if (index >= 0 && index < tables.size()) {
            return tables.get(index);
        } else {
            System.out.println("Индекс за пределами диапазона");
            return null;
        }
    }

    public String toString() {
        return "База данных {" + "Имя БД = " + databaseName + ", "
                + "Таблицы: " + tables + "}";
    }

    public void addTable(Table table) {
        this.tables.add(table);
    }
    public void addTable(String phisical_name, String description, String date_time) {
        Table one = new Table(phisical_name, description, date_time);
        tables.add(one);
    }

    public void removeTable(String tableName) {
        for (int i = 0; i < tables.size(); i++) {
            if (tables.get(i).getPhisical_name().equals(tableName)) {
                tables.remove(i);
                break;
            }
        }
    }
}
