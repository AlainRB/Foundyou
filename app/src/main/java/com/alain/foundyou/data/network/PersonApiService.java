package com.alain.foundyou.data.network;

import com.alain.foundyou.data.network.model.Person;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PersonApiService {

    @GET("api/")
    Single<List<Person>> getPersons(@Query("results") Integer results,
                                    @Query("gender") String gender,
                                    @Query("password") String password,
                                    @Query("seed") String seed);
}
