package calle.david.udacityrecipeapp.UI.Fragments;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.IngredientListAdapter;
import calle.david.udacityrecipeapp.UI.Adapters.StepListAdapter;
import calle.david.udacityrecipeapp.Utilities.EspressoIdlingResource;
import calle.david.udacityrecipeapp.Utilities.FragmentNavUtils;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;


public class RecipeIngredientsFragment extends Fragment implements StepListAdapter.StepListAdapterOnClickListener {
    private RecipeAppViewModel mViewModel;
    private FragmentManager fragmentManager;
    private View view;
    private int[] position= new int[2];
    IngredientListAdapter ingredientListAdapter;
    StepListAdapter stepListAdapter;

    @BindView(R.id.ingredients_master_list_fragment)ScrollView scrollView;
    @BindView(R.id.ingredientsListRV)RecyclerView recyclerViewIngredientsList;
    @BindView(R.id.ingredientsListStepsRV)RecyclerView recyclerViewStepDescription;
    @BindView(R.id.ingredients_card_name)TextView recipeName;
    @BindView(R.id.ingredients_card_image)ImageView recipeImage;
    private boolean isTwoPane= false;

    public RecipeIngredientsFragment(){

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.fragment_recipe_ingredients,container,false);
        Context mContext = getContext();
        ButterKnife.bind(this,view);
        LinearLayoutManager layoutManagerIng = new LinearLayoutManager(mContext);
        LinearLayoutManager layoutManagerSteps = new LinearLayoutManager(mContext);
        fragmentManager = getFragmentManager();

        recyclerViewStepDescription.setNestedScrollingEnabled(false);
        recyclerViewIngredientsList.setNestedScrollingEnabled(false);
        recyclerViewStepDescription.setLayoutManager(layoutManagerSteps);
        recyclerViewIngredientsList.setLayoutManager(layoutManagerIng);
        stepListAdapter = new StepListAdapter(mContext,this);
        ingredientListAdapter = new IngredientListAdapter(getContext());
        recyclerViewStepDescription.setAdapter(stepListAdapter);
        recyclerViewIngredientsList.setAdapter(ingredientListAdapter);



        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //checks if tablet, in future will get this through bundle
        if(Objects.requireNonNull(getActivity()).findViewById(R.id.twoPane)!=null){
            isTwoPane = true; }
            }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);
        mViewModel.getSelectedRecipe().observe(this, recipe -> {
            if (recipe != null) {
                populateUI(recipe);
                EspressoIdlingResource.Unlock();

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        //ScrollToPosition(position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onPause() {
        super.onPause();
        position[0] = scrollView.getScrollX();
        position[1] = scrollView.getScrollY();

    }
    @Override
     public void onClick(int position) {
        EspressoIdlingResource.Lock();//stop test till we navigate to next view

        mViewModel.getFocusedStep().removeObservers(this);
        mViewModel.getFetchedSteps().removeObservers(this);

        mViewModel.getFetchedSteps().observe(this, stepsList -> {
            if(stepsList!= null){
                mViewModel.getFocusedStep().setValue(stepsList.get(position));
                mViewModel.setStepNum(position);
                mViewModel.setHasVideo(!stepsList.get(position).getVideoURL().equals(""));
                if(!isTwoPane) {
                  //  Navigation.findNavController(view).navigate(R.id.recipeStepsDestination);
                    FragmentNavUtils.navigateToFragment(fragmentManager,new RecipeStepsFragment(),R.id.recipe_card_view_container,"STEPS_FRAGMENT");
                }
            }
        });



    }

    private void ScrollToPosition(int[] position) {
        if(isTwoPane)return;
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


        mViewModel.getStepsforRecipe().observe(this, stepsList -> {
            if (stepsList != null && stepsList.size()>0) {
                mViewModel.setStepListSize(stepsList.size());
                stepListAdapter.addStepList(stepsList);
                stepListAdapter.notifyDataSetChanged();
                recyclerViewStepDescription.setVisibility(View.VISIBLE);
                if(isTwoPane)mViewModel.getFocusedStep().setValue(stepsList.get(0));
            }
        });

    }


    private Boolean isLandscape(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            return true;
        }
        return false;
    }


}
