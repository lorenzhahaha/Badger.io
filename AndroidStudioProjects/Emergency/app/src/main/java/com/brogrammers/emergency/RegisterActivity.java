package com.brogrammers.emergency;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    //Register tools
    private EditText mRegisterDisplayNameVal;
    private EditText mRegisterPasswordVal;
    private EditText mRegisterEmailVal;
    private Button mRegisterBtn;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Registered Callers");
        mProgress = new ProgressDialog(this);

        mRegisterDisplayNameVal = (EditText) findViewById(R.id.registerDisplayNameVal);
        mRegisterPasswordVal = (EditText) findViewById(R.id.registerPasswordVal);
        mRegisterEmailVal = (EditText) findViewById(R.id.registerEmailVal);
        mRegisterBtn = (Button) findViewById(R.id.registerBtn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });
    }

    private void startRegister() {
        final String displayName = mRegisterDisplayNameVal.getText().toString().trim();
        String password = mRegisterPasswordVal.getText().toString().trim();
        String email = mRegisterEmailVal.getText().toString().trim();

        //To check if fields are not empty
        if(!TextUtils.isEmpty(displayName) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {
            mProgress.setMessage("Signing up ...");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        String user_id = mAuth.getCurrentUser().getUid();

                        DatabaseReference registered_caller_db = mDatabaseUsers.child(user_id);
                        registered_caller_db.child("Display Name").setValue(displayName);
                        registered_caller_db.child("Pofile Picture").setValue("default");

                        mProgress.dismiss();
                        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(mainIntent);
                    }
                }
            });
        }
    }
}
