package com.example.myapplication.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.LogIn;
import com.example.myapplication.Model.Account;
import com.example.myapplication.R;
import com.example.myapplication.SqlHelper;
import com.example.myapplication.databinding.FragmentforgotpassBinding;

import java.util.ArrayList;
import java.util.List;

public class FragmentForgotPass extends Fragment {
SqlHelper sqlHelper;
    List<Account> list;
FragmentforgotpassBinding binding;
    public static FragmentForgotPass newInstance() {
        FragmentForgotPass fragmentForgotPass = new FragmentForgotPass();
        Bundle args = new Bundle();
        fragmentForgotPass.setArguments(args);
        return fragmentForgotPass;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragmentforgotpass,container,false);
       sqlHelper = new SqlHelper(getContext());
        list = new ArrayList<>();
        list = sqlHelper.getAllAccount();
       // final String pass = binding.newpass.getText().toString();
        // String pass =

        binding.edtRePassWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = binding.edtPassWord.getText().toString();
                if(s.toString().equals(password)){
                    binding.layoutedtRepassWord.setError(null);
                }else{
                    binding.layoutedtRepassWord.setError(getString(R.string.check_equal));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = LoginFragmrnt.newInstance();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_fragment_login, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // binding.btnRegister.setText("Thay đổi");
                String phone = binding.edtUserName.getText().toString();
                String password = binding.edtPassWord.getText().toString();
                String repassword = binding.edtRePassWord.getText().toString();

                if(phone.length() <10 && phone.length() >10){
                    Toast.makeText(getContext(),"Enter your phone again!!!", Toast.LENGTH_SHORT).show();
//
                }
                else if ( password.length() > 8 && repassword.equals(password)) {

                    for (int i = 0 ; i<list.size();i++){
                        if (phone.equals(list.get(i).getPhone())){
                            binding.btnRegister.setText(getString(R.string.confirm));
                            binding.layoutCheckCode.setVisibility(View.VISIBLE);
                            binding.btnRegister.setText(getString(R.string.confirm));
                            binding.btnRegister.setText("Change password");
                            if (binding.edtConfirm.getText().toString().length() !=0){
                                binding.btnRegister.setText("Change password");
                                sqlHelper.upDatePass(phone,binding.edtRePassWord.getText().toString());
                                Toast.makeText(getContext(),"successful!!!", Toast.LENGTH_SHORT).show();
                                binding.layoutCheckCode.setVisibility(View.GONE);
                                binding.edtPassWord.setText("");
                                binding.edtRePassWord.setText("");
//
                            }
                            else {
                                binding.btnRegister.setText("enter the code");
                              //  Toast.makeText(getContext(),"enter the code", Toast.LENGTH_SHORT).show();
//
                            }

                        }else {
                            Toast.makeText(getContext(), "phone number is incorrect", Toast.LENGTH_SHORT).show();
                           // binding.btnRegister.setText("");
                        }
                    }
                }else {
                    Toast.makeText(getContext(),"Password length is greater than 8 characters", Toast.LENGTH_SHORT).show();

                }

            }

        });



        return binding.getRoot();
    }
}
