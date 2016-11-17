package com.androidapp.timepicker;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private TextView sTime;
    private TextView eTime;
    private String initStartTime;
    private String initEndTime;
    private boolean correct = false;
    private RecyclerView timeSloatList;
    private TimeSloatAdapter adapter;
    private float diff;
    private Button dateBtn;
    private RobotoCalendarView robotoCalendarView;
    private ArrayList<Studio> timeList;
    private ArrayList<Studio> list;
    private ArrayList<Studio> sugList;
    private Calendar currentDate;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        timeList = new ArrayList<>();
        list = new ArrayList<>();
        list.add(new Studio("1", "17-11-2016", "10:00", "15:00", "email", "cno", "17", "12", "2016"));

        list.add(new Studio("2", "17-11-2016", "20:00", "24:00", "email", "cno", "17", "11", "2016"));
        list.add(new Studio("3", "17-11-2016", "12:00", "16:00", "email", "cno", "17", "11", "2016"));
        list.add(new Studio("6", "17-11-2016", "0:00", "4:00", "email", "cno", "17", "11", "2016"));

        list.add(new Studio("6", "17-11-2016", "1:00", "23:00", "email", "cno", "18", "11", "2016"));

        list.add(new Studio("4", "17-11-2016", "10:00", "15:00", "email", "cno", "15", "9", "2016"));

        list.add(new Studio("5", "17-11-2016", "10:00", "15:00", "email", "cno", "3", "10", "2016"));

        list.add(new Studio("7", "17-11-2016", "10:00", "24:00", "email", "cno", "20", "8", "2016"));
        list.add(new Studio("8", "17-11-2016", "0:00", "8:00", "email", "cno", "20", "8", "2016"));

        currentDate = Calendar.getInstance();
        for (int i = 0; i < list.size(); i++) {
            if ((currentDate.get(Calendar.YEAR) == Integer.valueOf(list.get(i).getYear()) && ((currentDate.get(Calendar.MONTH) + 1) == Integer.valueOf(list.get(i).getMonth()))) && ((currentDate.get(Calendar.DAY_OF_MONTH)) == Integer.valueOf(list.get(i).getDay()))) {
                timeList.add(list.get(i));
            }
        }

        initStartTime = "00:00";
        initEndTime = "00:00";


        sTime = (TextView) findViewById(R.id.stime);
        eTime = (TextView) findViewById(R.id.etime);

        sTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                sTime.setText(hourOfDay + ":" + minute);
                            }
                        })
                        .setDoneText("DONE")
                        .setCancelText("CANCEL")
                        .setForced24hFormat();
                rtpd.show(getSupportFragmentManager(), "MainActivity");
            }
        });


        eTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                        .setOnTimeSetListener(new RadialTimePickerDialogFragment.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
                                eTime.setText(hourOfDay + ":" + minute);
                            }
                        })
                        .setDoneText("DONE")
                        .setCancelText("CANCEL")
                        .setForced24hFormat();
                rtpd.show(getSupportFragmentManager(), "MainActivity");
            }
        });


//        getTime("06:20", "10:00");
//        getTime("18:00", "24:00");
        Button btn = (Button) findViewById(R.id.add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sTime.getText().toString().trim().equals("From Time") && !eTime.getText().toString().trim().equals("To Time")) {
                    diff = (Float.valueOf(eTime.getText().toString().trim().replace(":", "."))) - (Float.valueOf(sTime.getText().toString().trim().replace(":", ".")));
                    if (diff >= 4) {
                        getTime(sTime.getText().toString().trim(), eTime.getText().toString().trim());
                    } else {
                        Toast.makeText(MainActivity.this, "Please select atleast 4 hour time interval.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Please select time interval.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Collections.sort(timeList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        sugList = sugList(timeList);
        Collections.sort(sugList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        adapter = new TimeSloatAdapter(this, sugList);
        timeSloatList = (RecyclerView) findViewById(R.id.freesloat);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        timeSloatList.setLayoutManager(linearLayoutManager);
        timeSloatList.setAdapter(adapter);

        timeSloatList.setVisibility(View.GONE);

        dateBtn = (Button) findViewById(R.id.date);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(MainActivity.this);
                dialog.setContentView(R.layout.date_picker);
                dialog.setTitle("Select Date");

                // set the custom dialog components - text, image and button

                robotoCalendarView = (RobotoCalendarView) dialog.findViewById(R.id.robotoCalendarPicker);

                robotoCalendarView.setRobotoCalendarListener(MainActivity.this);

                robotoCalendarView.setShortWeekDays(true);

                robotoCalendarView.showDateTitle(true);

                robotoCalendarView.updateView();

                Calendar today = Calendar.getInstance();

                for (int i = 0; i < list.size(); i++) {
                    setDateView(list.get(i), today);
                }

                dialog.show();
            }
        });
    }
//
//    public void getTime(String startTime, String endTime) {
//        correct = false;
////        Studio time1 = list.get(0);
////        Time a = new Time(initStartTime, startTime);
////        Time b = new Time(endTime, initEndTime);
//
//        //Log.e("compare", startTime.compareTo(endTime) + " " + endTime.compareTo(startTime));
//
//        for (int i = 0; i < list.size(); i++) {
//            Studio t = list.get(i);
//            String pattern = "HH:mm";
//            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
//
//            try {
//                Date d1 = sdf.parse(t.getStime());
//                Date d2 = sdf.parse(t.getEtime());
//
//                Date ds = sdf.parse(startTime);
//                Date de = sdf.parse(endTime);
//                if (!correct) {
//                    if ((ds.compareTo(d1) >= 0) && (d2.compareTo(ds) >= 0)) {
//                        //Log.e(startTime, "in");
//                        if ((de.compareTo(d1) >= 0) && (d2.compareTo(de) >= 0)) {
//                            Log.e(startTime, endTime);
//                            correct = true;
//                            Studio n=new Studio();
//                            n.setStime(startTime);
//                            n.setEtime(endTime);
//                            changeList(list.get(i), n);
//                            break;
//                        } else {
//                            Toast.makeText(MainActivity.this, "Please select valid time interval please see suggestion.", Toast.LENGTH_SHORT).show();
//                            continue;
////                            Log.e(startTime + " " + endTime, "out");
//                        }
//                    } else {
//                        Toast.makeText(MainActivity.this, "Please select valid time interval please see suggestion.", Toast.LENGTH_SHORT).show();
//                        continue;
////                        Log.e(startTime + " " + endTime, "out");
//
//                    }
//
//                }
//            } catch (ParseException e) {
//            }
//        }
//
//        adapter.notifyDataSetChanged();
//    }

//    public void changeList(Studio oldT, Studio newT) {
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("time", list.get(i).toString());
//        }
//        List<Studio> list = new ArrayList<>();
//        Studio new1 = new Studio();
//        new1.setStime(oldT.getStime());
//        new1.setEtime(newT.getStime());
//        Studio new2 = new Studio();
//        new2.setStime(newT.getEtime());
//        new2.setEtime(oldT.getEtime());
//        list.remove(oldT);
//        list.add(new1);
//        list.add(new2);
//        for (int i = 0; i < list.size(); i++) {
//            Log.e("time", list.get(i).toString());
//        }
//    }

    public void getTime(String startTime, String endTime) {
        correct = false;
        //Log.e("compare", startTime.compareTo(endTime) + " " + endTime.compareTo(startTime));

        for (int i = 0; i < sugList.size(); i++) {
            Studio t = sugList.get(i);
            String pattern = "HH:mm";
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            try {
                Date d1 = sdf.parse(t.getStime());
                Date d2 = sdf.parse(t.getEtime());

                Date ds = sdf.parse(startTime);
                Date de = sdf.parse(endTime);
                Log.e(startTime, t.getStime());
                if (!correct) {
                    if ((ds.compareTo(d1) >= 0) && (d2.compareTo(ds) >= 0)) {
                        //Log.e(startTime, "in");
                        if ((de.compareTo(d1) >= 0) && (d2.compareTo(de) >= 0)) {
                            Log.e(startTime, endTime);
                            correct = true;
                            Log.e("starttime", sugList.get(i).toString());
                            Log.e("endTime", endTime);
                            changeList(sugList.get(i), new Studio(startTime, endTime));
                            break;
                        } else {
                            Toast.makeText(MainActivity.this, "Please select valid time interval please see suggestion.", Toast.LENGTH_SHORT).show();
                            continue;
//                            Log.e(startTime + " " + endTime, "out");
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "Please select valid time interval please see suggestion.", Toast.LENGTH_SHORT).show();
                        continue;
//                        Log.e(startTime + " " + endTime, "out");

                    }

                }
            } catch (ParseException e) {
            }
        }

        Collections.sort(timeList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        sugList = sugList(timeList);
        Collections.sort(sugList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        adapter.notifyDataSetChanged();
    }

    public ArrayList<Studio> sugList(ArrayList<Studio> list) {

        ArrayList<Studio> list1 = new ArrayList<>();
        if ((Float.valueOf(list.get(0).getStime().toString().trim().replace(":", ".")) == 0.00) && (Float.valueOf(list.get(list.size() - 1).getEtime().toString().trim().replace(":", ".")) == 24.00) && (list.size() == 1)) {
                    list1.add(new Studio(list.get(0).getStime(), list.get(0).getEtime()));
        }else if ((Float.valueOf(list.get(0).getStime().toString().trim().replace(":", ".")) != 0.00) && (Float.valueOf(list.get(list.size() - 1).getEtime().toString().trim().replace(":", ".")) != 24.00) && (list.size() == 1)) {
            list1.add(new Studio(list.get(0).getStime(), list.get(0).getEtime()));
        } else if ((Float.valueOf(list.get(0).getStime().toString().trim().replace(":", ".")) == 0.00) && (Float.valueOf(list.get(list.size() - 1).getEtime().toString().trim().replace(":", ".")) == 24.00)) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (Float.valueOf(list.get(i + 1).getStime().replace(":", ".")) - Float.valueOf(list.get(i).getEtime().replace(":", ".")) >= 4) {
                    list1.add(new Studio(list.get(i).getEtime(), list.get(i + 1).getStime()));
                }
            }
        } else if ((Float.valueOf(list.get(0).getStime().toString().trim().replace(":", ".")) == 0.00) && (Float.valueOf(list.get(list.size() - 1).getEtime().toString().trim().replace(":", ".")) != 24.00)) {
            for (int i = 0; i < list.size() - 1; i++) {
                if (Float.valueOf(list.get(i + 1).getStime().replace(":", ".")) - Float.valueOf(list.get(i).getEtime().replace(":", ".")) >= 4) {
                    list1.add(new Studio(list.get(i).getEtime(), list.get(i + 1).getStime()));
                }
            }
            if (24.0 - (Float.valueOf(list.get(list.size() - 1).getEtime().replace(":", "."))) >= 4) {
                list1.add(new Studio(list.get(list.size() - 1).getEtime(), "24:00"));
            }
        } else if ((Float.valueOf(list.get(0).getStime().toString().trim().replace(":", ".")) != 0.00) && (Float.valueOf(list.get(list.size() - 1).getEtime().toString().trim().replace(":", ".")) == 24.00)) {
            if (Float.valueOf(list.get(0).getStime().replace(":", ".")) - 0.00 >= 4) {
                list1.add(new Studio("00:00", list.get(0).getStime()));
            }
            for (int i = 0; i < list.size(); i++) {
                if (Float.valueOf(list.get(i + 1).getStime().replace(":", ".")) - Float.valueOf(list.get(i).getEtime().replace(":", ".")) >= 4) {
                    list1.add(new Studio(list.get(i).getEtime(), list.get(i + 1).getStime()));
                }
            }
        } else {
            if ((Float.valueOf(list.get(0).getStime().replace(":", "."))) - (00.00) >= 4) {
                list1.add(new Studio("00:00", list.get(0).getStime()));
                Log.e("0", "0");
            }
            for (int i = 0; i < list.size() - 1; i++) {
                if ((Float.valueOf(list.get(i + 1).getStime().replace(":", "."))) - (Float.valueOf(list.get(i).getEtime().replace(":", "."))) >= 4) {
                    Log.e("1", i + "");
                    list1.add(new Studio(list.get(i).getEtime(), list.get(i + 1).getStime()));
                }
            }
            if (24.00 - (Float.valueOf(list.get(list.size() - 1).getEtime().replace(":", "."))) >= 4) {
                Log.e("2", "0");
                list1.add(new Studio(list.get(list.size() - 1).getEtime(), "24:00"));
            }
        }

        return list1;
    }

    public void changeList(Studio oldT, Studio newT) {
        for (int i = 0; i < sugList.size(); i++) {
            Log.e("time", sugList.get(i).toString());
        }
        ArrayList<Time> list = new ArrayList<>();
        Studio new1 = new Studio(oldT.getStime(), newT.getStime());
        Studio new2 = new Studio(newT.getEtime(), oldT.getEtime());
        sugList.remove(oldT);
        if ((Float.valueOf(new1.getEtime().toString().trim().replace(":", ".")) - Float.valueOf(new1.getStime().toString().trim().replace(":", ".")) >= 4)) {
            sugList.add(new1);
        }
        if ((Float.valueOf(new2.getEtime().toString().trim().replace(":", ".")) - Float.valueOf(new2.getStime().toString().trim().replace(":", ".")) >= 4)) {
            sugList.add(new2);
        }
        for (int i = 0; i < sugList.size(); i++) {
            Log.e("time", sugList.get(i).toString());
        }
        Collections.sort(sugList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        adapter.notifyDataSetChanged();
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
            if ((input.get(Calendar.YEAR) == Integer.valueOf(timeList.get(i).getYear()) && ((input.get(Calendar.MONTH) + 1) == Integer.valueOf(timeList.get(i).getMonth())))) {
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
        ArrayList<Studio> mList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if ((daySelectedCalendar.get(Calendar.YEAR) == Integer.valueOf(list.get(i).getYear()) && ((daySelectedCalendar.get(Calendar.MONTH) + 1) == Integer.valueOf(list.get(i).getMonth()))) && ((daySelectedCalendar.get(Calendar.DAY_OF_MONTH)) == Integer.valueOf(list.get(i).getDay()))) {
                Log.e("date", list.get(i).toString());
                mList.add(list.get(i));
            }
        }
        if (mList.size() > 0) {
        } else {
            mList.add(new Studio("00:00", "24:00"));
        }
        Collections.sort(mList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        sugList = sugList(mList);
        Collections.sort(sugList, new Comparator<Studio>() {
            @Override
            public int compare(Studio studio, Studio t1) {
                float st = Float.valueOf(studio.getStime().toString().trim().replace(":", "."));
                float et = Float.valueOf(t1.getStime().toString().trim().replace(":", "."));
                //Log.e("" + st, "" + et);
                return ((int) st) - ((int) et);
            }
        });
        if (sugList.size() > 0) {
            dateBtn.setText(getDate(daySelectedCalendar.getTimeInMillis() + ""));
            adapter = new TimeSloatAdapter(this, sugList);
            timeSloatList.setAdapter(adapter);
            dialog.dismiss();
            timeSloatList.setVisibility(View.VISIBLE);
        } else {
            timeSloatList.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, "No time Available.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate(String time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.valueOf(time));
        String date = DateFormat.format("MMM dd, yyyy", cal).toString();
        return date;
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
    }

    @Override
    public void onLeftButtonClick() {
    }

    @Override
    public void onDateChange(Calendar calendar) {
        for (int i = 0; i < list.size(); i++) {
            setDateView(list.get(i), calendar);
        }
    }


}
