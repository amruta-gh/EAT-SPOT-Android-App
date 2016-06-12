package com.example.amruta.csuffoodie.navigation.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.example.amruta.csuffoodie.R;

public class RestaurantActivity
        extends FragmentActivity
        implements RestaurantFragment.Communicator
{
    RestaurantFragment fragmentA;
    MenuFragment fragmentB;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        manager = getSupportFragmentManager();
        fragmentA = (RestaurantFragment) manager.findFragmentById(R.id.fragment);
        fragmentA.setCommunicator(this);
    }

    @Override
    public void respond(int restaurantId) {
        fragmentB = (MenuFragment) manager.findFragmentById(R.id.fragment2);
        if (fragmentB != null && fragmentB.isVisible())
            /* we can set the restaurant id in menu fragment from here which is passed
                in this method (respond) --> this repond is set in RestaurantActivity
                onItemClick
                */
            fragmentB.setRestaurantId(restaurantId);
        else {
            Intent intent = new Intent(RestaurantActivity.this, MenuActivity.class);
            //TODO: add all the restaurant info
            intent.putExtra("restaurant_id", restaurantId);
            startActivity(intent);

        }
    }
}