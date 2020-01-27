package com.example.stapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.stapp.models.Data;

import java.util.List;

@Dao
public interface Datadao {
    @Query("Select * From Data")
    LiveData<List<Data>> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Data task);

   @Delete
    int delete(Data task);

    @Update
    void update(Data task);
}
