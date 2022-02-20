package com.mc2022.template;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NewsFrament extends Fragment {

    // the fragment initialization parameters
    private ModelNews modelNews;
    Button mStartService;
    Button mStopService;
    TextView mTitle;
    TextView mBody;
    ImageView mNewsImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelNews = new ModelNews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news, container, false);
        Log.i("Fragment", "before edited");
        mStartService = (Button)v.findViewById(R.id.buttonStartService);
        mStartService.setText(modelNews.getmStartS());
        mStopService = (Button)v.findViewById(R.id.buttonStopService);
        mStopService.setText(modelNews.getmStopS());
        mTitle = (TextView)v.findViewById(R.id.TextTitle);
        mTitle.setText(modelNews.getmTextTitle());
        mBody = (TextView)v.findViewById(R.id.TextBody);
        mBody.setText(modelNews.getmTextBody());
        mNewsImage = (ImageView)v.findViewById(R.id.newsImage);

        //starting a service
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startService(new Intent(getContext(), MyService.class));
            }
        });

        //stopping a service
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().stopService(new Intent(getContext(), MyService.class));
            }
        });


        return v;
    }



}