package com.brogrammers.badger.io.MainFeatures;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.brogrammers.badger.io.Authentication.LoginActivity;
import com.brogrammers.badger.io.Model.Users;
import com.brogrammers.badger.io.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainInterfaceActivity extends AppCompatActivity {

    Button mProfileSetupBtn;
    Button mLogoutBtn;
    Button mCheckOnlineUsersBtn;

    private DatabaseReference onlineRef, currentUserRef, counterRef;

    private DatabaseReference mDatabaseOnline;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_interface);

        //Register assets
        mProfileSetupBtn = (Button) findViewById(R.id.profileSetupBtn);
        mLogoutBtn = (Button) findViewById(R.id.logoutBtn);
        mCheckOnlineUsersBtn = (Button) findViewById(R.id.checkOnlineUsersBtn);

        //Register others
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Registered Users");
        mDatabase.child("Callers").keepSynced(true);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //Check if already logged in or not
                if(firebaseAuth.getCurrentUser() == null) {
                    Intent loginIntent = new Intent(MainInterfaceActivity.this, LoginActivity.class);

                    //So users can't go back
                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(loginIntent);
                }
            }
        };

        mProfileSetupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setupIntent = new Intent(MainInterfaceActivity.this, SetupAccountActivity.class);
                startActivity(setupIntent);
            }
        });

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserRef = FirebaseDatabase.getInstance().getReference("lastOnline")
                        .child(mAuth.getCurrentUser().getUid());
                currentUserRef.removeValue();
                mAuth.signOut();
                Intent logoutIntent = new Intent(MainInterfaceActivity.this, LoginActivity.class);
                startActivity(logoutIntent);
            }
        });

        mCheckOnlineUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkIntent = new Intent(MainInterfaceActivity.this, CheckOnlineUsersActivity.class);
                startActivity(checkIntent);
            }
        });

        //ADD USER TO ONLINE TABLE IMMEDIATELY AFTER LOGIN
        onlineRef = FirebaseDatabase.getInstance().getReference().child(".info/connected");
        counterRef = FirebaseDatabase.getInstance().getReference("lastOnline");
        currentUserRef = FirebaseDatabase.getInstance().getReference("lastOnline")
                .child(mAuth.getCurrentUser().getUid());

        if(mAuth.getCurrentUser() != null) {
            checkUserExist();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

        //ADD USER TO ONLINE TABLE IMMEDIATELY AFTER LOGIN
        setupSystem();
    }

    private void setupSystem() {
        onlineRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.getValue(Boolean.class)) {
                    //Delete old value
                    currentUserRef.onDisconnect().removeValue();

                    //Set online the user in list
                    counterRef.child(mAuth.getCurrentUser().getUid())
                            .setValue(new Users(mAuth.getCurrentUser().getEmail(), "Online"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        counterRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot:dataSnapshot.getChildren()) {
                    Users user = postSnapshot.getValue(Users.class);
                    Log.d("LOG", " " + user.getEmail() + " is" + user.getStatus());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkUserExist() {
        final String user_id = mAuth.getCurrentUser().getUid();

        //Check if user exist in database
        mDatabase.child("Callers").child(user_id).child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == "default") {
                    Intent setupIntent = new Intent(MainInterfaceActivity.this, SetupAccountActivity.class);

                    //So users can't go back
                    setupIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(setupIntent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
