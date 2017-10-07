package com.example.android.androidregister;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.android.androidregister.database.BatchesDAO;
import com.example.android.androidregister.database.DbHelper;
import com.example.android.androidregister.database.StudentsDAO;
import com.example.android.androidregister.model.Student;
import com.piotrek.customspinner.CustomSpinner;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private SwipeDeck cardStack;
    private FloatingActionButton coursebutton;

    private String selectedBatch;
    private MyAdapter myAdapter;

    private LinearLayout background;

    private ArrayList<Student> students;

    private ArrayList<Integer> absentIds;
    private ArrayList<Integer> presentIds;
    BatchesDAO ob = new BatchesDAO(this);

    private Handler handler;

    private CustomSpinner spinner;
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            background.setBackgroundColor(Color.WHITE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FontsOverride.applyFontForToolbarTitle(this, FontsOverride.FONT_PROXIMA_NOVA, getWindow());

        initViews();
        initSpinnerAdapter();

        absentIds = new ArrayList<>();
        presentIds = new ArrayList<>();

        handler = new Handler();
    }

    private void initViews() {
        cardStack = (SwipeDeck) findViewById(R.id.swipe_deck);
        background = (LinearLayout) findViewById(R.id.activity_main);
        spinner = (CustomSpinner) findViewById(R.id.spinner_batch);
        coursebutton = (FloatingActionButton) findViewById(R.id.create_course_button);
        coursebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateNewCourse.class);
                startActivity(intent);
            }
        });
    }

    public void initSpinnerAdapter() {
        final String hintText = "Choose a batch... ";

        ArrayList<String> batches = (ArrayList<String>) ob.getAllBatches();
        spinner.initializeStringValues(batches.toArray(new String[batches.size()]), hintText);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (!adapterView.getSelectedItem().toString().equals(hintText)) {
                    selectedBatch = adapterView.getSelectedItem().toString();
                    fetchStudent(selectedBatch);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void fetchStudent(String batch) {
        StudentsDAO ob = new StudentsDAO(this);
        absentIds = new ArrayList<>();
        presentIds = new ArrayList<>();
        students = ob.getallStudents(batch);
        myAdapter = new MyAdapter();
        cardStack.setAdapter(myAdapter);
        setupEventListener();
    }

    private void setupEventListener() {

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                background.setBackgroundColor(Color.RED);
                handler.postDelayed(run, 200);
                absentIds.add(students.get(position).getUniqueId());
                Log.d(TAG, "cardSwipedLeft: " + students.get(position).getUniqueId());
            }

            @Override
            public void cardSwipedRight(int position) {
                //present
                background.setBackgroundColor(Color.GREEN);
                handler.postDelayed(run, 200);
                presentIds.add(students.get(position).getUniqueId());
                Log.d(TAG, "cardSwipedRight: " + students.get(position).getUniqueId());
            }

            @Override
            public void cardsDepleted() {
                Intent intent = new Intent(MainActivity.this, ListOfAbsentPresentStudentsActivity.class);
                intent.putExtra("presentIds", presentIds);
                intent.putExtra("absentIds", absentIds);
                intent.putExtra("batchName", selectedBatch);
                startActivity(intent);
            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });
    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return students.size();
        }

        @Override
        public Student getItem(int position) {
            return students.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater li = getLayoutInflater();
            StudentViewHolder studentViewHolder;

            if (convertView == null) {
                convertView = li.inflate(R.layout.list_item_students, parent, false);
                studentViewHolder = new StudentViewHolder();
                studentViewHolder.tv_batch = (TextView) convertView.findViewById(R.id.tv_batchName);
                studentViewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_studentName);
                studentViewHolder.iv_photo = (ImageView) convertView.findViewById(R.id.iv_studentPhoto);

                convertView.setTag(studentViewHolder);
            } else {
                studentViewHolder = (StudentViewHolder) convertView.getTag();
            }

            Student stu = getItem(position);
            studentViewHolder.tv_name.setText(stu.getName());
            studentViewHolder.tv_batch.setText(stu.getBatch());
            //TODO: Set Pic

            return convertView;

        }
    }

    private class StudentViewHolder {
        TextView tv_name;
        TextView tv_batch;
        ImageView iv_photo;
    }

    private int numberOfLectures(String batchName) {
        DbHelper mDbHelper = new DbHelper(this);
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_NAME_ATTENDANCE,
                new String[]{DbHelper.COLUMN_BATCH, DbHelper.COLUMN_LECTURE_NUMBER,},
                DbHelper.COLUMN_BATCH + " = ?",
                new String[]{batchName},
                null, null, null, null);
        if (cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex(DbHelper.COLUMN_LECTURE_NUMBER));
        else
            return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_database, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_database:
                viewAttendanceDatabase();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void viewAttendanceDatabase() {
        Intent intent = new Intent(MainActivity.this, AttendanceDatabaseActivity.class);
        startActivity(intent);
    }
}