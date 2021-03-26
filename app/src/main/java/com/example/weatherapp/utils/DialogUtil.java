package com.example.weatherapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.example.weatherapp.R;
import com.example.weatherapp.activity.MainActivity;

import static com.example.weatherapp.activity.MainActivity.LOCATION_REQUEST_CODE;

public class DialogUtil {

    private final Activity activity;

    public DialogUtil(Activity activity) {
        this.activity = activity;
    }

    public AlertDialog showEnableGPSDialog() {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.alert_message_enable_gps)
                .setPositiveButton(activity.getString(R.string.button_title_close), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialog.dismiss();
                    }
                }).create();
    }

    public AlertDialog showMessageRationale() {
        return new AlertDialog.Builder(activity)
                .setMessage(R.string.rationale_message)
                .setPositiveButton(activity.getString(R.string.button_title_allow), new DialogInterface.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    }
                })
                .setNegativeButton(activity.getString(R.string.button_title_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
