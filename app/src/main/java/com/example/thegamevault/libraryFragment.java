package com.example.thegamevault;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.thegamevault.pojo.Game;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link libraryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class libraryFragment extends Fragment {

    ViewPager2 libraryViewPager;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public libraryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment libraryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static libraryFragment newInstance(String param1, String param2) {
        libraryFragment fragment = new libraryFragment();
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
        View view = inflater.inflate(R.layout.fragment_library, container, false);
        //Finds and localizes the view pager
        libraryViewPager = view.findViewById(R.id.libraryViewPager);
        //sets the adapter
        libraryViewPager.setAdapter(new CustomViewPager2Adapter(getActivity()));
        //Sets transformer
        libraryViewPager.setPageTransformer(new DepthTransformer());

        ArrayList<Game> games = new ArrayList<>();


        return view;
    }



    //Class for adapter
    private class CustomViewPager2Adapter extends FragmentStateAdapter implements View.OnClickListener {

        public CustomViewPager2Adapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            //Opens the database and pulls all the games
            GameDatabase db = new GameDatabase(getContext());
            ArrayList<Game> games = db.getAllGames();
            db.close();

            libraryItemFragment frag = libraryItemFragment.newInstance("No Title saved", "No rating saved", "No date Saved", "No image saved");
            ArrayList<libraryItemFragment> itemFrag = new ArrayList<>();

            //Will run through all the games all pull the ones needed and set the items to the fragment
            for (int i = 0; i < games.size(); i++) {
                Game game = games.get(i);
                itemFrag.add(libraryItemFragment.newInstance(game.getName(), game.getRating(), game.getReleased(), game.getImage()));
            }


            return itemFrag.get(position);

        }


        @Override
        public int getItemCount() {
            //Gets the amount of games in the array
            GameDatabase db = new GameDatabase(getContext());
            ArrayList<Game> games = db.getAllGames();
            db.close();
            return games.size();
        }

        @Override
        public void onClick(View view) {

            ArrayList<Game>games = new ArrayList<>();

            new AlertDialog.Builder(getContext()).setTitle("Delete").setMessage("Are you sure you would like to delete this game?").setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            GameDatabase db = new GameDatabase(getContext());
                            db.deleteGame(games.get(getItemCount()).getId());
                            games.remove(getItemCount());
                            notifyItemRemoved(getItemCount());
                            db.close();
                        }
                    })
                    .setNegativeButton("No", null).show();
        }

    }


    public class DepthTransformer implements ViewPager2.PageTransformer{
        private static final float MIN_SCALE = 0.75f;
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();

                if (position < -1) {
                    // This page is way off-screen to the left.
                    view.setAlpha(0f);

                } else if (position <= 0) {
                    // Use the default slide transition when moving to the left page
                    view.setAlpha(1f);
                    view.setTranslationX(0f);
                    view.setTranslationZ(0f);
                    view.setScaleX(1f);
                    view.setScaleY(1f);

                } else if (position <= 1) {
                    // Fade the page out.
                    view.setAlpha(1 - position);

                    // Counteract the default slide transition
                    view.setTranslationX(pageWidth * -position);
                    // Move it behind the left page
                    view.setTranslationZ(-1f);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                } else {
                    // This page is way off-screen to the right.
                    view.setAlpha(0f);
                }
        }
    }

}