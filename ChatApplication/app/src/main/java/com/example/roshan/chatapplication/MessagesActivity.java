package com.example.roshan.chatapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.roshan.chatapplication.Model.Messages;
import com.example.roshan.chatapplication.Service.ListService;
import com.example.roshan.chatapplication.Service.ServiceBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesActivity extends AppCompatActivity {

    String Token;
    String id;
    HashMap<String, String> hashmap=new HashMap<String, String>();
    private final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Button button =(Button) findViewById(R.id.sendMessage);

        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + ".my_file", Context.MODE_PRIVATE);
         Token = sharedPreferences.getString("token", "N/A");

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
            getmessages();
        doTheAutoRefresh();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText=findViewById(R.id.sendText);
                final Messages message=new Messages();
                message.setText(editText.getText().toString());
                message.setToUserId(id);
                if(isNetworkConnected()) {
                    ListService messageService = ServiceBuilder.buildService(ListService.class);
                    Call<Void> msg = messageService.createMessage(message, Token);
                    msg.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            getmessages();
                            editText.setText("");
                            Toast.makeText(MessagesActivity.this, "OnResponse", Toast.LENGTH_SHORT).show();
                            //uploadsMessage(message);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
//                        getmessages();
//                        editText.setText("");
//                        Toast.makeText(MessagesActivity.this, "OnFailure", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    //storeMessage(message);
                    Toast.makeText(MessagesActivity.this, "offline messaage not send", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }

            }
        });
    }

//    private void uploadsMessage(Messages message) {
//
//    }

//    private void storeMessage(Messages message) {
//        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + ".my_file4", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sharedPreferences.edit();
//
//        Gson gson= new Gson();
//        String jsonString=gson.toJson(message);
//        editor.putString("Message"+id.toString(),jsonString);
//        editor.apply();
//
//    }

    void getmessages()
    {
        if(isNetworkConnected()) {
            ListService messageService = ServiceBuilder.buildService(ListService.class);
            Call<ArrayList<Messages>> messageRequest = messageService.getMessage(Token, id);

            messageRequest.enqueue(new Callback<ArrayList<Messages>>() {
                @Override
                public void onResponse(Call<ArrayList<Messages>> call, Response<ArrayList<Messages>> response) {

                    List<Messages> list = response.body();
                    SaveData(list);
                    ListView listView = findViewById(R.id.view);
                    String[] arr = new String[list.size()];
                    for (int i = 0; i < list.size(); i++) {
                        arr[i] = list.get(i).getText();
                    }

                    ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<ArrayList<Messages>> call, Throwable t) {
                }
            });
        }else
        {
          OnFailure();
        }
    }

    private List<Messages> LoadData() {
        SharedPreferences sharedPreferences2 = getSharedPreferences(getPackageName()+".my_file3", Context.MODE_PRIVATE);
        String jsonString = sharedPreferences2.getString("Message"+id.toString(),"N/A");
        Gson gson=new Gson();
        Type type = new TypeToken<List<Messages>>(){}.getType();
        if(jsonString.equals("N/A"))
        {
            Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        List<Messages> message = gson.fromJson(jsonString, type);
        return message;
    }

    private void SaveData(List<Messages> list) {
        SharedPreferences sharedPreferences2 = getSharedPreferences(getPackageName()+".my_file3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences2.edit();
        Gson gson= new Gson();
        String jsonString=gson.toJson(list);
        //String value=hashmap.put(id,jsonString);
        editor.putString("Message"+id.toString(),jsonString);
        //editor.putString("id",id);
        editor.apply();
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
    private void OnFailure()
    {
        SharedPreferences sharedPreferences2 = getSharedPreferences(getPackageName() + ".my_file3", Context.MODE_PRIVATE);
        String jsonString = sharedPreferences2.getString("Message" + id.toString(), "N/A");
        if (jsonString.equals("N/A")) {
            Toast.makeText(MessagesActivity.this, "null", Toast.LENGTH_SHORT).show();
        }
        else {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Messages>>(){
            }.getType();
            List<Messages> message = gson.fromJson(jsonString, type);

            ListView listView = findViewById(R.id.view);
            String[] arr = new String[message.size()];
            for (int i = 0; i < message.size(); i++) {
                arr[i] = message.get(i).getText();
            }
            ArrayAdapter adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, arr);
            listView.setAdapter(adapter);
        }

    }

    private void doTheAutoRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Write code for your refresh logic
                getmessages();
                doTheAutoRefresh();
            }
        }, 100);
    }
}


