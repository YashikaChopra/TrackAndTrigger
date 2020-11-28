package com.example.trackandtrigger;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class TOdo_Activity2 extends AppCompatActivity {
private EditText editText;
private int id;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menubar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
            if (id==R.id.todo_notification){
                Intent intent=new Intent(com.example.trackandtrigger.TOdo_Activity2.this,Message.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_t_odo_2);

        editText=findViewById(R.id.todo_edit_text);
        String contents=getIntent().getStringExtra("contents");
        id=getIntent().getIntExtra("id",0);
        editText.setText(contents);

    }
    @Override
    protected void onPause() {
        super.onPause();
        Todo_Activity.database.todoDAO().save(editText.getText().toString(),id);
    }

}