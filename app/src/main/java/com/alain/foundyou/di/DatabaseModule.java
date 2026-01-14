package com.alain.foundyou.di;

import android.content.Context;

import androidx.room.Room;

import com.alain.foundyou.data.database.FoundYouDatabase;
import com.alain.foundyou.data.database.dao.PersonDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    public PersonDao providePersonDao(FoundYouDatabase appDatabase) {
        return appDatabase.personDao();
    }

    @Provides
    @Singleton
    public FoundYouDatabase provideAppDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                        context,
                        FoundYouDatabase.class,
                        "found_you_database"
                )
                .build();
    }
}
