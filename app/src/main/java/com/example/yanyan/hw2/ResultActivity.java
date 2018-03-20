package com.example.yanyan.hw2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by yanyan on 3/19/18.
 */

public class ResultActivity extends AppCompatActivity {

    private ListView mListView;
    private Context mContext;
    private TextView  resultTextView;
    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipeList;
    private ArrayList<Recipe> foundList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_list);

        mContext = this;
        resultTextView = findViewById(R.id.result);
        recipeList = Recipe.getRecipesFromFile("recipes.json",this);
        foundList = new ArrayList<Recipe>();

        String diet = this.getIntent().getExtras().getString("diet");
        String serving = this. getIntent().getExtras().getString("serving");
        String time = this.getIntent().getExtras().getString("time");

        for (int i = 0; i < recipeList.size(); i++){
            if (labelcheck(diet, serving, time, recipeList.get(i))){
                foundList.add(recipeList.get(i));
            }
        }


        resultTextView.setText("Found "+foundList.size()+" recipes");

        adapter = new RecipeAdapter(this, foundList);
        mListView = findViewById(R.id.recipe_list_view);
        mListView.setAdapter(adapter);

    }



    public boolean dietcheck(String diet, Recipe recipe){
        if (diet.equals("No restriction") || recipe.searchlabels.contains(diet)){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean servingcheck(String serving, Recipe recipe){
        if (serving.equals("No restriction") || recipe.searchlabels.contains(serving)){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean timecheck(String time, Recipe recipe){
        if (time.equals("No restriction" )|| recipe.searchlabels.contains(time)){
            return true;
        }
        else if(time.equals("30 minutes or less") && recipe.preptime_label.equals("less than 1 hour")){
            String min = recipe.preptime.substring(0, 2);
            int mins = Integer.parseInt(min);
//            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!");
//            System.out.println(min);
            if (mins <= 30){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
    public boolean labelcheck(String diet, String serving, String time, Recipe recipe){
        boolean matchdiet = dietcheck(diet, recipe);
        boolean matchserving = servingcheck(serving,recipe);
        boolean matchtime = timecheck(time,recipe);

        if (matchdiet && matchserving && matchtime){
            return true;
        }
        else{
            return false;
        }
    }
}
