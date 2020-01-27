package com.example.stapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.stapp.models.Data;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private static final String TAG = "MainActivityViewModel";
    private Datadao datadao;
    private AppDatabase appDatabase;
    private LiveData<List<Data>> mAlldata;
    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        appDatabase = AppDatabase.getAppDatabase(application);
        datadao = appDatabase.dataDao();
        mAlldata=datadao.getAll();

    }

    public void insert(Data data) {
        new InsertAsyncTask(datadao).execute(data);
    }

    LiveData<List<Data>> getmAlldata(){
        return mAlldata;
    }
    public void delete(Data data)
    {
        new DeleteAsyncTask(datadao).execute(data);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    private class InsertAsyncTask extends AsyncTask<Data, Void, Void> {
        Datadao mDatadao;

        public InsertAsyncTask(Datadao mDatadao) {
            this.mDatadao = mDatadao;
        }

        @Override
        protected Void doInBackground(Data... data) {
            for (int i = 0; i < data.length; i++)
                mDatadao.insert(data[i]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute:row inserted ");
        }
    }

    private class DeleteAsyncTask extends AsyncTask<Data, Void, Void> {
        Datadao mDatadao;

        public DeleteAsyncTask(Datadao mDatadao) {
            this.mDatadao = mDatadao;
        }

        @Override
        protected Void doInBackground(Data... data) {
                mDatadao.delete(data[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d(TAG, "onPostExecute:row inserted ");
        }
    }
}
