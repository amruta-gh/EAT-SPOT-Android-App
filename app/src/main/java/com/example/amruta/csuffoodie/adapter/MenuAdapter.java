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
public class MenuAdapter extends BaseAdapter{
    private static final String LOG_TAG = MenuAdpater.class.getSimpleName();
    Context context;
    List<MenuItem> menuItems;
    DatabaseHandler db;
    MenuItem currentMenuItem;

    public MenuAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
        db = new DatabaseHandler(context);
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

        final ViewHolder holder;

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(
                Activity.LAYOUT_INFLATER_SERVICE
        );

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_menu_item_single_row, null);

            holder = new ViewHolder();
            holder.menu_name = (TextView) convertView.findViewById(R.id.admin_menu_item_name);
            holder.menu_price = (TextView) convertView.findViewById(R.id.admin_menu_item_price);
            holder.menu_image = (ImageView) convertView.findViewById(R.id.admin_menu_item_image);
            holder.menu_checkbox =  (CheckBox) convertView.findViewById(R.id.menu_checkbox);
            holder.menu_delete = (ImageButton) convertView.findViewById(R.id.btn_delete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        MenuItem row_pos = menuItems.get(position);
        Log.e("MenuAdapter: ", row_pos.toString());

        holder.menu_name.setText(row_pos.getItemName());
        holder.menu_price.setText(String.valueOf(row_pos.getPrice()));
        holder.menu_checkbox.setChecked(Boolean.valueOf(row_pos.isAvailable()));
        // set the image here
        String image_str = row_pos.getImage();
        int image_value = context.getResources().getIdentifier(
                Constants.IMAGES_PATH +image_str,
                null,
                null
        );
        holder.menu_image.setImageResource(image_value);

        //define the checkbox
        holder.menu_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMenuItem = menuItems.get(position);
                currentMenuItem.setAvailable(holder.menu_checkbox.isChecked());
                // update the database with checkbox value
                db.updateMenuItem(currentMenuItem);

                if (holder.menu_checkbox.isChecked()) {
                    Toast.makeText(context,
                            "" + currentMenuItem.getItemName() + " added in today's menu !",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context,
                            "" + currentMenuItem.getItemName() + " removed from today's menu !",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

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
        });

        return convertView;
    }

    //These fields will shown up on the screen from the MenuItem object
    private class ViewHolder {
        TextView menu_name;
        TextView menu_price;
        ImageView menu_image;
        CheckBox menu_checkbox;
        ImageButton menu_delete;
    }
}
