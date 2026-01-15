package com.alain.foundyou.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.alain.foundyou.R;
import com.alain.foundyou.databinding.FragmentPersonDetailBinding;
import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class PersonDetailFragment extends Fragment {


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
        FragmentPersonDetailBinding binding;
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
        ImageButton backButton = binding.backButton;
        Button btnAddContact = binding.btnAddContact;
        ImageView qrImage = binding.qrImage;


        backButton.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(binding.getRoot());
            navController.navigateUp();
        });

        btnAddContact.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_INSERT);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            intent.putExtra(ContactsContract.Intents.Insert.NAME, detailName.getText().toString());
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, detailPhone.getText().toString());
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, detailEmail.getText().toString());
            startActivity(intent);
        });

        qrImage.setOnClickListener(v -> mostrarDialogoQR());


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

            Glide.with(this)
                    .load(personPicture)
                    .circleCrop()
                    .placeholder(R.drawable.nopersona)
                    .error(R.drawable.nopersona)
                    .into(detailImage);
        }

    }

    private void mostrarDialogoQR() {
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_qr, null);
        ImageView imgDialogQR = dialogView.findViewById(R.id.imgDialogQR);
        View btnClose = dialogView.findViewById(R.id.btnContextClose);

        Bitmap qrBitmap = generarQRContacto();
        if (qrBitmap != null) {
            imgDialogQR.setImageBitmap(qrBitmap);
        }

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create();

        if (btnClose != null) {
            btnClose.setOnClickListener(v -> dialog.dismiss());
        }

        dialog.show();
    }

    private Bitmap generarQRContacto() {
        String vCard = "BEGIN:VCARD\n" +
                "VERSION:3.0\n" +
                "FN:" + detailName.getText().toString() + "\n" +
                "TEL:" + detailPhone.getText().toString() + "\n" +
                "EMAIL:" + detailEmail.getText().toString() + "\n" +
                "END:VCARD";

        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(vCard, BarcodeFormat.QR_CODE, 512, 512);
            Bitmap bmp = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565);
            for (int x = 0; x < 512; x++) {
                for (int y = 0; y < 512; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bmp;
        } catch (WriterException e) {
            return null;
        }
    }


    private String formatBirthday(String fechaNacimiento) {
        if (fechaNacimiento == null || fechaNacimiento.isEmpty()) {
            return getString(R.string.person_no_birthday);
        }
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

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
