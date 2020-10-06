package com.example.callingapp.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.callingapp.ActivityCalling;
import com.example.callingapp.Models.User;
import com.example.callingapp.R;

import java.util.ArrayList;

public class AllUsersAdapters extends RecyclerView.Adapter<AllUsersAdapters.AllUsersViewHolder>{
    //private static final android.R.attr R = ;
    Activity context;
    ArrayList<User> userArrayList;
    public AllUsersAdapters(Activity context,ArrayList<User> userArrayList){
        this.context=context;
        this.userArrayList=userArrayList;
    }

    @NonNull
    @Override
    public AllUsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_users,parent,false);
        AllUsersViewHolder allUsersAdapter=new AllUsersViewHolder(view);
        return allUsersAdapter;


    }

    @Override
    public void onBindViewHolder(@NonNull AllUsersViewHolder holder, int position) {
        User user=userArrayList.get(position);
        Log.v("onBindViewHolder", String.valueOf(userArrayList.get(position)));
        holder.textViewName.setText(user.getName());

    }

    @Override
    public int getItemCount() {
        Log.v("inside getItemCount",String.valueOf(userArrayList.size()));
        return userArrayList.size();
    }

    public class AllUsersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        Button button;
        public AllUsersViewHolder(View itemView) {
            super(itemView);
            textViewName=(TextView)itemView.findViewById(R.id.itemName);
            button =itemView.findViewById(R.id.callButton);
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    User user=userArrayList.get(getBindingAdapterPosition());
                    if (user!=null){
                    ((ActivityCalling)context).callUser(user);}
                }
            });
        }
    }
}
