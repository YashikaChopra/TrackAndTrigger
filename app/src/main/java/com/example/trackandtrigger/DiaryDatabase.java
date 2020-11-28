package com.example.trackandtrigger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {com.example.trackandtrigger.Diary.class},version = 1)
public abstract class DiaryDatabase extends RoomDatabase {
    public abstract DiaryDAO diaryDAO();
}
