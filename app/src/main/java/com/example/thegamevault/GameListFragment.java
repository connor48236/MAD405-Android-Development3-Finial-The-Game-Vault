package com.example.thegamevault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.thegamevault.API.GameSingleton;
import com.example.thegamevault.pojo.CustomGameAdapter;
import com.example.thegamevault.pojo.Game;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GameListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GameListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GameListFragment newInstance(String param1, String param2) {
        GameListFragment fragment = new GameListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game_list, container, false);

        //find the search bar
        SearchView gameSearch = view.findViewById(R.id.gameSearch);

        //When text is entered
        gameSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //Code to execute the search
                String usersQuery = gameSearch.getQuery().toString();
                Log.d("userEntered", usersQuery);
                String url = "https://api.rawg.io/api/games?&search=" + usersQuery + "&key=29c026ab8a7e414fb51447219aaa3397";

                //This code will search for the url entered and pull the json info
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                //will try to loop through the info and pull the info needed
                                try {
                                    ArrayList<Game> games = new ArrayList<>();

                                    JSONArray gamesArray = response.getJSONArray("results");

                                    for (int i = 0; i < gamesArray.length(); i++){
                                        String name = gamesArray.getJSONObject(i).getString("name");
                                        String released = gamesArray.getJSONObject(i).getString("released");
                                        String metacritic = gamesArray.getJSONObject(i).getString("metacritic");
                                        String background_image = gamesArray.getJSONObject(i).getString("background_image");
                                        if (metacritic == "null"){
                                            metacritic = "Not yet Rated";
                                        }
                                        //add a new game when one is found
                                        games.add(new Game(name, released, metacritic, background_image));
                                    }
                                    //Create the recycler view
                                    RecyclerView recyclerView = view.findViewById(R.id.gameListView);
                                    //Set the adapter to the one made
                                    CustomGameAdapter adapter = new CustomGameAdapter(games, getContext());
                                    recyclerView.setAdapter(adapter);
                                    //set the layout manager
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }


                },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("VOLLEY_ERROR", error.getLocalizedMessage());
                            }
                        });
                GameSingleton.getInstance(getContext()).getRequestQueue().add(request);
                return true;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        String url = "https://api.rawg.io/api/games?&search=&key=29c026ab8a7e414fb51447219aaa3397";


        //This code will search for the url entered and pull the json info
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //will try to loop through the info and pull the info needed
                        try {
                            ArrayList<Game> games = new ArrayList<>();

                            JSONArray gamesArray = response.getJSONArray("results");

                            for (int i = 0; i < gamesArray.length(); i++){
                                String name = gamesArray.getJSONObject(i).getString("name");
                                String released = gamesArray.getJSONObject(i).getString("released");
                                String metacritic = gamesArray.getJSONObject(i).getString("metacritic");
                                String background_image = gamesArray.getJSONObject(i).getString("background_image");
                                if (metacritic == "null"){
                                    metacritic = "Not yet Rated";
                                }
                                //add a new game when one is found
                                games.add(new Game(name, released, metacritic, background_image));
                            }
                            //Create the recycler view
                            RecyclerView recyclerView = view.findViewById(R.id.gameListView);
                            //Set the adapter to the one made
                            CustomGameAdapter adapter = new CustomGameAdapter(games, getContext());
                            recyclerView.setAdapter(adapter);
                            //set the layout manager
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY_ERROR", error.getLocalizedMessage());
                    }
                });
        GameSingleton.getInstance(getContext()).getRequestQueue().add(request);




        return view;
    }
}