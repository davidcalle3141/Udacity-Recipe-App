package calle.david.udacityrecipeapp.Data.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class RecentVisit {
    @PrimaryKey
    private int id;
    private int recipeID;

    public RecentVisit(int id, int recipeID){
        this.id = id;
        this.recipeID = recipeID;
    }
    @Ignore
    public RecentVisit(int recipeID){
        this.recipeID = recipeID;
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
