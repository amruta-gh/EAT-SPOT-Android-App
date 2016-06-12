package com.example.amruta.csuffoodie.navigation.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

/**
 * Created by amruta on 4/20/2016.
 */
public class AddNewMenuItem extends Activity {

    EditText edit_MenuName,edit_MenuDesc,edit_MenuPrice;
    Button button_submit;
    int rest_id;
    String rest_name, rest_desc, rest_image;
    DatabaseHandler db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        edit_MenuName = (EditText) findViewById(R.id.ed_menuItem_Name);
        edit_MenuDesc = (EditText) findViewById(R.id.ed_menuItem_Desc);
        edit_MenuPrice = (EditText) findViewById(R.id.ed_menuItem_Price);
        button_submit = (Button) findViewById(R.id.btn_submit);

        db = new DatabaseHandler(getApplicationContext());

        Intent intent = getIntent();
        if (null != intent) {
            rest_id = intent.getExtras().getInt("rest_id");
            rest_name = intent.getExtras().getString("rest_name");
            rest_desc = intent.getExtras().getString("rest_desc");
            rest_image = intent.getExtras().getString("rest_image");
        }

        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("AdminMenuItemsOnClick: ", "rest_id " + rest_id + " restName " + rest_name
                        + " edit_MenuPrice " + edit_MenuPrice.getText()
                        + " edit_MenuName " + edit_MenuName.getText());

                MenuItem menuItem = new MenuItem();
                menuItem.setRestaurantId(rest_id);
                menuItem.setItemName(String.valueOf(edit_MenuName.getText()));
                menuItem.setPrice(Double.parseDouble(edit_MenuPrice.getText().toString()));

                try {
                    if(db.createMenuItemForRestaurant(menuItem)) {
                        Toast.makeText(AddNewMenuItem.this,
                                "Menu Item created successfully!...",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AddNewMenuItem.this,
                                "Menu Item creation failed...",
                                Toast.LENGTH_LONG).show();
                    }
                    return;
                } catch (Exception ex) {
                    Toast.makeText(AddNewMenuItem.this,
                            "Menu item can't be added..." + ex,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}