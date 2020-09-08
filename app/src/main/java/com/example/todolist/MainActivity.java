package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Button btnAddTask,btnCreateProject,btnViewAllTask;
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

        btnViewAllTask=findViewById(R.id.btnViewAllTask);

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
