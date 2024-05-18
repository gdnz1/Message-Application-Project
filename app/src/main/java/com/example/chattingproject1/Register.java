package com.example.chattingproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText SignUpemail,SignUppassword;
    Button btn_register;
    TextView tv_loginnow;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SignUpemail=findViewById(R.id.rg_email);
        SignUppassword=findViewById(R.id.rg_password);
        btn_register=findViewById(R.id.btn_register);
        tv_loginnow=findViewById(R.id.txt_loginnow);
        auth=FirebaseAuth.getInstance();


        btn_register.setOnClickListener(v -> {
            String user=SignUpemail.getText().toString().trim();
            String pass=SignUppassword.getText().toString();

            if(TextUtils.isEmpty(user)){
                SignUpemail.setError("Email dont exist!");
            }
            if(TextUtils.isEmpty(pass)){
                SignUppassword.setError("Password dont exist!");
            }
            else{
                auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "SignUp Succesful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Register.this,Login.class));
                            finish();
                        }
                        else{
                            Toast.makeText(Register.this, "SignUp Failed!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }


        });

        tv_loginnow.setOnClickListener(v -> {
            startActivity(new Intent(Register.this,Login.class));
            finish();
        });

    }}