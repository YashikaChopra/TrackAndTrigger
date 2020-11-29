package com.example.trackandtrigger;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Diary_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DiaryAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static com.example.trackandtrigger.DiaryDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_);
        setTitle("Diary");
        database= Room.databaseBuilder(getApplicationContext(), com.example.trackandtrigger.DiaryDatabase.class,"DIARYS")
                .allowMainThreadQueries()
                .build();

        recyclerView=findViewById(R.id.recycler_view2);
        layoutManager= new LinearLayoutManager(this);
        adapter=new DiaryAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        FloatingActionButton button=findViewById(R.id.add_diary_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.diaryDAO().create();
                adapter.reload();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}