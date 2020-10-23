package com.example.timer.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Training {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    public String Name;
    public int PreparationTime;
    public int WorkTime;
    public int RestTime;
    public int Calm;
    public int Cycles;
    public int Sets;
    public int Color;
}
