package com.mc2022.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


 //getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new NewsFrament()).commit();


        FragmentManager fragMan = getSupportFragmentManager();
        Fragment F = fragMan.findFragmentById(R.id.fragmentContainer);
        if(F == null){
            Log.i("Fragment", "Fragment is null");
            F = new NewsFrament();
            fragMan.beginTransaction().add(R.id.fragmentContainer,F).commit();
        }
        Log.i("Fragment", "After inflating");
    }
}