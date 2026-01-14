package com.alain.foundyou.ui;

import static android.content.ContentValues.TAG;

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

        // --- OBSERVADOR PARA LA LISTA DE PERSONAS ---
        // Este se activará cada vez que la base de datos notifique un cambio y el ViewModel nos lo envíe.
        viewModel.persons.observe(this, personList -> {
            // Primero, comprobamos si la lista que llega es válida y no está vacía.
            if (personList != null && !personList.isEmpty()) {

                // ¡ESTA ES TU PRUEBA DEFINITIVA!
                // Si este log aparece, significa que los datos se guardaron en la BD,
                // se leyeron, se procesaron y llegaron correctamente a la UI.
                Log.i("BD_TEST", ">>>>>> DATOS RECIBIDOS CORRECTAMENTE DESDE LA BASE DE DATOS <<<<<<");
                Log.d("BD_TEST", "Número de personas en la lista: " + personList.size());

                // Imprimimos el email de la PRIMERA persona como prueba específica.
                String primerEmail = personList.get(0).getEmail();
                Log.d("BD_TEST", "Email de la primera persona: " + primerEmail);

                // Opcional: Imprimir toda la lista para ver todos los datos
                for (int i = 0; i < personList.size(); i++) {
                    String firstName = personList.get(i).getName().getFirst();
                    Log.d("BD_TEST_LIST", "  -> Persona " + (i + 1) + ": " + firstName);
                }
                Log.i("BD_TEST", ">>>>>> FIN DE LA COMPROBACIÓN <<<<<<");

            } else {
                Log.w("BD_TEST", "La lista de personas está vacía. Esto es normal al iniciar la app antes de la primera carga.");
            }
        });

        // --- Observadores de Carga y Error (ya los tienes bien) ---
        viewModel.isLoading.observe(this, isLoading -> {
            Log.d("BD_TEST", "[Estado de Carga]: " + (isLoading ? "CARGANDO..." : "FINALIZADO"));
        });

        viewModel.error.observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Log.e("BD_TEST", "¡ERROR DETECTADO!: " + errorMessage);
            }
        });
    }

}