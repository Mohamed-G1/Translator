package com.android.tanslatorapp.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class SavedEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "text_from")
    public String textFrom;
    @ColumnInfo(name = "text_to")
    public String textTo;
}
