package com.alain.foundyou.data.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.alain.foundyou.data.database.entities.PersonEntity;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM person_table")
    Flowable<List<PersonEntity>> getAllPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<PersonEntity> persons);

    @Query("DELETE FROM person_table")
    void deleteAllUsers();

}
