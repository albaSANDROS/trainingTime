package com.example.timer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.timer.Models.Stage;
import com.example.timer.R;

import java.util.ArrayList;

public class PartsAdapter extends ArrayAdapter<Stage> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Stage> stages;

    public PartsAdapter(Context context, int resource, ArrayList<Stage> stages) {
        super(context, resource, stages);
        this.stages = stages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        PartsAdapter.ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new PartsAdapter.ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (PartsAdapter.ViewHolder) convertView.getTag();
        }
        Stage stage = stages.get(position);
        viewHolder.textNamePart.setText(stage.stageName);

        viewHolder.inputTime.setText(Integer.toString(stage.stageTime));

        viewHolder.btnPlus.setOnClickListener(i -> {
            stage.stageTime++;
            stages.set(position, stage);
            notifyDataSetChanged();
        });

        viewHolder.btnMinus.setOnClickListener(i -> {
            if(stage.stageTime != 1) {
                stage.stageTime--;
                stages.set(position, stage);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        Button btnPlus, btnMinus;
        TextView textNamePart;
        EditText inputTime;

        ViewHolder(View view) {
            btnPlus = view.findViewById(R.id.btnPlus);
            btnMinus = view.findViewById(R.id.btnMinus);
            textNamePart = view.findViewById(R.id.textNamePart);
            inputTime = view.findViewById(R.id.inputTime);
        }
    }

}
