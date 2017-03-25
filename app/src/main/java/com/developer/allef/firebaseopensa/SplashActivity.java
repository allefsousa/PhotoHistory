package com.developer.allef.firebaseopensa;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance().getReference("users").child(mAuth.getCurrentUser().getUid());

        // verificando autenticação Anonyma.
        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    // TODO: Fail ......
                    return;
                }
                // mostrando o Uid Do Usuario
                Log.d("OpenSanca" , mAuth.getCurrentUser().getUid().toString());



                //listner para adicionar no BD
                db.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //se nao existir o uid do usuario ele adiona
                        if(!dataSnapshot.exists()){
                            //Set default values for user...
                            HashMap users = new HashMap();
                            users.put("name", "anonymous");
                            users.put("likes_count", 0);
                            users.put("posts_count", 0);
                            db.setValue(users);
                        }
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
