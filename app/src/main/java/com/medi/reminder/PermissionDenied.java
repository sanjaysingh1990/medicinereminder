package com.medi.reminder;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Lakhwinder Singh{lsingh@openkey.io} on 26/4/17.
 * <p>
 * Image picker dialog from bottom
 */

public class PermissionDenied extends DialogFragment implements View.OnClickListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_permission_denied, null);
        view.findViewById(R.id.txt_ok).setOnClickListener(this);
        view.findViewById(R.id.txt_close).setOnClickListener(this);
        builder.setView(view);
        setCancelable(false);
        return builder.create();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.txt_ok) {
            enablePermission();
        }
        dismiss();
    }


    /*  if required permissions are denied start the setting activity
  so that user can manually allow the permissions*/

    public void enablePermission() {
        String packageName = getActivity().getPackageName();
        try {
            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + packageName));
            getActivity().startActivityForResult(intent, Constants.FETCH_LOCATION);

        } catch (ActivityNotFoundException e) {
            //e.printStackTrace();
            //Open the generic Apps page:
            Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
            getActivity().startActivityForResult(intent, Constants.FETCH_LOCATION);

        }
    }

}
