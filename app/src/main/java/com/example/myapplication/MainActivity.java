package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText edtId, edtName, edtAge, edtGpa;
    private Button btnAdd, btnClear;
    private TextView tvStudentList;
    private final ArrayList<Student> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtId = findViewById(R.id.edtId);
        edtName = findViewById(R.id.edtName);
        edtAge = findViewById(R.id.edtAge);
        edtGpa = findViewById(R.id.edtGpa);
        btnAdd = findViewById(R.id.btnAdd);
        btnClear = findViewById(R.id.btnClear);
        tvStudentList = findViewById(R.id.tvStudentList);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addStudent();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll();
            }
        });
    }

    private void addStudent() {
        String id = edtId.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String ageStr = edtAge.getText().toString().trim();
        String gpaStr = edtGpa.getText().toString().trim();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name)
                || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(gpaStr)) {
            Toast.makeText(this, getString(R.string.error_empty_fields), Toast.LENGTH_SHORT).show();
            return;
        }

        int age;
        double gpa;
        try {
            age = Integer.parseInt(ageStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.error_age_format), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            gpa = Double.parseDouble(gpaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, getString(R.string.error_gpa_format), Toast.LENGTH_SHORT).show();
            return;
        }

        if (age <= 0 || age > 100) {
            Toast.makeText(this, getString(R.string.error_age_range), Toast.LENGTH_SHORT).show();
            return;
        }
        if (gpa < 0 || gpa > 10) {
            Toast.makeText(this, getString(R.string.error_gpa_range), Toast.LENGTH_SHORT).show();
            return;
        }

        Student student = new Student(id, name, age, gpa);
        studentList.add(student);

        renderStudentList();
        edtId.setText("");
        edtName.setText("");
        edtAge.setText("");
        edtGpa.setText("");
        edtId.requestFocus();
    }

    private void renderStudentList() {
        if (studentList.isEmpty()) {
            tvStudentList.setText(getString(R.string.list_placeholder));
            return;
        }

        StringBuilder sb = new StringBuilder();
        String separator = getString(R.string.list_separator);
        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            sb.append(i + 1).append(". ").append(s.displayInfo(this));
            sb.append(separator);
        }
        tvStudentList.setText(sb.toString());
    }

    private void clearAll() {
        studentList.clear();
        renderStudentList();
        edtId.setText("");
        edtName.setText("");
        edtAge.setText("");
        edtGpa.setText("");
    }

    public static class Student {

        private String id;
        private String name;
        private int age;
        private double gpa;

        public Student(String id, String name, int age, double gpa) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.gpa = gpa;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public double getGpa() {
            return gpa;
        }

        public void setGpa(double gpa) {
            this.gpa = gpa;
        }

        public String getRank(Context context) {
            if (gpa >= 8.5) {
                return context.getString(R.string.rank_gioi);
            } else if (gpa >= 7.0) {
                return context.getString(R.string.rank_kha);
            } else if (gpa >= 5.0) {
                return context.getString(R.string.rank_trungbinh);
            } else {
                return context.getString(R.string.rank_yeu);
            }
        }

        public String displayInfo(Context context) {
            return context.getString(
                    R.string.student_info_format,
                    id, name, age, gpa, getRank(context)
            );
        }
    }
}