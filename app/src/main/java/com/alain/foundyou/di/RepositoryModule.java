package com.alain.foundyou.di;

import com.alain.foundyou.data.repository.PersonRepositoryImpl;
import com.alain.foundyou.domain.PersonRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract PersonRepository bindPersonRepository(
            PersonRepositoryImpl personRepositoryImpl
    );
}
