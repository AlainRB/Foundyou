package com.alain.foundyou.data.network.model;

import com.google.gson.annotations.SerializedName;

public class Location {
    @SerializedName("city")
    private String city;
    @SerializedName("country")
    private String country;
    @SerializedName("postcode")
    private String postcode;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

}
