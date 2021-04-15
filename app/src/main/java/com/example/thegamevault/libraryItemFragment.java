package com.example.thegamevault;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link libraryItemFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class libraryItemFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";//Name
    private static final String ARG_PARAM2 = "param2";//rating
    public static final String ARG_PARAM3 = "param3";//date
    public static final String ARG_PARAM4 = "param4";//image

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    public libraryItemFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment libraryItemFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static libraryItemFragment newInstance(String param1, String param2, String param3, String param4) {
        libraryItemFragment fragment = new libraryItemFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_library_item, container, false);

        //Check for the size of the text
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String textSize = preferences.getString("font_size", "20");
        int fontSize = Integer.parseInt(textSize);

        //This will find and set the text for the title
        if (mParam1 != null){
            TextView name = view.findViewById(R.id.savedTitle);
            name.setText(mParam1);
            name.setTextSize(fontSize + 10);
        }
        //This will find and set the text for the rating
        if (mParam4 != null){
            TextView rating = view.findViewById(R.id.savedGameRating);
            rating.setText(mParam4);
            rating.setTextSize(fontSize);
        }
        //This will find and set the text for the date
        if (mParam3 != null){
            TextView date = view.findViewById(R.id.savedGameDate);
            date.setText(mParam3);
            date.setTextSize(fontSize);
        }
        //This will find and set the image for the savedImage using Picasso
        if (mParam2 != null){
            ImageView gameImage = view.findViewById(R.id.savedGameImage);
            //gameImage.setImageDrawable(Drawable.createFromPath(mParam2));
            Log.d("testing", mParam2);

            Picasso.get().load(mParam2).resize(800, 800).into(gameImage);


        }

        return view;
    }
}