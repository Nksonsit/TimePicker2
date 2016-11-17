package com.androidapp.timepicker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.marcohc.robotocalendar.RobotoCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DateSelectActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView;
    private List<Studio> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_select);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        list = new ArrayList<>();
        list.add(new Studio("1", "01-11-2016", "10:00", "15:00", "email", "cno", "1", "12", "2016"));
        list.add(new Studio("2", "01-11-2016", "10:00", "15:00", "email", "cno", "10", "11", "2016"));
        list.add(new Studio("3", "01-11-2016", "15:00", "22:00", "email", "cno", "10", "11", "2016"));
        list.add(new Studio("4", "01-11-2016", "10:00", "15:00", "email", "cno", "15", "9", "2016"));
        list.add(new Studio("5", "01-11-2016", "10:00", "15:00", "email", "cno", "3", "10", "2016"));
        list.add(new Studio("6", "01-11-2016", "10:00", "15:00", "email", "cno", "6", "11", "2016"));
        list.add(new Studio("7", "01-11-2016", "10:00", "15:00", "email", "cno", "28", "8", "2016"));
        list.add(new Studio("8", "01-11-2016", "10:00", "15:00", "email", "cno", "20", "11", "2016"));

        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.robotoCalendarPicker);

        robotoCalendarView.setRobotoCalendarListener(this);

        robotoCalendarView.setShortWeekDays(true);

        robotoCalendarView.showDateTitle(true);

        robotoCalendarView.updateView();

        Calendar today = Calendar.getInstance();
        for (int i = 0; i < list.size(); i++) {
            setDateView(list.get(i), today);
        }
    }

    public void setDateView(Studio s, Calendar input) {
        Calendar c = Calendar.getInstance();
        Log.e(input.get(Calendar.YEAR) + "", "" + Integer.valueOf(s.getYear()));
        Log.e((input.get(Calendar.MONTH) + 1) + "", "" + Integer.valueOf(s.getMonth()));
        if ((input.get(Calendar.YEAR) == Integer.valueOf(s.getYear()) && ((input.get(Calendar.MONTH) + 1) == Integer.valueOf(s.getMonth())))) {
            c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(s.getDay()));
            c.set(Calendar.MONTH, (Integer.valueOf(s.getMonth()) - 1));
            c.set(Calendar.YEAR, Integer.valueOf(s.getYear()));
            robotoCalendarView.markCircleImage1(c);
        }
    }

    public boolean checkDateIsInList(Calendar input) {
        for (int i = 0; i < list.size(); i++) {
            if ((input.get(Calendar.YEAR) == Integer.valueOf(list.get(i).getYear()) && ((input.get(Calendar.MONTH) + 1) == Integer.valueOf(list.get(i).getMonth())))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDayClick(Calendar daySelectedCalendar) {
        if (checkDateIsInList(daySelectedCalendar)) {
            Toast.makeText(this, "Date is full. select other.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onDayClick: " + daySelectedCalendar.getTime(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDayLongClick(Calendar daySelectedCalendar) {
        if (checkDateIsInList(daySelectedCalendar)) {
            Toast.makeText(this, "Date is full. select other.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "onDayClick: " + daySelectedCalendar.getTime(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRightButtonClick() {
        Toast.makeText(this, "onRightButtonClick!", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onLeftButtonClick() {
        Toast.makeText(this, "onLeftButtonClick!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDateChange(Calendar calendar) {
        for (int i = 0; i < list.size(); i++) {
            setDateView(list.get(i), calendar);
        }
    }

}
