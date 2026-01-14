package com.alain.foundyou.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import com.alain.foundyou.databinding.ActivityMainBinding;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private PersonListViewModel viewModel;
    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }


    @Override
    public boolean onSupportNavigateUp() {
        // Permite al NavController gestionar la navegación hacia atrás.
        // Si no puede, utiliza el comportamiento por defecto de la actividad.
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}