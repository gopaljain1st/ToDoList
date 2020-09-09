package com.example.todolist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ViewAllTaskAdapter extends RecyclerView.Adapter<ViewAllTaskAdapter.TaskViewHolder> {

    Context context;
    ArrayList<Task> taskList;

    public ViewAllTaskAdapter(Context context, ArrayList<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custome_view_all_task, parent, false);
        return new ViewAllTaskAdapter.TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        Task task=taskList.get(position);
        holder.txtTaskName.setText(task.getName());
        holder.txtOwnerName.setText(task.getoName());
        holder.txtDueDate.setText(task.getDueDate());
        if(task.getIsComplete().equals("true"))
            holder.checkBox.setChecked(true);
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
