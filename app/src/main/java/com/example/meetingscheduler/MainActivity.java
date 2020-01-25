package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.example.meetingscheduler.Utils.Repository;
import com.example.meetingscheduler.Utils.RequestCallBack;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler_meetings)
    RecyclerView recycler_meetings;

    ArrayList<Meeting> meetings;
    MeetingsAdapter meetingsAdapter;

    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        meetings = new ArrayList<>();

        meetingsAdapter = new MeetingsAdapter(meetings);
        recycler_meetings.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recycler_meetings.setLayoutManager(mLayoutManager);
        recycler_meetings.setItemAnimator(new DefaultItemAnimator());
        recycler_meetings.setNestedScrollingEnabled(false);
        recycler_meetings.setAdapter(meetingsAdapter);
        fetchMeetings("24/01/2020");
    }

    private void fetchMeetings(String date)
    {
        Repository.getInstance().getMeetings(date
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
        intent.putParcelableArrayListExtra("meeting",meetings);
        startActivity(intent);
    }
}
