package com.alain.foundyou.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.alain.foundyou.R;
import com.alain.foundyou.databinding.ActivityMainBinding;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        PersonListViewModel viewModel = new ViewModelProvider(this).get(PersonListViewModel.class);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);
        setupNavigation();
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean isLoading = viewModel.isLoading.getValue();
            return isLoading != null && isLoading;
        });


        viewModel.error.observe(this, errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Snackbar.make(binding.getRoot(), errorMessage, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setupNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            Log.e("MainActivity", "NavHostFragment no encontrado. La navegación no funcionará.");
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        //
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

}