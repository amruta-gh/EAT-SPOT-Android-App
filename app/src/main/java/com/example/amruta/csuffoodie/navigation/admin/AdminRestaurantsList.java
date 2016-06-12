package com.example.amruta.csuffoodie.navigation.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.RestaurantAdapter;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amruta on 4/20/2016.
 */
public class AdminRestaurantsList extends Activity {

    ListView mylistview;
    DatabaseHandler db;
    List<Restaurant> allRestaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantlist);

        db = new DatabaseHandler(getApplicationContext());

        /*File dbFile = getApplicationContext().getDatabasePath("FoodieDB.db");
        Log.e("dbFile: ", dbFile + " -- "+dbFile.exists());
        if (!dbFile.exists()) {
            dbFile.setWritable(true);
            dbFile.mkdir();
        }*/

        allRestaurants = db.getAllRestaurants();

        mylistview = (ListView) findViewById(R.id.list);
        RestaurantAdapter adapter = new RestaurantAdapter(this, allRestaurants);
        mylistview.setAdapter(adapter);

        final RestaurantAdapter finalAdapter = adapter;
        mylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,
                                    View view,
                                    int position,
                                    long offset)
            {
                Restaurant rest = (Restaurant) finalAdapter.getItem(position);

                Intent intent = new Intent(getApplicationContext(), AdminMenuItems.class);
                intent.putExtra("position", position);
                intent.putExtra("rest_id", rest.getId());
                intent.putExtra("rest_name", rest.getName());
                intent.putExtra("rest_desc", rest.getDescription());
                intent.putExtra("rest_image", rest.getImage());
                Log.e(" item ", rest.getName() + " position " + position);

                startActivity(intent);
            }
        });
    }
}


