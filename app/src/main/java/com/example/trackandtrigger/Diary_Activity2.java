package com.example.trackandtrigger;

import android.os.Bundle;
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
    }

    @Override
    protected void onPause() {
        super.onPause();
        Diary_Activity.database.diaryDAO().save(editText.getText().toString(),id);
    }

}