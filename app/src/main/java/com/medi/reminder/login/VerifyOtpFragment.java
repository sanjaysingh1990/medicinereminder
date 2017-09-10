package com.medi.reminder.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.processbutton.iml.ActionProcessButton;
import com.medi.reminder.R;
import com.medi.reminder.databinding.LoginFragmentBinding;
import com.medi.reminder.databinding.VerifyOtpFragmentBinding;

/**
 *
 */
public class VerifyOtpFragment extends BaseFragment {

    private VerifyOtpFragmentBinding binding;
    private boolean isPasswordShown;

    /**
     * Create a new instance of the fragment
     */
    public static VerifyOtpFragment newInstance() {
        VerifyOtpFragment fragment = new VerifyOtpFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.verify_otp_fragment, container, false));
        binding.setClickEvent(new ClickHandler());
        init();
        return binding.getRoot();

    }

    void init() {
        binding.btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);


    }

    private boolean validateFields() {
        String email = binding.edittextOtp.getText().toString();
        if (email.length() == 0) {
            binding.edittextOtp.requestFocus();
            binding.edittextOtp.setError(getString(R.string.otp_field_empty));
            return false;
        }


        return true;
    }


    public class ClickHandler {

        public void verifyotp(View view) {
            if (validateFields()) {
                binding.btnSignIn.setProgress(10);
                PhoneAuthActivity phoneAuthActivity = (PhoneAuthActivity) getActivity();
                phoneAuthActivity.verifyOtpCode(binding.edittextOtp.getText().toString().trim());

            }
        }

        public void resendOtp(View view) {
            binding.btnSignIn.setProgress(10);
            PhoneAuthActivity phoneAuthActivity = (PhoneAuthActivity) getActivity();
            phoneAuthActivity.resendCode();


        }


    }
    public void reset()
    {
        binding.btnSignIn.setProgress(100);
        binding.btnSignIn.setProgress(0);
        binding.edittextOtp.setError(null);
    }

}




