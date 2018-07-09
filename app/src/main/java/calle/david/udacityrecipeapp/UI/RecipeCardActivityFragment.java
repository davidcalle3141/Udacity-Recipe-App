package calle.david.udacityrecipeapp.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import calle.david.udacityrecipeapp.R;

public class RecipeCardActivityFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_card_row);
    }
}
