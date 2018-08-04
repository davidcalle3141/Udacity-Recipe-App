package calle.david.udacityrecipeapp.UI.Fragments;

import android.appwidget.AppWidgetProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.appwidget.AppWidgetManager;


import java.util.Objects;


import butterknife.BindView;
import butterknife.ButterKnife;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.R;
import calle.david.udacityrecipeapp.UI.Adapters.RecipeCardAdapter;
import calle.david.udacityrecipeapp.UI.Widget.HomeScreenWidget;
import calle.david.udacityrecipeapp.UI.Widget.WidgetUtils;
import calle.david.udacityrecipeapp.Utilities.EspressoIdlingResource;
import calle.david.udacityrecipeapp.Utilities.FragmentNavUtils;
import calle.david.udacityrecipeapp.Utilities.InjectorUtils;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModelFactory;
import calle.david.udacityrecipeapp.ViewModel.RecipeAppViewModel;

public class RecipeCardsViewFragment extends Fragment implements RecipeCardAdapter.RecipeCardAdapterOnClickListener {
    private RecipeAppViewModel mViewModel;
    private Context mContext;
    private  boolean isTwoPane=false;
    private FragmentManager fragmentManager;


    private RecipeCardAdapter recipeCardAdapter;
    @BindView(R.id.recipeCardsRV)RecyclerView recyclerView;
    private View view;

    public RecipeCardsViewFragment(){
        }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_recipe_cards,container,false);
        mContext = this.getContext();
        ButterKnife.bind(this,view);
        EspressoIdlingResource.Lock();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentManager = getFragmentManager();
        //since this is the start destination this code checks is we are using a tablet or not
        //in the future i will pass this as an argument to other fragments instead of checking at every fragment
        if(Objects.requireNonNull(getActivity()).findViewById(R.id.twoPane)!=null){
            isTwoPane = true; }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int spanCount = isTwoPane ? 3 : 1;
        GridLayoutManager mGrid = new GridLayoutManager(mContext, spanCount);
        recyclerView.setLayoutManager(mGrid);
        recipeCardAdapter = new RecipeCardAdapter(mContext, this);
        recyclerView.setAdapter(recipeCardAdapter);
        RecipeAppViewModelFactory factory = InjectorUtils.provideRecipeCardViewFactory(Objects.requireNonNull(getActivity()));
        mViewModel = ViewModelProviders.of(getActivity(),factory).get(RecipeAppViewModel.class);

        populateUI();

    }


    private void populateUI() {
        mViewModel.getRecipeList().removeObservers(this);
        mViewModel.getRecipeList().observe(this,
                recipes->{
            if(recipes != null){
                if(recipes.size()!=0){
                recipeCardAdapter.addRecipeList(recipes);
                recipeCardAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);
                EspressoIdlingResource.Unlock();

                }

            }
                });
    }


    @Override
    public void onItemClick(int position) {
        EspressoIdlingResource.Lock();
        mViewModel.getRecipeList().observe(this, recipes -> {
            if(recipes!=null) {
                mViewModel.setSelectedRecipe(recipes.get(position));
                updateWidget(recipes.get(position));
                if(isTwoPane) FragmentNavUtils.navigateToFragment(fragmentManager,new MasterListFragment(),R.id.recipe_card_view_container,"MASTER_LIST_FRAGMENT");
                else FragmentNavUtils.navigateToFragment(fragmentManager,new RecipeIngredientsFragment(),R.id.recipe_card_view_container,"INGREDIENTS_FRAGMENT");

            }

        });

    }

    private void updateWidget(Recipe recipe) {
        WidgetUtils.setWidgetAttributes(mContext,recipe.getId(),recipe.getName());
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
        int[] widgetIDs = appWidgetManager.getAppWidgetIds(
                new ComponentName(mContext,HomeScreenWidget.class)
        );
        appWidgetManager.notifyAppWidgetViewDataChanged(widgetIDs,R.id.widget_ingredients_list_items);
        HomeScreenWidget.updateAppWidget(mContext,appWidgetManager,widgetIDs);

    }
}
