package com.example.roshan.chatapplication.Service;

import com.example.roshan.chatapplication.Model.Person;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Roshan on 08-03-2019.
 */

public interface LoginService {

    @POST("api/user/login")
    Call<Person> createUser(@Body Person person);
}
