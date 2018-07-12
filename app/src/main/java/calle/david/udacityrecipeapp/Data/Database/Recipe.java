package calle.david.udacityrecipeapp.Data.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

@Entity
public class Recipe {
    @PrimaryKey
    private int id;
    private int servings;
    private String name;
    private String image;

    @Ignore
    private List<Ingredients> ingredients = null;
    @Ignore
    private List<Steps> steps = null;
    @Ignore
    private boolean hasUpdatedIngredients=false;
    @Ignore
    private boolean hasUpdatedSteps = false;

    public Recipe(int id, String name){
        this.id = id;
        this.name = name;
    }
    @Ignore
    public Recipe() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredients> getIngredients() {
        if(!hasUpdatedIngredients){
        for (Ingredients ingredient: this.ingredients
                ) {
            ingredient.setRecipeID(id);
        }
        hasUpdatedIngredients = true;
            }
        return this.ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {

        this.ingredients = ingredients;


    }

    public List<Steps> getSteps() {
        if(!hasUpdatedSteps){
        for (Steps step: this.steps
                ) {
            step.setRecipeID(id);
        }
            hasUpdatedSteps=true;
            }
        return this.steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
        }
}
