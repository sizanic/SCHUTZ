package com.schutz.stock.service;


import android.content.Context;

import androidx.room.Room;

import com.schutz.stock.data.AppDatabase;
import com.schutz.stock.data.entity.Allee;
import com.schutz.stock.data.entity.Emplacement;
import com.schutz.stock.data.entity.Reference;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class DatabaseClient {

    private static DatabaseClient mInstance;

    //our app database object
    private final AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "stock3").allowMainThreadQueries().build();

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
    public List<Allee> getAllees() {
        return appDatabase.alleeDao().getAllAllees();
    }


    public Reference addReference(@NotNull String alleeId, int emplID, @NotNull String referenceID, long time) {
        Reference reference = new Reference(alleeId, emplID, referenceID, time);
        try {
            appDatabase.referenceDao().insertReference(reference);
        } catch (Exception e) {
            return null;
        }
        return reference;
    }

    public Reference removeReference(@NotNull String alleeId, int emplID, @NotNull String referenceID, long time) {
        Reference reference = new Reference(alleeId, emplID, referenceID, time);
        try {
            appDatabase.referenceDao().removeReference(reference);
        } catch (Exception e) {
            return null;
        }
        return reference;
    }

    public Boolean removeReference(@NotNull Reference reference) {
        try {
            appDatabase.referenceDao().removeReference(reference);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public Reference searchReference(@NotNull String refID) {
        try {
            return appDatabase.referenceDao().searchReference(refID);
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getNbEmplacement(@NotNull String alleeId) {
        return appDatabase.referenceDao().getNbEmplacement(alleeId);
    }

    public Integer getNbReference(@NotNull String alleeId) {
        return appDatabase.referenceDao().getNbReferenceFromAllee(alleeId);
    }

    public Integer getNbReference(@NotNull String alleeId, @NotNull Integer emplID) {
        return appDatabase.referenceDao().getNbReference(alleeId, emplID);
    }

    public Reference getReference(@NotNull String alleeId, @NotNull Integer emplID) {
        return appDatabase.referenceDao().getReference(alleeId, emplID);
    }

    @NotNull
    public List<Emplacement> getEmplacementsFromAllee(@NotNull String alleeId) {
        return appDatabase.emplacementDao().getEmplacementsFromAllee(alleeId);
    }

    public List<Emplacement> getEmplacementsLibres(@NotNull String alleeId) {
        List<Emplacement> valempls = appDatabase.emplacementDao().getEmplacementsFromAllee(alleeId);
        List<Emplacement> valemplOccupes = appDatabase.emplacementDao().getEmplacementsOccupes(alleeId);
        valempls.removeAll(valemplOccupes);
        return valempls;
    }

    public List<Emplacement> getEmplacementsOccupes(@NotNull String alleeId) {
        List<Emplacement> valemplOccupes = appDatabase.emplacementDao().getEmplacementsOccupes(alleeId);
        return valemplOccupes;
    }


/*
    private AppDatabase getAppDatabase() {
        return appDatabase;
    }
*/

}