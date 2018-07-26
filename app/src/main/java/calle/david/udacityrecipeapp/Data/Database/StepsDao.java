package calle.david.udacityrecipeapp.Data.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;

@Dao
public interface StepsDao {
    @Insert()
    void bulkInsert(List<Steps > steps);

    @Query("SELECT * FROM Steps WHERE recipeID = :recipeID ORDER BY id")
    LiveData<List<Steps>> loadSteps(int recipeID);

    @Query("DELETE FROM steps")
    void deleteSteps();
}
