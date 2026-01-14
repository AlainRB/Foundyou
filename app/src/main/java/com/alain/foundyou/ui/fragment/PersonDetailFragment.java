package com.alain.foundyou.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alain.foundyou.R;
import com.alain.foundyou.ui.viewModel.PersonListViewModel;
import com.bumptech.glide.Glide;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonDetailFragment extends Fragment {
    private PersonListViewModel viewModel;
    private ImageView detailImage;
    private TextView detailName;
    private TextView detailEmail;
    private TextView detailPhone;
    private TextView detailBirthday;
    private TextView detailCountry;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_detail, container, false);

        detailImage = view.findViewById(R.id.detail_image);
        detailName = view.findViewById(R.id.detail_name);
        detailEmail = view.findViewById(R.id.detail_email);
        detailPhone = view.findViewById(R.id.detail_phone);
        detailBirthday = view.findViewById(R.id.detail_birthday);
        detailCountry = view.findViewById(R.id.detail_country);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String personName = getArguments().getString("personName", "Sin nombre");
            String personEmail = getArguments().getString("personEmail", "Sin correo");
            String personPhone = getArguments().getString("personPhone", "Sin teléfono");
            String personBirthday = getArguments().getString("personBirthday", "Sin cumpleaños");
            String personCountry = getArguments().getString("personCountry", "Sin país");
            String personPicture = getArguments().getString("personPicture", null); // Usa null como default

            // 3. Asigna los datos a las vistas correctas
            detailName.setText(personName);
            detailEmail.setText(personEmail);
            detailPhone.setText(personPhone);
            detailBirthday.setText(personBirthday);
            detailCountry.setText(personCountry);

            // 4. Carga la imagen principal con Glide y su placeholder
            Glide.with(this)
                    .load(personPicture)
                    .placeholder(R.drawable.nopersona) // Placeholder desde tu XML (tools:src)
                    .error(R.drawable.nopersona)       // Imagen de error si la URL falla
                    .into(detailImage);
        }

    }

}