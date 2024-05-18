package com.example.chattingproject1;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Chatting extends AppCompatActivity {

    EditText message;
    Button sendBtn ,btn_logout;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        sendBtn=findViewById(R.id.send);
        btn_logout = findViewById(R.id.btn_logout);
        ArrayList al= new ArrayList();
        al.clear();
        list=findViewById(R.id.list);
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        message=findViewById(R.id.EditTextMessage);
        ArrayAdapter adapter =  new ArrayAdapter(Chatting.this, android.R.layout.simple_list_item_1,al);
        adapter.clear();



        db.getReference("Messages").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //Toast.makeText(ChatActivity.this, "Message Added! " + snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                al.add(snapshot.getValue().toString());
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent loginIntent = new Intent(Chatting.this, Login.class);
                startActivity(loginIntent);
                finish();
            }
        });

        sendBtn.setOnClickListener(v -> {

            FirebaseAuth auth = FirebaseAuth.getInstance();
            Date getdate= Calendar.getInstance().getTime();
            String newMessage = message.getText().toString();
            db.getReference("Messages").child(auth.getUid()+getdate.toString()).setValue(newMessage);
            message.setText("");

        });

    }
}
