package com.example.trackandtrigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackandtrigger.activities.CategoryActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
public Button button,button2,list,signout;
private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        button=(Button)findViewById(R.id.todo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.example.trackandtrigger.MainActivity.this,Todo_Activity.class);
                startActivity(intent);

            }
        });
        button2=(Button)findViewById(R.id.diary_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(com.example.trackandtrigger.MainActivity.this,Diary_Activity.class);
                startActivity(intent);

            }
        });
        list=(Button)findViewById(R.id.list_button);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, CategoryActivity.class);
                startActivity(intent);

            }
        });
        signout=(Button)findViewById(R.id.sign_out_button);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent intent=new Intent(MainActivity.this,RegistrationActivity.class);
                startActivity(intent);

            }
        });
    }
}