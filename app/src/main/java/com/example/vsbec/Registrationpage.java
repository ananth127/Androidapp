package com.example.vsbec;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registrationpage extends AppCompatActivity {
    EditText rg_name,rg_userid,rg_password,rg_repassword,rg_batchstart,rg_batchend,rg_department,rg_mobileno,rg_rollno;
    Button rg_signup,login_but;
    CircleImageView rg_profileImg;
    FirebaseAuth auth;
    Uri imageURI;
    String imageuri;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseDatabase database;
    FirebaseStorage storage;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrationpage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Establishing The Account");
        progressDialog.setCancelable(false);
        // getSupportActionBar().hide();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        rg_name = findViewById(R.id.signname);
        rg_userid = findViewById(R.id.signusername);
        rg_password = findViewById(R.id.signpassword);
        rg_repassword = findViewById(R.id.signrepassword);
        rg_batchstart = findViewById(R.id.signbatchstart);
        rg_batchend = findViewById(R.id.signbatchend);
        rg_department = findViewById(R.id.signdepartment);
        rg_mobileno = findViewById(R.id.signmobileno);
        rg_rollno=findViewById(R.id.signrollno);
        rg_profileImg=findViewById(R.id.profilepic);
        rg_signup = findViewById(R.id.signbutton);


        TextView mytextview=findViewById(R.id.signtologin);
        mytextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registrationpage.this,Loginpage.class);
                startActivity(intent);
                finish();

            }
        });
        rg_profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),10);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            if (data!=null){
                imageURI = data.getData();
                rg_profileImg.setImageURI(imageURI);
            }
        }
    }

    public void signuppage(View view) {

        String namee = rg_name.getText().toString();
        String usersid = rg_userid.getText().toString();
        String Password = rg_password.getText().toString();
        String cPassword = rg_repassword.getText().toString();
        String batch_start = rg_batchstart.getText().toString();
        String batch_end = rg_batchend.getText().toString();
        String department = rg_department.getText().toString();
        String mobileno = rg_mobileno.getText().toString();
        String rollno = rg_rollno.getText().toString();
        String status = "Hey I'm Using This Application";

        if (TextUtils.isEmpty(namee) || TextUtils.isEmpty(usersid) || TextUtils.isEmpty(rollno) ||
                TextUtils.isEmpty(Password) || TextUtils.isEmpty(cPassword) || TextUtils.isEmpty(batch_start) || TextUtils.isEmpty(batch_end) || TextUtils.isEmpty(department) || TextUtils.isEmpty(mobileno)) {
            progressDialog.dismiss();
            Toast.makeText(Registrationpage.this, "Please Enter Valid Information", Toast.LENGTH_SHORT).show();
        }else  if (!usersid.matches(emailPattern)) {
            //    progressDialog.dismiss();
            //    rg_email.setError("Type A Valid Email Here");}
            //else  if (!batch_start.matches(year)){
            //        progressDialog.dismiss();
            //        rg_batchstart.setError("Type A Valid year Here");}
        }else if (Password.length() < 6) {
            progressDialog.dismiss();
            rg_password.setError("Password Must Be 6 Characters Or More");
        } else if (!Password.equals(cPassword)) {
            progressDialog.dismiss();
            rg_password.setError("The Password Doesn't Match");
        }else if ((rollno.length() < 8) || (rollno.length() > 8)) {
            progressDialog.dismiss();
            rg_rollno.setError("Enter a valid Roll No");
        }
        else if ((batch_start.length() < 4) || (batch_start.length() > 4)) {
            progressDialog.dismiss();
            rg_batchstart.setError("Enter a valid Year");
        }
        else if ((batch_end.length() < 4) || (batch_start.length() > 4)) {
            progressDialog.dismiss();
            rg_batchend.setError("Enter a valid Year");
        }
        else if ((mobileno.length() < 10) || (mobileno.length() > 10)) {
            progressDialog.dismiss();
            rg_mobileno.setError("Enter a valid MobileNo");
        }else {

            auth.createUserWithEmailAndPassword(usersid,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        String id = task.getResult().getUser().getUid();
                        DatabaseReference reference = database.getReference().child("user").child(id);
                        StorageReference storageReference = storage.getReference().child("Upload").child(id);

                        if (imageURI!=null){
                            storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()){
                                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                imageuri = uri.toString();
                                                Users users = new Users(id,namee,usersid,rollno,Password,imageuri,status,batch_start,batch_end,department,mobileno);
                                                reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            progressDialog.show();
                                                            Intent intent = new Intent(Registrationpage.this,MainActivity.class);
                                                            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                                            startActivity(intent);
                                                            finish();
                                                        }else {
                                                            Toast.makeText(Registrationpage.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                }
                            });
                        }else {
                            String status = "Hey I'm Using This Application";
                            imageuri = "https://firebasestorage.googleapis.com/v0/b/vsbec-5c8aa.appspot.com/o/default.png?alt=media&token=84c3f3e7-a6cc-4464-a30c-255e79f47b7d";
                            Users users = new Users(id,namee,usersid,rollno,Password,imageuri,status,batch_start,batch_end,department,mobileno);
                            reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        progressDialog.show();
                                        Intent intent = new Intent(Registrationpage.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        Toast.makeText(Registrationpage.this, "Error in creating the user", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    }else {
                        Toast.makeText(Registrationpage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }


    }


        }