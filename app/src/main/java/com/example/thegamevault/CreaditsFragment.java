package com.example.thegamevault;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreaditsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreaditsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreaditsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreaditsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreaditsFragment newInstance(String param1, String param2) {
        CreaditsFragment fragment = new CreaditsFragment();
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
        View view = inflater.inflate(R.layout.fragment_creadits, container, false);

        //Check for the size of the text
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        final String textSize = preferences.getString("font_size", "20");
        int fontSize = Integer.parseInt(textSize);

        TextView creditsTitle = view.findViewById(R.id.creditsTitle);
        creditsTitle.setTextSize(fontSize);

        TextView creditsDesc = view.findViewById(R.id.creditsDesc);
        creditsDesc.setTextSize(fontSize);


        //Find and allow the user to email
        final Button emailButton = view.findViewById(R.id.emailButton);

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] emailAddresses = {"w0741233@myscc.ca"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emailAddresses);
                intent.putExtra(Intent.EXTRA_SUBJECT, "About the Game Vault app");
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(getView(), "No Email App Found", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //Find and allow the user to call
        final Button phoneButton = view.findViewById(R.id.callButton);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] phoneNumber = {"51947906230000"};
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.fromParts("tel", Arrays.toString(phoneNumber), null));

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(getView(), "No phone App Found", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //Find and allow to text
        final Button textButton = view.findViewById(R.id.textButton);

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] phoneNumber = {"51947906230000"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("sms:"));
                intent.putExtra(Intent.EXTRA_PHONE_NUMBER, phoneNumber);

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Snackbar.make(getView(), "No text App Found", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}