package com.myproject.expensetacker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Re-schedule the daily alarm here
            Toast.makeText(context, "Boot completed in Expense Tracker", Toast.LENGTH_SHORT).show();
        }
    }
}
