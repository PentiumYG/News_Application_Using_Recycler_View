package com.mc2022.template;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class NewsFrament extends Fragment {

    private String TAG = NewsFrament.class.getSimpleName();

    ArrayList<HashMap<String, String>> newsList;
    private boolean isNetworkOK;

    // the fragment initialization parameters
    private ModelNews modelNews;
    Button mStartService;
    Button mStopService;
    TextView mTitle;
    TextView mBody;
    ImageView mNewsImage;
    String url;
    ProgressDialog pd;
    ListView list;
    HashMap<String, String> news = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelNews = new ModelNews();
        url = modelNews.getNewsURL();
        newsList = new ArrayList<>();

        isNetworkOK = NetworkHelper.isNetworkAvailable(getActivity().getBaseContext());
        Log.i("Network:", String.valueOf(isNetworkOK));


    }

    private class getNews extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(getActivity());
            pd.setMessage("Loading...");
            pd.setCancelable(false);
            pd.show();
            //Toast.makeText(getActivity().getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Handler handler = new Handler();

            String jsonString = handler.makeServiceCall(url);
            if(jsonString != null){
                try {
                    JSONObject jsonObject = new JSONObject(jsonString);
                   // JSONArray news = jsonObject.getJSONArray("news");
                    String body = jsonObject.getString("body");
                    String title = jsonObject.getString("title");



                    news.put("title", title);
                    news.put("body", body);

                    newsList.add(news);

                } catch (JSONException e) {
                    Toast.makeText(getActivity().getApplicationContext(), "Json Parsing Error", Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity().getApplicationContext(),
                                "Server Error",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            if(pd.isShowing()){
                pd.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), newsList,
                    R.layout.list_view, new String[]{ "title","body"},
                    new int[]{R.id.newsTitle, R.id.newsBody});
            list.setAdapter(adapter);

        }
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
//        mTitle = (TextView)v.findViewById(R.id.TextTitle);
//        mTitle.setText(modelNews.getmTextTitle());
//        mBody = (TextView)v.findViewById(R.id.TextBody);
//        mBody.setText(modelNews.getmTextBody());
//        mNewsImage = (ImageView)v.findViewById(R.id.newsImage);

        list = (ListView)v.findViewById(R.id.newsList);

        //starting a service
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity().getBaseContext(), MyService.class);
                //Toast.makeText(getActivity(), "Toast check", Toast.LENGTH_SHORT).show();
                if(isNetworkOK == true) {
                    new getNews().execute();
                    getActivity().startService(in);

                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //stopping a service
        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(), "Toast check", Toast.LENGTH_SHORT).show();
                getActivity().stopService(new Intent(getActivity().getBaseContext(), MyService.class));
            }
        });

        return v;
    }



}