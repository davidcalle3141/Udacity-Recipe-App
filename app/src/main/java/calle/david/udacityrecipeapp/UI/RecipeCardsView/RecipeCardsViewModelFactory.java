package calle.david.udacityrecipeapp.UI.RecipeCardsView;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import calle.david.udacityrecipeapp.Data.RecipeAppRepo;

public class RecipeCardsViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final RecipeAppRepo mRepo;
    public RecipeCardsViewModelFactory(RecipeAppRepo repo){
        this.mRepo = repo;
    }

    @NonNull
    @SuppressWarnings("Unchecked")
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new RecipeCardsViewVM(mRepo);
    }
}
