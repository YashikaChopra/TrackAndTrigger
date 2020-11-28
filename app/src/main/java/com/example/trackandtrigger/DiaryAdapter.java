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

public  class DiaryAdapter extends RecyclerView.Adapter<com.example.trackandtrigger.DiaryAdapter.DiaryViewHolder> {
    public static class DiaryViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout containerView;
        public TextView textView;

        DiaryViewHolder(View view){
            super(view);
            containerView=view.findViewById(R.id.diary_row);
            textView=view.findViewById(R.id.diary_row_text);

            containerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.example.trackandtrigger.Diary current=(com.example.trackandtrigger.Diary) containerView.getTag();
                    Intent a= new Intent(v.getContext(), com.example.trackandtrigger.Diary_Activity2.class);
                    a.putExtra("id",current.id);
                    a.putExtra("contents",current.contents);

                    v.getContext().startActivity(a);

                }
            });
        }
    }
    List<com.example.trackandtrigger.Diary> diary=new ArrayList<>();

    @NonNull
    @Override
    public com.example.trackandtrigger.DiaryAdapter.DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_row,parent,false);
        return new com.example.trackandtrigger.DiaryAdapter.DiaryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        com.example.trackandtrigger.Diary current= diary.get(position);
        holder.textView.setText(current.contents);
        holder.containerView.setTag(current);
    }


    @Override
    public int getItemCount() {
        return diary.size();
    }

    public void reload(){
        diary=Diary_Activity.database.diaryDAO().getAlldiary();
        notifyDataSetChanged();
    }
}

