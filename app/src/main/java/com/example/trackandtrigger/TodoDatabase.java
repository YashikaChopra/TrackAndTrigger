package com.example.trackandtrigger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Todo.class},version = 1)
public abstract class TodoDatabase extends RoomDatabase {
    public abstract TodoDAO todoDAO();
}
