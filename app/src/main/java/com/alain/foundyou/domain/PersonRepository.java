package com.alain.foundyou.domain;

import com.alain.foundyou.data.network.model.Person;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface PersonRepository {
    Flowable<List<Person>> getPersons();
    Completable refreshPersons();
}
