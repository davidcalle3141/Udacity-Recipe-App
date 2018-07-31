package calle.david.udacityrecipeapp.UI.Widget;

import android.content.Context;
import android.preference.PreferenceManager;

import calle.david.udacityrecipeapp.R;

public class WidgetUtils {
    private WidgetUtils(){

    }

    public static void setWidgetAttributes(Context context, int recipeId, String title){
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putInt(context.getString(R.string.preference_id_key),recipeId)
                .apply();
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(context.getString(R.string.preference_title_key),title)
                .apply();

    }
    public static int getWidgetRecipeID(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(context.getString(R.string.preference_id_key),-1);
    }
    public static String getWidgetTitle(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(context.getString(R.string.preference_title_key),
                        context.getString(R.string.widget_default_text));
    }
}
