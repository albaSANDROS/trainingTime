package Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import Models.Training;

@Dao
public interface TrainingDao {
    @Query("SELECT * FROM training")
    List<Training> getAll();

    @Query("SELECT * FROM training WHERE id = :id")
    Training getById(long id);

    @Insert
    void insert(Training training);

    @Update
    void update(Training training);

    @Delete
    void delete(Training training);
}
