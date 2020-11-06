package com.example.timer.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import com.example.timer.Models.Stage;
import com.example.timer.Models.Training;
import com.example.timer.Models.TrainingStages;

@Dao
public abstract class TrainingDao {

    @Query("SELECT * FROM training")
    public abstract List<Training> getAllTrainings();

    @Transaction
    @Query("SELECT * FROM training WHERE id LIKE :id")
    public abstract List<TrainingStages> getTrainingStagesByTrainingId(long id);

    @Transaction
    public void insert(Training training, List<Stage> stages) {
        long companyId = insert(training);
        for (Stage stage : stages) {
            stage.trainingId = companyId;
            insert(stage);
        }
    }


    @Query("SELECT * FROM training WHERE id = :id")
    public abstract Training getById(long id);

    @Insert
    public abstract long insert(Training training);

    @Insert
    public abstract void insert(Stage stage);

    @Update
    public abstract void update(Training training);

    @Update
    public abstract void update(Stage stage);

    @Delete
    public abstract void delete(Training training);
}
