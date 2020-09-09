package com.example.todolist.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.CreateTask;
import com.example.todolist.R;
import com.example.todolist.model.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewAllTaskAdapter extends RecyclerView.Adapter<ViewAllTaskAdapter.TaskViewHolder> {

    private final FirebaseFirestore fStore;
    Context context;
    ArrayList<Task> taskList;

    public ViewAllTaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
        fStore= FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_view_all_task, parent, false);
        return new ViewAllTaskAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        final Task task=taskList.get(position);
        holder.txtTaskName.setText(task.getName());
        holder.txtOwnerName.setText(task.getoName());
        holder.txtDueDate.setText(task.getDueDate());
        if(task.getIsComplete().equals("true"))
            holder.checkBox.setChecked(true);
       /* holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    new AlertDialog.Builder(context).setTitle("Update").setMessage("Are You Sure You Want To Complete This Task").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which)
                        {
                            final DocumentReference docRef=fStore.collection("Tasks").document(task.getTaskId());
                            task.setIsComplete("true");
                            Map<String,Object> hm=new HashMap<>();
                            hm.put("isComplete","true");
                            docRef.update(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(context, "Task Updated Successfully", Toast.LENGTH_SHORT).show();

                                    }
                                    else Toast.makeText(context, ""+task, Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return taskList.size();
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
