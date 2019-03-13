package com.example.roshan.chatapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.roshan.chatapplication.ListActivity;
import com.example.roshan.chatapplication.MessagesActivity;
import com.example.roshan.chatapplication.Model.Person;
import com.example.roshan.chatapplication.R;

import java.util.List;

/**
 * Created by Roshan on 09-03-2019.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>{

    private List<Person> mperson;


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView nameTextView;
        private Context context;
        public ViewHolder(Context context,View itemView) {
            super(itemView);
            this.nameTextView = (TextView) itemView.findViewById(R.id.user_name);
            this.context=context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                //SharedPreferences sharedPreferences = getSharedPreferences(getPackageName()+".my_file",Context.MODE_PRIVATE);
                Person person = mperson.get(position);
                String a=person.getName();
                int id=person.getId();
                String ID=Integer.toString(id);
                //Toast.makeText(context, nameTextView.getText(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context,MessagesActivity.class);
                intent.putExtra("id",ID);
                context.startActivity(intent);

            }
        }
    }
    public UserAdapter(List<Person> person){
        mperson=person;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.user_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(context,contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder viewHolder, int position) {
        Person person=mperson.get(position);
        TextView textView = viewHolder.nameTextView;
        textView.setText(person.getName());
    }

    @Override
    public int getItemCount() {
        return mperson.size();
    }


}
