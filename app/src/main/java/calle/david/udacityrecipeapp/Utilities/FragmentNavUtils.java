package calle.david.udacityrecipeapp.Utilities;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;

import calle.david.udacityrecipeapp.UI.Fragments.RecipeCardsViewFragment;

public class FragmentNavUtils {

    public static void startActivityFragment(FragmentManager fragmentManager, Fragment entryFragment, int container){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container,entryFragment,"ENTRY_FRAGMENT");
        fragmentTransaction.commit();
    }
    public static void navigateToFragment(FragmentManager fragmentManager, Fragment destination, int container,String FRAGMENT_TAG){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(container,destination);
        fragmentTransaction.addToBackStack(FRAGMENT_TAG);
        fragmentTransaction.commit();
    }
    public static void popInclusiveTrue(FragmentManager fragmentManager, String DESTINATION_TAG){
        fragmentManager.popBackStack(DESTINATION_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    public static void popInclusiveFalse(FragmentManager fragmentManager, String DESTINATION_TAG){
        fragmentManager.popBackStack(DESTINATION_TAG,0);
    }


}
