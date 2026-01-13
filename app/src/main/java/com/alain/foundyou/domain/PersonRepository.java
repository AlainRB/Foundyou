package com.alain.foundyou.domain;

import com.alain.foundyou.data.PersonFilters;
import com.alain.foundyou.data.network.model.Person;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface PersonRepository {
    Single<List<Person>> getRandomPersons(int count);
    Single<List<Person>> getRandomPersonsByGender(int count, String gender);
    Single<Person> getRandomPersonWithPassword(int count, String passwordComplexity);
    void refreshPersons();
}
