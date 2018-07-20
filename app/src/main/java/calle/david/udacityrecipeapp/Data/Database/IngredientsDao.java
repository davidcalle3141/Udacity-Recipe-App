package calle.david.udacityrecipeapp.Data.Database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.ABORT;

@Dao
public interface IngredientsDao {
    @Insert
    void bulkInsert(List<Ingredients> ingredients);

    @Query("SELECT * FROM Ingredients WHERE recipeID = :recipeID ORDER BY ingredientID")
    LiveData<List<Ingredients>>loadIngredients(int recipeID);

    @Query("SELECT * FROM Ingredients ORDER BY ingredientID")
    LiveData<List<Ingredients>> loadAllIngredients();

    @Query("DELETE FROM Ingredients")
    void deleteIngredients();
}
