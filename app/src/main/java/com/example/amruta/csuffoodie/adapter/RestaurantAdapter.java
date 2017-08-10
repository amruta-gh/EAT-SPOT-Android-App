package com.example.amruta.csuffoodie.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.helper.Constants;
import com.example.amruta.csuffoodie.model.Restaurant;

import java.util.List;
//import R.*

/**
 * Created by amruta on 4/20/2016.
 */
public class RestaurantAdapter extends BaseAdapter {
    private static final String LOG_TAG = RestaurantAdapter.class.getSimpleName();
    Context context;
    List<Restaurant> rowItems;

    public RestaurantAdapter(Context context, List<Restaurant> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_restaurant_single_row, null);

            holder = new ViewHolder();
            holder.rest_name = (TextView) convertView.findViewById(R.id.restName);
            holder.rest_desc = (TextView) convertView.findViewById(R.id.restDesc);
            holder.rest_image = (ImageView) convertView.findViewById(R.id.logoImage);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        Restaurant row_pos = rowItems.get(position);

        holder.rest_name.setText(row_pos.getName());
        holder.rest_desc.setText(row_pos.getDescription());

        //set the image
        String image_str = row_pos.getImage();
        int image_value = context.getResources().getIdentifier(
                Constants.IMAGES_PATH + image_str,
                null,
                null
        );
        holder.rest_image.setImageResource(image_value);

        return convertView;
    }

    // These fields will show up on the screen from the Restaurant object
    private class ViewHolder {
        TextView rest_name;
        TextView rest_desc;
        ImageView rest_image;
    }

}

