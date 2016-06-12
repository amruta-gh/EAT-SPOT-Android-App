package com.example.amruta.csuffoodie.navigation.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.MenuAdapter;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amruta on 3/20/2016.
 */
public class AdminMenuItems
        extends Activity
        implements AdapterView.OnItemClickListener {

    //CheckBox menu_item_checkbox;
    ImageButton delete_item_button;
    Button add_new_item_button;
    List<MenuItem> menuItems;
    ListView menuItemListView;
    int position, rest_id;
    String rest_name, rest_desc, rest_image;
    MenuAdapter madapter;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item_list);

        delete_item_button =(ImageButton) findViewById(R.id.btn_delete);
        //create new menu item
        add_new_item_button = (Button)findViewById(R.id.btn_add);

        db = new DatabaseHandler(getApplicationContext());

        //get the intent and get the restaurant values from the previous screen
        Intent intent = getIntent();
        if (null != intent) {
            position = intent.getExtras().getInt("position");
            rest_id = intent.getExtras().getInt("rest_id");
            rest_name = intent.getExtras().getString("rest_name");
            rest_desc = intent.getExtras().getString("rest_desc");
            rest_image = intent.getExtras().getString("rest_image");
        }

        menuItems = db.getRestaurantMenuItems(rest_id);
        Log.e("AdminMenuItems:", ""+ menuItems);

        menuItemListView = (ListView) findViewById(R.id.menu_item_list);
        madapter = new MenuAdapter(this, menuItems);
        menuItemListView.setAdapter(madapter);
        //TODO: update in this code
        menuItemListView.setOnItemClickListener(this);


        //create new menu item
        add_new_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNewMenuItem.class);

                intent.putExtra("rest_id", rest_id);
                intent.putExtra("rest_name", rest_name);
                intent.putExtra("rest_image", rest_image);
                Log.e("AdminMenuItems ", rest_id + " - " + rest_name);

                startActivity(intent);
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
