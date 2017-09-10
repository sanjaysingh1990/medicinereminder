package com.medi.reminder.login;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;


import com.medi.reminder.R;

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
    private SweetAlertDialog mSweetAlertDialog;
    private SweetAlertDialog mSweetAlertDialog2;

    private Dialog mProgressDialog;

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


    /**
     * Attach fragment with or without addToBackStack
     */
    public void replaceFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse_frag, R.anim.anim_out_reverse_frag);


        transaction.replace(R.id.container, fragment, tag)
                .commit();
    }

    public void addFragment(Fragment fragment) {
        String tag = fragment.getClass().getSimpleName();
        FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.anim_in, R.anim.anim_out, R.anim.anim_in_reverse_frag, R.anim.anim_out_reverse_frag);

        transaction.addToBackStack(tag);
        transaction.add(R.id.container, fragment, tag)
                .commit();
    }

    public void showWarningAlert(final String title1, final String title2, String message1, final String message2, final String confirmtxt1, String confirmtxt2, final ActionPerformed actionPerformed) {
        if (mSweetAlertDialog == null) {
            mSweetAlertDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE);
            mSweetAlertDialog.setCancelable(false);
        }
        else if (mSweetAlertDialog.isShowing()) {
            return;
        }

        mSweetAlertDialog.setTitleText(title1)
                .setContentText(message1)
                .setConfirmText(confirmtxt1)

                .setCancelText(confirmtxt2)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();

                        if (actionPerformed != null) {
                            actionPerformed.actionPerformed();
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

    public void showDoneAlert(String title, String message) {
        if (mSweetAlertDialog2 == null) {
            mSweetAlertDialog2 = new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE);
            mSweetAlertDialog2.setCancelable(false);
        }
        else if (mSweetAlertDialog2.isShowing()) {
            return;
        }
                mSweetAlertDialog2.setTitleText(title)
                .setContentText(message)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                }).show();
    }



}












