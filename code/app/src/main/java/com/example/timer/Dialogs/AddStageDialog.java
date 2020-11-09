package com.example.timer.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.preference.PreferenceManager;

import com.example.timer.CreateActivity;
import com.example.timer.R;

public class AddStageDialog extends AppCompatDialogFragment {
    Spinner spinner;
    EditText time;
    String namePart;
    SharedPreferences sp;
    Button btnDialogOk;
    Button btnDialogCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_stage_dialog, null);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String size = sp.getString("font", "");

        time = view.findViewById(R.id.inputPartTime);
        spinner = view.findViewById(R.id.spnNameStage);
        btnDialogOk = view.findViewById(R.id.btnDialogOk);
        btnDialogCancel = view.findViewById(R.id.btnDialogCancel);
        time.setTextSize(Float.parseFloat(size));
        btnDialogOk.setTextSize(Float.parseFloat(size));
        btnDialogCancel.setTextSize(Float.parseFloat(size));

        ArrayAdapter adapter = ArrayAdapter.createFromResource(getContext(), R.array.stages,
                 R.layout.spinner_item);
        adapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent,
                                       View itemSelected, int selectedItemPosition, long selectedId) {
                String[] choose = getResources().getStringArray(R.array.stages);
                namePart = choose[selectedItemPosition];
                if(namePart.equals(getResources().getStringArray(R.array.stages)[3])){
                    time.setHint(getResources().getString(R.string.hintSetsCount));
                }
                else {
                    time.setHint(getResources().getString(R.string.hintStageTime));
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
            else {
                Toast toast = Toast.makeText(getContext(),
                        getResources().getString(R.string.toastInputTimeStage), Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        builder.setView(view);
        return builder.create();
    }

}
