package com.medi.reminder.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.medi.reminder.Constants;
import com.medi.reminder.PermissionDenied;
import com.medi.reminder.R;
import com.medi.reminder.adapter.ContactAdapter;
import com.medi.reminder.databinding.ActivityAddAlertContactsBinding;
import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.RealmResults;


public class AddAlertContactsActivity extends BaseActivity implements IMedicineContract {

    ActivityAddAlertContactsBinding binding;
    private ContactAdapter mAdapter;
    private List<ContactData> mList;
    private MedicinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_alert_contacts);
        presenter = new MedicinePresenter(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
    }


    @Override
    public void onStart() {
        super.onStart();


        presenter.subscribeCallbacks();
        presenter.getAllContacts();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unSubscribeCallbacks();
    }

    private void init() {
        mList = new ArrayList<>();
        mAdapter = new ContactAdapter(this, mList);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.addItemDecoration(dividerItemDecoration);
        binding.recyclerView.setAdapter(mAdapter);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon action bar is clicked; go to parent activity
                this.finish();
                return true;
            case R.id.action_info:
                showInfo();
                return true;
            case R.id.action_add:
                if (checkPermission())
                    getContatcts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void showInfo() {
        new SweetAlertDialog(this, SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Information")
                .setContentText("Add contact will allow you to send sms or phone call to the assigned user in case of alert")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();

                    }
                })
                .show();
    }

    public boolean checkPermission() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((this), new String[]{Manifest.permission.READ_CONTACTS}, Constants.REQUEST_CODE_PICK_CONTACTS);
                return false;
            }
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.REQUEST_CODE_PICK_CONTACTS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getContatcts();
            } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showPermissionDeniedDialog();
                }
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == Constants.REQUEST_CODE_PICK_CONTACTS) && (resultCode == Activity.RESULT_OK)) {

            retrieveContactNumber(data.getData());

        }
    }

    private void getContatcts() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), Constants.REQUEST_CODE_PICK_CONTACTS);

    }

    private void showPermissionDeniedDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new PermissionDenied().show(getSupportFragmentManager(), PermissionDenied.class.getSimpleName());
            }
        }, 100);
    }

    private void retrieveContactNumber(Uri Id) {

        List<String> contactslist = new ArrayList<>();
        String contactID = null;
        // getting contacts ID
        Cursor cursorID = getContentResolver().query(Id, new String[]{ContactsContract.Contacts._ID}, null, null, null);

        if (cursorID.moveToFirst()) {
            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }
        cursorID.close();


        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " +

                        contactID, null, null);

        showMessage("hello");
        if (cursorPhone.moveToFirst()) {

            String contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            String contactName = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String image_thumb = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactID, null, null);
            while (phones.moveToNext()) {
                String number = (phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", ""));
                String name = (phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                if (!contactslist.contains(number)) {
                    String sendname = name;
                    contactslist.add(number);
                }
            }
            phones.close();
            try {
                if (image_thumb != null) {
                    Bitmap bit_thumb = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(image_thumb));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            ContactData contactData = new ContactData();
            contactData.setContactName(contactName);
            contactData.setPhoneNo(contactNumber);
            contactData.setPhone(false);
            contactData.setSms(false);
            mList.add(contactData);
            mAdapter.notifyDataSetChanged();
            presenter.addContact(contactData);
            showMessage(contactNumber + "," + contactName + "," + mList.size());


        }

        cursorPhone.close();

        if (contactslist.size() > 1) {
            showMessage(contactslist.size() + "");
        }
    }


    @Override
    public void showStudents(RealmResults<Medicine> medicines) {
        //nothing to do
    }

    @Override
    public void showContacts(RealmResults<ContactData> contactsList) {
        Log.e("data", "received");
        mList.clear();
        for (int i = 0; i < contactsList.size(); i++) {
            mList.add(contactsList.get(i));
        }
        //no data found show empty screen
        if (mList.size() == 0) {
            //   showEmptyScreen();
        }
        mAdapter.notifyDataSetChanged();
    }
}
