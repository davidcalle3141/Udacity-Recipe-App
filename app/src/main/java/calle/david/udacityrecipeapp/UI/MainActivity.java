package calle.david.udacityrecipeapp.UI;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Fragments.MasterListFragment;
import calle.david.udacityrecipeapp.UI.Fragments.RecipeCardsViewFragment;
import calle.david.udacityrecipeapp.Utilities.FragmentNavUtils;


public class MainActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
            getSupportFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        if(findViewById(R.id.twoPane)==null)setSupportActionBar(findViewById(R.id.my_toolbar));
        else setSupportActionBar(findViewById(R.id.my_toolbar_tablet));


        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        if(savedInstanceState == null){
            if(findViewById(R.id.twoPane) != null) FragmentNavUtils.startActivityFragment(getSupportFragmentManager(),new RecipeCardsViewFragment(),R.id.recipe_card_view_container_tablet);
            else FragmentNavUtils.startActivityFragment(getSupportFragmentManager(),new RecipeCardsViewFragment(),R.id.recipe_card_view_container);
        }


    }





}
