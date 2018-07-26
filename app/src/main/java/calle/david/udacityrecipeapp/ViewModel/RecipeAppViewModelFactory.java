package calle.david.udacityrecipeapp.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import calle.david.udacityrecipeapp.Data.RecipeAppRepo;

public class RecipeAppViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    private final RecipeAppRepo mRepo;
    public RecipeAppViewModelFactory(RecipeAppRepo repo){
        this.mRepo = repo;
    }

    @NonNull
    @SuppressWarnings({"Unchecked", "unchecked"})
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass){
        return (T) new RecipeAppViewModel(mRepo);
    }
}
