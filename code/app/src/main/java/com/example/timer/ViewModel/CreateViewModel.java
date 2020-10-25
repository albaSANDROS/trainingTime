package com.example.timer.ViewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateViewModel extends ViewModel {
    private MutableLiveData<String> Name = new MutableLiveData<>("T");
    private MutableLiveData<Integer> PreparationTime = new MutableLiveData<>(10);
    private MutableLiveData<Integer> WorkTime = new MutableLiveData<>(20);
    private MutableLiveData<Integer> RestTime = new MutableLiveData<>(10);
    private MutableLiveData<Integer> Calm = new MutableLiveData<>(10);
    private MutableLiveData<Integer> Cycles = new MutableLiveData<>(2);
    private MutableLiveData<Integer> Sets = new MutableLiveData<>(1);


    public MutableLiveData<String> getName() {
        return Name;
    }


    public void setPreparationTime(MutableLiveData<Integer> preparationTime) {
        PreparationTime = preparationTime;
    }

    public void setWorkTime(MutableLiveData<Integer> workTime) {
        WorkTime = workTime;
    }

    public void setRestTime(MutableLiveData<Integer> restTime) {
        RestTime = restTime;
    }

    public void setCalm(MutableLiveData<Integer> calm) {
        Calm = calm;
    }

    public void setCycles(MutableLiveData<Integer> cycles) {
        Cycles = cycles;
    }

    public void setName(String name) {
        Name.setValue(name);
    }

    public MutableLiveData<Integer> getPreparationTime() {
        return PreparationTime;
    }

    public void setIncrementPreparationTime() {
        PreparationTime.setValue(PreparationTime.getValue() + 1);
    }

    public void setDecrementPreparationTime() {
        if(PreparationTime.getValue() != 0) {
            PreparationTime.setValue(PreparationTime.getValue() - 1);
        }
    }

    public void setPrep(int prep) {
        PreparationTime.setValue(prep);
    }

    public MutableLiveData<Integer> getWorkTime() {
        return WorkTime;
    }

    public void setIncrementWorkTime( ) {
        WorkTime.setValue(WorkTime.getValue() + 1);
    }

    public void setDecrementWorkTime() {
        if(WorkTime.getValue() != 0) {
            WorkTime.setValue(WorkTime.getValue() - 1);
        }
    }

    public void setWork(int work) {
        WorkTime.setValue(work);
    }

    public MutableLiveData<Integer> getRestTime() {
        return RestTime;
    }

    public void setIncrementRestTime() {
        RestTime.setValue(RestTime.getValue() + 1);
    }

    public void setDecrementRestTime() {
        if(RestTime.getValue() != 0) {
            RestTime.setValue(RestTime.getValue() - 1);
        }
    }

    public void setRest(int rest) {
        RestTime.setValue(rest);
    }

    public MutableLiveData<Integer> getCalm() {
        return Calm;
    }

    public void setIncrementCalm() {
        Calm.setValue(Calm.getValue() + 1);
    }

    public void setDecrementCalm() {
        if(Calm.getValue() != 0) {
            Calm.setValue(Calm.getValue() - 1);
        }
    }

    public void setCalm(int calm) {
        Calm.setValue(calm);
    }

    public MutableLiveData<Integer> getCycles() {
        return Cycles;
    }

    public void setIncrementCycle() {
        Cycles.setValue(Cycles.getValue() + 1);
    }

    public void setDecrementCycle() {
        if(Cycles.getValue() != 0) {
            Cycles.setValue(Cycles.getValue() - 1);
        }
    }

    public void setCycle(int cycle) {
        Cycles.setValue(cycle);
    }

    public MutableLiveData<Integer> getSets() {
        return Sets;
    }

    public void setSets(int sets) {
        Sets.setValue(sets);
    }

    public void setIncrementSets() {
        Sets.setValue(Sets.getValue() + 1);
    }

    public void setDecrementSets() {
        if(Sets.getValue() != 1) {
            Sets.setValue(Sets.getValue() - 1);
        }
    }
}
