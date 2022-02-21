package com.mc2022.template;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryPowerInfo extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(Intent.ACTION_BATTERY_LOW.equals(intent.getAction())){
            Toast.makeText(context, "Battery Low", Toast.LENGTH_SHORT).show();
            Log.i("Phone","Battery Low");
        }
//
        if(Intent.ACTION_BATTERY_OKAY.equals(intent.getAction())){
            Toast.makeText(context, "Battery OK", Toast.LENGTH_SHORT).show();
            Log.i("Phone","Battery OK");
        }

        if(Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())){
            Toast.makeText(context, "Power Connected", Toast.LENGTH_SHORT).show();
            Log.i("Phone","Phone is Charging");
        }

        if(Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())){
            Toast.makeText(context, "Power Disconnected", Toast.LENGTH_SHORT).show();
            Log.i("Phone","Power Disconnected");
        }

    }
}
