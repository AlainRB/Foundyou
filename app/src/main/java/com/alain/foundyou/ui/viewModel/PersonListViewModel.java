package com.alain.foundyou.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alain.foundyou.data.network.model.Person;
import com.alain.foundyou.domain.PersonRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PersonListViewModel extends ViewModel {
    private final PersonRepository personRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();

    private final MutableLiveData<List<Person>> _persons = new MutableLiveData<>();
    public final LiveData<List<Person>> persons = _persons;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;

    @Inject
    public PersonListViewModel(PersonRepository personRepository) {
        this.personRepository = personRepository;
        observePersonsFromRepository();
        refreshData();
    }

    private void observePersonsFromRepository() {
        disposables.add(
                // El repositorio ya nos da un flujo del tipo de dato que necesitamos (List<Person>)
                personRepository.getPersons()// Devuelve Flowable<List<Person>>
                        .subscribeOn(Schedulers.io())
                        // .observeOn(AndroidSchedulers.mainThread()) // Es buena práctica cambiar al hilo principal antes de actualizar la UI
                        .subscribe(
                                personList -> _persons.postValue(personList), // ¡Directo! Sin mapeo.
                                throwable -> _error.postValue("Error al leer los datos: " + throwable.getMessage())
                        )
        );
    }

    public void refreshData() {
        disposables.add(
                personRepository.refreshPersons()
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(disposable -> _isLoading.postValue(true))
                        .doFinally(() -> _isLoading.postValue(false))
                        .subscribe(
                                () -> Log.d("ALAIN", "La operación de refresco se completó."),
                                throwable -> {
                                    Log.e("ALAIN", "Error en la operación de refresco: " + throwable.getMessage());
                                    _error.postValue(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear(); // Muy importante para evitar memory leaks
    }
}
