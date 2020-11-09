package com.example.timer.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import com.example.timer.Dialogs.AddStageDialog;
import com.example.timer.CreateActivity;
import com.example.timer.Dialogs.DeleteStageDialog;
import com.example.timer.Models.Stage;
import com.example.timer.R;

import java.util.ArrayList;

public class PartsAdapter extends ArrayAdapter<Stage> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Stage> stages;
    String size;
    String baseActivity;

    public PartsAdapter(Context context, int resource, ArrayList<Stage> stages, String size
            , String baseActivity) {
        super(context, resource, stages);
        this.stages = stages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.size = size;
        this.baseActivity = baseActivity;
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

        viewHolder.inputTime.setTextSize(Float.parseFloat(size));
        viewHolder.textNamePart.setTextSize(Float.parseFloat(size));
        viewHolder.btnPlus.setTextSize(Float.parseFloat(size));
        viewHolder.btnMinus.setTextSize(Float.parseFloat(size));

        viewHolder.inputTime.setOnClickListener(i -> {
            if (baseActivity.equals("CreateActivity")) {
                CreateActivity activity = (CreateActivity) getContext();
                DeleteStageDialog dialogFragment = new DeleteStageDialog();
                FragmentManager manager = activity.getSupportFragmentManager();

                Bundle bundle = new Bundle();
                bundle.putInt("index", position);
                dialogFragment.setArguments(bundle);

                FragmentTransaction transaction = manager.beginTransaction();
                dialogFragment.show(transaction, "dialog");
            }
        });

        viewHolder.btnPlus.setOnClickListener(i -> {
            stage.stageTime++;
            stages.set(position, stage);
            notifyDataSetChanged();
        });

        viewHolder.btnMinus.setOnClickListener(i -> {
            if (stage.stageTime != 1) {
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
