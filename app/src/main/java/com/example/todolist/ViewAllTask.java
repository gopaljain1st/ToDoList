package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.example.todolist.adapter.ViewAllTaskAdapter;
import com.example.todolist.model.Task;

import java.util.ArrayList;

public class ViewAllTask extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Task> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_task);


        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycle);

        arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));

        ViewAllTaskAdapter viewAllTaskAdapter=new ViewAllTaskAdapter(ViewAllTask.this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(viewAllTaskAdapter);

    }
}
