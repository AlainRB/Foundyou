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

    // 1. Método para obtener por cantidad
    @GET("api")
    Single<ApiResponse> getPersonsCount(@Query("results") int results);

    // 2. Método para obtener por cantidad y género
    @GET("api/")
    Single<ApiResponse> getPersonsByGender(@Query("results") int results, @Query("gender") String gender);

    // 3. Método para obtener por cantidad y complejidad de contraseña
    @GET("api/")
    Single<ApiResponse> getPersonsByPasswordComplexity(@Query("results") int results, @Query("password") String passwordComplexity);
}
