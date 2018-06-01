package com.nvt.androidshimerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity {
    ShimmerFrameLayout shimmerFrameLayout;
    RecyclerView mRecyclerView;
    RecipeListAdapter mAdapter;
    List<Recipe> list = new ArrayList<>();
    private static final String dataUrl ="https://api.androidhive.info/json/shimmer/menu.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAdapter = new RecipeListAdapter(getApplicationContext(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),DividerItemDecoration.HORIZONTAL));
        mRecyclerView.setAdapter(mAdapter);

    }

    private void loadData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(dataUrl, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response == null) {Toast.makeText(getApplicationContext(),"Could not get data",Toast.LENGTH_LONG).show();
                return;}

                    List<Recipe> recipeList = new Gson().fromJson(response.toString(), new TypeToken<List<Recipe>>() {}.getType());
                    list.clear();
                    list.addAll(recipeList);
                    mAdapter.notifyDataSetChanged();
                shimmerFrameLayout.stopShimmerAnimation();
                shimmerFrameLayout.setVisibility(GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MyApplication.getInstance().addToRequestQueue(jsonArrayRequest);

    }

    @Override
    protected void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmerAnimation();
        loadData();
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmerAnimation();
        super.onPause();
    }
}
