package com.example.trackandtrigger;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TODO")
public class Todo {
    @PrimaryKey
    public int id;
    @ColumnInfo(name="contents")
    public String contents;
}
