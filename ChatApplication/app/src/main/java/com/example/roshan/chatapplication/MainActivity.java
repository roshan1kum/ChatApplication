package com.example.roshan.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.roshan.chatapplication.Model.Person;
import com.example.roshan.chatapplication.Service.LoginService;
import com.example.roshan.chatapplication.Service.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.etUser);
    }


    public void login(View view) {

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + ".my_file", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (!editText.getText().toString().contains(" ")) {
            Person person = new Person();
            person.setName(editText.getText().toString());
            if (isNetworkConnected()) {
                LoginService loginservice = ServiceBuilder.buildService(LoginService.class);
                Call<Person> call = loginservice.createUser(person);
                call.enqueue(new Callback<Person>() {

                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        Intent intent = new Intent(MainActivity.this, ListActivity.class);
                        startActivity(intent);
                        String Token = response.body().getToken();
                        int id = response.body().getId();
                        editor.putString("token", Token);
                        editor.putString("id", Integer.toString(id));
                        editor.apply();
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {
                        //Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            else {
                Toast.makeText(this, "network not connected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_SHORT).show();
        }

}
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
