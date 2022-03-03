package com.mc2022.template;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class NewsFrament extends Fragment{

   // ArrayList<HashMap<String, String>> newsList;
    private boolean isNetworkOK;

    //Creating ArrayList of ModelNews type
    ArrayList<ModelNews> newsdataholder = new ArrayList<>();

    // the fragment initialization parameters
    Button mStartService;
    Button mStopService;
    String url;
    ProgressDialog pd;
   // ListView list;

    //useful variables
    int i=0;
    boolean halt=true;
    int k=0;
    int n=0;

    //creating object for recycler view
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  modelNews = new ModelNews();
      //  newsList = new ArrayList<>();

        isNetworkOK = NetworkHelper.isNetworkAvailable(getActivity().getBaseContext());
        Log.i("Network:", String.valueOf(isNetworkOK));

    }

    private class getNews extends AsyncTask<Void, Void, Void> implements RecyclerViewInterface{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(getActivity());
//            pd.setMessage("Loading...");
//            pd.setCancelable(false);
//            pd.show();
            i=k;

            Collections.reverse(newsdataholder);

            Toast.makeText(getActivity().getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(halt && n!=1) {
                url = "https://petwear.in/mc2022/news/news_"+i+".json";
                HelpHandler handler = new HelpHandler();
                String jsonString = handler.makeServiceCall(url);
                if (jsonString != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                       // JSONArray news = jsonObject.getJSONArray("news");

//                        String body = jsonObject.getString("body");
//                        String title = jsonObject.getString("title");
//                        String newsNumber = String.valueOf(i);
//                        String imgUrl = jsonObject.getString("image-url");

                        ModelNews m = new ModelNews(
                                jsonObject.getString("title"),
                                jsonObject.getString("body"),
                                jsonObject.getString("image-url"),
                                String.valueOf(i));


                        newsdataholder.add(m);

                        String text4 = newsdataholder.get(i).getmTextTitle();
                        System.out.println(i+" :"+text4);


                    } catch (JSONException e) {
                        halt = false;
                        // Toast.makeText(getActivity(), "Json Parsing Error", Toast.LENGTH_SHORT).show();
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Json parsing error: " + e.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (Exception ex) {
                        halt = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Exception error: " + ex.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Error er) {
                        halt = false;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Error found: " + er.getMessage().toString(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } else {
                    halt = false;
                    //Toast.makeText(getActivity().getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity().getApplicationContext(),
                                    "Server Error",
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                i++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
//            if(pd.isShowing()){
//                pd.dismiss();
//            }
            Collections.reverse(newsdataholder);

            adapter = new AdapterClass((RecyclerViewInterface) this, newsdataholder, getContext());
            recyclerView.setAdapter(adapter);
        }

        @Override
        public void onItemClick(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("title", newsdataholder.get(position).getmTextTitle());
            bundle.putString("body", newsdataholder.get(position).getmTextBody());
            bundle.putString("image", newsdataholder.get(position).getmImageUrl());


            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.findFragmentById(R.id.fragmentContainer);
            Fragment df = new DescriptionFragment();
            df.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer,df).addToBackStack(null).commit();
            Log.i("Description Fragment", "After inflating dec frag");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news, container, false);

        //fetching recycleView
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerViewNews);
        recyclerView.setHasFixedSize(true);

        Log.i("Fragment", "before edited");
        mStartService = (Button)v.findViewById(R.id.buttonStartService);
        mStartService.setText("Start Service");
        mStopService = (Button)v.findViewById(R.id.buttonStopService);
        mStopService.setText("Stop Service");

        //Adding recycler view layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       // list = (ListView)v.findViewById(R.id.newsList);

//        if(isNetworkOK == true) {
//            new getNews().execute();
//        }



       // starting a service
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity().getBaseContext(), MyService.class);
                halt =true;
                n=0;
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
                n=1;
                k=i+1;
                halt = false;
                //Toast.makeText(getActivity(), "Toast check", Toast.LENGTH_SHORT).show();
                getActivity().stopService(new Intent(getActivity().getBaseContext(), MyService.class));
            }
        });

        return v;
    }



}