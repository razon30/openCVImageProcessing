package com.prince.thesis;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.opencv.cultoftheunicorn.marvel.R;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ActivityAddCourse extends AppCompatActivity {


    EditText coursecode, coursetitle,  lastDay, room, friday, saturday, sunday, monday, tuesday, wednesday, thursday;
    private DatabaseReference databaseRoot;
    String teacher = "";
    String code;
    Button btn_login;

    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        coursecode = findViewById(R.id.course_code);
        coursetitle = findViewById(R.id.course_title);
       // teacher = findViewById(R.id.course_teacher);
        dropdown = findViewById(R.id.teacherSpinner);
        lastDay = findViewById(R.id.course_lastday);
        room = findViewById(R.id.course_room);
        friday = findViewById(R.id.course_friday);
        saturday = findViewById(R.id.course_saturday);
        sunday = findViewById(R.id.course_sunday);
        monday = findViewById(R.id.course_monday);
        tuesday = findViewById(R.id.course_tuesday);
        wednesday = findViewById(R.id.course_wednesday);
        thursday = findViewById(R.id.course_thursday);
        btn_login = findViewById(R.id.btn_login);


        String[] items = ActivityMain.teacherUserString.split("_");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);//set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        if (getIntent().getStringExtra("code") != null) {

            code = getIntent().getStringExtra("code");
            setdata(code);

        }

        lastDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetDate(lastDay);
            }
        });
        
        friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(friday);
            }
        });
        saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(saturday);
            }
        });
        sunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(sunday);
            }
        });
        monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(monday);
            }
        });
        tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(tuesday);
            }
        });
        wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(wednesday);
            }
        });
        thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAndSetTime(thursday);
            }
        });


    }

    private void getAndSetDate(final EditText lastDay) {

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        lastDay.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();


    }

    private void getAndSetTime(final EditText day) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                day.setText( selectedHour + ":" + selectedMinute+":"+"00");
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    Map<String, String> map;

    private void setdata(final String code) {

        map = new HashMap<>();
        final ProgressDialog progressDialog = new ProgressDialog(ActivityAddCourse.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();


        databaseRoot = FirebaseDatabase.getInstance().getReference();
        databaseRoot.child("course").child(code).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                map = (Map<String, String>) dataSnapshot.getValue();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {


                //handler.postDelayed(this, 3000);

                progressDialog.dismiss();
                setDataToView(map);


            }
        };
        handler.postDelayed(runnable, 3000);


    }

    private void setDataToView(Map<String, String> map) {

        coursecode.setText(code);
        btn_login.setText("Edit the course.");
        coursecode.setEnabled(false);
        if (!map.get("title").equals("")) coursetitle.setText(map.get("title"));
        if (!map.get("teacher").equals("")){
            teacher = map.get("teacher");
        }
        if (!map.get("room").equals("")) room.setText(map.get("room"));
        if (!map.get("lastDay").equals("")) lastDay.setText(map.get("lastDay"));

        if (!map.get("sun").equals("")) {
            sunday.setText(map.get("sun"));
        } else {
            sunday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("mon").equals("")) {
            monday.setText(map.get("mon"));
        } else {
            monday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("tue").equals("")) {
            tuesday.setText(map.get("tue"));
        } else {
            tuesday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("wed").equals("")) {
            wednesday.setText(map.get("wed"));
        } else {
            wednesday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("thu").equals("")) {
            thursday.setText(map.get("thu"));
        } else {
            thursday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("fri").equals("")) {
            friday.setText(map.get("fri"));
        } else {
            friday.setText(map.get("HH:mm:ss"));
        }
        if (!map.get("sat").equals("")) {
            saturday.setText(map.get("sat"));
        } else {
            saturday.setText(map.get("HH:mm:ss"));
        }


    }




    public void onSubmitCourse(View view) {

        if (coursecode.getText().toString().isEmpty()) {
            coursecode.setError("Please Add Course Code");
            return;
        }

        String course_code = coursecode.getText().toString();
        String course_title = coursetitle.getText().toString();
       // String course_teacher = teacher.getText().toString();
        teacher = dropdown.getSelectedItem().toString();
        String course_room = room.getText().toString();
        String course_lastday = lastDay.getText().toString();
        String course_friday = friday.getText().toString();
        String course_saturday = saturday.getText().toString();
        String course_sunday = sunday.getText().toString();
        String course_monday = monday.getText().toString();
        String course_tuesday = tuesday.getText().toString();
        String course_wednesday = wednesday.getText().toString();
        String course_thursday = thursday.getText().toString();

        databaseRoot = FirebaseDatabase.getInstance().getReference();

        databaseRoot.child("course").child(course_code).child("title").setValue(course_title);
        databaseRoot.child("course").child(course_code).child("teacher").setValue(teacher);
        databaseRoot.child("course").child(course_code).child("room").setValue(course_room);
        databaseRoot.child("course").child(course_code).child("lastDay").setValue(course_lastday);
        databaseRoot.child("course").child(course_code).child("sun").setValue(course_sunday);
        databaseRoot.child("course").child(course_code).child("mon").setValue(course_monday);
        databaseRoot.child("course").child(course_code).child("tue").setValue(course_tuesday);
        databaseRoot.child("course").child(course_code).child("wed").setValue(course_wednesday);
        databaseRoot.child("course").child(course_code).child("thu").setValue(course_thursday);
        databaseRoot.child("course").child(course_code).child("fri").setValue(course_friday);
        databaseRoot.child("course").child(course_code).child("sat").setValue(course_saturday);


        if (getIntent().getStringExtra("code") != null) {

            Toast.makeText(ActivityAddCourse.this, "Course Info Edited", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(ActivityAddCourse.this, "Course Info Added", Toast.LENGTH_SHORT).show();
        }


        startActivity(new Intent(ActivityAddCourse.this, ActivityAdmin.class));
    }
}
