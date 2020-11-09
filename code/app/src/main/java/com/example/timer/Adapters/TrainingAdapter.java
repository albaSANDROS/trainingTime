package com.example.timer.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import com.example.timer.EditActivity;
import com.example.timer.MainActivity;
import com.example.timer.Models.Training;
import com.example.timer.R;
import com.example.timer.WorkActivity;

public class TrainingAdapter extends ArrayAdapter<Training> {
    private LayoutInflater inflater;
    private int layout;
    private List<Training> trainingList;
    String size;

    public TrainingAdapter(Context context, int resource, List<Training> trainings, String size) {
        super(context, resource, trainings);
        this.trainingList = trainings;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.size = size;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Training training = trainingList.get(position);
        viewHolder.nameView.setText(training.name);

        viewHolder.nameView.setTextSize(Float.parseFloat(size));

        viewHolder.idView.setText((Long.toString(training.id)));
        viewHolder.layout.setBackgroundColor(training.Color);

        viewHolder.startButton.setOnClickListener(i -> {
            Context context = getContext();
            Intent intent = new Intent(context, WorkActivity.class);
            intent.putExtra("trainingId", training.id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        viewHolder.removeButton.setOnClickListener(i -> {
            ((MainActivity) getContext()).deleteTraining(trainingList.get(position));
            trainingList.remove(training);
            notifyDataSetChanged();
        });

        viewHolder.editButton.setOnClickListener(i -> {
            Context context = getContext();
            Intent intent = new Intent(context, EditActivity.class);
            intent.putExtra("trainingId", training.id);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        return convertView;
    }

    private class ViewHolder {
        Button removeButton, editButton, startButton;
        TextView nameView, idView;
        LinearLayout layout;

        ViewHolder(View view) {
            removeButton = view.findViewById(R.id.removeButton);
            editButton = view.findViewById(R.id.editButton);
            startButton = view.findViewById(R.id.startButton);
            nameView = view.findViewById(R.id.nameView);
            idView = view.findViewById(R.id.idView);
            layout = view.findViewById(R.id.layout);
        }
    }
}
