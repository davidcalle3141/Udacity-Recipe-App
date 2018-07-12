package calle.david.udacityrecipeapp.Data;


import android.arch.lifecycle.LiveData;

import java.util.List;

import calle.david.udacityrecipeapp.AppExecutors;
import calle.david.udacityrecipeapp.Data.Database.IngredientsDao;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.RecipeDao;
import calle.david.udacityrecipeapp.Data.Database.StepsDao;
import calle.david.udacityrecipeapp.Data.Network.RecipeNetworkDataSource;
import calle.david.udacityrecipeapp.Data.Network.RecipeResponse;

public class RecipeAppRepo {
    private static  final Object LOCK = new Object();
    private static RecipeAppRepo sInstance;
    private final RecipeNetworkDataSource mRecipeNetworkDataSource;
    private final RecipeDao mRecipeDao;
    private final StepsDao mStepsDao;
    private final IngredientsDao mIngredientsDao;
    private final AppExecutors mExecutors;
    private boolean mInitialized = false;

    private RecipeAppRepo(RecipeDao recipeDao, IngredientsDao ingredientsDao, StepsDao stepsDao, RecipeNetworkDataSource recipeNetworkDataSource,
                          AppExecutors executors){
        this.mRecipeDao = recipeDao;
        this.mStepsDao = stepsDao;
        this.mIngredientsDao = ingredientsDao;
        this.mRecipeNetworkDataSource = recipeNetworkDataSource;
        this.mExecutors = executors;


            LiveData<List<Recipe>> networkData = mRecipeNetworkDataSource.getRecipes();
        networkData.observeForever(recipesFromNetwork -> {
            mExecutors.diskIO().execute(() ->{
                if (recipesFromNetwork != null ) {

                    deleteOldData();

                    mRecipeDao.bulkInsert(recipesFromNetwork);

                }
            });
        });
    }

    private void getDataFromNetwork() {

    }

    private void deleteOldData() {
        mRecipeDao.deleteRecipes();
        mIngredientsDao.deleteIngredients();
        mStepsDao.deleteSteps();
    }

    public synchronized static RecipeAppRepo getsInstance(RecipeDao recipeDao, IngredientsDao ingredientsDao, StepsDao stepsDao, RecipeNetworkDataSource recipeNetworkDataSource,
                                                          AppExecutors executors){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new RecipeAppRepo(recipeDao,ingredientsDao,stepsDao,recipeNetworkDataSource,executors);
            }
        }
        return sInstance;
    }

    private synchronized void initilizeData(){
        if(mInitialized)return;
        mInitialized = true;
        mExecutors.diskIO().execute(()->{
            if(mRecipeDao.getCount()==0) {
                mRecipeNetworkDataSource.fetchRecipes();
            }

        });

    }


    public LiveData<List<Recipe>> getRecipeList(){
        initilizeData();
        return mRecipeDao.loadAllMovies();
    }




}
