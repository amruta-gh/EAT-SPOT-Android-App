package com.example.amruta.csuffoodie.navigation.consumer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.CartAdapter;
import com.example.amruta.csuffoodie.adapter.MenuAdapter;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amruta on 3/20/2016.
 */
public class CartActivity
        extends Activity
        implements AdapterView.OnItemClickListener {

    //CheckBox menu_item_checkbox;
    Button submit_order_button;
    //Button add_new_item_button;
    List<MenuItem> cartItems;
    ListView cartListView;
    int position, rest_id;
    String rest_name, rest_desc, rest_image;
    CartAdapter cartAdapter;
    DatabaseHandler db;
    List<Integer> cartItemIds;
    List<Integer> qty_list;
    double totalPrice;
    TextView cartCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        submit_order_button = (Button)findViewById(R.id.btn_submit_order);
        cartCost = (TextView) findViewById(R.id.cart_cost);

        db = new DatabaseHandler(getApplicationContext());


        //get the intent and get the restaurant values from the previous screen
        Intent intent = getIntent();
        if (null != intent) {
            position = intent.getExtras().getInt("position");
            //rest_id = intent.getExtras().getInt("rest_id");
            //rest_name = intent.getExtras().getString("rest_name");
            //rest_desc = intent.getExtras().getString("rest_desc");
            //rest_image = intent.getExtras().getString("rest_image");

            rest_id = intent.getExtras().getInt("rest_id");
            intent.getExtras().getIntegerArrayList("qty_list");
            cartItemIds = intent.getExtras().getIntegerArrayList("cartItemIds");

            Log.e("CartActivity: ", "CartActivity " +
                    intent.toString()
                    + " restaurant_id " + rest_id
                    + " cartItemIds " + cartItemIds);
        }

        //TODO: get all menu item for selected menu ids ONLY


        cartItems = db.getRestaurantMenuItems(rest_id);
        //getMenuItem
        cartItems = new ArrayList<MenuItem>();
        if (cartItemIds == null) {
            return;
        }

        MenuItem m;
        for (Integer i : cartItemIds) {
            m = db.getMenuItem(rest_id, i);
            cartItems.add(m);
            totalPrice+=m.getPrice();
        }
        cartCost.setText(String.valueOf(totalPrice));

        Log.e("CartActivity: ", ""+ cartItems + " totalPrice " + totalPrice);

        cartListView = (ListView) findViewById(R.id.cart_item_list);
        cartAdapter = new CartAdapter(this, cartItems);
        cartListView.setAdapter(cartAdapter);
        //TODO: update in this code
        cartListView.setOnItemClickListener(this);

        //Order message
        submit_order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = null, chooser = null;
                    intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    String[] to = {"amby.g7@gmail.com", "amrita.ghangale@gmail.com"};
                    intent.putExtra(Intent.EXTRA_EMAIL, to);
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Your Order at CSUF:Eat-Spot");
                    intent.putExtra(Intent.EXTRA_TEXT, "ORDER CONFIRMATION, Your order will ready shortly. Thank You! :)");
                    intent.setType("message/rfc822");
                    chooser = Intent.createChooser(intent, "Send Email");
                    startActivity(chooser);
                    Toast.makeText(CartActivity.this, "Thanks for your order ! ", Toast.LENGTH_LONG).show();
                } catch (ActivityNotFoundException anfe) {
                    Toast.makeText(CartActivity.this, "Sorry No Email Client Found :( ", Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final CheckBox menu_item_checkbox = (CheckBox) findViewById(R.id.menu_checkbox);
        menu_item_checkbox.performClick();
        /*
        delete_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminMenuItems.this, "Item Deleted!...", Toast.LENGTH_LONG).show();

            }
        });
        */

        /*menu_item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AdminMenuItems.this, "Item Checked!...", Toast.LENGTH_LONG).show();
                Log.e("AdminMenuItems: ", "menu_item_checkbox"+ menu_item_checkbox.isChecked());
                //boolean itemcheck = menu_item_checkbox.isChecked();
            }
        });*/

        Toast.makeText(getApplicationContext(), "" , Toast.LENGTH_SHORT).show();
    }

}
