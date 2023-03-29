package com.example.pedalpals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class ViewDateWiseReport extends AppCompatActivity {
    CalendarView calendarView;
    TextView date;
    Button searchBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_date_wise_report);

        calendarView=findViewById(R.id.DateCalendarView);
        date=findViewById(R.id.date);
        searchBtn=findViewById(R.id.button);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int y, int m, int d) {
                String sdate = y+"-"+(m+1)+"-"+d; //d + "/" + m + "/" + y;
                date.setText(sdate);
            }
        });

        int isDate=getIntent().getIntExtra("isDate",0);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewDateWiseReport.this, TransactionAdmin.class);
                i.putExtra("isDate",1);
                i.putExtra("date","sdate");

                startActivity(i);
            }
        });
    }
}