package com.globalspace.miljonsales.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;

import com.globalspace.miljonsales.MyApplication;
import com.globalspace.miljonsales.interface_.di.AppPreference;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.globalspace.miljonsales.R;
import com.globalspace.miljonsales.model.Login_Response;
import com.globalspace.miljonsales.permissions.CommandWrapper;
import com.globalspace.miljonsales.permissions.EnableGpsCommand;
import com.globalspace.miljonsales.retrofit.ApiInterface;
import com.globalspace.miljonsales.retrofit.ApiUtils;
import com.globalspace.miljonsales.utils.Internet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends Activity {

    private TextView versionNo;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int FINE_ACCESS_PERMISSION_CONSTANT = 101;
    private static final int COARSE_ACCESS_PERMISSION_CONSTANT = 102;
    private static final int READ_PHONE_STATE_CONSTANT = 103;
    private static final String TAG = Login.class.getSimpleName();
    private EditText username_et, password_et;
    private CheckBox showPass;
    private Button signIn;
    private String str_username, str_password, token_regId, str_deviceID, function_name, str_userID;
    private SharedPreferences sPref;
    private SharedPreferences.Editor edit;
    private LinearLayout mainLayout;
    private String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\-+[a-z]+\\.+[a-z]+";
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
    private Boolean isPermissionGranted = false;
    private ProgressDialog progress;
    private ApiInterface apiInterface;
    private int mDay, mMonth, mYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContent();
        //region Check show password
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    password_et.setTransformationMethod(null);
                } else {
                    password_et.setTransformationMethod(new PasswordTransformationMethod());
                }

            }
        });
        //endregion

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (username_et.getText().toString().equals("") || password_et.getText().toString().equals("")) {
                    Snackbar snackbar = Snackbar.make(mainLayout, "Please enter login credentials ", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    str_username = username_et.getText().toString().trim();
                    str_password = password_et.getText().toString();

                    if (str_username.matches(emailPattern) || str_username.matches(emailPattern2) &&
                            str_username.length() > 0) {
                        edit.putString(getString(R.string.Username), str_username);
                        edit.putString(getString(R.string.Password), str_password);
                        edit.commit();
                        checkPermissions();
                    } else {
                        Toast.makeText(Login.this, "Invalid Email Id", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
    }

    private void setContent() {

        versionNo = (TextView) findViewById(R.id.tv_version);

        //region version No
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            versionNo.setText("Version " + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //endregion
        username_et = (EditText) findViewById(R.id.et_input_username);
        password_et = (EditText) findViewById(R.id.et_input_password);
        signIn = (Button) findViewById(R.id.btn_login);
        showPass = (CheckBox) findViewById(R.id.showPassword);
        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);

        disableSpecialChar(username_et);
        disableSpecialChar(password_et);

        sPref = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        edit = sPref.edit();
        progress = new ProgressDialog(getApplicationContext());
    }

    //To avoid Block characters
    private void disableSpecialChar(EditText editText) {
        final String blockCharacterSet = "~#^|$%&*!";

        InputFilter filter = new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {

                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };

        editText.setFilters(new InputFilter[]{filter});

    }


    //region Check Permissions
    private boolean EnableGPSIfPossible() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            return true;
        }
        return false;
        /*Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
        intent.putExtra("enabled", true);
        sendBroadcast(intent);*/
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Your GPS seems to be disabled, click ok to enable it?")
                .setCancelable(false)
                .setPositiveButton("OK",
                        new CommandWrapper(new EnableGpsCommand(this)));
               /* .setNegativeButton("No",
                        new CommandWrapper(new CancelCommand(this)));*/

        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void proceedAfterPermission() {
        isPermissionGranted = true;
        //Toast.makeText(ctx, "sucess", Toast.LENGTH_LONG).show();
        /*getSelectedMenu(isPermissionGranted);*/
        if (EnableGPSIfPossible()) {
            // Toast.makeText(ctx, "Enable location and Proceed", Toast.LENGTH_LONG).show();
            buildAlertMessageNoGps();
            Snackbar snackbar = Snackbar
                    .make(mainLayout, "Enable location and Proceed", Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {
            login();
        }

    }

    private void checkPermissions() {

        int fineLocation = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_FINE_LOCATION);
        int readphonestate = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.READ_PHONE_STATE);
        int coarseLocation = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int externalStorage = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int camera = ContextCompat.checkSelfPermission(Login.this, Manifest.permission.CAMERA);

        if (fineLocation == PackageManager.PERMISSION_GRANTED
                && coarseLocation == PackageManager.PERMISSION_GRANTED
                && externalStorage == PackageManager.PERMISSION_GRANTED && readphonestate == PackageManager.PERMISSION_GRANTED) {
            proceedAfterPermission();
        } else {

            if (fineLocation != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You need to give permission to access storage in order to work this feature.");
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            // Show permission request popup
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_ACCESS_PERMISSION_CONSTANT
                            );
                        }
                    });
                    builder.show();
                    Toast.makeText(Login.this, "Fine Location denied", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            FINE_ACCESS_PERMISSION_CONSTANT);
                }
            }
            if (coarseLocation != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    Toast.makeText(Login.this, "Coarse Location denied", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                            COARSE_ACCESS_PERMISSION_CONSTANT);
                }
            }
            if (externalStorage != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You need to give permission to access storage in order to work this feature.");
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            // Show permission request popup
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            EXTERNAL_STORAGE_PERMISSION_CONSTANT);
                }
            }

            if (readphonestate != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(Login.this, Manifest.permission.READ_PHONE_STATE)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("You need to give permission to access phone state in order to work this feature.");
                    builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.setPositiveButton("GIVE PERMISSION", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            // Show permission request popup
                            ActivityCompat.requestPermissions(Login.this,
                                    new String[]{Manifest.permission.READ_PHONE_STATE},
                                    READ_PHONE_STATE_CONSTANT);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(Login.this, new String[]{Manifest.permission.READ_PHONE_STATE},
                            READ_PHONE_STATE_CONSTANT);
                }
            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case FINE_ACCESS_PERMISSION_CONSTANT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    //  Toast.makeText(Login.this, "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();

                } else {

                    //Toast.makeText(Login.this, "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                }
                break;
            case READ_PHONE_STATE_CONSTANT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    //  Toast.makeText(Login.this, "Permission Granted, Now you can access location data.", Toast.LENGTH_LONG).show();

                } else {

                   // Toast.makeText(Login.this, "Permission Denied, You cannot access location data.", Toast.LENGTH_LONG).show();

                }
                break;
            case COARSE_ACCESS_PERMISSION_CONSTANT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    //Toast.makeText(Login.this, "Permission Granted, Now you can coarse location data.", Toast.LENGTH_LONG).show();

                } else {

                    //Toast.makeText(Login.this, "Permission Denied, You cannot coarse location data.", Toast.LENGTH_LONG).show();

                }
                break;
            case EXTERNAL_STORAGE_PERMISSION_CONSTANT:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkPermissions();
                    //Toast.makeText(Login.this, "Permission Granted, Now you can access external storage data.", Toast.LENGTH_LONG).show();

                } else {

                   // Toast.makeText(Login.this, "Permission Denied, You cannot access external storage data.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    //endregion


    private void login() {
        Log.d(TAG, "login");

        if (Internet.checkConnection(getApplicationContext())) {
            //loginbutton.setEnabled(false);

            onLoginSuccess();
        } else {
            // Toast.makeText(context, "Please Check Your Internet Connection !!!", Toast.LENGTH_SHORT).show();
            Snackbar snackbar = Snackbar
                    .make(mainLayout, "No internet connection!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void onLoginSuccess() {
        //showProgressDialog();
        //get value from edittext

        if (!sPref.getString(getString(R.string.Username), "").equals("")) {

            str_username = sPref.getString(getString(R.string.Username), "");
            str_password = sPref.getString(getString(R.string.Password), "");
        } else {
            str_username = username_et.getText().toString().trim();
            str_password = password_et.getText().toString().trim();
        }
        token_regId = "null";
        function_name = "SalesPersonReporting";

        edit.putString(getString(R.string.token_regid), token_regId);
        edit.putString(getString(R.string.function_name), function_name);
        edit.commit();
        apiInterface = ApiUtils.getWalletData();
        LoadData(str_username, str_password, token_regId, function_name);

    }

    private void showProgressDialog() {
        progress = new ProgressDialog(this);
        progress.setMax(100);
        // progress.setTitle("Progress Dialog");
        progress.setMessage("Your Data is loading Please Wait.......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.show();
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
    }

    private void LoadData(String str_username, String str_password, String token_regId, String function_name) {
        showProgressDialog();
        get_IMEI(this);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        if (Internet.checkConnection(Login.this)) {
            Call<Login_Response> call = apiInterface.getWalletdetail(str_username, str_password, token_regId, str_deviceID,
                    function_name, formattedDate);
            call.enqueue(new Callback<Login_Response>() {
                @Override
                public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                    if (response.isSuccessful()) {
                        if (progress.isShowing()) {
                            progress.dismiss();
                            if (response.body().getStatus().equals("200")) {
                                str_userID = response.body().getMEMPLOYEEID();
                                edit.putString(getResources().getString(R.string.employee_id), str_userID);
                                edit.putString(getResources().getString(R.string.employee_name), response.body().getName());
                                edit.putString(getResources().getString(R.string.reporting_manager), response.body().getReportingPersonName());
                                edit.putString(getResources().getString(R.string.designation), response.body().getDesignation());
                                edit.putString(getResources().getString(R.string.subordinate_flag), response.body().getSub_ordinate_flag());
                                edit.putBoolean(getResources().getString(R.string.isLogin), true);
                                edit.commit();

                                Intent i = new Intent(Login.this, Dashboard.class);
                                startActivity(i);
                                Login.this.finish();
                            } else {
                                Toast.makeText(Login.this, "Incorrect Credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        if (progress.isShowing())
                            progress.dismiss();
                        Toast.makeText(Login.this, "Error Occurred! ", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Login_Response> call, Throwable t) {
                    if (progress.isShowing())
                        progress.dismiss();
                    Toast.makeText(Login.this, "Error Occurred! " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No internet connection !", Toast.LENGTH_SHORT).show();
        }
        //region Comment
		
/*
		ApiUtils.getInstance().getWalletdata(str_username, str_password, token_regId, str_deviceID, function_name, new ApiUtils.ILoginCallback() {
			@Override
			public void onSuccessLogin(Login_Response response) {
				if (response != null) {
					if (response.getError().toString().equals("true")) {
						//Toast.makeText(ctx, response.getErrorMessage().toString(), Toast.LENGTH_LONG).show();
						
						Snackbar snackbar = Snackbar
								.make(mainLayout, response.getErrorMessage().toString(), Snackbar.LENGTH_LONG);
						snackbar.show();
						
						
						// sp.edit().putBoolean("chk_login", true).commit();
						progress.dismiss();
					} else {
						try {
							// sp.edit().putBoolean("chk_login", false).commit();
							
							str_userID = response.getMEMPLOYEEID().toString();
							edit.putString("employee_id", str_userID);
							edit.commit();
							
							
							//  response.getCHMIESTINFO() != null ||
							
							if ( response.getCHMIESTINFO().size() > 0) {
								//Chemist_Information = response.getCHMIESTINFO();
							}
							
							//response.getSTOCKIESTINFO() != null ||
							if ( response.getSTOCKIESTINFO().size() > 0) {
								//Stockist_Information = response.getSTOCKIESTINFO();
							}
							Intent i = new Intent(Login.this, Dashboard.class);
							//i.putExtra("Chemist_detail", (Serializable) Chemist_Information);
							// i.putExtra("Stockist_detail", (Serializable) Stockist_Information);
                            */
/*i.putParcelableArrayListExtra("Chemist_detail",
                                    (ArrayList<? extends Parcelable>) Chemist_Information);*//*

							// Now we put the large data into our enum instead of using Intent extras
							//DataHolder.setData(Chemist_Information);
							//DataHolder_stockist.setData(Stockist_Information);
							i.putExtra(Constants.ACTIVITY,"loginactivity");

                            */
/*i.putParcelableArrayListExtra("Stockist_detail",
                                    (ArrayList<? extends Parcelable>) Stockist_Information);*//*

							// i.putExtra("username", str_username);
							startActivity(i);
							finish();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				} else {
					progress.dismiss();
					Snackbar snackbar = Snackbar
							.make(mainLayout, "connection error !!!!!", Snackbar.LENGTH_LONG);
					snackbar.show();
					// Toast.makeText(ctx, "", Toast.LENGTH_LONG).show();
				}
				
			}
			
			@Override
			public void onFailure(String failureMessage) {
				progress.dismiss();
				// Toast.makeText(ctx, failureMessage, Toast.LENGTH_LONG).show();
				Snackbar snackbar = Snackbar
						.make(mainLayout, failureMessage, Snackbar.LENGTH_LONG);
				snackbar.show();
				edit.putBoolean("login", false).commit();
			}
		});
		
*/
        //endregion

    }

    public static String get_IMEI(Context context) {

        String deviceId;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            deviceId = Settings.Secure.getString(
                    context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        } else {
            final TelephonyManager mTelephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (mTelephony.getDeviceId() != null) {
                deviceId = mTelephony.getDeviceId();
            } else {
                deviceId = Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }
        }

        return deviceId;
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.ic_exit_to_app_black_24dp)
                .setTitle("Application Exist")
                .setMessage("Do you want to exit application")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
