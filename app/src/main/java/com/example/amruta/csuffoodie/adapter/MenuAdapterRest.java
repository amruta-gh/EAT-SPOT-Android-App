package com.example.amruta.csuffoodie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.Constants;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;
import com.example.amruta.csuffoodie.navigation.consumer.CartFragment;
import com.example.amruta.csuffoodie.navigation.consumer.MenuFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by amruta on 4/20/2016.
 */
public class MenuAdapterRest
        extends BaseAdapter
        //implements MenuFragment.CartCommunicator
{

    private static final String LOG_TAG = MenuAdapterRest.class.getSimpleName();
    Context context;
    List<MenuItem> menuItems;
    DatabaseHandler db;
    MenuItem currentMenuItem;
    public List<Boolean> selectedMenuItems;
    public List<Integer> selectedMenuItemIds;
    public List<Integer> selectedMenuItemQty;
    MenuFragment menuFragment;

    public MenuAdapterRest(Context context, List<MenuItem> menuItems, MenuFragment fragment) {
        int menuSize = menuItems.size();
        this.context = context;
        this.menuItems = menuItems;
        db = new DatabaseHandler(context);
        selectedMenuItems = new ArrayList<Boolean>(Arrays.asList(new Boolean[menuSize]));
        selectedMenuItemIds = Arrays.asList(new Integer[menuSize]);
        selectedMenuItemQty = Arrays.asList(new Integer[menuSize]);
        this.menuFragment = fragment;

        for (int i = 0; i < menuSize ; i++) {
            selectedMenuItemIds.set(i, -1);
            selectedMenuItemQty.set(i, 0);
            selectedMenuItems.set(i, false);
        }
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public MenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuItems.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final CartViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE
        );

        if (selectedMenuItems == null) {
            selectedMenuItems = new ArrayList<Boolean>(menuItems.size());
        }

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.fragment_menu_single_row,parent,false);

            holder = new CartViewHolder();
            holder.menu_item_name = (TextView) convertView.findViewById(R.id.largeText);
            holder.menu_item_price = (TextView) convertView.findViewById(R.id.smallText);
            holder.menu_item_image = (ImageView) convertView.findViewById(R.id.imageView);
            holder.menu_checkbox =  (CheckBox) convertView.findViewById(R.id.check);
            holder.menu_qty = (EditText) convertView.findViewById(R.id.menu_item_qty);
            convertView.setTag(holder);
        } else {
            holder = (CartViewHolder) convertView.getTag();
        }
        MenuItem row_pos = menuItems.get(position);
        //Log.e("MenuAdapterRest: ", row_pos.toString());
        //Log.e("MenuAdapterRest: ", "menu_qty " + holder.menu_qty);

        holder.menu_item_name.setText(row_pos.getItemName());
        holder.menu_item_price.setText("$" + String.valueOf(row_pos.getPrice()));
        holder.menu_qty.setText("1");

        // set the image here
        String image_str = row_pos.getImage();
        int image_value = context.getResources().getIdentifier(
                Constants.IMAGES_PATH +image_str,
                null,
                null
        );
        holder.menu_item_image.setImageResource(image_value);


        //define the checkbox
        holder.menu_checkbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                currentMenuItem = menuItems.get(position);

                if (selectedMenuItems == null) {
                    Log.e("MenuAdapterRest:", "selectedMenuItems" + selectedMenuItems);
                    selectedMenuItems = new ArrayList<Boolean>(Arrays.asList(new Boolean[menuItems.size()]));
                }

                Log.e("MenuAdapterRest:", ""+currentMenuItem.toString() +
                 " selectedMenuItems " + selectedMenuItems);
                if (holder.menu_checkbox.isChecked()) {
                    selectedMenuItems.set(position,true);

                    selectedMenuItemIds.set(position, currentMenuItem.getId());
                    //MenuFragment..CartCommunicator
                    //TODO: get this quantity from the text box
                    selectedMenuItemQty.set(position, 1);

                    //TODO: call methods here to set the values
                    menuFragment.setMenuId(selectedMenuItems);
                    menuFragment.setCartItems(selectedMenuItemIds);

                    Toast.makeText(context,
                            "" + currentMenuItem.getItemName() + " added in cart !"
                                    + selectedMenuItems.toString()
                                    + " ~ " + selectedMenuItemIds.toString() ,
                            Toast.LENGTH_LONG).show();
                } else {
                    selectedMenuItems.set(position,false);
                    selectedMenuItemIds.set(position, -1);
                    selectedMenuItemQty.set(position, 0);

                    Toast.makeText(context,
                            "" + currentMenuItem.getItemName() + " removed from cart "
                            + selectedMenuItems.toString()
                                    + " ~ " + selectedMenuItemIds.toString() ,
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        /*
        //define the delete button
        holder.menu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMenuItem = menuItems.get(position);
                // delete the menu item from db
                if (db.deleteMenuItem(currentMenuItem.getId())) {
                    Toast.makeText(context,
                            ""+currentMenuItem.getItemName() + " delete from your restaurant menu",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "problem in deleting menu item ",
                            Toast.LENGTH_LONG).show();
                }
            }
        });*/

        return convertView;
    }

    //These fields will shown up on the screen from the MenuItem object
    private class CartViewHolder {
        TextView menu_item_name;
        TextView menu_item_price;
        ImageView menu_item_image;
        CheckBox menu_checkbox;
        EditText menu_qty;
        //ImageButton menu_delete;
    }

    //CartFragment.CartCommunicator
//    public void cartResponder(int restaurantId) {

  //  }
}
