package calle.david.udacityrecipeapp.UI.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(getApplicationContext());
    }
    public static Intent getIntent(Context context){
        return new Intent(context, WidgetService.class);

    }
}
