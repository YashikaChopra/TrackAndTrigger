package com.example.trackandtrigger;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;


@Dao
public interface DiaryDAO {
    @Query("INSERT INTO diary(contents) VALUES('Date: ')")
    void create();
    @Query("SELECT*FROM diary")
    List<Diary> getAlldiary();
    @Query("UPDATE diary SET contents=:contents WHERE id=:id")
    void save(String contents, int id);
    @Query("DELETE FROM diary")
    void delete();

}

