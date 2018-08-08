package calle.david.udacityrecipeapp.Utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.Objects;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Fragments.RecipeIngredientsFragment;
import calle.david.udacityrecipeapp.UI.Fragments.RecipeStepsFragment;

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
    public static void replaceFragment(FragmentManager fragmentManager, int container, Fragment fragment, String FRAGMENT_TAG){

        fragmentManager
                .beginTransaction()
                .replace(container, fragment,FRAGMENT_TAG)
                .commit();
    }
    public static void popInclusiveTrue(FragmentManager fragmentManager, String DESTINATION_TAG){
        fragmentManager.popBackStack(DESTINATION_TAG,FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }
    public static void popInclusiveFalse(FragmentManager fragmentManager, String DESTINATION_TAG){
        fragmentManager.popBackStack(DESTINATION_TAG,0);
    }


}
