package com.medi.reminder;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class MyAlertDialog extends AppCompatActivity {
    String medicineName;
    String expirtyDate;

    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_my_alert_dialog);


    if(getIntent()!=null)
    {
        Bundle bundle=getIntent().getExtras();
        if(bundle.containsKey(Constants.MEDICINE_NAME))
        {
            medicineName=bundle.getString(Constants.MEDICINE_NAME);
        }
        else
        {
            medicineName="Medicine Name";
        }
        if(bundle.containsKey(Constants.MEDICINE_EXPIRY_TIME))
        {
            expirtyDate=bundle.getString(Constants.MEDICINE_EXPIRY_TIME);
        }
        else
        {
            expirtyDate="expirty date";
        }
    }
    String alertMessage="Hi! your medicine  "+medicineName.toUpperCase()+" "+"will be expired at "+expirtyDate.toUpperCase();
    AlertDialog.Builder Builder=new AlertDialog.Builder(this)
            .setMessage(alertMessage)
            .setTitle(medicineName.toLowerCase())
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setCancelable(false)
            .setNegativeButton(R.string.OK, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyAlertDialog.this.finish();
                }
            });

    AlertDialog alertDialog=Builder.create();
    alertDialog.show();

}

}