package com.example.yanyan.hw2;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by yanyan on 3/6/18.
 */

public class SearchActivity extends AppCompatActivity {

    private Context mContext;
    private Spinner dietSpinner;
    private Spinner servingSpinner;
    private Spinner timeSpinner;
    private Button search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        mContext = this;
        dietSpinner = (Spinner)findViewById(R.id.diet_select);
        servingSpinner =(Spinner)findViewById(R.id.serving_select);
        timeSpinner =(Spinner)findViewById(R.id.time_select);
        search_btn =(Button)findViewById(R.id.Search);

        final ArrayList<Recipe> recipeList = Recipe.getRecipesFromFile("recipes.json", this);
        ArrayList<String> dietlabel = new ArrayList<String>();
        dietlabel.add("No restriction");
        String[] servinglabel = new String[]{"No restriction","less than 4", "4-6", "7-9", "more than 10"};
        String[] timelabel = new String[]{"No restriction","30 minutes or less", "less than 1 hour", "more than 1 hour"};

        for (int i = 0; i < recipeList.size(); i++){
            String diet =recipeList.get(i).label;
            if (dietlabel.contains(diet)){
            }
            else{
                dietlabel.add(diet);
            }
        }

        ArrayAdapter<String> dietadpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dietlabel);
        ArrayAdapter<String> servingadpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, servinglabel);
        ArrayAdapter<String> timeadpter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, timelabel);

        dietSpinner.setAdapter(dietadpter);
        servingSpinner.setAdapter(servingadpter);
        timeSpinner.setAdapter(timeadpter);



        dietSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  //              String selected_diet = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        servingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        timeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        search_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent detailIntent = new Intent(mContext, ResultActivity.class);

                detailIntent.putExtra("diet", dietSpinner.getSelectedItem().toString());
//                System.out.println(dietSpinner.getSelectedItem().toString());
                detailIntent.putExtra("serving", servingSpinner.getSelectedItem().toString() );
                detailIntent.putExtra("time", timeSpinner.getSelectedItem().toString() );

                startActivity(detailIntent);

            }

        });



    }
}
