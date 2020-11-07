package com.example.timer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.timer.R;
import com.example.timer.WorkActivity;

import java.util.ArrayList;

public class TimerPageAdapter extends ArrayAdapter<String> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<String> stages;

    public TimerPageAdapter(Context context, int resource, ArrayList<String> stages) {
        super(context, resource, stages);
        this.stages = stages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        TimerPageAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new TimerPageAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (TimerPageAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.textNamePart.setText(stages.get(position));
        return convertView;
    }

    class ViewHolder {
        public TextView textNamePart;
        public LinearLayout layout;

        ViewHolder(View view) {
            textNamePart = view.findViewById(R.id.textNamePart);
            layout = view.findViewById(R.id.item_container);
        }
    }
}
