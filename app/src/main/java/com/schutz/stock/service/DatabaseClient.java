package com.schutz.stock.service;


import android.content.Context;

import androidx.room.Room;

import com.schutz.stock.data.AppDatabase;
import com.schutz.stock.data.entity.Allee;
import com.schutz.stock.data.entity.Emplacement;
import com.schutz.stock.data.entity.Reference;

import java.util.Arrays;
import java.util.List;

public class DatabaseClient {

    private static DatabaseClient mInstance;

    //our app database object
    private final AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "stock1").build();

        appDatabase.getOpenHelper().getWritableDatabase(); //<<<<< FORCE OPEN

    }

    public static synchronized DatabaseClient initInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(context);
            mInstance.initDatabase();
        }
        return mInstance;
    }

    public static synchronized DatabaseClient getInstance() {
        return mInstance;
    }

    private void initDatabase() {

        Thread t = new Thread() {
            public void run() {
                // si les allées existent alors la base est déjà initialisée
                List<Allee> alleeTest = appDatabase.alleeDao().getAllAllees();
                if (!alleeTest.isEmpty())
                    return;

                // Ajouter les allées
                List<Allee> allees = Arrays.asList(
                        new Allee("A"), new Allee("B"), new Allee("C")
                        , new Allee("D"), new Allee("E"), new Allee("F")
                        , new Allee("G"), new Allee("H"), new Allee("I"));
//        appDatabase.alleeDao().insertAllAllees(allees);

                // Ajouter les emplacements
                for(Allee allee : allees) {
                    appDatabase.alleeDao().insertAllee(allee);
                    for (int numEmplacement=1; numEmplacement<83; numEmplacement++) {
                        String alleeId = allee.getId();
                        if (alleeId.equals("I") && numEmplacement==57) break;
                        Emplacement empl = new Emplacement(alleeId, numEmplacement);
                        appDatabase.emplacementDao().insertEmplacement(empl);
                    }
                }
            }
        };
        t.start();
    }


/*
    public Allee getAllee(String alleeId) {

        List<Allee> allees = appDatabase.alleeDao().getAllee(alleeId);

        return !allees.isEmpty() ? allees.get(0) : null;
    }
*/

    public Reference addReference(String alleeId, int emplID, String referenceID) {
        Reference reference = new Reference(alleeId, emplID, referenceID);
        appDatabase.referenceDao().insertReference(reference);
        return reference;
    }

    public Integer getNbReference(String alleeId, int emplID) {
        return appDatabase.referenceDao().getNbReference(alleeId, emplID);
    }


/*
    private AppDatabase getAppDatabase() {
        return appDatabase;
    }
*/

}