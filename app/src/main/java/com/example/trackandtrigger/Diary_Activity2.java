package com.example.trackandtrigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Diary_Activity2 extends AppCompatActivity {
    private EditText editText;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_2);

        editText=findViewById(R.id.diary_edit_text);
        String contents=getIntent().getStringExtra("contents");
        id=getIntent().getIntExtra("id",0);
        editText.setText(contents);

        Button save = findViewById(R.id.diarysave);
        save.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(Diary_Activity2.this, Diary_Activity.class);
                startActivity(i);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        com.example.trackandtrigger.Diary_Activity.database.diaryDAO().save(editText.getText().toString(),id);
    }

}