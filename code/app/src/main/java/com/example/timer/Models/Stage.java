package com.example.timer.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(
                entity = Training.class,
                parentColumns = "id",
                childColumns = "trainingId",
                onDelete = CASCADE))
public class Stage {
    @PrimaryKey(autoGenerate = true)
    public long stId;

    public long trainingId;
    public String stageName;
    public int stageTime;
}
