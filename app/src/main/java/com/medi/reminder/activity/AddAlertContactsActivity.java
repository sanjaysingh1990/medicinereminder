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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.medi.reminder.Constants;
import com.medi.reminder.PermissionDenied;
import com.medi.reminder.R;
import com.medi.reminder.adapter.ContactAdapter;
import com.medi.reminder.databinding.ActivityAddAlertContactsBinding;
import com.medi.reminder.login.MyAppUser;
import com.medi.reminder.realm.IMedicineContract;
import com.medi.reminder.realm.model.ContactData;
import com.medi.reminder.realm.model.Medicine;
import com.medi.reminder.realm.presenters.impl.MedicinePresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.RealmResults;


public class AddAlertContactsActivity extends BaseActivity implements IMedicineContract {

    ActivityAddAlertContactsBinding binding;
    private ContactAdapter mAdapter;
    private List<Object> mList;
    private MedicinePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_alert_contacts);
        presenter = new MedicinePresenter(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();
        setListener();
    }

    private void setListener() {
        binding.searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //clear info
                if (charSequence.length() == 0) {
                    binding.textSearchHeading.setText(null);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //handle imeoption
        binding.searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchContact();
                    return true;
                }
                return false;
            }
        });
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
            case R.id.action_search:
                searchContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void searchContact() {
        String number = binding.searchBox.getText().toString();

        if (number.length() > 0) {
            String phoneNo = "+" + binding.ccp.getSelectedCountryCode() + number;
            Log.e("phoneno", phoneNo);
            checkPlayerNum(phoneNo);
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

    public void addContact(MyAppUser myAppUser, int pos) {
        boolean isfound = false;
        for (Object obj : mList) {
            if (obj instanceof ContactData) {
                ContactData contactData = (ContactData) obj;
                if (myAppUser.getUserPhone().compareToIgnoreCase(contactData.getPhoneNo()) == 0) {
                    isfound = true;
                    break;
                }
            }
        }
        if (!isfound) {
            ContactData contactData = new ContactData();
            contactData.setContactName(myAppUser.getUserName());
            contactData.setPhoneNo(myAppUser.getUserPhone());
            contactData.setPhone(false);
            contactData.setSms(false);
            mList.add(contactData);
            mAdapter.notifyDataSetChanged();
            presenter.addContact(contactData);
            showMessage(myAppUser.getUserName() + "," + myAppUser.getUserPhone() + "," + mList.size());
        } else {
            showMessage("Contact already saved!");

        }

        mList.remove(pos);
        mAdapter.notifyItemRemoved(pos);
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

    String progressText = "Hold on looking for....";

    private void showProgress() {

        binding.progressbarSearching.setVisibility(View.VISIBLE);
        binding.textSearchHeading.setVisibility(View.VISIBLE);
        binding.textSearchHeading.setText(progressText);


    }

    private void hideProgress() {
        binding.progressbarSearching.setVisibility(View.INVISIBLE);
        binding.textSearchHeading.setVisibility(View.INVISIBLE);
        binding.textSearchHeading.setText(progressText);


    }

    private void noResultFound() {
        binding.progressbarSearching.setVisibility(View.INVISIBLE);
        binding.textSearchHeading.setVisibility(View.VISIBLE);
        binding.textSearchHeading.setText("No result found!");


    }


    public void removeContact(final int pos) {
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Alert")
                .setContentText("Are you sure want to remove ?")
                .setConfirmText("YES")
                .setCancelText("NO")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        presenter.deleteContactByPosition(pos);
                        mList.remove(pos);
                        mAdapter.notifyItemRemoved(pos);
                    }
                }).show();


    }

    private DatabaseReference mDatabase;

    public void checkPlayerNum(final String phoneNum) {
        showProgress();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        if (!TextUtils.isEmpty(phoneNum)) {
            final Query phoneNumReference = mDatabase.child("users").orderByChild("userPhone").equalTo(phoneNum);

            ValueEventListener phoneNumValueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    hideProgress();
                    // user exists
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                        binding.searchBox.setText(null);

                        MyAppUser myAppUser = dataSnapshot1.getValue(MyAppUser.class);

                        String clubkey = dataSnapshot1.getKey();
                        Log.e("key", dataSnapshot.getChildrenCount() + "" + clubkey);
                        // getUserInf(clubkey);
                        Iterator<Object> it = mList.iterator();
                        while (it.hasNext()) {
                            if (it.next() instanceof MyAppUser) {
                                it.remove();
                            }

                        }

                        mList.add(0, myAppUser);
                        mAdapter.notifyDataSetChanged();
                    }

                    //IF NO RESULT FOUND
                    if (dataSnapshot.getChildrenCount() == 0) {
                        noResultFound();
                    }


                }


                @Override
                public void onCancelled(DatabaseError databaseError) {
                    hideProgress();
                }
            };

            phoneNumReference.addListenerForSingleValueEvent(phoneNumValueEventListener);


        } else {
            Log.e("Error", "phoneNum is null");
        }
    }
}
