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
                    int stepID = steps.getJSONObject(j).getInt("id");
                    String shortDescription = steps.getJSONObject(j).getString("shortDescription");
                    String description = steps.getJSONObject(j).getString("description");
                    String thumbnailURL = steps.getJSONObject(j).getString("thumbnailURL");
                    String videoURL = steps.getJSONObject(j).getString("videoURL");
                    if(thumbnailURL.substring(thumbnailURL.lastIndexOf(".")+1, thumbnailURL.length()).equals("mp4")){
                        videoURL=thumbnailURL;
                        thumbnailURL="";}

                    Steps tempSteps = new Steps(recipeList.get(i).getId(),stepID,shortDescription,description,thumbnailURL,videoURL);
                    stepsList.add(tempSteps);
//                    stepsList.add(new Steps(recipeList.get(i).getId()));
//                    //stepsList.get(i).setId(ingredients.getJSONObject(i).getInt("id"));
//                    stepsList.get(j).setDescription(steps.getJSONObject(j).getString("description"));
//                    stepsList.get(j).setShortDescription(steps.getJSONObject(j).getString("shortDescription"));
//                    stepsList.get(j).setThumbnailURL(steps.getJSONObject(j).getString("thumbnailURL"));
//                    stepsList.get(j).setVideoURL(steps.getJSONObject(j).getString("videoURL"));
                }
                for(int j=0; j< ingredients.length(); j++){
                    String ingName = ingredients.getJSONObject(j).getString("ingredient");
                    String ingMeasure = ingredients.getJSONObject(j).getString("measure");
                    String ingQuantity = ingredients.getJSONObject(j).getString("quantity");
                    Ingredients tempIng = new Ingredients(recipeList.get(i).getId(),ingName,ingMeasure,ingQuantity);
                    ingredientsList.add(tempIng);
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
