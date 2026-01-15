package com.alain.foundyou.ui;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.core.splashscreen.SplashScreen;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.alain.foundyou.R;
import com.alain.foundyou.databinding.ActivityMainBinding;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 1. Instalar el Splash Screen antes de super.onCreate
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        PersonListViewModel viewModel = new ViewModelProvider(this).get(PersonListViewModel.class);
        splashScreen.setKeepOnScreenCondition(() -> {
            Boolean isLoading = viewModel.isLoading.getValue();
            return isLoading != null && isLoading;
        });

        EdgeToEdge.enable(this);

        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setupNavigation();
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