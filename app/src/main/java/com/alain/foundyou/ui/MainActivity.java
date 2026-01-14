package com.alain.foundyou.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
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

        // 1. Instalar el Splash Screen antes de super.onCreate
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Obtiene la instancia del ViewModel
        viewModel = new ViewModelProvider(this).get(PersonListViewModel.class);

        // 3. Configurar la condición para mantener el Splash Screen en pantalla
        // Se mantendrá visible mientras 'isLoading' sea true o sea null (estado inicial)
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean isLoading = viewModel.isLoading.getValue();
            return isLoading == null || isLoading;
        });

        // 4. Llama a un método para configurar los observadores
        setupObservers();
    }

    private void setupObservers() {
        // Observador para la lista de personas
        viewModel.persons.observe(this, persons -> {
            if (persons != null && !persons.isEmpty()) {
                Log.d("PersonListActivity", "Respuesta recibida: " + persons.get(0).getName().getFirst());
            }
        });

        // Observador para el estado de carga (opcional si ya usas setKeepOnScreenCondition)
        viewModel.isLoading.observe(this, isLoading -> {
            Log.d("PersonListActivity", "El estado de carga es: " + isLoading);
        });

        // Observador para los errores
        viewModel.error.observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Log.e("PersonListActivity", "Error: " + errorMessage);
            }
        });
    }
}
