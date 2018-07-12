package calle.david.udacityrecipeapp.UI.RecipeCardsView;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;


import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.Steps;
import calle.david.udacityrecipeapp.Data.RecipeAppRepo;

public class RecipeCardsViewVM extends ViewModel {
    private final RecipeAppRepo mRepo;
    private final LiveData<List<Recipe>> mRecipeList;
    //private final LiveData<Steps> mRecipeSteps;
    //private final LiveData<Ingredients> mRecipeIngredients;

    RecipeCardsViewVM(RecipeAppRepo repo){
        this.mRepo = repo;
        this.mRecipeList = mRepo.getRecipeList();
    }

    public LiveData<List<Recipe>> getmRecipeList() {
        return mRecipeList;
    }
}
