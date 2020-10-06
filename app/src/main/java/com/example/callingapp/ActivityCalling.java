package com.example.callingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.callingapp.Adapters.AllUsersAdapters;
import com.example.callingapp.Models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.ArrayList;
import java.util.List;

public class ActivityCalling extends AppCompatActivity {
    private static final String TAG ="ActivityCalling" ;
    RecyclerView recyclerview;
    FirebaseAuth auth;
    FirebaseUser firebaseUser;
    SinchClient sinchClient;
    Call call;
    DatabaseReference reference;
    ArrayList<User> userArrayList;
    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);
        recyclerview=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        reference= FirebaseDatabase.getInstance().getReference().child("Members");
        userArrayList=new ArrayList<>();
        auth= FirebaseAuth.getInstance();
        auth.getCurrentUser();
        Throwable e = null;
        //String currentUserId;
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser!= null) {
            currentUserId = currentUser.getUid();
        }

        sinchClient= Sinch.getSinchClientBuilder().context(this)
                .userId(currentUserId)
                .applicationKey("3da2fbf1-4138-4d4d-85c2-aa933d32273d")
                .applicationSecret("IMzXUNrcyUK8dv2/0ydRkQ==")
                .environmentHost("clientapi.sinch.com")
                .build();
        sinchClient.setSupportCalling(true);
        sinchClient.startListeningOnActiveConnection();
        sinchClient.getCallClient().addCallClientListener(new SinchCallClientListener(){});
        sinchClient.start();
        Log.d("fetchAllUsers","fetchAllUsers();");
        fetchAllUsers();
        Log.d("fetchAllUsers---","***fetchAllUsers();****");
    }
    private class SinchCallListener implements CallListener {

        @Override
        public void onCallProgressing(Call call) {
            Toast.makeText(ActivityCalling.this, "Ringing", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEstablished(Call call) {
            Toast.makeText(ActivityCalling.this, "Connected", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCallEnded(Call endedCall) {
            Toast.makeText(ActivityCalling.this, "Call Ended", Toast.LENGTH_SHORT).show();
            call=null;
            endedCall.hangup();
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {
            //Toast.makeText(ActivityCalling.this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchAllUsers(){
        Log.d("inside fetchAllUsers","@#%$^ETDVSCVGHNFBD");
        reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    //userArrayList.clear();
                    if (snapshot.exists()){
                    for (DataSnapshot dss:snapshot.getChildren()){
                        User user=dss.getValue(User.class);
                        Log.d(TAG, "UserID inside getData: "+user);
                        //Log.d(TAG, "User Name inside getData: "+dss.child(String.valueOf(user)).child("name").getValue());
                        //Log.d(TAG, "DS inside getData: "+dss.child(String.valueOf(user)));

                        userArrayList.add(user);
                    }}
                    else{
                        Toast.makeText(ActivityCalling.this, "No user found", Toast.LENGTH_SHORT).show();
                    }
                    AllUsersAdapters adapters=new AllUsersAdapters(ActivityCalling.this,userArrayList);
                    Log.d("userArrayList", String.valueOf((userArrayList)));
                    recyclerview.setAdapter(adapters);
                    adapters.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ActivityCalling.this, "error" +error.getMessage(), Toast.LENGTH_SHORT).show();
                }}
        );
        Log.d("after attaching lstr","tag after");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.menu_logout){
            if (firebaseUser !=null ){
                auth.signOut();
                finish();
                //startActivity(new Intent(ActivityCalling.this,Login.class));
                Intent i =new Intent(ActivityCalling.this,LoginActivity.class);
                startActivity(i);
            }
        }
        return onOptionsItemSelected(item);
    }


    private class SinchCallClientListener implements CallClientListener {

        @Override
        public void onIncomingCall(CallClient callClient, Call incomingCall) {
            AlertDialog alertDialog=new AlertDialog.Builder(ActivityCalling.this).create();
            alertDialog.setTitle("CALLING");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Reject", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    call.hangup();
                }
            });
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Pick", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    call=incomingCall;
                    call.answer();
                    call.addCallListener(new SinchCallListener());
                    Toast.makeText(ActivityCalling.this, "Call is started", Toast.LENGTH_LONG).show();
                }
            });
            alertDialog.show();

        }
    }
    public void callUser(User user) {
        if(call==null){
            call=sinchClient.getCallClient().callUser(user.getUserId());
            call.addCallListener(new SinchCallListener());
            openCallerDialog(call);

        }
    }

    private void openCallerDialog(Call call) {
        AlertDialog alertDialogCall=new AlertDialog.Builder(ActivityCalling.this).create();
        alertDialogCall.setTitle("ALERT");
        alertDialogCall.setMessage("CALLING");
        alertDialogCall.setButton(AlertDialog.BUTTON_NEUTRAL, "Hang Up", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                call.hangup();
            }
        });
        alertDialogCall.show();
            }

}