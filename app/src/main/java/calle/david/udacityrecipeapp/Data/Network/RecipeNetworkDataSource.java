package calle.david.udacityrecipeapp.Data.Network;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;


import java.util.List;
import java.util.Objects;

import calle.david.udacityrecipeapp.AppExecutors;
import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.Steps;

public class RecipeNetworkDataSource {
    private  static final Object LOCK = new Object();
    private static RecipeNetworkDataSource sInstance;
    private final Context mContext;

    private final MutableLiveData<RecipeResponse> mDownloadedRecipes;



    private final AppExecutors mExecutors;

    private RecipeNetworkDataSource(Context context, AppExecutors executors){
        this.mContext = context;
        this.mExecutors = executors;
        mDownloadedRecipes = new MutableLiveData<>();


    }

    public static RecipeNetworkDataSource getsInstance(Context context, AppExecutors executors){
        if(sInstance == null){
            synchronized (LOCK){
                sInstance = new RecipeNetworkDataSource(context.getApplicationContext(),executors);

            }
        }
        return  sInstance;
    }

    public LiveData<RecipeResponse> getRecipes(){
        return mDownloadedRecipes;
    }



    public void fetchRecipes(){
        mExecutors.networkIO().execute(()->{
            try {
                RecipeResponse recipeResponse;
                String RecipeJsonData;
                RecipeJsonData = NetworkUtils.getStringJSONRecipeNetworkResponse();
                    recipeResponse = new RecipeJsonUtils().parseRecipeJson(RecipeJsonData, mExecutors);
                mDownloadedRecipes.postValue(recipeResponse);

            }catch (Exception e){
                e.printStackTrace();
            }


        });
    }

}



