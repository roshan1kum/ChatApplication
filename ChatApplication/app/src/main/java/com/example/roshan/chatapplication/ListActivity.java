package com.example.roshan.chatapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.roshan.chatapplication.Adapter.UserAdapter;
import com.example.roshan.chatapplication.Model.Person;
import com.example.roshan.chatapplication.Service.ListService;
import com.example.roshan.chatapplication.Service.ServiceBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        final SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + ".my_file", Context.MODE_PRIVATE);
        String Token = sharedPreferences.getString("token", "N/A");
        if (isNetworkConnected()) {

            ListService listService = ServiceBuilder.buildService(ListService.class);
            Call<List<Person>> listRequest = listService.getPerson(Token);

            listRequest.enqueue(new Callback<List<Person>>() {
                @Override
                public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                    List<Person> list = response.body();
                    ArrayList<Person> arr = new ArrayList<Person>();
                    arr.addAll(list);
                    SaveData(list);
                    Displaydata(arr);
                }
                @Override
                public void onFailure(Call<List<Person>> call, Throwable t) {

                }
            });
        }
        else {
            OnFailure();
        }
    }

    private void Displaydata(ArrayList<Person> arr) {
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);

        UserAdapter adapter = new UserAdapter(arr);
        rvContacts.setAdapter(adapter);
        rvContacts.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvContacts.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
        rvContacts.setAdapter(adapter);
    }

    private void SaveData(List<Person> list) {
        SharedPreferences sharedPreferences2 = getSharedPreferences(getPackageName()+".my_file2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();

        Gson gson= new Gson();
        String jsonString=gson.toJson(list);
        editor.putString("Person",jsonString);
        editor.apply();
    }
    private List<Person> LoadData()
    {
        SharedPreferences sharedPreferences12 = getSharedPreferences(getPackageName()+".my_file2", Context.MODE_PRIVATE);
        String jsonString = sharedPreferences12.getString("Person","N/A");
        Gson gson=new Gson();
        Type type = new TypeToken<List<Person>>(){}.getType();
        List<Person> person = gson.fromJson(jsonString, type);
        return person;
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void OnFailure()
    {
        List<Person> person = LoadData();


        //gson.fromJson(jsonString);
        ArrayList<Person> arr = new ArrayList<Person>();
        arr.addAll(person);
        RecyclerView rvContacts = (RecyclerView) findViewById(R.id.rvContacts);
        Displaydata(arr);

    }
}
