package com.example.stapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.stapp.models.Data;

@Database(entities = {Data.class},version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract Datadao dataDao();

    public static volatile AppDatabase  appDatabaseInstance;

    static AppDatabase  getAppDatabase (final Context context)
    {
        if (appDatabaseInstance==null)
        {
            synchronized (AppDatabase.class)
            {
                if (appDatabaseInstance==null)
                {
                    appDatabaseInstance= Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class,"app_database")
                            .build();

                }
            }
        }
    return appDatabaseInstance;
    }

}
