package calle.david.udacityrecipeapp.Utilities;

import android.support.test.espresso.IdlingResource;

public class EspressoIdlingResource {
    private static final String RESOURCE = "GLOBAL";
    private static CustomIdlingResource mCustomIdlingResource =
            new CustomIdlingResource(RESOURCE);

    public static void increment(){
        mCustomIdlingResource.increment();
    }
    public static void decrement(){
        mCustomIdlingResource.decrement();
    }
    public static IdlingResource getIdlingResource(){
        return mCustomIdlingResource;
    }
}
