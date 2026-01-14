package com.alain.foundyou.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alain.foundyou.R;
import com.alain.foundyou.ui.adapter.PersonAdapter;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonListFragment extends Fragment {
    private PersonListViewModel viewModel;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private NavController navController;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_person_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        recyclerView = view.findViewById(R.id.recycler_view_persons);
        viewModel = new ViewModelProvider(requireActivity()).get(PersonListViewModel.class);

        setupRecyclerView(view);
        setupSwipeToRefresh();
        setupObservers();

    }

    private void setupRecyclerView(View view) {
        adapter = new PersonAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(person -> {
            // Ahora tienes el objeto 'post' y el NavController en el mismo lugar.

            // Crea el Bundle para pasar los datos
            Bundle bundle = new Bundle();
            bundle.putString("personName",person.getName().getTitle()+" "+person.getName().getFirst()+" "+person.getName().getLast());
            bundle.putString("personEmail",person.getEmail());
            bundle.putString("personPhone",person.getPhone());
            bundle.putString("personBirthday",person.getDob().getDate());
            bundle.putString("personCountry",person.getLocation().getCountry());
            bundle.putString("personPicture",person.getPicture().getLarge());


            // Navega con el bundle
            Navigation.findNavController(view).navigate(R.id.action_personListFragment_to_personDetailFragment, bundle);
        });
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Esta es la acción que se ejecuta cuando el usuario "tira para refrescar"
            Log.d("API_FETCH", "Swipe to refresh activado. Forzando actualización desde la API.");
            viewModel.refreshData(); // Necesitarás crear este método en tu ViewModel
        });
    }

    private void setupObservers() {
        // --- OBSERVADOR PARA LA LISTA DE PERSONAS ---
        // Este se activa automáticamente al cargar el fragmento y cada vez que los datos cambian en la BD.
        viewModel.persons.observe(getViewLifecycleOwner(), personList -> {
            if (personList != null) {
                adapter.submitList(personList); // ListAdapter actualiza la UI eficientemente
                Log.i("BD_TEST", "Datos de la BD actualizados en el RecyclerView. " + personList.size() + " personas.");
            }
        });

        // --- OBSERVADOR DE ESTADO DE CARGA ---
        // Gestiona la visibilidad del indicador de carga del SwipeRefreshLayout
        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            // Solo mostramos el ProgressBar inicial si no hay datos en el adapter
            swipeRefreshLayout.setRefreshing(isLoading);
            Log.d("BD_TEST", "[Estado de Carga]: " + (isLoading ? "CARGANDO..." : "FINALIZADO"));
        });

        // --- OBSERVADOR DE ERRORES ---
        viewModel.error.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), "Error: " + errorMessage, Toast.LENGTH_LONG).show();
                Log.e("BD_TEST", "¡ERROR DETECTADO!: " + errorMessage);
            }
        });
    }

}