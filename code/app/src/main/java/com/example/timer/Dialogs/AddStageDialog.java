package com.example.timer.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.timer.CreateActivity;
import com.example.timer.R;

public class AddStageDialog extends AppCompatDialogFragment {
    Spinner spinner;
    EditText time;
    String namePart;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_stage_dialog, null);

        time = view.findViewById(R.id.inputPartTime);
        spinner = view.findViewById(R.id.spnNameStage);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.stages, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.stages);
                namePart = choose[selectedItemPosition];
                if(namePart.equals("Сеты")){
                    time.setHint("Кол-во сетов");
                }
                else {
                    time.setHint("Время этапа");
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        view.findViewById(R.id.btnDialogCancel).setOnClickListener(i -> {
            dismiss();
        });

        view.findViewById(R.id.btnDialogOk).setOnClickListener(i -> {
            if(!time.getText().toString().equals("")){
                ((CreateActivity)getActivity()).setDataFromDialog(namePart, time.getText().toString());
                dismiss();
            }
        });

        builder.setView(view);
        return builder.create();
    }
}
