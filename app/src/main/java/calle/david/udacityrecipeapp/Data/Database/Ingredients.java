package calle.david.udacityrecipeapp.Data.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Ingredients {
    @PrimaryKey(autoGenerate = true)
    private int ingredientID;
    private int recipeID;
    private String quantity;
    private String measure;
    private String ingredientName;

    public Ingredients(int ingredientID, int recipeID, String quantity, String measure, String ingredientName){
        this.ingredientID = ingredientID;
        this.recipeID = recipeID;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
        this.measure = measure;
    }
    @Ignore
    public Ingredients(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getIngredientID() {
        return ingredientID;
    }

    public void setIngredientID(int ingredientID) {
        this.ingredientID = ingredientID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }
}
