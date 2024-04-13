package com.example.databasapracticeSPRING.model;
import java.util.List;
import java.util.UUID;

public class Column {
    private String belongsToTable;
    private String phisical_name;
    private String data_type;
    private int field_length;
    private String key_attribute;
    private String comment;
    private UUID uuid;

    public Column(UUID uuid, String belongsToTable, String phisical_name, String data_type, int field_length, String key_attribute, String comment) {
        this.uuid = UUID.randomUUID();
        this.belongsToTable = belongsToTable;
        this.phisical_name = phisical_name;
        this.data_type = data_type;
        this.field_length = field_length;
        this.key_attribute = key_attribute;
        this.comment = comment;
    }

    public UUID getUuid() {return uuid;}

    public void setUuid() {
        this.uuid = UUID.randomUUID();
    }

    public static Column findColumn(UUID uuid, List<Column> columns) {
        for (Column column : columns) {
            if (column.getUuid().equals(uuid)) {
                return column;
            }
        }
        return null;
    }

    public static void deleteColumn(String uuidString, List<Column> columns) {
        UUID uuid = UUID.fromString(uuidString);
        Column foundColumn = findColumn(uuid, columns);
        if (foundColumn != null)
            columns.remove(foundColumn);
    }

    public void setBelongsToTable(String belongsToTable) {this.belongsToTable = belongsToTable;}
    public String getBelongsToTable() {return belongsToTable;}

    public void setName(String phisical_name) {this.phisical_name = phisical_name;}
    public String getName() {return phisical_name;}

    public void setData_type(String data_type) {this.data_type = data_type;}
    public String getData_type() {return data_type;}

    public void setField_length(int field_length) {this.field_length = field_length;}
    public int getField_length() {return field_length;}

    public void setKey_attribute(String key_attribute) {this.key_attribute = key_attribute;}
    public String getKey_attribute() {return key_attribute;}

    public void setComment(String comment) {this.comment = comment;}
    public String getComment() {return comment;}

    public String toString() {
        return "\n" + "Имя таблицы = " + belongsToTable + ", " +
                "uuid = " + uuid + ", " +
                "Имя колонки = " + phisical_name + ", " +
                "Тип данных = " + data_type + ", " +
                "Длина поля = " + field_length + ", " +
                "Атрибут ключа = " + key_attribute + ", " +
                "Комментарий = " + comment + "}";
    }
}