package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.todolist.adapter.ViewAllTaskAdapter;
import com.example.todolist.model.Project;
import com.example.todolist.model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class ViewAllTask extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Task> arrayList;
    private FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_task);


        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycle);

        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        fStore= FirebaseFirestore.getInstance();

        Query query = fStore.collection("Tasks");

        FirestoreRecyclerOptions<Task> response = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Task, TaskViewHolder>(response) {
            @Override
            public void onBindViewHolder(TaskViewHolder holder, int position, Task model) {
                holder.txtTaskName.setText(model.getName());
                holder.txtDueDate.setText(model.getDueDate());
                holder.txtOwnerName.setText(model.getoName());
                if(model.getIsComplete().equals("true"))
                    holder.checkBox.setChecked(true);
            }

            @Override
            public TaskViewHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.custome_view_all_task, group, false);

                return new TaskViewHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };


        //ViewAllTaskAdapter viewAllTaskAdapter=new ViewAllTaskAdapter(ViewAllTask.this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView txtTaskName,txtOwnerName,txtDueDate;
        CheckBox checkBox;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDueDate=itemView.findViewById(R.id.txtDueDate);
            txtOwnerName=itemView.findViewById(R.id.txtOwnerName);
            txtTaskName=itemView.findViewById(R.id.txtTaskName);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}
