package com.example.vsbec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Loginpage extends AppCompatActivity {
    EditText email,password;
    FirebaseAuth auth;
    FirebaseDatabase database;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    android.app.ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        getSupportActionBar().hide();
        auth = FirebaseAuth.getInstance();
        email = findViewById(R.id.loginemail);
        password = findViewById(R.id.loginpasword);



    }

    public void signuppage(View view) {
        Intent intent=new Intent(this, Registrationpage.class);

        startActivity(intent);
        finish();
    }

    public void loginpage(View view) {
        String Email = email.getText().toString();
        String pass = password.getText().toString();

        if ((TextUtils.isEmpty(Email))){
            progressDialog.dismiss();
            Toast.makeText(Loginpage.this, "Enter The Email", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(pass)){
            progressDialog.dismiss();
            Toast.makeText(Loginpage.this, "Enter The Password", Toast.LENGTH_SHORT).show();
        }else if (!Email.matches(emailPattern)){
            progressDialog.dismiss();
            email.setError("Give Proper Email Address");
        }else if (password.length()<6){
            progressDialog.dismiss();
            password.setError("More Then Six Characters");
            Toast.makeText(Loginpage.this, "Password Needs To Be Longer Then Six Characters", Toast.LENGTH_SHORT).show();
        }else {
//connect to firebase for checking the username and password
            auth.signInWithEmailAndPassword(Email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        progressDialog.show();
                        try {
                            Intent intent = new Intent(Loginpage.this , MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                            finish();
                        }catch (Exception e){
                            Toast.makeText(Loginpage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(Loginpage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }

}