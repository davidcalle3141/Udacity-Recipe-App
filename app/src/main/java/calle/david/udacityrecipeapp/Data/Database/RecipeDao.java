package calle.david.udacityrecipeapp.Data.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface RecipeDao {
    @Insert(onConflict = REPLACE)
    void save(Recipe recipe);
    @Insert
    void bulkInsert(List<Recipe> recipes);

    @Query("SELECT count(*) FROM Recipe")
    int getCount();

    @Query("SELECT * FROM Recipe ORDER BY id")
    LiveData<List<Recipe>> loadAllMovies();

    @Query("DELETE FROM Recipe")
    void deleteRecipes();

}
