package com.example.trackandtrigger;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Todo_Activity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TodoAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static com.example.trackandtrigger.TodoDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_);

        database= Room.databaseBuilder(getApplicationContext(), com.example.trackandtrigger.TodoDatabase.class,"TODOS")
                .allowMainThreadQueries()
                .build();

        recyclerView=findViewById(R.id.recycler_view);
        layoutManager= new LinearLayoutManager(this);
        adapter=new TodoAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        FloatingActionButton button=findViewById(R.id.add_todo_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.todoDAO().create();
                adapter.reload();

            }
        });

        // To remove items
        /*recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.reload();
    }
}