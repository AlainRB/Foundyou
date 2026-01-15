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
    private PersonAdapter adapter;
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


        viewModel = new ViewModelProvider(requireActivity()).get(PersonListViewModel.class);

        setupRecyclerView();
        setupSwipeToRefresh();
        setupObservers();

    }

    private void setupRecyclerView() {
        adapter = new PersonAdapter();
        binding.recyclerViewPersons.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewPersons.setAdapter(adapter);

        adapter.setOnItemClickListener(person -> {
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

            if (getView() != null) {
                Navigation.findNavController(getView()).navigate(R.id.action_personListFragment_to_personDetailFragment, bundle);
            }

        });
    }

    private void setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            Log.d("API_FETCH", getString(R.string.log_swipe_refresh));
            viewModel.refreshData();
        });
    }

    private void setupObservers() {
        viewModel.persons.observe(getViewLifecycleOwner(), personList -> {
            if (personList != null) {
                adapter.submitList(personList);
                Log.i("BD_TEST", String.format(getString(R.string.log_db_updated), personList.size()));
            }
        });

        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> binding.swipeRefreshLayout.setRefreshing(isLoading));

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