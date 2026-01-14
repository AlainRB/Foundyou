package com.alain.foundyou.domain;

import com.alain.foundyou.data.PersonFilters;
import com.alain.foundyou.data.network.model.Person;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface PersonRepository {
    Flowable<List<Person>> getPersons();
    Single<List<Person>> getRandomPersonsByGender(int count, String gender);
    Single<Person> getRandomPersonWithPassword(int count, String passwordComplexity);
    Completable refreshPersons();
}
