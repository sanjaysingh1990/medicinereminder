package com.medi.reminder;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class ImagePickerDialog extends DialogFragment implements View.OnClickListener,Constants {


    //ImagePickerDialog
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.dialog_image_picker, null);
        view.findViewById(R.id.txt_take_photo).setOnClickListener(this);
        view.findViewById(R.id.txt_choose_photo).setOnClickListener(this);
        view.findViewById(R.id.txt_close).setOnClickListener(this);
        builder.setView(view);
        setCancelable(false);
        return builder.create();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.txt_take_photo:
                takePhoto();
                break;
            case R.id.txt_choose_photo:
                choosePhoto();
                break;
            case R.id.txt_close:
                dismiss();
                break;
        }
    }


    /**
     * Take Photo from the native camera app
     */
    private void takePhoto() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "NEW");
        Uri mCapturedImageURI = getActivity().getContentResolver().
                insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentPicture.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
        getActivity().startActivityForResult(intentPicture, REQUEST_CAMERA);
        dismiss();
    }

    /**
     * Pick a image from the local storage of the device,
     */
    private void choosePhoto() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        getActivity().startActivityForResult(galleryIntent, REQUEST_GALLERY);
        dismiss();
    }

}
