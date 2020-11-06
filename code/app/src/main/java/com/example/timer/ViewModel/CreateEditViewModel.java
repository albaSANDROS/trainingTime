package com.example.timer.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.timer.Models.Stage;

import java.util.ArrayList;

public class CreateEditViewModel extends ViewModel {
    private MutableLiveData<ArrayList<Stage>> stageMutableLiveData = new MutableLiveData<ArrayList<Stage>>();

    public MutableLiveData<ArrayList<Stage>> getStageMutableLiveData() {
        return stageMutableLiveData;
    }

    public void setStageMutableLiveData(ArrayList<Stage> stages) {
        stageMutableLiveData.setValue(stages);
    }
}
