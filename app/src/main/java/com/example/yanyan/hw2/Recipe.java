package com.example.yanyan.hw2;

/**
 * Created by yanyan on 3/6/18.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {

    // instance variable or fields
    public int rowid;
    public String title;
    public String imageURL;
    public String instructorURL;
    public String description;
    public String label;
    public String servings;
    public String preptime;
    public String preptime_label;
    public String serving_label;
    public ArrayList<String>  searchlabels;

    //static method that read the json file in and load into Recipe
    // static method that loads our recipes.json using the helper method
    // this method will return an array list of recipes constructed from the JSON file

    public static ArrayList<Recipe> getRecipesFromFile(String filename, Context context){
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();


        try{
            String jsonString = loadJsonFromAsset("recipes.json", context);
            JSONObject json = new JSONObject(jsonString);
            JSONArray recipes = json.getJSONArray("recipes");


            for (int i = 0; i < recipes.length(); i++){
                Recipe recipe = new Recipe();
                recipe.rowid = i;
                recipe.title = recipes.getJSONObject(i).getString("title");
                recipe.description = recipes.getJSONObject(i).getString("description");
                recipe.imageURL = recipes.getJSONObject(i).getString("image");
                recipe.instructorURL =recipes.getJSONObject(i).getString("url");
                recipe.label =recipes.getJSONObject(i).getString("dietLabel");
                recipe.servings =recipes.getJSONObject(i).getString("servings");
                recipe.preptime = recipes.getJSONObject(i).getString("prepTime");
                recipe.searchlabels = new ArrayList<String>();

                int serving_number = Integer.parseInt(recipe.servings);
                if(serving_number < 4){
                    recipe.serving_label ="less than 4";
                }
                else if(serving_number <=6){
                    recipe.serving_label ="4-6";
                }
                else if(serving_number <= 9){
                    recipe.serving_label = "7-9";
                }
                else{
                    recipe.serving_label = "more than 10";
                }

                if (recipe.preptime.contains("hour")){
                    recipe.preptime_label = "more than 1 hour";
                }
                else{
                    recipe.preptime_label ="less than 1 hour";
                }
                recipe.searchlabels.add(recipe.label);
                recipe.searchlabels.add(recipe.preptime_label);
                recipe.searchlabels.add(recipe.serving_label);



                //add to arraylist
                recipeList.add(recipe);





            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeList;
    }


    // helper method that loads from any Json file
    private static String loadJsonFromAsset(String filename, Context context) {
        String json = null;

        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        }
        catch (java.io.IOException ex) {
            ex.printStackTrace();
            return null;
        }

        return json;
    }

}