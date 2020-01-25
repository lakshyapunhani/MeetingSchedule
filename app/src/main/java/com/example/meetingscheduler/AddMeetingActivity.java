package com.example.meetingscheduler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        ButterKnife.bind(this);

        meetings = new ArrayList<>();
        meetings = getIntent().getParcelableArrayListExtra("meeting");
    }

    @OnClick(R.id.btn_submit)
    public void submit()
    {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
    }
}
