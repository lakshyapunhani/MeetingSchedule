package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.meetingscheduler.Utils.Repository;
import com.example.meetingscheduler.Utils.RequestCallBack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_meetings)
    RecyclerView recycler_meetings;

    @BindView(R.id.txt_selected_day)
    AppCompatTextView txt_selected_day;

    ArrayList<Meeting> meetings;
    MeetingsAdapter meetingsAdapter;

    String selectedDate = "";

    SimpleDateFormat dateFormat;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        meetings = new ArrayList<>();

        calendar = Calendar.getInstance();

        dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        selectedDate = dateFormat.format(calendar.getTime());
        txt_selected_day.setText(selectedDate);

        meetingsAdapter = new MeetingsAdapter(meetings);
        recycler_meetings.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(1,
                        StaggeredGridLayoutManager.VERTICAL);
        recycler_meetings.setLayoutManager(mLayoutManager);
        recycler_meetings.setItemAnimator(new DefaultItemAnimator());
        recycler_meetings.setNestedScrollingEnabled(false);
        recycler_meetings.setAdapter(meetingsAdapter);
        fetchMeetings();
    }

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
                        meetingsAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onFailure(int failureCode)
                    {
                        meetings.clear();
                        meetingsAdapter.notifyDataSetChanged();
                    }
                });
    }

    @OnClick(R.id.btn_add_new_meeting)
    public void addMeeting()
    {
        Intent intent = new Intent(MainActivity.this,
                AddMeetingActivity.class);
        intent.putExtra("selectedDate",selectedDate);
        startActivity(intent);
    }

    @OnClick(R.id.layout_prev_day)
    public void onClickPrevDay()
    {
        calendar.add(Calendar.DATE, -1);
        selectedDate = dateFormat.format(calendar.getTime());
        txt_selected_day.setText(selectedDate);
        fetchMeetings();

    }

    @OnClick(R.id.layout_next_day)
    public void onClickNextDay()
    {
        calendar.add(Calendar.DATE, 1);
        selectedDate = dateFormat.format(calendar.getTime());
        txt_selected_day.setText(selectedDate);
        fetchMeetings();
    }
}
