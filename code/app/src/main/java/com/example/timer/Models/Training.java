package com.example.timer.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Training {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public int Color;
}
