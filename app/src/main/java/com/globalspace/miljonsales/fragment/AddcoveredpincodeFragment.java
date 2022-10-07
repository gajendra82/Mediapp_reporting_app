package com.globalspace.miljonsales.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.adapter.CoveredPinAdapter;
import com.globalspace.miljonsales.adapter.getprdlistadapter;
import com.globalspace.miljonsales.model.Addpincode;
import com.globalspace.miljonsales.model.JointWork;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.model.ManufactureInfo;
import com.globalspace.miljonsales.model.discountProductInfo;
import com.globalspace.miljonsales.model.getcoveredpincode;
import com.globalspace.miljonsales.model.productwisediscountprdlist;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddcoveredpincodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddcoveredpincodeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View v;

    EditText et_addcoveredpincode;
    Button btn_add;
    private RecyclerView coveredPinRecycler;
    Button saveAddPinBtn;
    TextInputEditText addCovPinEdt;
    TextInputLayout addCovPinTIL;
    private SharedPreferences sPref;
    private Boolean area;
    public static String[] coveredPinArray;
    String UserId = "";
    private ApiInterface apiInterface;
    public static ProgressDialog progress;
    private Call<getcoveredpincode> callprdwisedis;
    private Call<Addpincode> calladdpin;
    String covered_pinarea = "";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddcoveredpincodeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddcoveredpincodeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddcoveredpincodeFragment newInstance(String param1, String param2) {
        AddcoveredpincodeFragment fragment = new AddcoveredpincodeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_addcoveredpincode, container, false);

        setProgressDialog();

        coveredPinRecycler = (RecyclerView) v.findViewById(R.id.covPinRcv);
        saveAddPinBtn = (Button) v.findViewById(R.id.btn_add_pin);

        addCovPinEdt = (TextInputEditText) v.findViewById(R.id.et_changes);
        addCovPinTIL = (TextInputLayout) v.findViewById(R.id.changeTIL);

        addCovPinEdt.addTextChangedListener(CoveredPinTextWatcher);
        coveredPinRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        apiInterface = ApiUtils.getWalletData();
        sPref = getActivity().getSharedPreferences(getActivity().getResources().getString(R.string.app_name), Context.MODE_PRIVATE);

        //endregion

        UserId = sPref.getString(getResources().getString(R.string.employee_id),"");


        // Inflate the layout for this fragment
        progress.show();
        getcoveredpincode(UserId);


        saveAddPinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!covered_pinarea.equals("")) {
                    progress.show();
                    HideKeyBoard(getActivity());

                    addcoveredpincode(UserId, covered_pinarea);

                }

            }
        });


        return v;


    }

    private TextWatcher CoveredPinTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            validatealtmobno();
        }
    };

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private boolean validatealtmobno() {


        if ((addCovPinEdt.getText().toString().trim().isEmpty())) {


                addCovPinTIL.setError(getString(R.string.err_msg_coverepincode));
                requestFocus(addCovPinEdt);
                return false;



        } else {

             covered_pinarea = addCovPinEdt.getText().toString();
            String arr[] = covered_pinarea.split(",");
            String complete_pin = arr[arr.length - 1];
            String remainder = covered_pinarea.substring(covered_pinarea.lastIndexOf(",") + 1);


            if (!(complete_pin.length() >= 6) || complete_pin.equals("")) {
                area = false;
                addCovPinTIL.setError(getString(R.string.err_msg_coverepincode));
                requestFocus(addCovPinEdt);
                return false;
            } else {
                if (area == false && complete_pin.length() % 6 == 0) {
                    area = true;
                    if (remainder.isEmpty()) {
                        addCovPinEdt.append("");
                        if (covered_pinarea.length() > 0)
                            addCovPinEdt.getText().delete(covered_pinarea.length() - 1, covered_pinarea.length());
                    } else
                        addCovPinEdt.append(",");

                } else if (complete_pin.length() > 6) {
                    covered_pinarea = covered_pinarea.substring(0, covered_pinarea.length() - 1) + ","
                            + covered_pinarea.substring(covered_pinarea.length() - 1, covered_pinarea.length());
                    addCovPinEdt.setText("");
                    addCovPinEdt.setText(covered_pinarea);
                    addCovPinEdt.setSelection(addCovPinEdt.getText().toString().length());
                }

                addCovPinTIL.setErrorEnabled(false);

                return true;

            }

        }
    }

    private void addcoveredpincode(String emp_id, String covered_pinar) {

        if (Internet.checkConnection(getContext())) {



            apiInterface = ApiUtils.getWalletData();;


            calladdpin = apiInterface.Addcoveredpincode("setCoveredPin",emp_id,covered_pinar);


            calladdpin.enqueue(new Callback<Addpincode>() {
                @Override
                public void onResponse(Call<Addpincode> call, Response<Addpincode> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("200")) {

                            if (!response.body().getCoveredPin().equals("")) {

                                AddcoveredpincodeFragment.coveredPinArray =
                                        (response.body().getCoveredPin()).split(",");
                            }else {
                                AddcoveredpincodeFragment.coveredPinArray = new String[0];
                                Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progress.dismiss();
                            addCovPinEdt.setText("");
                            covered_pinarea = "";
                            if (coveredPinArray.length > 0) {
                                coveredPinRecycler.setAdapter(new CoveredPinAdapter((AppCompatActivity) getContext(), UserId));
                            }


                        }else {
                        }
                    }
                }

                @Override
                public void onFailure(Call<Addpincode> call, Throwable t) {
                    if (!call.isCanceled()) {


                    }

                }

            });
        }
    }


    private void getcoveredpincode(String emp_id) {

        if (Internet.checkConnection(getContext())) {



            apiInterface = ApiUtils.getWalletData();;


            callprdwisedis = apiInterface.getaddedcoveredpincode("getCoveredPin",emp_id);


            callprdwisedis.enqueue(new Callback<getcoveredpincode>() {
                @Override
                public void onResponse(Call<getcoveredpincode> call, Response<getcoveredpincode> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("200")) {
                            progress.dismiss();
                            coveredPinArray = (response.body().getCoveredPin()).split(",");

                            if (coveredPinArray.length > 0 && !coveredPinArray[0].equals("")) {
                                coveredPinRecycler.setAdapter(new CoveredPinAdapter((AppCompatActivity) getContext(), UserId));
                            }


                        }
                    }
                }

                @Override
                public void onFailure(Call<getcoveredpincode> call, Throwable t) {
                    if (!call.isCanceled()) {


                    }

                }

            });
        }
    }




    private void setProgressDialog() {
        progress = new ProgressDialog(getActivity());
        progress.setMax(100);
        // progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

    }
    public static void HideKeyBoard(Activity activity)
    {
        InputMethodManager inputMethodManager=(InputMethodManager) activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getWindow().getDecorView().getRootView();

        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}