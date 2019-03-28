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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.opencv.cultoftheunicorn.marvel.R;

public class ActivityRemoveCourse extends AppCompatActivity {
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
        btn_add.setText("Remove this course.");

        String[] items = ActivityMain.courseString.split("_");

        if ((items.length == 1 && items[0].equals(""))||items.length == 0){
            dropdown.setVisibility(View.GONE);
            btn_add.setVisibility(View.GONE);
            alert.setVisibility(View.VISIBLE);
            alert.setText("No Course to remove.");
            return;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
    public void onSubmitTeacher(View view){
        databaseRoot = FirebaseDatabase.getInstance().getReference();

        String code = dropdown.getSelectedItem().toString();

        databaseRoot.child("course").child(code).setValue(null);
        ActivityMain.courseInfoRefresh();

        Toast.makeText(ActivityRemoveCourse.this, "Course Info Removed <" + code +">", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(ActivityRemoveCourse.this, ActivityAdmin.class));
    }
}
