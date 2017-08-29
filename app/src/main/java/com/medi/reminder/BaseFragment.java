package com.medi.reminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Toast;


import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Bhupinder on 28/3/17.
 * <p>
 * Every Fragment must extend this fragment
 */

public class BaseFragment extends Fragment {

    protected final String TAG = getClass().getSimpleName();
    public View mContent;// For showing snackbar
    private FragmentActivity mActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContent = view;
    }


    public void showMessage(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }

    public void showWarningAlert(final String title1, final String title2, String message1, final String message2, final String confirmtxt1, String confirmtxt2, final ActionPerformed actionPerformed) {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(title1)

                .setContentText(message1)
                .setConfirmText(confirmtxt1)
                .setCancelText(confirmtxt2)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText(title2)
                                .setContentText(message2)
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                }).show();
                        if(actionPerformed!=null)
                        {
                          //  actionPerformed.actionPerformed();
                        }



                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                })
                .show();
    }

}












