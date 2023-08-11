package com.example.vsbec;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class profile extends AppCompatActivity {

    ImageView setprofile;
    TextView setname, setstatus;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String email,password;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        storage = FirebaseStorage.getInstance();
        setprofile = findViewById(R.id.menuprofilepic);
        setname = findViewById(R.id.menuusername);
        setstatus = findViewById(R.id.menurollno);
        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());

       reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("profilepic")) {
                    email = snapshot.child("usersid").getValue().toString();
                    password = snapshot.child("password").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String profile = snapshot.child("profilepic").getValue().toString();
                    String status = snapshot.child("rollno").getValue().toString();
                    setname.setText(name);
                    setstatus.setText(status);
                    Picasso.get().load(profile).into(setprofile);
                }
                else {
                    Toast.makeText(profile.this, "Profile pic not uploaded", Toast.LENGTH_SHORT).show();
                    email = snapshot.child("usersid").getValue().toString();
                    password = snapshot.child("password").getValue().toString();
                    String name = snapshot.child("name").getValue().toString();
                    String profile = "https://firebasestorage.googleapis.com/v0/b/vsbec-5c8aa.appspot.com/o/default.png?alt=media&token=84c3f3e7-a6cc-4464-a30c-255e79f47b7d";
                    String status = snapshot.child("rollno").getValue().toString();
                    setname.setText(name);
                    setstatus.setText(status);
                   Picasso.get().load(profile).into(setprofile);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(profile.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void homepage(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(profile.this,Loginpage.class);
        startActivity(intent);
        finish();
    }

}