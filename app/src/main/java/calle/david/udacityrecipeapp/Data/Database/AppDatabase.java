package calle.david.udacityrecipeapp.Data.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {Recipe.class, Ingredients.class, Steps.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final  Object LOCK = new Object();
    private static final String DATABASE_NAME = "RecipeList";
    private static  AppDatabase sInstance;

    public static AppDatabase getsInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                Log.d(LOG_TAG, "creating new DB instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }Log.d(LOG_TAG, "getting DB instance");
        return sInstance;

    }

    public abstract RecipeDao recipesDao();
    public abstract IngredientsDao ingredientsDao();
    public abstract StepsDao stepsDao();

}
