package com.alain.foundyou.data.database.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "person_table")
public class PersonEntity {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    public String uuid;

    @ColumnInfo(name = "gender")
    public String gender;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @ColumnInfo(name = "city")
    public String city;

    @ColumnInfo(name = "country")
    public String country;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "dob_date")
    public String dateOfBirth;

    @ColumnInfo(name = "dob_age")
    public String age;

    @ColumnInfo(name = "phone")
    public String phone;


    @ColumnInfo(name = "picture_large")
    public String pictureLarge;

    @ColumnInfo(name = "picture_medium")
    public String pictureMedium;

    @ColumnInfo(name = "picture_thumbnail")
    public String pictureThumbnail;

    @ColumnInfo(name = "cell")
    public String cell;

    @ColumnInfo(name = "postcode")
    public String postcode;


    public PersonEntity(@NonNull String uuid, String gender, String title, String firstName, String lastName,
                        String city, String country, String email, String dateOfBirth, String age,
                        String phone, String pictureLarge, String pictureMedium, String pictureThumbnail, String cell, String postcode) {
        this.uuid = uuid;
        this.gender = gender;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.country = country;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.phone = phone;
        this.pictureLarge = pictureLarge;
        this.pictureMedium = pictureMedium;
        this.pictureThumbnail = pictureThumbnail;
        this.cell = cell;
        this.postcode = postcode;


    }
}