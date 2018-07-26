package calle.david.udacityrecipeapp.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androidx.navigation.Navigation;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Fragments.RecipeCardsViewFragment;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);



    }
}
