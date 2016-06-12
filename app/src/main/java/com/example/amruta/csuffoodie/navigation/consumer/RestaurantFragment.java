package com.example.amruta.csuffoodie.navigation.consumer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.RestaurantAdapter;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.Restaurant;

import java.util.List;


/**
 * Created by amruta on 4/23/2016.
 */
public class RestaurantFragment
        extends ListFragment
        implements AdapterView.OnItemClickListener
{
    RestaurantAdapter restaurantAdapter;
    Context context;
    Communicator communicator;
    List<Restaurant> restaurantList;

    public interface Communicator {
        void respond(int restaurantId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
            ViewGroup rootView = (ViewGroup) inflater.inflate(
                    R.layout.fragment_hotellist,
                    container,
                    false
            );
            return rootView;
            // Inflate the layout for this fragment
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DatabaseHandler db = new DatabaseHandler(getContext());

        restaurantList = db.getAllRestaurants();
        Log.e("RestaurantFragment:", "restaurants"+restaurantList);
        restaurantAdapter = new RestaurantAdapter(getActivity(), restaurantList);
        setListAdapter(restaurantAdapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //whatever we want to pass to next fragment (MenuFragment) goes here
        communicator.respond(restaurantList.get(position).getId());

        Log.e("RestaurantFragment",
                "restaurantList "+restaurantList.get(position).getId()+
                " position "+ position);

        // Set the item as checked to be highlighted when in two-pane layout
        getListView().setItemChecked(position, true);
        Toast.makeText(
                getActivity(),
                "Clicked",
                Toast.LENGTH_SHORT
        ).show();
    }

    public void setCommunicator(Communicator communicator) {
        this.communicator = communicator;
    }

}