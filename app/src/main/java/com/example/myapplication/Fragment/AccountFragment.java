package com.example.myapplication.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;


import com.example.myapplication.Internet;
import com.example.myapplication.LogIn;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentAccountBinding;

import static android.content.Context.MODE_PRIVATE;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_ADDRESS;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_FULL_NAME;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_PHONE;
import static com.example.myapplication.Model.AccountAttribute.ACCOUNT_STATUS;
import static com.example.myapplication.Model.AccountAttribute.SHARE_PRE_NAME;


public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;

    public static AccountFragment newInstance() {

        Bundle args = new Bundle();

        AccountFragment fragment = new AccountFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        if (getStatus()) {
            SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
            String fullname = sharedPreferences.getString(ACCOUNT_FULL_NAME, "");
            String phone = sharedPreferences.getString(ACCOUNT_PHONE, "");
            String address = sharedPreferences.getString(ACCOUNT_ADDRESS, "");
            //
            binding.avatar.setVisibility(View.VISIBLE);
            binding.layoutPic.setVisibility(View.VISIBLE);
            binding.layoutAddress.setVisibility(View.VISIBLE);
            binding.layoutPhone.setVisibility(View.VISIBLE);
            binding.isLogin.setVisibility(View.GONE);
            //
            binding.accName.setText(fullname);
            binding.phoneNumber.setText(phone);
            binding.address.setText(address);
            binding.btnLoginAndLogout.setText(getString(R.string.sign_out));
        } else {
            binding.btnLoginAndLogout.setText(getString(R.string.sign_in));
        }
        binding.btnLoginAndLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Internet.checkConnection(getContext())) {
                    if (getStatus()) {
                        binding.avatar.setVisibility(View.GONE);
                        binding.layoutPic.setVisibility(View.GONE);
                        binding.layoutAddress.setVisibility(View.GONE);
                        binding.layoutPhone.setVisibility(View.GONE);
                        binding.isLogin.setVisibility(View.VISIBLE);
                        binding.btnLoginAndLogout.setText(getString(R.string.sign_in));
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(ACCOUNT_STATUS, false);
                        editor.apply();
                        Toast.makeText(getContext(), getString(R.string.logout_success), Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getContext(), LogIn.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getContext(), getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

                }

            }
        });
        return binding.getRoot();
    }

    public boolean getStatus() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARE_PRE_NAME, MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean(ACCOUNT_STATUS, false);
        return check;
    }

}
