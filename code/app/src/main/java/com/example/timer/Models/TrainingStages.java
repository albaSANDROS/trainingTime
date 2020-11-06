package com.example.timer.Models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TrainingStages {
    @Embedded
    public Training training;

    @Relation(parentColumn = "id", entityColumn = "trainingId", entity = Stage.class)
    public List<Stage> stages;
}
