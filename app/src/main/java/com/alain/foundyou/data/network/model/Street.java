package com.alain.foundyou.data.network.model;

import com.google.gson.annotations.SerializedName;

public class Street {
    @SerializedName("number")
    private int number;
    @SerializedName("name")
    private String name;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
