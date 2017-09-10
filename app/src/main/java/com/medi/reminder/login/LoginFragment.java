package com.medi.reminder.login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dd.processbutton.iml.ActionProcessButton;
import com.medi.reminder.Constants;
import com.medi.reminder.R;
import com.medi.reminder.databinding.LoginFragmentBinding;
import com.medi.reminder.utils.Utils;

/**
 *
 */
public class LoginFragment extends BaseFragment {

    private LoginFragmentBinding binding;
    private boolean isPasswordShown;

    /**
     * Create a new instance of the fragment
     */
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.bind(inflater.inflate(R.layout.login_fragment, container, false));
        binding.setClickEvent(new ClickHandler());
        init();
        return binding.getRoot();

    }

    void init() {
        binding.btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);


    }

    private boolean validateFields() {
        String email = binding.edittextPhone.getText().toString();
        if (email.length() == 0) {
            binding.edittextPhone.requestFocus();
            binding.edittextPhone.setError(getString(R.string.empty_phone_no));
            return false;
        }


        return true;
    }


    public class ClickHandler {

        public void signIn(View view) {
            if (validateFields()) {
                String phoneNo=binding.edittextPhone.getText().toString();
                binding.btnSignIn.setProgress(10);
                PhoneAuthActivity phoneAuthActivity = (PhoneAuthActivity) getActivity();
                phoneAuthActivity.verifyNumber(phoneNo);

            }
        }


    }
    public void reset()
    {
        binding.btnSignIn.setProgress(100);
        binding.btnSignIn.setProgress(0);
        binding.edittextPhone.setError(null);
    }

}




