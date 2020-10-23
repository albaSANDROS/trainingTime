package com.example.timer.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.timer.Models.Training;

@Database(entities = {Training.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TrainingDao trainingDao();
}
