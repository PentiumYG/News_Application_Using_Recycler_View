package com.mc2022.template;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NewsFrament extends Fragment {

    private final String TAG = NewsFrament.class.getSimpleName();

    ArrayList<HashMap<String, String>> newsList;
    private boolean isNetworkOK;
    BatteryPowerInfo batteryPowerInfo = new BatteryPowerInfo();


    // the fragment initialization parameters
    private ModelNews modelNews;
    Button mStartService;
    Button mStopService;
    String url;
    ProgressDialog pd;
    ListView list;
    BroadcastReceiver broadcastReceiver;

    //useful variables
    int i=0;
    boolean halt=true;
    int k=0;
    int n=0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelNews = new ModelNews();
        newsList = new ArrayList<>();
        isNetworkOK = NetworkHelper.isNetworkAvailable(getActivity().getBaseContext());
        Log.i("Network:", String.valueOf(isNetworkOK));

    }

    private class getNews extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pd = new ProgressDialog(getActivity());
//            pd.setMessage("Loading...");
//            pd.setCancelable(false);
//            pd.show();
            i=k;

            Collections.reverse(newsList);

            Toast.makeText(getActivity().getApplicationContext(),"Json Data is downloading",Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while(halt && n!=1) {
                url = modelNews.getNewsURL(i);
                HelpHandler handler = new HelpHandler();
                String jsonString = handler.makeServiceCall(url);
                if (jsonString != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);

                        String body = jsonObject.getString("body");
                        String title = jsonObject.getString("title");

                        HashMap<String, String> n = new HashMap<>();
                        n.put("title", title);
                        n.put("body", body);

                        newsList.add(n);


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
                i++;
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
//            if(pd.isShowing()){
//                pd.dismiss();
//            }
            Collections.reverse(newsList);
            ListAdapter adapter = new SimpleAdapter(getActivity().getApplicationContext(), newsList,
                    R.layout.list_view, new String[]{ "title","body"},
                    new int[]{R.id.newsTitle, R.id.newsBody});
            list.setAdapter(adapter);

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter1 = new IntentFilter(Intent.ACTION_BATTERY_OKAY);
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        IntentFilter filter3 = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        IntentFilter filter4 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        getActivity().registerReceiver(batteryPowerInfo, filter1);
        getActivity().registerReceiver(batteryPowerInfo, filter2);
        getActivity().registerReceiver(batteryPowerInfo, filter3);
        getActivity().registerReceiver(batteryPowerInfo, filter4);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(batteryPowerInfo);
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

        IntentFilter filter1 = new IntentFilter(Intent.ACTION_BATTERY_OKAY);
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        IntentFilter filter3 = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        IntentFilter filter4 = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        getActivity().registerReceiver(batteryPowerInfo, filter1);
        Log.i("Battery OK", String.valueOf(filter1.getAction(0)));
        getActivity().registerReceiver(batteryPowerInfo, filter2);
        Log.i("Battery LOW", String.valueOf(filter2.getAction(0)));
        getActivity().registerReceiver(batteryPowerInfo, filter3);
        Log.i("Power Connected", filter3.getAction(0));
        getActivity().registerReceiver(batteryPowerInfo, filter4);
        Log.i("Power Dis-Connected", filter4.getAction(0));


        list = (ListView)v.findViewById(R.id.newsList);

        //starting a service
        mStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity().getBaseContext(), MyService.class);
                halt =true;
                n=0;
                //Toast.makeText(getActivity(), "Toast check", Toast.LENGTH_SHORT).show();

                if(isNetworkOK == true && (filter1.hasAction(Intent.ACTION_BATTERY_OKAY) || filter4.hasAction(Intent.ACTION_POWER_DISCONNECTED))) {
                    new getNews().execute();
                    getActivity().startService(in);
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Internet Connection Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(filter2.hasAction(Intent.ACTION_BATTERY_LOW) || filter3.hasAction(Intent.ACTION_POWER_CONNECTED)) {
            getActivity().stopService(new Intent(getActivity().getBaseContext(), MyService.class));
        }

        //stopping a service
        mStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n=1;
                k=i;
                halt = false;
                //Toast.makeText(getActivity(), "Toast check", Toast.LENGTH_SHORT).show();
                getActivity().stopService(new Intent(getActivity().getBaseContext(), MyService.class));
            }
        });

        return v;
    }



}