package com.example.visualcovid_19;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.StringRequest;


public class CountryCardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static TextView countryName;
    private static TextView totalCases;
    private static ImageButton star;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CountryName = "Country Name";
    private static final String CasesCount = "99999";

    // TODO: Rename and change types of parameters
    private String mCountryName;
    private String mCasesCount;
    private boolean isStarred;


    // Saving into database SQLiteHelper
    private DatabaseHandler db;

    public CountryCardFragment(String name, String count) {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CountryCardFragment newInstance(String name, String count, boolean starredFlag) {
        CountryCardFragment fragment = new CountryCardFragment(name, count);
        Bundle args = new Bundle();
        args.putString(CountryName, name);
        args.putString(CasesCount, count);
        args.putBoolean("isStarred",starredFlag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mCountryName = getArguments().getString(CountryName);
            mCasesCount = getArguments().getString(CasesCount);
            isStarred = getArguments().getBoolean("isStarred");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.country_card_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        countryName = view.findViewById(R.id.countryName);
        totalCases = view.findViewById(R.id.totalCases);
        star = view.findViewById(R.id.btnOFF);

        if(isStarred){

            star.setImageResource(android.R.drawable.btn_star_big_on);
        }


        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageView imageView = (ImageView) v;
                String tag =  (String) imageView.getTag();

                if(tag ==null){
                    imageView.setImageResource(android.R.drawable.btn_star_big_on);
                    imageView.setTag("on");
                    ScrollingActivity.db.addStarredCounties(mCountryName);
                }else{
                    if(tag == "on"){
                        imageView.setImageResource(android.R.drawable.btn_star_big_off);
                        imageView.setTag("off");
                        ScrollingActivity.db.deleteStarredCountry(mCountryName);
                    }else{
                        imageView.setImageResource(android.R.drawable.btn_star_big_on);
                        imageView.setTag("on");
                        ScrollingActivity.db.addStarredCounties(mCountryName);
                    }

                }
            }
        });
        countryName.setText(mCountryName);
        totalCases.setText(mCasesCount);
    }
}