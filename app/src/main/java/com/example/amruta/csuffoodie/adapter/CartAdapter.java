package com.example.amruta.csuffoodie.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.Constants;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

import java.util.List;

/**
 * Created by amruta on 4/20/2016.
 */
public class CartAdapter extends BaseAdapter{
    Context context;
    List<MenuItem> cartItems;
    DatabaseHandler db;
    MenuItem currentMenuItem;

    public CartAdapter(Context context, List<MenuItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
        db = new DatabaseHandler(context);
    }

    @Override
    public int getCount() {
        return cartItems.size();
    }

    @Override
    public MenuItem getItem(int position) {
        return cartItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return cartItems.indexOf(getItem(position));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE
        );

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_cart_single_row, null);

            holder = new ViewHolder();
            holder.item_name = (TextView) convertView.findViewById(R.id.cart_item_name);
            holder.item_total_price = (TextView) convertView.findViewById(R.id.cart_item_total_price);
            holder.item_image = (ImageView) convertView.findViewById(R.id.cart_item_image);
            holder.item_qty =  (TextView) convertView.findViewById(R.id.cart_item_qty);
            //holder.menu_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        MenuItem row_pos = cartItems.get(position);

        holder.item_name.setText(row_pos.getItemName());
        //TODO : change this price later to actual price
        holder.item_total_price.setText(String.valueOf(row_pos.getPrice()));
        //holder.item_total_price.setText(String.valueOf(row_pos.getPrice()));
        //holder.item_qty.setText(String.valueOf(row_pos.getPrice()));

        //holder.menu_checkbox.setChecked(Boolean.valueOf(row_pos.isAvailable()));
        // set the image here
        String image_str = row_pos.getImage();
        int image_value = context.getResources().getIdentifier(
                Constants.IMAGES_PATH +image_str,
                null,
                null
        );
        holder.item_image.setImageResource(image_value);

        //define the delete button
        /*holder.menu_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMenuItem = cartItems.get(position);
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
        }); */

        return convertView;
    }

    //These fields will shown up on the screen from the MenuItem object
    private class ViewHolder {
        ImageView item_image;
        TextView item_name;
        //TextView item_price;
        TextView item_total_price;
        TextView item_qty;
    }
}