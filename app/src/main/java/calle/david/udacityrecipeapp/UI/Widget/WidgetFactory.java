package calle.david.udacityrecipeapp.UI.Widget;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import calle.david.udacityrecipeapp.Data.Database.AppDatabase;
import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.R;

public class WidgetFactory implements WidgetService.RemoteViewsFactory {
    private final Context context;
    private final AppDatabase appDatabase;
    private List<String> ingredientList;

    WidgetFactory(Context context){
        this.context = context;
        appDatabase = Room.databaseBuilder(context,
                AppDatabase.class,
                "RecipeList").build();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        int recipeId = WidgetUtils.getWidgetRecipeID(context);
        if(recipeId != -1){
            ingredientList = new ArrayList<>();
            List<Ingredients> tempIngredients = appDatabase.ingredientsDao().loadIngredientsSnapshot(recipeId);
            for (Ingredients ingredient :tempIngredients
                 ) {
                ingredientList.add(
                        String.format(
                                Locale.getDefault(),
                                "%s %s %s",
                                ingredient.getQuantity(),
                                ingredient.getMeasure(),
                                ingredient.getIngredientName()));

            }

        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(ingredientList == null) return 0;
        return ingredientList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(position == AdapterView.INVALID_POSITION || ingredientList == null )
        return null;

        RemoteViews remoteViews =
                new RemoteViews(context.getPackageName(), R.layout.widget_ingredients_list);
        remoteViews.setTextViewText(R.id.widget_ingredients_items, ingredientList.get(position));

        Intent fillIntent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_ingredients_items, fillIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
