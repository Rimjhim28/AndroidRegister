package com.example.android.androidregister;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.androidregister.database.AttendanceDAO;
import com.example.android.androidregister.model.Attendance;

import java.util.ArrayList;

/**
 * Created by HP on 07-10-2017.
 */

public class AttendanceDatabaseActivity extends AppCompatActivity {

    ListView attendanceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_database);
        attendanceListView = (ListView) findViewById(R.id.listview_attendance_database);
        getAllAttendance();
    }

    private void getAllAttendance(){
        AttendanceDAO ob = new AttendanceDAO(this);
        ArrayList<Attendance> allAttendance = (ArrayList<Attendance>) ob.getAllAttendance();
        AttendanceAdapter myAdapter = new AttendanceAdapter(this,allAttendance);
        attendanceListView.setAdapter(myAdapter);
    }
}
