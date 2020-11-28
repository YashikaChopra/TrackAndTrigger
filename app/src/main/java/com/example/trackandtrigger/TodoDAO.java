package com.example.trackandtrigger;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TodoDAO {
    @Query("INSERT INTO todo(contents) VALUES('New TODO Item')")
    void create();
@Query("SELECT*FROM todo")
List<Todo> getAllTodo();
@Query("UPDATE todo SET contents=:contents WHERE id=:id")
void save(String contents, int id);

}
