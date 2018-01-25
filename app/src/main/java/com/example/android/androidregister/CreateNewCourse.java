package com.example.android.androidregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.androidregister.database.BatchesDAO;
import com.example.android.androidregister.database.StudentsDAO;

/**
 * Created by  on 07-10-2017.
 */

public class CreateNewCourse extends AppCompatActivity {

    EditText txtCourseName,txtNumberOfLectures,txtStudentName;
    Button btnAddStudent,btnDone,btnCreateCourse;
    String mCourseName,mStringNumberOfLectures,mStudentName;
    int mNumberOfStudents = 0,mNumberOfLectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_course);

        txtCourseName = (EditText)findViewById(R.id.add_course_name);
        txtNumberOfLectures = (EditText) findViewById(R.id.add_number_of_lectures);
        txtStudentName = (EditText)findViewById(R.id.add_student_name);

        btnAddStudent = (Button) findViewById(R.id.btn_add_student);
        btnCreateCourse = (Button) findViewById(R.id.btn_create_course);
        btnDone = (Button) findViewById(R.id.btn_done);

        //Create on Click Listener to create a course
        btnCreateCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourse();
            }
        });

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });

    }

    private void addCourse(){
        mCourseName = txtCourseName.getText().toString();
        mStringNumberOfLectures = txtNumberOfLectures.getText().toString();
        mNumberOfLectures = Integer.parseInt(mStringNumberOfLectures);
        BatchesDAO ob = new BatchesDAO(this);
        long id = ob.createBatch(mCourseName,mNumberOfLectures);
    }

    public void addStudent(){
        mNumberOfStudents++;
        mStudentName = txtStudentName.getText().toString();
        txtStudentName.setText("");
        StudentsDAO ob = new StudentsDAO(this);
        long id = ob.createStudent(mStudentName,mCourseName);
    }

    private void done(){
        Intent intent = new Intent(CreateNewCourse.this,MainActivity.class);
        startActivity(intent);
    }
}

