package com.example.yanyan.hw2;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
/**
 * Created by yanyan on 3/18/18.
 */

public class RecipeAdapter extends BaseAdapter {

    // adapter takes the app itself and a list of data to display
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;
    private LayoutInflater mInflater;
    private TextView titleTextView;

    // constructor
    public RecipeAdapter(Context mContext, ArrayList<Recipe> mRecipeList) {
        //initialize instances variables
        this.mContext = mContext;
        this.mRecipeList = mRecipeList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //Methods
    //a list of methods we need to override
    @Override
    public int getCount() {
        return mRecipeList.size();
    }

    //returns the item at the position
    @Override
    public Object getItem(int position) {
        return mRecipeList.get(position);
    }

    //return the row id associated with the specific position in the list
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //check if the view already exists
        //if yes, you dont need to inflate and findViewbyId again
        if (convertView == null) {
            //inflate
            convertView = mInflater.inflate(R.layout.detail_recipe, parent, false);
            //add the views to the holder
            holder = new ViewHolder();
            holder.servingTextView = convertView.findViewById(R.id.recipe_list_serving);
            holder.titleTextView = convertView.findViewById(R.id.recipe_list_title);
            holder.timeTextView = convertView.findViewById(R.id.recipe_list_prep);
            holder.thumbnailImageView = convertView.findViewById(R.id.recipe_list_thumb);
            holder.instruction = convertView.findViewById(R.id.cook);
            //add the holder to the view
            convertView.setTag(holder);
        } else {
            //get the view holder from conerview
            holder = (ViewHolder) convertView.getTag();
        }
        //get ralevant subview of the row view
        //get corresponding recipe for each row

        titleTextView = holder.titleTextView;
        TextView servingTextVeiw = holder.servingTextView;
        TextView timeTextView = holder.timeTextView;
        ImageView thumbnailImage = holder.thumbnailImageView;
        ImageButton instruction = holder.instruction;

        //get corresponding recipe for each row
        final Recipe recipe = (Recipe) getItem(position);

        //titleTextview
        titleTextView.setText(recipe.title);
        titleTextView.setTextColor(ContextCompat.getColor(mContext, R.color.colorAccent));
        titleTextView.setTextSize(24);

        //servingTextView

        servingTextVeiw.setText(recipe.servings + " servings");
        servingTextVeiw.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        servingTextVeiw.setTextSize(16);

        //timeTextView
        timeTextView.setText(recipe.preptime);
        servingTextVeiw.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
        timeTextView.setTextSize(16);

        //ImageView
        //Use picasso library to load image for the image url
        Picasso.with(mContext).load(recipe.imageURL).into(thumbnailImage);

        instruction.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String instructions = "The instruction for " + recipe.title + " can be found here!";

                final Notification.Builder builder = new Notification.Builder(mContext);

                builder.setStyle(new Notification.BigTextStyle(builder)
                        .bigText(instructions)
                        .setBigContentTitle("Cooking instruction")
                        .setSummaryText("Big summary"))
                        .setContentTitle("Title")
                        .setContentText("Summary")
                        .setSmallIcon(android.R.drawable.sym_def_app_icon);


                Uri webpage = Uri.parse(recipe.instructorURL);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);

                PendingIntent pendingIntent = PendingIntent.getActivity(mContext,0,webIntent,0);
                builder.setContentIntent(pendingIntent);

                NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1,builder.build());
            }
        });

        return convertView;
    }


    private static class ViewHolder {
        public TextView titleTextView;
        public TextView servingTextView;
        public ImageView thumbnailImageView;
        public TextView timeTextView;
        public ImageButton instruction;

    }


}