package com.example.trackandtrigger;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DiaryDAO {
    @Query("INSERT INTO diary(contents) VALUES('New Diary')")
    void create();
    @Query("SELECT*FROM diary")
    List<com.example.trackandtrigger.Diary> getAlldiary();
    @Query("UPDATE diary SET contents=:contents WHERE id=:id")
    void save(String contents, int id);

}

