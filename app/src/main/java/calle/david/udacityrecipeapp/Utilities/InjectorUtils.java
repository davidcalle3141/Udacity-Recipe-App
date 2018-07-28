package calle.david.udacityrecipeapp.Utilities;

import android.content.Context;

import calle.david.udacityrecipeapp.AppExecutors;
import calle.david.udacityrecipeapp.Data.Database.AppDatabase;
import calle.david.udacityrecipeapp.Data.Network.RecipeNetworkDataSource;
import calle.david.udacityrecipeapp.Data.RecipeAppRepo;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;

public class InjectorUtils {

    private static RecipeAppRepo provideRepo(Context context){
        AppDatabase database = AppDatabase.getsInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        RecipeNetworkDataSource networkDataSource =
                RecipeNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
        return RecipeAppRepo.getsInstance(database.recipesDao(),database.ingredientsDao(),database.stepsDao(),database.recentVisitsDao(),networkDataSource,executors);
    }

    public static RecipeNetworkDataSource provideNetworkDataSource(Context context){
        provideRepo(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return RecipeNetworkDataSource.getsInstance(context.getApplicationContext(), executors);
    }

    public  static RecipeAppViewModelFactory provideRecipeCardViewFactory(Context context){
        RecipeAppRepo repo = provideRepo(context.getApplicationContext());
        return new RecipeAppViewModelFactory(repo);
    }
}
