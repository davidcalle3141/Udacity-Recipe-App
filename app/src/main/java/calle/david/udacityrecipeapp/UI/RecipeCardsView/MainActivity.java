package calle.david.udacityrecipeapp.UI.RecipeCardsView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import calle.david.udacityrecipeapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            RecipeCardsViewFragment fragment = new RecipeCardsViewFragment();

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeCardFragment_container,fragment).commit();

        }
    }
}
