package com.example.werentapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class descfragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String ProductName,Location, Image,Description;
    Double RatePerDay;

    public descfragment() {
        // Required empty public constructor
    }


    public descfragment(String ProductName, String Location, String Image, String Description, Double RatePerDay) {

        this.ProductName = ProductName;
        this.Location = Location;
        this.Image = Image;
        this.Description = Description;
        this.RatePerDay = RatePerDay;

    }


    public static descfragment newInstance(String param1, String param2) {
        descfragment fragment = new descfragment();
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
        View view =  inflater.inflate(R.layout.fragment_descfragment, container, false);

        ImageView imageholder = view.findViewById(R.id.imageholder);
        TextView nameholder = view.findViewById(R.id.nameholder);
        TextView locholder = view.findViewById(R.id.locationholder);
        TextView descholder = view.findViewById(R.id.descriptionholder2);
        TextView ratePDholder = view.findViewById(R.id.ratePerDayholder);

        nameholder.setText(ProductName);
        locholder.setText("Location : " + Location);
        descholder.setText("Description : "+Description);

        ratePDholder.setText("Rate per Day : " + String.valueOf(RatePerDay));

        Glide.with(getContext()).load(Image).into(imageholder);

        return view;
    }
    public void onBackPressed(){

        AppCompatActivity activity=(AppCompatActivity)getContext();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.wrapper,new recfragment()).addToBackStack(null).commit();
    }


}