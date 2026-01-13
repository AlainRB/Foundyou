package com.alain.foundyou.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.alain.foundyou.R;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private PersonListViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Obtiene la instancia del ViewModel
        viewModel = new ViewModelProvider(this).get(PersonListViewModel.class);

        // 2. Llama a un método para configurar los observadores
        setupObservers();
    }

    private void setupObservers() {
        // Suponiendo que tienes un RecyclerView (recyclerView) y un ProgressBar (progressBar) en tu layout

        // Observador para la lista de personas
        viewModel.persons.observe(this, persons -> {
            // 'persons' es la List<Person> que llega desde el ViewModel
            // Aquí actualizas tu RecyclerView Adapter con la nueva lista.
            // Por ejemplo: personAdapter.submitList(persons);
            Log.d("PersonListActivity", "La lista de personas se ha actualizado en la UI.");
        });

        // Observador para el estado de carga
        viewModel.isLoading.observe(this, isLoading -> {
            // Muestra u oculta tu ProgressBar (o cualquier indicador de carga)
            // if (isLoading) {
            //     progressBar.setVisibility(View.VISIBLE);
            // } else {
            //     progressBar.setVisibility(View.GONE);
            // }
            Log.d("PersonListActivity", "El estado de carga es: " + isLoading);
        });

        // Observador para los errores
        viewModel.error.observe(this, errorMessage -> {
            // Si el mensaje no es nulo o vacío, muéstralo al usuario
            if (errorMessage != null && !errorMessage.isEmpty()) {
                // Muestra el error en un Toast, Snackbar o TextView
                // Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
               Log.e("PersonListActivity", "Se ha recibido un error: " + errorMessage);
            }
        });
    }
}