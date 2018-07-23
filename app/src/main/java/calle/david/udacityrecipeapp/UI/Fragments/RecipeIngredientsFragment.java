package calle.david.udacityrecipeapp.UI.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.IngredientListAdapter;
import calle.david.udacityrecipeapp.UI.Adapters.StepListAdapter;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;


public class RecipeIngredientsFragment extends Fragment implements StepListAdapter.StepListAdapterOnClickListener {
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private View view;
    private int recipeID;

    //private String recipeImage;
   // private String recipeName;
    int[] position= new int[2];
    IngredientListAdapter ingredientListAdapter;
    StepListAdapter stepListAdapter;

    @BindView(R.id.ingredients_scroll_view)NestedScrollView scrollView;
    @BindView(R.id.ingredientsListRV)RecyclerView recyclerViewIngredientsList;
    @BindView(R.id.ingredientsListStepsRV)RecyclerView recyclerViewStepDescription;
    @BindView(R.id.ingredients_card_name)TextView recipeName;
    @BindView(R.id.ingredients_card_image)ImageView recipeImage;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_recipe_ingredients_fragment,container,false);
        this.mContext = getContext();
        ButterKnife.bind(this,view);

        LinearLayoutManager layoutManagerIng = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(mContext);

        recyclerViewStepDescription.setNestedScrollingEnabled(false);
        recyclerViewIngredientsList.setNestedScrollingEnabled(false);
        recyclerViewStepDescription.setLayoutManager(layoutManagerSteps);
        recyclerViewIngredientsList.setLayoutManager(layoutManagerIng);
        stepListAdapter = new StepListAdapter(mContext,this);
        ingredientListAdapter = new IngredientListAdapter(mContext);
        recyclerViewStepDescription.setAdapter(stepListAdapter);
        recyclerViewIngredientsList.setAdapter(ingredientListAdapter);



        return view;
    }

    private void ScrollToPosition(int[] position) {
        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            if (position != null) {
                scrollView.scrollTo(position[0], position[1] + getScreenWidth()/4);
            }
        });
    }

    private int getScreenWidth() {
        return  Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);

        mViewModel.getSelectedRecipe().observe(this, this::populateUI);

        }

    @Override
    public void onResume() {
        super.onResume();
        ScrollToPosition(position);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putIntArray("SCROLL_POSITION",
//                new int[]{ scrollView.getScrollX(), scrollView.getScrollY()});
//    }
    //I think there is a bug where scrollview doesnt return to its position when there are nested recyclerviews
    //to overcome this I manually had to handle onsaveinstancestate for rotations and on pause and on resume for navigation

//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        if(savedInstanceState!=null){
//            position = savedInstanceState.getIntArray("SCROLL_POSITION");
//        }
//        if(position!=null)ScrollToPosition(position);
//
//    }


    @Override
    public void onPause() {
        super.onPause();
        position[0] = scrollView.getScrollX();
        position[1] = scrollView.getScrollY();

    }



    private void populateUI(Recipe recipe) {
        int recipeID = recipe.getId();
        String name = recipe.getName();
        String image = recipe.getImage();

        recipeName.setText(name);
        Picasso.get().load(image).into(recipeImage);


        mViewModel.getIngredientsforRecipe(recipeID).observe(this, ingredients -> {
            if (ingredients != null) {
                ingredientListAdapter.addIngredientsList(ingredients);
                ingredientListAdapter.notifyDataSetChanged();

            }

        });


        mViewModel.getStepsforRecipe(recipeID).observe(this, stepsList -> {
            if (stepsList != null) {
                stepListAdapter.addStepList(stepsList);
                stepListAdapter.notifyDataSetChanged();
                recyclerViewStepDescription.setVisibility(View.VISIBLE);
            }
        });

    }


    @Override
    public void onClick(int position) {
        mViewModel.getFetchedSteps().observe(this, stepsList -> {
            if(stepsList!= null){
                mViewModel.getFocusedStep().setValue(stepsList.get(position));

                Navigation.findNavController(view).navigate(R.id.action_recipeIngredientsFragment_to_recipeStepsFragment);
            }
        });


    }
}
