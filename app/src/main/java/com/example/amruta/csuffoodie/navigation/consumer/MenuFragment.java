package com.example.amruta.csuffoodie.navigation.consumer;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.example.amruta.csuffoodie.R;
import com.example.amruta.csuffoodie.adapter.MenuAdapter;
import com.example.amruta.csuffoodie.adapter.MenuAdapterRest;
import com.example.amruta.csuffoodie.helper.DatabaseHandler;
import com.example.amruta.csuffoodie.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amruta on 4/14/2016.
 */
public class MenuFragment
        extends ListFragment
        implements AdapterView.OnItemClickListener
{
    public final static String ARG_POSITION = "position";
    int mCurrentPosition = -1;
    MenuAdapterRest menuAdapter;
    ListView mlistview;
    //Context context;
    //TextView text;
    int restaurantId;
    List<MenuItem> menuItemList;
    CartCommunicator cartCommunicator;
    public SharedPreferences sharedpreferences;
    public static List<Boolean> selectedMenuItemsB;
    List<Boolean> menus;
    List<Integer> cartItemIds;

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

        //sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);


        //Log.e("MenuFragment3: ", "menuAdapter" + menuAdapter.selectedMenuItems
          //      + menuAdapter.selectedMenuItemIds + " ___ "
//                + menuAdapter.selectedMenuItemQty);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.e("savedInstanceState ", ""+savedInstanceState + "restaurantId " + restaurantId);

        DatabaseHandler db = new DatabaseHandler(getContext());
        menuItemList = db.getRestaurantMenuItems(restaurantId);

        menuAdapter = new MenuAdapterRest(getActivity(), menuItemList, this);
        setListAdapter(menuAdapter);
        getListView().setOnItemClickListener(this);

        Log.e("MenuFragment: ", "menuAdapter" + menuAdapter.selectedMenuItems
                + menuAdapter.selectedMenuItemIds + " ___ "
                + menuAdapter.selectedMenuItemQty);
        //getActivity().getIntent()
        //if (savedInstanceState != null) {
        //    restaurantId = savedInstanceState.getInt("pos");
        //    setRestaurantId(restaurantId);
         //   Log.e("MenuFragment: ", "id"+restaurantId);
        //}
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        //cartCommunicator.cartResponder(menuItemList.get(position).getId())

        // TODO: this listener will be called if we click on the row and not on the checkbox
        // but if this row is clicked it will fetch the values from adapter
        Log.e("MenuFragment2: ", "parent "+ parent.getAdapter());
        Toast.makeText(
                getActivity(),
                "Clicked " + position,
                Toast.LENGTH_SHORT).show();

        Log.e("MenuFragment2~~~: ", "menuAdapter" + menuAdapter.selectedMenuItems
                + menuAdapter.selectedMenuItemIds + " ___ "
                + menuAdapter.selectedMenuItemQty);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("MenuFragment: ", "Bundle " + outState.toString());
        //TODO: uncomment this to pass on this info for next fragment.. guess
        //outState.putInt("pos", restaurantId);
        // Save the current article selection in case we need to recreate the fragment
       // outState.putInt(ARG_POSITION, mCurrentPosition);
    }

    public ArrayList<Integer> getCartItems(){
        ArrayList<Integer> selectedItems = new ArrayList<Integer>();
        for (Integer item : this.cartItemIds) {
            if (item > -1) {
                selectedItems.add(item);
            }
        }
        Log.e("getMenuId : ", "" + cartItemIds
                + "& selectedItems : " + selectedItems);
        return selectedItems;
    }

    public void setCartItems(List<Integer> ids){
        Log.e("setMenuId:  : ", ""+ ids);
        this.cartItemIds = ids;
    }

    public void setMenuId(List<Boolean> menus) {
        Log.e("setMenuId:", "" + menus);
        this.menus = menus;
    }

    List<Boolean> getMenus(){
        Log.e("setMenuId: -returns : ", ""+menus);
        return this.menus;
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


