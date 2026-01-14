package com.alain.foundyou.data.repository;

import com.alain.foundyou.data.database.dao.PersonDao;
import com.alain.foundyou.data.database.entities.PersonEntity;
import com.alain.foundyou.data.network.PersonApiService;
import com.alain.foundyou.data.network.model.Name;
import com.alain.foundyou.data.network.model.Person;
import com.alain.foundyou.data.network.model.Picture;
import com.alain.foundyou.domain.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

@Singleton
public class PersonRepositoryImpl implements PersonRepository {
    private final PersonApiService personApiService;
    private final PersonDao personDao;

    @Inject
    public PersonRepositoryImpl(PersonApiService personApiService, PersonDao personDao) {
        this.personApiService = personApiService;
        this.personDao = personDao;
    }

    @Override
    public Flowable<List<Person>> getPersons() {
        return personDao.getAllPosts()
                .map(this::mapEntityListToApiList);
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
    public Completable refreshPersons() {
        return personApiService.getPersonsCount(100)
                // No necesitas subscribeOn aquí, se manejará en el ViewModel
                .map(personResponse -> personResponse.getResults().stream()
                        .map(this::mapDtoToEntity) // DTO -> Entity (para guardar)
                        .collect(Collectors.toList()))
                .flatMapCompletable(personEntities -> {
                    // flatMapCompletable es la clave.
                    // Le decimos que ejecute esta acción (borrar e insertar) y nos notifique cuando termine.
                    return Completable.fromAction(() -> {
                                personDao.deleteAllUsers();
                                personDao.insertAll(personEntities);
                            })
                            // ¡LA CLAVE ESTÁ AQUÍ! Asignamos el Scheduler DENTRO del Completable.
                            .subscribeOn(Schedulers.io());
                });
    }

    //Para guardar en Room
    private PersonEntity mapDtoToEntity(Person person) {
        return new PersonEntity(
                // --- Datos principales ---
                person.getLogin().getUuid(),
                person.getGender(),
                person.getName().getFirst(),
                person.getName().getLast(),
                person.getLocation().getCity(),
                person.getLocation().getCountry(),
                person.getEmail(),
                person.getDob().getDate(),
                String.valueOf(person.getDob().getAge()), // La API da un int, la BD espera un String
                person.getPhone(),
                person.getPicture().getLarge(),
                person.getPicture().getMedium(),
                person.getPicture().getThumbnail()
        );
    }

    // MAPEO INVERSO: ENTIDAD de BD -> DTO de API (Para exponer al ViewModel)
    private List<Person> mapEntityListToApiList(List<PersonEntity> entityList) {
        return entityList.stream().map(entity -> {
            Person person = new Person();
            person.setGender(entity.gender);
            person.setEmail(entity.email);
            person.setPhone(entity.phone);
            Name name = new Name();
            name.setFirst(entity.firstName);
            name.setLast(entity.lastName);
            person.setName(name);
            Picture picture = new Picture();
            picture.setLarge(entity.pictureLarge);
            picture.setMedium(entity.pictureMedium);
            picture.setThumbnail(entity.pictureThumbnail);
            person.setPicture(picture);
            return person;
        }).collect(Collectors.toList());
    }

}

