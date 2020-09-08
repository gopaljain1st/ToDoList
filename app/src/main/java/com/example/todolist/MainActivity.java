package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask,btnCreateProject,btnViewAllTask,btnAllTaskByProjects;
    private View popupInputDialogView = null;
    private EditText taskName = null,taskOwnerName;
    private EditText taskDueDate = null;
    private CheckBox status = null;
    private ImageView saveUserDataButton = null;
    private ImageView cancelUserDataButton = null;
    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAddTask=findViewById(R.id.btnAddTask);
        fStore=FirebaseFirestore.getInstance();

        btnViewAllTask=findViewById(R.id.btnViewAllTask);

        btnAllTaskByProjects=findViewById(R.id.btnViewAllTaskByProject);
        btnAllTaskByProjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Test.class);
                startActivity(intent);

            }
        });
        btnViewAllTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ViewAllTask.class);
                startActivity(intent);
            }
        });

        btnCreateProject=findViewById(R.id.btnCreateProject);
        btnCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,CretateProject.class);
                startActivity(intent);
            }
        });
        btnAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alert;
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
                    alert=new android.app.AlertDialog.Builder(MainActivity.this,android.R.style.Theme_Material_Dialog_Alert);
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
                        public void onClick(View view) {
                            final ProgressDialog pd=new ProgressDialog(MainActivity.this);
                            pd.setTitle("Uploading....");
                            pd.show();

                            com.example.todolist.model.Task t=new com.example.todolist.model.Task("0",taskName.getText().toString(),taskOwnerName.getText().toString(),taskDueDate.getText().toString(),""+status.isChecked());

                            final DocumentReference docRef=fStore.collection("Tasks").document(""+System.currentTimeMillis());

                            docRef.set(t).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    pd.dismiss();
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(MainActivity.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                    else Toast.makeText(MainActivity.this, ""+task, Toast.LENGTH_SHORT).show();
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
                    alert=new AlertDialog.Builder(MainActivity.this);
                }

            }
        });


    }
}
