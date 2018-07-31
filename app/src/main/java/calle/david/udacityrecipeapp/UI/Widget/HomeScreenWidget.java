package calle.david.udacityrecipeapp.UI.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class HomeScreenWidget extends AppWidgetProvider {


   public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int[] appWidgetIds) {
       for (int ID: appWidgetIds) {
           PendingIntent pendingIntent = PendingIntent.getActivity(context,0,
                   new Intent(context, MainActivity.class),0);
           RemoteViews remoteViews =
                   new RemoteViews(context.getPackageName(),R.layout.widget_homescreen_layout);
           remoteViews.setTextViewText(R.id.widget_ingredients_list_title,
                   WidgetUtils.getWidgetTitle(context));
           remoteViews.setRemoteAdapter(R.id.widget_ingredients_list_items,
                   WidgetService.getIntent(context));
           remoteViews.setPendingIntentTemplate(R.id.widget_ingredients_list_items,pendingIntent);
           remoteViews.setOnClickPendingIntent(R.id.widget_homescreen_layout, pendingIntent);
           appWidgetManager.updateAppWidget(ID,remoteViews);

       }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        updateAppWidget(context,appWidgetManager,appWidgetIds);
    }

}

