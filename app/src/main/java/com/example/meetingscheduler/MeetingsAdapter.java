package com.example.meetingscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MeetingsAdapter extends
        RecyclerView.Adapter<MeetingsAdapter.ListViewHolder>
{

    private Context context;
    private ArrayList<Meeting> meetings;

    public MeetingsAdapter(ArrayList<Meeting> meetings) {
        this.meetings = meetings;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_meeting, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Meeting meeting = meetings.get(position);
        holder.txt_meeting_start_time.setText(meeting.getStart_time());
        holder.txt_meeting_end_time.setText(meeting.getEnd_time());
        holder.meeting_description.setText(meeting.getDescription());
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.txt_meeting_start_time)
        public AppCompatTextView txt_meeting_start_time;

        @BindView(R.id.txt_meeting_end_time)
        public AppCompatTextView txt_meeting_end_time;

        @BindView(R.id.txt_meeting_description)
        public AppCompatTextView meeting_description;

        ListViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
