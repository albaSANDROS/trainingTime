package Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Training {
    @PrimaryKey(autoGenerate = true)
    public int Id;

    public String Name;
}
