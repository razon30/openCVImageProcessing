package com.prince.thesis.utils;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import org.opencv.cultoftheunicorn.marvel.R;


/**
 * Created by razon30 on 31-05-17.
 */

public class ShowNetworkError {

    public ShowNetworkError(final Activity activity) {

        AlertDialog.Builder builderAlertDialog = new AlertDialog.Builder(activity);

        builderAlertDialog.setTitle("No Internet Connection")
                .setMessage("Try for connecting?")
                .setIcon(R.drawable.ic_warning_black_24dp)
                .setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        activity.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));

                    }
                })
                .setNegativeButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();

    }
}
