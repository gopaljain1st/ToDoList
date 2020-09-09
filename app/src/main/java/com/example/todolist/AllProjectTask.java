package com.example.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.model.Task;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllProjectTask extends AppCompatActivity {

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

        Intent intent=getIntent();
        final String pid=intent.getStringExtra("projectId");

        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        //arrayList.add(new Task("Buy Grocie","sag jain","12/09/2020"));
        fStore= FirebaseFirestore.getInstance();

        Query query = fStore.collection("Tasks");

        FirestoreRecyclerOptions<Task> response = new FirestoreRecyclerOptions.Builder<Task>()
                .setQuery(query, Task.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Task, AllProjectTask.TaskViewHolder>(response) {
            @Override
            protected void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull final Task model) {

                if (!pid.equals(model.getProjectId())){
                    holder.cv.setVisibility(View.GONE);
                }
                holder.txtTaskName.setText(model.getName());
                holder.txtDueDate.setText(model.getDueDate());
                holder.txtOwnerName.setText(model.getoName());
                if(model.getIsComplete().equals("true"))
                    holder.checkBox.setChecked(true);

                /*
                * holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if(b)
                        {
                            new AlertDialog.Builder(AllProjectTask.this).setTitle("Update").setMessage("Are You Sure You Want To Complete This Task").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    final DocumentReference docRef=fStore.collection("Tasks").document(model.getTaskId());
                                    Map<String,Object> hm=new HashMap<>();
                                    hm.put(""+model.getTaskId(),model);
                                    docRef.update(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task)
                                        {
                                            if(task.isSuccessful())
                                            {
                                                Toast.makeText(AllProjectTask.this, "Task Updated Successfully", Toast.LENGTH_SHORT).show();
                                                adapter.notifyDataSetChanged();
                                            }
                                            else Toast.makeText(AllProjectTask.this, ""+task, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i)
                                {

                                }
                            }).show();
                        }
                    }
                });*/

            }

            @NonNull
            @Override
            public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.custome_view_all_task, parent, false);

                return new AllProjectTask.TaskViewHolder(view);
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
