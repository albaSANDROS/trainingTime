package com.example.timer.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.preference.PreferenceManager;

import com.example.timer.CreateActivity;
import com.example.timer.R;

public class DeleteStageDialog extends AppCompatDialogFragment {
    TextView textView;
    SharedPreferences sp;
    Button btnDialogOk;
    Button btnDialogCancel;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.delete_stage_dialog, null);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String size = sp.getString("font", "");
        if(size.equals("") || size.equals("false")) size = "17";
        textView = view.findViewById(R.id.textViewDelete);
        btnDialogOk = view.findViewById(R.id.btnDialogOk);
        btnDialogCancel = view.findViewById(R.id.btnDialogCancel);

        btnDialogOk.setTextSize(Float.parseFloat(size));
        btnDialogCancel.setTextSize(Float.parseFloat(size));
        textView.setTextSize(Float.parseFloat(size));

        Bundle args = getArguments();
        int index = 0;
        if (args != null) {
            index = args.getInt("index");
        }

        view.findViewById(R.id.btnDialogCancel).setOnClickListener(i -> {
            dismiss();
        });

        int finalIndex = index;
        view.findViewById(R.id.btnDialogOk).setOnClickListener(i -> {
            ((CreateActivity) getActivity()).deleteStage(finalIndex);
            dismiss();
        });

        builder.setView(view);
        return builder.create();
    }
}
