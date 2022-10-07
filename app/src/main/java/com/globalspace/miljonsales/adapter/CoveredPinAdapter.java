package com.globalspace.miljonsales.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.fragment.AddcoveredpincodeFragment;
import com.globalspace.miljonsales.model.Deletepincode;
import com.globalspace.miljonsales.model.getcoveredpincode;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CoveredPinAdapter extends RecyclerView.Adapter<CoveredPinAdapter.CoveredPinVH> {

    private AppCompatActivity activity;
    private ApiInterface apiInterface;
    private Typeface fontawesome;
    private String serverUrlCoveredPin = "" + "covered_pin_area.php";
    private SharedPreferences sPref;
    private SharedPreferences.Editor edit;
    private ProgressDialog progress;
    private String UserId;
    private String pincode_toremove = "";
    private Call<Deletepincode> callprdwisedis;


    public CoveredPinAdapter(AppCompatActivity activity, String UserId) {
        this.activity = activity;
        fontawesome = Typeface.createFromAsset(activity.getAssets(), "fa-solid-900.ttf");
        sPref = activity.getSharedPreferences(activity.getString(R.string.app_name), activity.MODE_PRIVATE);
        edit = sPref.edit();
        this.UserId = UserId;
        progress = new ProgressDialog(activity);
        progress.setMax(100);
        progress.setMessage("Loading.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    @NonNull
    @Override
    public CoveredPinVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pin, viewGroup, false);
        CoveredPinVH vh = new CoveredPinVH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull CoveredPinVH h, final int i) {
        h.pinTv.setText(AddcoveredpincodeFragment.coveredPinArray[i]);
        h.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Internet.checkConnection(activity)){
                    if (AddcoveredpincodeFragment.coveredPinArray.length > 0) {

                        pincode_toremove = AddcoveredpincodeFragment.coveredPinArray[i];
                        if (!pincode_toremove.equals("")) {

                            AddcoveredpincodeFragment.progress.show();
                            deletecoveredpincode(UserId, pincode_toremove);
                        }

                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return AddcoveredpincodeFragment.coveredPinArray.length;
    }

    public class CoveredPinVH extends RecyclerView.ViewHolder {
        private TextView pinTv, deleteIcon;

        public CoveredPinVH(@NonNull View itemView) {
            super(itemView);
            pinTv = (TextView) itemView.findViewById(R.id.pinTv);
            deleteIcon = (TextView) itemView.findViewById(R.id.deleteIcon);
            deleteIcon.setTypeface(fontawesome);
        }
    }


    private void deletecoveredpincode(String emp_id, String pincode_toremove) {

        if (Internet.checkConnection(activity)) {



            apiInterface = ApiUtils.getWalletData();;


            callprdwisedis = apiInterface.deleteaddedcoveredpincode("removeCoveredPin",emp_id,pincode_toremove);


            callprdwisedis.enqueue(new Callback<Deletepincode>() {
                @Override
                public void onResponse(Call<Deletepincode> call, Response<Deletepincode> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("200")) {
                            AddcoveredpincodeFragment.progress.dismiss();
                            if (!response.body().getCoveredPin().equals("")) {

                                AddcoveredpincodeFragment.coveredPinArray =
                                        (response.body().getCoveredPin()).split(",");
                            }else {
                                AddcoveredpincodeFragment.coveredPinArray = new String[0];
                            }

                            notifyDataSetChanged();
                            progress.dismiss();
                            Toast.makeText(activity, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                }

                @Override
                public void onFailure(Call<Deletepincode> call, Throwable t) {
                    if (!call.isCanceled()) {


                    }

                }

            });
        }
    }







/*
    private class LongOperationCoveredPin extends AsyncTask<String, Void, String> {

        private String Error = null;

        String data = "";
        int sizeData = 0;
        String identifier;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            // Start Progress Dialog (Message)

        }


        @Override
        protected String doInBackground(String... params) {
            BufferedReader reader = null;
            String Content = "";
            JSONObject jsonResponse;
            URL url = null;


            try {
                //region send Data
                url = new URL(params[0]);

                if (!params[1].equals(""))
                    data += "&" + URLEncoder.encode("user_id", "UTF-8")
                            + "=" + params[1];


                if (!params[2].equals(""))
                    data += "&" + URLEncoder.encode("delete_pin", "UTF-8") + "="
                            + params[2];


                // Send POST data request

                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter(
                        conn.getOutputStream());
                wr.write(data);
                wr.flush();

                // Get the server response

                reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null) {
                    // Append server response in string
                    sb.append(line + "\n");
                }

                Content = sb.toString();
                //endregion

            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                } catch (Exception ex) {
                }
            }
            return Content;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //region Display Data
            try {
                JSONObject jsonResponse = new JSONObject(s);

                String update = jsonResponse.getString("STATUS");

                if (update.equalsIgnoreCase("1")) {
                    final String message = jsonResponse.getString("MESSAGE");
                    final String CoveredpinArea = jsonResponse.getString("PIN_AREA");
                    edit.putString("covere_pin_area_toshow", CoveredpinArea);
                    edit.commit();
                    AddcoveredpincodeFragment.coveredPinArray = (sPref.getString("covere_pin_area_toshow", "")).split(",");
                    notifyDataSetChanged();
                    progress.dismiss();

                    Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(activity, "Name has not been updated, please try later!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            //endregion
        }
    }
*/

}
