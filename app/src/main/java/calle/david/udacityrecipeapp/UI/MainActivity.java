package calle.david.udacityrecipeapp.UI;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.Utilities.CustomIdlingResource;
import calle.david.udacityrecipeapp.Utilities.EspressoIdlingResource;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }

}
