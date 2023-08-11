package com.example.vsbec;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseDatabase databas;
    String email,password;

    TextView setname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database.getInstance().setPersistenceEnabled(true);
        setname = findViewById(R.id.mainname);



        if (auth.getCurrentUser() == null){
            Intent intent = new Intent(MainActivity.this,Loginpage.class);
            startActivity(intent);
            finish();
        }
        DatabaseReference reference = database.getReference().child("user").child(auth.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email = snapshot.child("usersid").getValue().toString();
                password = snapshot.child("password").getValue().toString();
                String name = snapshot.child("name").getValue().toString();
                setname.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void homepage(View view) {

                Intent intent = new Intent(MainActivity.this, profile.class);
                startActivity(intent);

    }


    public void logout(View view) {

        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this,Loginpage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if(intent!=null){String data=intent.getStringExtra("key");
            if(data!=null&&data.equals("some")){
                finish();
                return;
            }

        }
    }

    public void menu(MenuItem item) {
        Intent intent=new Intent(MainActivity.this,home.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}