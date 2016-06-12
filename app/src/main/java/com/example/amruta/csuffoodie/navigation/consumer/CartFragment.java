package com.example.amruta.csuffoodie.navigation.consumer;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.CartAdapter;
import com.example.amruta.csuffoodie.adapter.MenuAdapterRest;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

import java.util.List;

/**
 * Created by amruta on 4/14/2016.
 */
public class CartFragment
        extends ListFragment
        implements AdapterView.OnItemClickListener
{
    public final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    CartAdapter cartAdapter;
    ListView mlistview;
    Context context;
    TextView text;
    int restaurantId;
    List<MenuItem> menuItemList;
    CartCommunicator cartCommunicator;


    public interface CartCommunicator {
        void cartResponder(int restaurantId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        ViewGroup rootView =
                (ViewGroup) inflater.inflate(
                        R.layout.fragment_menulist,
                        container,
                        false);
        if (savedInstanceState != null) {
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("CartFragment: ", "savedInstanceState"+savedInstanceState
                + "restaurantId " + restaurantId);

        DatabaseHandler db = new DatabaseHandler(getContext());
        menuItemList = db.getRestaurantMenuItems(restaurantId);

        cartAdapter = new CartAdapter(getActivity(), menuItemList);
        setListAdapter(cartAdapter);
        getListView().setOnItemClickListener(this);

        if (savedInstanceState != null) {
            restaurantId = savedInstanceState.getInt("pos");
            setRestaurantId(restaurantId);
            Log.e("CartFragment: ", "id"+restaurantId);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //this is not needed probably
        Toast.makeText(
                getActivity(),
                "Clicked",
                Toast.LENGTH_SHORT).show();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("CartFragment: ", "outState" + outState);

        //TODO: uncomment this to pass on this info for next fragment.. guess
        //outState.putInt("pos", restaurantId);
        // Save the current article selection in case we need to recreate the fragment
        // outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public void setRestaurantId(int id) {
        this.restaurantId = id;
        //changeData(id);
    }

    //public void  changeData(int i ) {
    //   Resources res = getResources();
     /*   String[] titles = res.getStringArray(R.array.titles);
        if (text == null)
            text = (TextView) getActivity().findViewById(R.id.largeText);
        text.setText(titles[i]); */
    //}

    public void setCartCommunicator(CartCommunicator communicator) {
        this.cartCommunicator = communicator;
    }

}


