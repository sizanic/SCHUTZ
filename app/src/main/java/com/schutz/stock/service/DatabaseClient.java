package com.schutz.stock.service;


import android.content.ContentValues;
import android.content.Context;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import androidx.room.Room;

import com.schutz.stock.data.AppDatabase;
import com.schutz.stock.data.entity.Allee;
import com.schutz.stock.data.entity.Emplacement;
import com.schutz.stock.data.entity.Reference;

import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

public class DatabaseClient {

    private static DatabaseClient mInstance;

    //our app database object
    private final AppDatabase appDatabase;

    private DatabaseClient(Context mCtx) {

//        File databaseDir = new File(Environment.getExternalStorageDirectory(), "SCHUTZ-data");
//        File databaseDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath(), "SCHUTZ-data");

//        File externalDir = mCtx.getExternalFilesDir(null); // Obtenez le répertoire de stockage externe de l'application
//        File databaseDir = new File(externalDir, "SCHUTZ-data"); // Créez un sous-répertoire pour la base de données

// Assurez-vous que le répertoire des bases de données existe
        String dbName = "stock.db";
        File databaseDir = createDatabaseFile(mCtx, dbName);
//        String databasePath = createDatabaseFileExt(mCtx, dbName);
//        String databasePath = databaseDir.getPath();
/*
        if (!databaseDir.exists()) {
            databaseDir.mkdirs();
        }
*/


        String databasePath = databaseDir.getAbsolutePath();

// Utilisez databasePath lors de la création de la base de données ou de la configuration de sa connexion
//        SQLiteDatabase database = SQLiteDatabase.openDatabase(databasePath, null, SQLiteDatabase.OPEN_READWRITE | SQLiteDatabase.CREATE_IF_NECESSARY);

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, databasePath ).allowMainThreadQueries().build();

        try {
            appDatabase.getOpenHelper().getWritableDatabase(); //<<<<< FORCE OPEN
        }
        catch (Exception e){
            e.printStackTrace();
        }

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

        //Thread t = new Thread()
        {
//            public void run() {
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
                //  l’allée ‘I’ on commence par 104 jusqu’à 111, de 704 jusqu’à 711
                for(Allee allee : allees) {
                    appDatabase.alleeDao().insertAllee(allee);
                    for (int numEmplacement=100; numEmplacement<=700; numEmplacement+=100) {
                        String alleeId = allee.getId();
                        int i=0;
                        if (alleeId.equals("I")) i = 4;
                        for (; i<=11; i++) {
                            Emplacement empl = new Emplacement(alleeId, numEmplacement+i);
                            appDatabase.emplacementDao().insertEmplacement(empl);
                        }
                    }
                }
//            }
        };
        //t.start();
    }

    public static File getExternalStorageDirectory(Context context, boolean isRemovable) {
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();

        for (StorageVolume volume : storageVolumes) {
            if (volume.isRemovable() == isRemovable) {
                File path = volume.getDirectory();
                if (path != null) {
                    return path;
                }
            }
        }
        return null;
    }

    public File createDatabaseFile(Context context, String fileName) {
//        File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
        File sdCard = getExternalStorageDirectory(context, true);
        if (sdCard != null) {
            File dbDir = new File(sdCard, "Android/media/SCHUTZ");
//            File dbDir = new File(sdCard, "Documents/SCHUTZ/DATABASE");
            if (!dbDir.exists() && !dbDir.mkdirs()) {
                return null; // Failed to create directory
            }
            File dbFile = new File(dbDir, fileName);
            try {
                if (dbFile.exists() || dbFile.createNewFile()) {
                    return dbFile;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // SD card is not available
    }


    private File createDatabaseFileMedia(Context context, String fileName) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File externalStorageDir = context.getExternalFilesDir(null);
            if (externalStorageDir != null) {
                File dbDir = new File(externalStorageDir, "stock_SCHUTZ");
                if (!dbDir.exists() && !dbDir.mkdirs()) {
                    return null; // Failed to create directory
                }
                File dbFile = new File(dbDir, fileName);
                try {
                    if (dbFile.createNewFile() || dbFile.exists()) {
                        return dbFile;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null; // External storage is not available
    }

    private String createDatabaseFileExt(Context context, String fileName) {
//        String relativeLocation = Environment.getExternalStorageDirectory() + "/stock_SCHUTZ";
        String relativeLocation = Environment.DIRECTORY_DOCUMENTS + "/stock_SCHUTZ";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/octet-stream");
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, relativeLocation);

        Uri uri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), contentValues);
        if (uri != null) {
            try (OutputStream outputStream = context.getContentResolver().openOutputStream(uri)) {
                if (outputStream != null) {
                    // Just to create the file, no content to write initially
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return getPathFromUri(context, uri);
    }

    private String getPathFromUri(Context context, Uri uri) {
        // Convert the Uri to the actual file path
        String filePath = null;
        if ("content".equals(uri.getScheme())) {
            String[] projection = { MediaStore.MediaColumns.DATA };
            try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                    filePath = cursor.getString(columnIndex);
                }
            }
        } else if ("file".equals(uri.getScheme())) {
            filePath = uri.getPath();
        }
        return filePath;
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