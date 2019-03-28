package com.prince.thesis;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.prince.thesis.Models.EnrolledDataModel;
import com.prince.thesis.Models.courseInfo;
import com.prince.thesis.attendance.MainActivityRecognizer;

import org.opencv.cultoftheunicorn.marvel.R;

public class CourseDetailsActivity extends AppCompatActivity {


    private static courseInfo dataModel;

    CardView takeAttendance;
    TextView courseTv;

    public static void enterToCourseDetails(Activity activity, courseInfo enrolledDataModel) {

        dataModel = enrolledDataModel;
        activity.startActivity(new Intent(activity, CourseDetailsActivity.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);

        takeAttendance = findViewById(R.id.takeAttendance);
        courseTv = findViewById(R.id.courseTv);

        courseTv.setText(dataModel.getCode() + ": " + dataModel.getTitle());
        takeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CourseDetailsActivity.this, MainActivityRecognizer.class));
            }
        });

        Toast.makeText(this, dataModel.getCode(), Toast.LENGTH_LONG).show();


    }


}
