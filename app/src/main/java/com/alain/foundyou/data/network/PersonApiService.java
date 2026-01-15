package com.alain.foundyou.data.network;

import com.alain.foundyou.data.network.model.ApiResponse;
import com.alain.foundyou.data.network.model.Person;

import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface PersonApiService {

    @GET("api")
    Single<ApiResponse> getPersonsCount(@Query("results") int results);

}
