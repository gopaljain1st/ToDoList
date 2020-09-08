package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todolist.model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class ViewTaskByProject extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Task> arrayList;
    private FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;
    Spinner type;
    Button viewTasks;
    List<String> al;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_by_project);
        arrayList=new ArrayList<>();
        al=new ArrayList<>();
        recyclerView=findViewById(R.id.rv);
        type=findViewById(R.id.selectProject);
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
                arrayList.add(new Task(model.getProjectId(),model.getName(),model.getoName(),model.getDueDate(),model.getIsComplete()));
                al.add(model.getName());
                Log.e("123name",model.getName());
                Toast.makeText(ViewTaskByProject.this, ""+model.getName(), Toast.LENGTH_SHORT).show();
                //spinnerAdapter.notifyDataSetChanged();
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        final ProgressDialog pd=new ProgressDialog(ViewTaskByProject.this);
        pd.setTitle("Uploading....");
        pd.show();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    pd.dismiss();
                    al.add("hfdknm");
                    final ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<String>(ViewTaskByProject.this,android.R.layout.simple_spinner_item,al);
                    spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    type.setAdapter(spinnerAdapter);
                    type.setSelection(0);
                    spinnerAdapter.notifyDataSetChanged();
                }
            }, 5000);

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
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDueDate=itemView.findViewById(R.id.txtDueDate);
            txtOwnerName=itemView.findViewById(R.id.txtOwnerName);
            txtTaskName=itemView.findViewById(R.id.txtTaskName);
        }
    }
}
