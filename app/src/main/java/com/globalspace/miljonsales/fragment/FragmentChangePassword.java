package com.globalspace.miljonsales.fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class FragmentChangePassword extends Fragment {

    private View v;
    private EditText oldPass, newPass, confirmPass;
    private CheckBox showPass;
    private Button resetBtn;
    private SharedPreferences sPref;
    private SharedPreferences.Editor edit;
    private ProgressDialog progress;
    private ApiInterface api;

    public FragmentChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_change_password, container, false);
        setComponents();

        //region Hide/Show Password
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    oldPass.setTransformationMethod(null);
                    newPass.setTransformationMethod(null);
                    confirmPass.setTransformationMethod(null);
                } else {
                    oldPass.setTransformationMethod(new PasswordTransformationMethod());
                    newPass.setTransformationMethod(new PasswordTransformationMethod());
                    confirmPass.setTransformationMethod(new PasswordTransformationMethod());
                }

            }
        });
        //endregion

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!sPref.getString(getString(R.string.Password), "").equals(oldPass.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "Incorrect Old Password", Toast.LENGTH_SHORT).show();
                } else if (!newPass.getText().toString().trim().equals(confirmPass.getText().toString().trim())) {
                    Toast.makeText(getActivity(), "New Password and Confirm Password does not match", Toast.LENGTH_SHORT).show();
                } else {
                    //Call Change Password Api
                    ChangePassword("setChangePassword",oldPass.getText().toString().trim(),newPass.getText().toString().trim(),
                            sPref.getString(getActivity().getResources().getString(R.string.employee_id), ""));
                }
            }
        });

        return v;
    }

    private void setComponents() {
        sPref = getActivity().getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        edit = sPref.edit();
        oldPass = (EditText) v.findViewById(R.id.et_old_password_login);
        newPass = (EditText) v.findViewById(R.id.et_new_password_login);
        confirmPass = (EditText) v.findViewById(R.id.et_conf_password_login);
        showPass = (CheckBox) v.findViewById(R.id.showPassword);
        resetBtn = (Button) v.findViewById(R.id.resetBtn);
        progress = new ProgressDialog(getActivity());
    }


    private void ChangePassword(String function_name, final String oldPassword, final String newPassword, String userId) {
        if (Internet.checkConnection(getActivity())) {
            api = ApiUtils.getDashboardData();
            Call<Login_Response> call = api.changePassword(function_name,userId, oldPassword, newPassword);
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {

                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                    if (response.body().getStatus().equals("200")) {
                        edit.putString(getString(R.string.Password), newPassword);
                        edit.commit();
                        oldPass.setText("");
                        newPass.setText("");
                        confirmPass.setText("");
                       Fragment fragment = new FragmentAnalytics();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    Toast.makeText(getActivity(),"Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}
