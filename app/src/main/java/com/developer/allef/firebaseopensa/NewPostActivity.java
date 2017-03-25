package com.developer.allef.firebaseopensa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;
import java.util.Map;

import static android.R.attr.key;

public class NewPostActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        findViewById(R.id.post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = ((EditText) findViewById(R.id.coment)).getText().toString();
                // criando um novo NÓ FB
                final DatabaseReference db = FirebaseDatabase.getInstance().getReference();
                String postId = db.push().getKey();

                final String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Map postValues = new HashMap();
                                postValues.put("user_id", userId);
                                postValues.put("comment", comment);
                                postValues.put("image", null);
                                postValues.put("likes_count", 0);
                                postValues.put("comments_count", 0);
                                postValues.put("created_at", ServerValue.TIMESTAMP);

                Map updateValues = new HashMap();
                                updateValues.put("posts/" + postId, postValues);
                                updateValues.put("user_posts/" + userId + "/" + postId, postValues);
                db.updateChildren(updateValues, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            // TODO deu erro e agora kk

                        }
                        finish();
                    }
                });


            }
        });


    }
}
