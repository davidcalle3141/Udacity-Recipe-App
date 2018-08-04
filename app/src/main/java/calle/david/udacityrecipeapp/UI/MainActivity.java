package calle.david.udacityrecipeapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Fragments.RecipeCardsViewFragment;
import calle.david.udacityrecipeapp.Utilities.FragmentNavUtils;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            FragmentNavUtils.startActivityFragment(getSupportFragmentManager(),new RecipeCardsViewFragment(),R.id.recipe_card_view_container);
        }

    }





}
