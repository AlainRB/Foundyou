package com.alain.foundyou.data.network.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("uuid")
    private String uuid;

    public String getUuid() {
        return uuid;
    }


}
