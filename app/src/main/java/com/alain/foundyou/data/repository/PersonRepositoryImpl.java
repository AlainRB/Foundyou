package com.alain.foundyou.data.repository;

import com.alain.foundyou.data.network.PersonApiService;
import com.alain.foundyou.data.network.model.Person;
import com.alain.foundyou.domain.PersonRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonApiService personApiService;

    @Inject
    public PersonRepositoryImpl(PersonApiService personApiService) {
        this.personApiService = personApiService;
    }

    @Override
    public Single<List<Person>> getRandomPersons(int count) {
        // Ahora llamamos al método sobrecargado que solo pide 'results'. ¡Más limpio!
        return personApiService.getPersonsCount(count)
                .subscribeOn(Schedulers.io())
                // Reemplazamos la referencia al método por una lambda
                .map(apiResponse -> apiResponse.getResults());
    }

    @Override
    public Single<List<Person>> getRandomPersonsByGender(int count, String gender) {
        // Usamos la versión sobrecargada para género y cantidad
        return personApiService.getPersonsByGender(count, gender)
                .subscribeOn(Schedulers.io())
                .map(apiResponse -> apiResponse.getResults());
    }

    @Override
    public Single<Person> getRandomPersonWithPassword(int count, String passwordComplexity) {
        // Usamos la versión sobrecargada para contraseña y pedimos 1 resultado
        return personApiService.getPersonsByPasswordComplexity(count, passwordComplexity)
                .subscribeOn(Schedulers.io())
                // Extraemos la lista y luego el primer (y único) elemento
                .map(apiResponse -> apiResponse.getResults().get(0));
    }

    @Override
    public void refreshPersons() {
        // Lógica futura para implementar con Room
        System.out.println("Funcionalidad de refresco de datos pendiente de implementación.");
    }
}
