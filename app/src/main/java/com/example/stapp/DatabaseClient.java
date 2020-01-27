package com.example.stapp;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {
    Context mContext;
    private static DatabaseClient mDatabaseClient;
    AppDatabase mAppDatabase;

    public DatabaseClient(Context mContext) {
        this.mContext = mContext;

        mAppDatabase= Room.databaseBuilder(mContext,AppDatabase.class,"empdata").build();
    }
    public static synchronized DatabaseClient getInstance(Context mContext)
    {
        if (mDatabaseClient==null)
        {
            mDatabaseClient=new DatabaseClient(mContext);
        }
        return mDatabaseClient;
    }
    public AppDatabase getAppDatabase()
    {
        return mAppDatabase;
    }
}
