package com.alain.foundyou.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alain.foundyou.data.database.dao.PersonDao;
import com.alain.foundyou.data.database.entities.PersonEntity;

@Database(entities = {PersonEntity.class}, version = 1, exportSchema = false)
public abstract class FoundYouDatabase extends RoomDatabase {

    public abstract PersonDao personDao();
}
