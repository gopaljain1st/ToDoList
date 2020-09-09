package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todolist.adapter.ViewAllTaskAdapter;
import com.example.todolist.model.Project;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class CretateProject extends AppCompatActivity {
    private View popupInputDialogView = null;
    Button btnAddTask;
    private EditText taskName = null,taskOwnerName,projectName,projectDescription;
    private EditText taskDueDate = null;
    private CheckBox status = null;
    private ImageView saveUserDataButton = null;
    private ImageView cancelUserDataButton = null;
    RecyclerView rv;
    RecyclerView.Adapter<ViewAllTaskAdapter.TaskViewHolder> adapter;
    Button addProject;
    FirebaseFirestore fStore;
    ArrayList<com.example.todolist.model.Task>al=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cretate_project);
        rv=findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        fStore=FirebaseFirestore.getInstance();
        btnAddTask=findViewById(R.id.btnAddTask);
        addProject=findViewById(R.id.addProject);
        projectName=findViewById(R.id.et_project_name);
        projectDescription=findViewById(R.id.et_project_description);
        al=new ArrayList<>();
        adapter=new ViewAllTaskAdapter(this,al);
        rv.setAdapter(adapter);
        //Query query = fStore.collection("Projects");



        final String projectId=""+System.currentTimeMillis();
        addProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final ProgressDialog pd=new ProgressDialog(CretateProject.this);
                pd.setTitle("Uploading....");
                pd.show();

                final DocumentReference docRef=fStore.collection("Projects").document(projectId);

                docRef.set(new Project(projectName.getText().toString(),projectDescription.getText().toString(),""+al.size(),projectId)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        pd.dismiss();
                       if(task.isSuccessful())
                       {
                           Toast.makeText(CretateProject.this, "Project Added Successfully", Toast.LENGTH_SHORT).show();
                           finish();
                       }
                       else Toast.makeText(CretateProject.this, ""+task, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    alert=new android.app.AlertDialog.Builder(CretateProject.this,android.R.style.Theme_Material_Dialog_Alert);
                    LayoutInflater inflater=getLayoutInflater();
                   // View view1=inflater.inflate(R.layout.addtask__popup,null);
                    popupInputDialogView = inflater.inflate(R.layout.addtask__popup, null);
                    taskOwnerName=popupInputDialogView.findViewById(R.id.et_oName);
                    taskName = popupInputDialogView.findViewById(R.id.et_tName);
                    taskDueDate = popupInputDialogView.findViewById(R.id.et_dueDate);
                    status = popupInputDialogView.findViewById(R.id.cb_status) ;
                    saveUserDataButton = popupInputDialogView.findViewById(R.id.bt_save);
                    cancelUserDataButton = popupInputDialogView.findViewById(R.id.bt_cancel);

                    alert.setView(popupInputDialogView);
                    alert.setCancelable(false);

                    final AlertDialog alertDialog=alert.create();
                    alertDialog.getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
                    alertDialog.show();

                    saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view)
                        {
                            com.example.todolist.model.Task t=new com.example.todolist.model.Task(projectId,taskName.getText().toString(),taskOwnerName.getText().toString(),taskDueDate.getText().toString(),""+status.isChecked());
                            al.add(t);
                            final ProgressDialog pd=new ProgressDialog(CretateProject.this);
                            pd.setTitle("Uploading....");
                            pd.show();

                            final DocumentReference docRef=fStore.collection("Tasks").document(""+System.currentTimeMillis());

                            docRef.set(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    pd.dismiss();
                                    if(task.isSuccessful())
                                    {
                                        adapter.notifyDataSetChanged();
                                        Toast.makeText(CretateProject.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                    else Toast.makeText(CretateProject.this, ""+task, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                    cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                }
                else {
                    alert=new AlertDialog.Builder(CretateProject.this);
                }

            }
        });



    }

    private void initPopupViewControls()
    {
        // Get layout inflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(CretateProject.this);

        // Inflate the popup dialog from a layout xml file.
                popupInputDialogView = layoutInflater.inflate(R.layout.addtask__popup, null);

        // Get user input edittext and button ui controls in the popup dialog.
        taskOwnerName=popupInputDialogView.findViewById(R.id.et_oName);
        taskName = popupInputDialogView.findViewById(R.id.et_tName);
        taskDueDate = popupInputDialogView.findViewById(R.id.et_dueDate);
        status = popupInputDialogView.findViewById(R.id.cb_status) ;
        saveUserDataButton = popupInputDialogView.findViewById(R.id.bt_save);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.bt_cancel);

    }
}
