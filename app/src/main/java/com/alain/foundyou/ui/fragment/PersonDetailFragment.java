package com.alain.foundyou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alain.foundyou.R;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonDetailFragment extends Fragment {
    private PersonListViewModel viewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Recupera los argumentos del Bundle
        if (getArguments() != null) {
            int postId = getArguments().getInt("postId", -1); // El segundo valor es un default
            String postTitle = getArguments().getString("postTitle", "Sin título");
            String postBody = getArguments().getString("postBody", "Sin contenido");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_detail, container, false);


        return view;
    }
}