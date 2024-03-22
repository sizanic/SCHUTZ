package com.schutz.stock.service;


import android.content.Context;

import androidx.room.Room;

import com.schutz.stock.data.AppDatabase;
import com.schutz.stock.data.entity.Allee;

import java.util.List;

public class DatabaseClient {

    private Context mCtx;
    private static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos").build();
    }

    public static synchronized DatabaseClient initInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public static synchronized DatabaseClient getInstance() {
        return mInstance;
    }

    public Allee getAllee(String alleeId) {

        List<Allee> allees = appDatabase.alleeDao().getAllee(alleeId);

        return !allees.isEmpty() ? allees.get(0) : null;
    }

/*
    private AppDatabase getAppDatabase() {
        return appDatabase;
    }
*/

}