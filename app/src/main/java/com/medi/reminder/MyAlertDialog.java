package com.medi.reminder;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

public class MyAlertDialog extends AppCompatActivity {

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_my_alert_dialog);

    AlertDialog.Builder Builder=new AlertDialog.Builder(this)
            .setMessage("Do You Want continue ?")
            .setTitle("exit")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyAlertDialog.this.finish();
                }
            })
            .setPositiveButton(R.string.no,null);
    AlertDialog alertDialog=Builder.create();
    alertDialog.show();

}

}