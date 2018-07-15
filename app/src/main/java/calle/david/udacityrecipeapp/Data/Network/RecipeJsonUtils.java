package calle.david.udacityrecipeapp.Data.Network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import calle.david.udacityrecipeapp.AppExecutors;
import calle.david.udacityrecipeapp.Data.Database.Ingredients;
import calle.david.udacityrecipeapp.Data.Database.Recipe;
import calle.david.udacityrecipeapp.Data.Database.Steps;

public final class RecipeJsonUtils {

    AppExecutors executors;
    RecipeResponse parseRecipeJson(String json, AppExecutors executors){
        this.executors = executors;
        try{
            JSONArray results = new JSONArray(json);
            List<Recipe> recipeList = new ArrayList<>();
            List<Steps> stepsList = new ArrayList<>();
            List<Ingredients> ingredientsList = new ArrayList<>();


            for (int i=0; i< results.length();i++){
                int numOfSteps=0;
                recipeList.add(new Recipe());
                recipeList.get(i).setId(results.getJSONObject(i).getInt("id"));
                recipeList.get(i).setName(results.getJSONObject(i).getString("name"));
                recipeList.get(i).setServings(results.getJSONObject(i).getInt("servings"));
                String image = results.getJSONObject(i).getString("image");
                if(image.trim().length()==0){
                    String [] split = recipeList.get(i).getName().split("\\s+");
                    String description = split[split.length-1];
                    image = NetworkUtils.getBakingStringImageNetworkResponse(description);
                }
                recipeList.get(i).setImage(image);

                JSONArray ingredients = results.getJSONObject(i).getJSONArray("ingredients");
                JSONArray steps = results.getJSONObject(i).getJSONArray("steps");

                for(int j=0; j< steps.length(); j++){
                    numOfSteps++;
                    stepsList.add(new Steps(recipeList.get(i).getId()));
                    //stepsList.get(i).setId(ingredients.getJSONObject(i).getInt("id"));
                    stepsList.get(i).setDescription(steps.getJSONObject(i).getString("description"));
                    stepsList.get(i).setShortDescription(steps.getJSONObject(i).getString("shortDescription"));
                    stepsList.get(i).setThumbnailURL(steps.getJSONObject(i).getString("thumbnailURL"));
                    stepsList.get(i).setVideoURL(steps.getJSONObject(i).getString("videoURL"));
                }
                for(int j=0; j< ingredients.length(); j++){
                    ingredientsList.add(new Ingredients(recipeList.get(i).getId()));
                    ingredientsList.get(i).setIngredientName(ingredients.getJSONObject(i).getString("ingredient"));
                    ingredientsList.get(i).setMeasure(ingredients.getJSONObject(i).getString("measure"));
                    ingredientsList.get(i).setQuantity(ingredients.getJSONObject(i).getString("quantity"));
                }

                recipeList.get(i).setNumOfSteps(numOfSteps);


            }
        return new RecipeResponse(recipeList,stepsList,ingredientsList);
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

}
