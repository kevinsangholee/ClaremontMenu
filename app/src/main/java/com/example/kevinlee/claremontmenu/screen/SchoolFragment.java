package com.example.kevinlee.claremontmenu.screen;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kevinlee.claremontmenu.R;
import com.example.kevinlee.claremontmenu.adapters.FoodAdapter;
import com.example.kevinlee.claremontmenu.adapters.SchoolAdapter;
import com.example.kevinlee.claremontmenu.data.Food;
import com.example.kevinlee.claremontmenu.data.loaders.AddFoodLoader;
import com.example.kevinlee.claremontmenu.data.loaders.GetMealLoader;
import com.example.kevinlee.claremontmenu.data.network.DBConfig;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SchoolFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Food>> {

    private static final int ADD_FOODS_LOADER = 0;
    private static final int GET_FOODS_LOADER = 1;

    private int school_id;
    private int meal;
    private ArrayList<Food> foodList = new ArrayList<Food>();
    private ListView listView;
    private FoodAdapter adapter;

    private LoaderManager.LoaderCallbacks<String> AddFoodLoaderListener =
            new LoaderManager.LoaderCallbacks<String>() {
                @Override
                public Loader<String> onCreateLoader(int id, Bundle args) {
                    return new AddFoodLoader(getActivity(), school_id);
                }

                @Override
                public void onLoadFinished(Loader<String> loader, String data) {
                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLoaderReset(Loader<String> loader) {
                }
            };

    public SchoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_school, container, false);

        Bundle b = getArguments();
        school_id = b.getInt(SchoolAdapter.DINING_HALL_KEY, -1);
        meal = b.getInt(SchoolAdapter.MEAL_KEY, -1);
        Log.i("School id for this frag", String.valueOf(school_id));

//        getLoaderManager().initLoader(ADD_FOODS_LOADER, null, AddFoodLoaderListener);
        startLoader(GET_FOODS_LOADER);

        listView = (ListView) rootView.findViewById(R.id.food_list);
        adapter = new FoodAdapter(getActivity(), new ArrayList<Food>());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Food current = adapter.getItem(position);
            Bundle b = new Bundle();
            b.putInt(DBConfig.KEY_FOOD_ID, current.getId());
            b.putString(DBConfig.KEY_FOOD_NAME, current.getName());
            b.putInt(DBConfig.KEY_FOOD_SCHOOL, current.getSchool());
            b.putInt(DBConfig.KEY_FOOD_REVIEW_COUNT, current.getReview_count());
            b.putDouble(DBConfig.KEY_RATING, current.getRating());
            b.putString(DBConfig.KEY_FOOD_IMAGE, current.getImage());
            Intent intent = new Intent(getActivity(), ReviewActivity.class);
            intent.putExtras(b);
            startActivity(intent);
            }
        });

        return rootView;
    }

    @Override
    public Loader<ArrayList<Food>> onCreateLoader(int id, Bundle args) {
        return new GetMealLoader(getActivity(), school_id, meal);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Food>> loader, ArrayList<Food> data) {
        adapter.clear();
        adapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Food>> loader) {
        adapter.clear();
    }

    public void startLoader(int type) {
        getLoaderManager().initLoader(type, null, this);
    }
}
