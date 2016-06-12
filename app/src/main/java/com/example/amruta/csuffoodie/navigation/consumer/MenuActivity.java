package com.example.amruta.csuffoodie.navigation.consumer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends FragmentActivity {

    Button add_to_cart;
    int restaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
        restaurantId = getIntent().getExtras().getInt("restaurant_id");
        final MenuFragment fragmentB =
                (MenuFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2);

        fragmentB.setRestaurantId(restaurantId);

        Log.e("MenuActivity : ", "restaurantId " + restaurantId);
        Log.e("MenuActivity : ", "onCreate, savedInstanceState " + savedInstanceState);

        add_to_cart = (Button) findViewById(R.id.btn_add_cart);

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartActivity.class);
                ArrayList<Integer> cartItemIds = fragmentB.getCartItems();

                Integer[] cartIdArray = new Integer[cartItemIds.size()];
                for (int i = 0; i< cartItemIds.size() ; i++){
                    cartIdArray[i] = cartItemIds.get(i);
                }

                for (int i =0 ; i < cartIdArray.length ; i++ ) {
                    Log.e("cartIdsArray  ", " " + cartIdArray[i]);
                }

                intent.putExtra("rest_id", restaurantId);
                intent.putExtra("cartItemIds2", cartIdArray);
                intent.putExtra("singleValue", cartIdArray[0]);
                intent.putIntegerArrayListExtra("cartItemIds", cartItemIds);

                Log.e("MenuActivity ", "onClick, restaurantId " + restaurantId
                        + " fragmentB.getMenus() " + fragmentB.getMenus()
                        + "cartIdArray " + cartIdArray);

               // Toast.makeText(getApplicationContext(),
                //        "menusss -- " + fragmentB.getMenus(),
                 //       Toast.LENGTH_LONG).show();

                startActivity(intent);
            }
        });
    }


}
