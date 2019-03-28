package com.prince.thesis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.prince.thesis.Models.userInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.cultoftheunicorn.marvel.R;


public class ActivityAddTeacher extends AppCompatActivity {

    Spinner dropdown;
    private DatabaseReference databaseRoot;
    TextView alert;
    Button btn_add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        dropdown = findViewById(R.id.teacherSpinner);
        alert = findViewById(R.id.alert);
        btn_add = findViewById(R.id.btn_add);

        btn_add.setText("Add Teacher");

        String[] items = ActivityMain.notTeacherUserString.split("_");

        if ((items.length == 1 && items[0].equals(""))||items.length == 0){
            dropdown.setVisibility(View.GONE);
            btn_add.setVisibility(View.GONE);
            alert.setVisibility(View.VISIBLE);
            return;
        }

        //set the spinners adapter to the previously created one.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

    }


    public void onSubmitTeacher(View view){
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        String userEmail = dropdown.getSelectedItem().toString();
        userInfo teacher = new userInfo();

        for(int i = 0; i < ActivityMain.userList.size() ; i++)
        {
         userInfo temp = ActivityMain.userList.get(i);

         if(temp.getEmail().contains(userEmail) && temp.getEmail().length() == userEmail.length())
         {
             teacher = temp;
             break;
         }
        }

        databaseRoot.child("student").child(teacher.getUid()).child("role").setValue("teacher");
        ActivityMain.userInfoRefresh();

        Toast.makeText(ActivityAddTeacher.this, "Teacher Info Added <" + userEmail +">", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityAddTeacher.this, ActivityAdmin.class));
    }
}