package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.meetingscheduler.Utils.Repository;
import com.example.meetingscheduler.Utils.RequestCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeetingActivity extends AppCompatActivity {

    @BindView(R.id.txt_date)
    AppCompatTextView txt_date;

    @BindView(R.id.txt_start_time)
    AppCompatTextView txt_start_time;

    @BindView(R.id.txt_end_time)
    AppCompatTextView txt_end_time;

    ArrayList<Meeting> meetings;

    SimpleDateFormat timeFormat;

    Date startTime,endTime;

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);

        meetings = new ArrayList<>();
        selectedDate = getIntent().getStringExtra("selectedDate");

        timeFormat = new SimpleDateFormat("HH:mm");

        txt_date.setText(selectedDate);

        fetchMeetings();
    }

    @OnClick(R.id.txt_date)
    public void openDatePicker()
    {
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new  DatePickerDialog(this,
                R.style.DatePickerTheme, mDateListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH)
                ,myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.btn_submit)
    public void submit()
    {
        for (int i =0;i<meetings.size();i++)
        {
            Meeting meeting = meetings.get(i);
            try {
                Date meetingStartTime = timeFormat.parse(meeting.getStart_time());
                Date meetingEndTime= timeFormat.parse(meeting.getEnd_time());

                if (startTime.after(meetingStartTime) &&
                        startTime.before(meetingEndTime))
                {
                    Toast.makeText(this, "Slot not available",
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                if (endTime.after(meetingStartTime) &&
                        endTime.before(meetingEndTime))
                {
                    Toast.makeText(this, "Slot not available",
                            Toast.LENGTH_SHORT).show();
                    break;
                }

                if (i != (meetings.size() -1))
                {
                    continue;
                }
                Toast.makeText(this, "Slot available",
                        Toast.LENGTH_SHORT).show();

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    final DatePickerDialog.OnDateSetListener mDateListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                                      int selectedDay) {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date reportDate = df.parse(selectedDay + "/" + (selectedMonth+1) +
                                "/" + selectedYear);
                        txt_date.setText(df.format(reportDate));
                        fetchMeetings();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };

    @OnClick(R.id.txt_start_time)
    public void onClickStartTime()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                start_time_listener, hour, minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener start_time_listener = (view, hourOfDay, minute) -> {

        try {
            startTime = timeFormat.parse(hourOfDay + ":" + minute);
            txt_start_time.setText(timeFormat.format(startTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    };

    @OnClick(R.id.txt_end_time)
    public void onClickEndTime()
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                end_time_listener, hour, minute,
                DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    TimePickerDialog.OnTimeSetListener end_time_listener = (view, hourOfDay, minute) -> {

        try {
            endTime = timeFormat.parse(hourOfDay + ":" + minute);
            txt_end_time.setText(timeFormat.format(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    };

    private void fetchMeetings()
    {
        Repository.getInstance().getMeetings(selectedDate
                , new RequestCallBack<List<Meeting>>()
                {
                    @Override
                    public void onSuccess(List<Meeting> response)
                    {
                        meetings.clear();
                        meetings.addAll(response);
                    }
                    @Override
                    public void onFailure(int failureCode)
                    {
                        meetings.clear();
                    }
                });
    }

    @OnClick(R.id.layout_back)
    public void onClickBack()
    {
        super.onBackPressed();
    }


}
