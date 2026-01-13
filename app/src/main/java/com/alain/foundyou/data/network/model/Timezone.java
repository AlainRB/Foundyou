package com.alain.foundyou.data.network.model;

import com.google.gson.annotations.SerializedName;

public class Timezone {
    @SerializedName("offset")
    private String offset;
    @SerializedName("description")
    private String description;

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
