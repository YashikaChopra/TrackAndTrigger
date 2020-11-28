package com.example.trackandtrigger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<com.example.trackandtrigger.TodoAdapter.TodoViewHolder>{
    public static class TodoViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout containerView;
        public TextView textView;

        TodoViewHolder(View view){
            super(view);
            containerView=view.findViewById(R.id.todo_row);
            textView=view.findViewById(R.id.todo_row_text);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Todo current=(Todo)containerView.getTag();
                    Intent a= new Intent(v.getContext(), com.example.trackandtrigger.TOdo_Activity2.class);
                    a.putExtra("id",current.id);
                    a.putExtra("contents",current.contents);

                    v.getContext().startActivity(a);

                }
            });
        }
}
    List<Todo> todo=new ArrayList<>();

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_row,parent,false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo current= todo.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }

    @Override
    public int getItemCount() {
        return todo.size();
    }

    public void reload(){
        todo= com.example.trackandtrigger.Todo_Activity.database.todoDAO().getAllTodo();
        notifyDataSetChanged();
    }
}
