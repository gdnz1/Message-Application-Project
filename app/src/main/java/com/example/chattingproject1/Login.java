package com.example.chattingproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText LoginEmail, Loginpassword;
    Button btn_login;

    TextView tv_registernow;

    FirebaseAuth auth;
    FirebaseUser currentuser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoginEmail = findViewById(R.id.lg_email);
        Loginpassword = findViewById(R.id.lg_password);
        btn_login = findViewById(R.id.btn_login);
        tv_registernow = findViewById(R.id.txt_registernow);
        auth = FirebaseAuth.getInstance();
        currentuser = auth.getCurrentUser();



        if (currentuser != null) {
            startActivity(new Intent(Login.this, Chatting.class));
            finish();
        } else {

            btn_login.setOnClickListener(v -> {


                String emailText = LoginEmail.getText().toString().trim();
                String passText = Loginpassword.getText().toString().trim();

                if (TextUtils.isEmpty(emailText)) {
                    LoginEmail.setError("Email dont exist!");
                }
                if (TextUtils.isEmpty(passText)) {
                    Loginpassword.setError("Password dont exist!");
                }

                if (!emailText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    if (!passText.isEmpty()) {
                        auth.signInWithEmailAndPassword(emailText, passText).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(Login.this, "Login Succesful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, Chatting.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                }
            });

            tv_registernow.setOnClickListener(v -> {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            });
        }
    }
}