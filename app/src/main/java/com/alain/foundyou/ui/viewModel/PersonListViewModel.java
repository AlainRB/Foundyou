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
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class PersonListViewModel extends ViewModel {
    private final PersonRepository personRepository;
    private final MutableLiveData<List<Person>> _persons = new MutableLiveData<>();
    public final LiveData<List<Person>> persons = _persons;

    private final MutableLiveData<Boolean> _isLoading = new MutableLiveData<>();
    public final LiveData<Boolean> isLoading = _isLoading;

    private final MutableLiveData<String> _error = new MutableLiveData<>();
    public final LiveData<String> error = _error;
    @Inject
    public PersonListViewModel(PersonRepository personRepository) {
        this.personRepository = personRepository;
        observePersons();
    }

    private void observePersons() {

        // Log para saber que el método se ha iniciado
      Log.d("AlAIN", "Iniciando la obtención de personas...");

        personRepository.getRandomPersons(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> {
                    _isLoading.setValue(true);
                    // Log para el estado de carga
                    Log.d("AlAIN", "Cargando... (isLoading = true)");
                })
                .doFinally(() -> {
                    _isLoading.setValue(false);
                    // Log para cuando finaliza la operación (con éxito o error)
                   Log.d("AlAIN", "Operación finalizada. (isLoading = false)");
                })
                .subscribe(
                        persons -> {
                            _persons.setValue(persons);
                            // 2. Log cuando la llamada es exitosa
                            Log.d("AlAIN", "Personas recibidas con éxito. Cantidad: " + persons.size());
                            // Opcional: Imprimir los datos de la primera persona para verificar
                            if (!persons.isEmpty()) {
                             Log.d("AlAIN", "Primera persona: " + persons.get(0).getName().getFirst());
                            }
                        },
                        error -> {
                            _error.setValue(error.getMessage());
                            // 3. Log cuando ocurre un error
                            Log.e("AlAIN", "Error al obtener personas: " + error.getMessage());
                        }
                );
    }


}
