package com.example.roshan.chatapplication.Service;

import android.os.Message;

import com.example.roshan.chatapplication.Model.Messages;
import com.example.roshan.chatapplication.Model.Person;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Roshan on 08-03-2019.
 */

public interface ListService {
    @GET("/api/user")
    Call<List<Person>> getPerson(@Header("Authorization")String authToken);

    @GET("/api/chat/{userId}")
    Call<ArrayList<Messages>> getMessage(@Header("Authorization")String authToken, @Path("userId") String userId);

    @POST("/api/chat")
        Call<Void> createMessage(@Body Messages messages,@Header("Authorization")String authToken);
}
