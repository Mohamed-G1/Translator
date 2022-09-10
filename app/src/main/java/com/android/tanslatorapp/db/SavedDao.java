package com.android.tanslatorapp.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SavedDao {

    @Query("SELECT * FROM savedentity")
    List<SavedEntity> getAllSaved();

    @Insert
    void insert(SavedEntity... savedEntities);

    @Delete
    void delete(SavedEntity savedEntity);

}
