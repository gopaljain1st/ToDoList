package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateTask extends AppCompatActivity {
    private EditText taskName = null,taskOwnerName;
    private EditText taskDueDate = null;
    private CheckBox status = null;
    private ImageView saveUserDataButton = null;
    private ImageView cancelUserDataButton = null;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);
        taskOwnerName=findViewById(R.id.et_oName);
        taskName = findViewById(R.id.et_tName);
        taskDueDate = findViewById(R.id.et_dueDate);
        status = findViewById(R.id.cb_status) ;
        saveUserDataButton = findViewById(R.id.bt_save);
        cancelUserDataButton = findViewById(R.id.bt_cancel);
        cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        fStore= FirebaseFirestore.getInstance();

        saveUserDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pd=new ProgressDialog(CreateTask.this);
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
                            Toast.makeText(CreateTask.this, "Task Added Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else Toast.makeText(CreateTask.this, ""+task, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
