package calle.david.udacityrecipeapp.Utilities;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";
    private static CustomIdlingResource mCustomIdlingResource =
            new CustomIdlingResource(RESOURCE);

    public static void Lock(){
        mCustomIdlingResource.Lock();
    }
    public static void Unlock(){
        mCustomIdlingResource.Unlock();
    }
    public static IdlingResource getIdlingResource(){
        return mCustomIdlingResource;
    }
}
