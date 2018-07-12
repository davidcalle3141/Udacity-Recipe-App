package calle.david.udacityrecipeapp.Data.Network;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.Steps;

public class RecipeResponse {
    private final List<Recipe> mRecipes;
    private final List<Steps> mSteps;
    private final List<Ingredients> mIngredients;
    RecipeResponse(){
        mRecipes = new ArrayList<>();
        mSteps = new ArrayList<>();
        mIngredients = new ArrayList<>();
    }

    RecipeResponse(@NonNull final List<Recipe> recipes,@NonNull final List<Steps> steps,@NonNull final List<Ingredients> ingredients){
        this.mRecipes = recipes;
        this.mIngredients = ingredients;
        this.mSteps = steps;

    }
    public void AddToRecipeResponse(@NonNull List<Recipe> recipes,@NonNull List<Steps> steps,@NonNull List<Ingredients> ingredients){
        this.mRecipes.addAll(recipes);
        this.mSteps.addAll(steps);
        this.mIngredients.addAll(ingredients);
    }



    public void UpdateImage(int position, String image){
        mRecipes.get(position).setImage(image);
    }




    public List<Recipe> getmRecipes() {
        return mRecipes;
    }

    public List<Ingredients> getmIngredients() {
        return mIngredients;
    }

    public List<Steps> getmSteps() {
        return mSteps;
    }
}
