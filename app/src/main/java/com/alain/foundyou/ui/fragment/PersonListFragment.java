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
import com.alain.foundyou.databinding.FragmentPersonListBinding;
import com.alain.foundyou.ui.adapter.PersonAdapter;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonListFragment extends Fragment {
    private PersonListViewModel viewModel;
    private RecyclerView recyclerView;
    private PersonAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FragmentPersonListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = binding.swipeRefreshLayout;
        recyclerView = binding.recyclerViewPersons;

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
            bundle.putString("personCell",person.getCell());
            bundle.putString("personCity",person.getLocation().getCity());
            bundle.putString("personGender",person.getGender());
            bundle.putString("personAge", String.valueOf(person.getDob().getAge()));
            bundle.putString("personPostcode",person.getLocation().getPostcode());


            Navigation.findNavController(view).navigate(R.id.action_personListFragment_to_personDetailFragment, bundle);
        });
    }

    private void setupSwipeToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Esta es la acción que se ejecuta cuando el usuario "tira para refrescar"
            Log.d("API_FETCH", getString(R.string.log_swipe_refresh));
            viewModel.refreshData(); // Necesitarás crear este método en tu ViewModel
        });
    }

    private void setupObservers() {
        // --- OBSERVADOR PARA LA LISTA DE PERSONAS ---
        // Este se activa automáticamente al cargar el fragmento y cada vez que los datos cambian en la BD.
        viewModel.persons.observe(getViewLifecycleOwner(), personList -> {
            if (personList != null) {
                adapter.submitList(personList); // ListAdapter actualiza la UI eficientemente
                Log.i("BD_TEST", String.format(getString(R.string.log_db_updated), personList.size()));
            }
        });

        // --- OBSERVADOR DE ESTADO DE CARGA ---
        // Gestiona la visibilidad del indicador de carga del SwipeRefreshLayout
        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> swipeRefreshLayout.setRefreshing(isLoading));

        // --- OBSERVADOR DE ERRORES ---
        viewModel.error.observe(getViewLifecycleOwner(), errorMessage -> {
            if (errorMessage != null && !errorMessage.isEmpty()) {
                Toast.makeText(getContext(), String.format(getString(R.string.error_toast), errorMessage), Toast.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}