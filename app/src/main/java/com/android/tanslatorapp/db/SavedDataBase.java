package com.android.tanslatorapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {SavedEntity.class}, version = 1)
public abstract class SavedDataBase extends RoomDatabase {

    public abstract SavedDao savedDao();
    private static SavedDataBase INSTANCE;
    public static SavedDataBase getINSTANCE(Context context){
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), SavedDataBase.class, "DB_NAME")
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}
