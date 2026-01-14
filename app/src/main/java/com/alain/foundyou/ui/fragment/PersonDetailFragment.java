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
import com.alain.foundyou.databinding.FragmentPersonDetailBinding;
import com.bumptech.glide.Glide;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonDetailFragment extends Fragment {

    private FragmentPersonDetailBinding binding;
    private ImageView detailImage;
    private TextView detailName;
    private TextView detailEmail;
    private TextView detailPhone;
    private TextView detailBirthday;
    private TextView detailCountry;
    private TextView detailCell;
    private TextView detailCity;
    private TextView detailGender;
    private TextView detailAge;
    private TextView detailPostcode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPersonDetailBinding.inflate(inflater, container, false);

        detailImage = binding.detailImage;
        detailName = binding.detailName;
        detailEmail = binding.detailEmail;
        detailPhone = binding.detailPhone;
        detailBirthday = binding.detailBirthday;
        detailCountry = binding.detailCountry;
        detailCell = binding.detailCell;
        detailCity = binding.detailCity;
        detailGender = binding.detailGender;
        detailAge = binding.detailAge;
        detailPostcode = binding.detailPostcode;

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            String personName = getArguments().getString("personName", getString(R.string.person_no_name));
            String personEmail = getArguments().getString("personEmail", getString(R.string.person_no_email));
            String personPhone = getArguments().getString("personPhone", getString(R.string.person_no_phone));
            String personBirthday = formatBirthday(getArguments().getString("personBirthday", getString(R.string.person_no_birthday)));
            String personCountry = getArguments().getString("personCountry", getString(R.string.person_no_country));
            String personPicture = getArguments().getString("personPicture", null);
            String personCell = getArguments().getString("personCell", getString(R.string.person_no_cell));
            String personCity = getArguments().getString("personCity", getString(R.string.person_no_city));
            String personGender = getArguments().getString("personGender", getString(R.string.person_no_gender));
            String personAge = getArguments().getString("personAge", getString(R.string.person_no_age));
            String personPostcode = getArguments().getString("personPostcode", getString(R.string.person_no_postcode));


            detailName.setText(personName);
            detailEmail.setText(personEmail);
            detailPhone.setText(personPhone);
            detailBirthday.setText(personBirthday);
            detailCountry.setText(personCountry);
            detailCell.setText(personCell);
            detailCity.setText(personCity);
            detailGender.setText(personGender);
            detailAge.setText(String.format(getString(R.string.person_years_suffix), personAge));
            detailPostcode.setText(String.format(getString(R.string.person_postcode_prefix), personPostcode));

            // Cargar la imagen con Glide


            Glide.with(this)
                    .load(personPicture)
                    .circleCrop()
                    .placeholder(R.drawable.nopersona) // Placeholder desde tu XML (tools:src)
                    .error(R.drawable.nopersona)       // Imagen de error si la URL falla
                    .into(detailImage);
        }

    }

    private String formatBirthday(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            return getString(R.string.person_no_birthday);
        }
        // Formato para parsear la fecha de entrada
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // La 'Z' indica zona horaria UTC

        // Formato para la fecha de salida
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(fechaNacimiento);
            if (date != null) {
                return outputFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return getString(R.string.person_invalid_date);
        }
        return getString(R.string.person_no_birthday);
    }


}