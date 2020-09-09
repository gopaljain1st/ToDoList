package com.example.todolist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.ViewAllTask;
import com.example.todolist.model.Project;
import com.example.todolist.model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity
{
    RecyclerView recyclerView;
    ArrayList<Project> arrayList;
    private FirebaseFirestore fStore;
    private FirestoreRecyclerAdapter adapter;
    List<String> al;
    Spinner type;
    ArrayList<String> stringArrayList;
    Button viewTasks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task_by_project);

        viewTasks=findViewById(R.id.viewTasks);
        al=new ArrayList<>();
        stringArrayList=new ArrayList<>();
        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycle);
        type=findViewById(R.id.selectProject);

        fStore= FirebaseFirestore.getInstance();

        Query query = fStore.collection("Projects");

        FirestoreRecyclerOptions<Project> response = new FirestoreRecyclerOptions.Builder<Project>()
                .setQuery(query, Project.class)
                .build();
        al.clear();
      adapter= new FirestoreRecyclerAdapter<Project, TaskViewHolder>(response) {
            @Override
            public void onBindViewHolder(TaskViewHolder holder, int position, Project model) {

                al.add(model.getProjectName());
                holder.cv.setVisibility(View.GONE);
                arrayList.add(new Project(model.getProjectName(),model.getProjectDescription(),model.getProjectDescription(),model.getProjectId()));

                // String pid=getItem(type.getSelectedItemPosition());
                //int position1=type.getSelectedItemPosition();
                //String id = adapter.getSnapshots().getSnapshot(position1).g();

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



        viewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Test.this,AllProjectTask.class);
                int p=type.getSelectedItemPosition();
                Project project=arrayList.get(p);
                intent.putExtra("projectId",project.getProjectId());
                Toast.makeText(Test.this, ""+project.getProjectId(), Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        //ViewAllTaskAdapter viewAllTaskAdapter=new ViewAllTaskAdapter(ViewAllTask.this,arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        final ProgressDialog pd=new ProgressDialog(Test.this);
        pd.setTitle("loading....");
        pd.show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                pd.dismiss();
                final ArrayAdapter<String> spinnerAdapter= new ArrayAdapter<>(Test.this,android.R.layout.simple_spinner_item,al);
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
        CardView cv;
        CheckBox checkBox;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDueDate=itemView.findViewById(R.id.txtDueDate);
            txtOwnerName=itemView.findViewById(R.id.txtOwnerName);
            txtTaskName=itemView.findViewById(R.id.txtTaskName);
            cv=itemView.findViewById(R.id.cv);
            checkBox=itemView.findViewById(R.id.checkbox);
        }
    }
}
