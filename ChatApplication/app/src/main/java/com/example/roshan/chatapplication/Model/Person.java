package com.example.roshan.chatapplication.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Roshan on 08-03-2019.
 */

public class Person {
   // @SerializedName("id")
    private int id;
    //@SerializedName("name")
    private String name;
   // @SerializedName("token")
    private String token;

    public Person() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
