package com.globalspace.miljonsales.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.globalspace.miljonsales.utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class lat_long_service extends Service implements LocationListener {
    Context context;
    //Lat_long lat_long_async;
    boolean isGPSEnabled = false;
    Context ctx;

    // Flag for network status
    boolean isNetworkEnabled,isPassiveEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double longitude; // Longitude
    double latitude; // Latitude
    public double longitude_updated; // Longitude
    public double latitudes_updated; // Latitude
    double longitude_updated_fun; // Longitude
    double latitudes_updated_fun; // Latitude

    LocationManager locationManager;
    String Flag = "";

    SharedPreferences pref;
    Editor edit;

    String user_id, sign_in_check, respemail, stratitude, strlongitude = "";

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0; // 20 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 0; // 1 minute


    Geocoder geocoder;
    List<Address> addresses;

    public lat_long_service(Context context2) {
        // TODO Auto-generated constructor stub

        context = context2;
        getLocation(context);
        geocoder = new Geocoder(context, Locale.getDefault());

        // Here 1 represent max location result to returned, by documents it recommended 1 to 5

    }

    public lat_long_service() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        context = this;

      /*  pref = context.getSharedPreferences(Constants.ZOMED_PREF,
                context.MODE_PRIVATE);*/

        pref = context.getSharedPreferences("login", context.MODE_PRIVATE);
        edit = pref.edit();

        sign_in_check = pref.getString("sign_in_check", "");
        if (sign_in_check.equals("1")) {
            //user_id = pref.getString(Constants.USER_ID_SIGNIN, "");
            user_id = pref.getString("employee_id", "");
        } else {

            //user_id = pref.getString(Constants.USER_ID, "");
            user_id = pref.getString("employee_id", "");

        }
        // user_id = "EMP_175";
        getLocation(context);
        return START_STICKY;
    }


    public Location getLocation(Context context) {
       /* pref = context.getSharedPreferences(Constants.ZOMED_PREF,
                context.MODE_PRIVATE);*/
        pref = context.getSharedPreferences(Constants.MILJON_PREF,
                context.MODE_PRIVATE);
        edit = pref.edit();
        //String serverUrl = Constants.urlAll + "save_lat_long.php";
        respemail = pref.getString(Constants.EMAIL_ID, "");
        context = context;
        try {

            locationManager = (LocationManager) context
                    .getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            isPassiveEnabled = locationManager
                    .isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled && !isPassiveEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
//					List<String> providers = locationManager.getProviders(true);
//					Location bestLocation = null;
//					for (String provider : providers) {
//
//
//						Location l = locationManager.getLastKnownLocation(provider);
//						if (l == null) {
//							continue;
//						}
//						if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
//							// Found best last known location: %s", l);
//							bestLocation = l;
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                            stratitude = String.valueOf(latitude);
                            strlongitude = String.valueOf(longitude);
                            Flag = "0";
                            addresses = geocoder.getFromLocation(latitude, longitude, 1);


//							if (String.valueOf(longitude_updated).contains(
//									"0.0")) {

                            try {
                                if(!user_id.equals( "" )) {

                                   /* LongOperation serverRequest = new LongOperation();

                                    serverRequest.execute( serverUrl, user_id,
                                            stratitude, strlongitude, Flag );*/
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            //}

                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {



                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                stratitude = String.valueOf(latitude);
                                strlongitude = String.valueOf(longitude);
                                Flag = "0";


                                if (stratitude.equals(String.valueOf(latitude))) {

                                    try {
                                        if(!user_id.equals( "" )) {
                                            //
                                          /*  LongOperation serverRequest = new LongOperation();

                                            serverRequest.execute( serverUrl, user_id,
                                                    stratitude, strlongitude, Flag );*/
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    }
                }

                {
                    if (location == null) {



                        locationManager.requestLocationUpdates(
                                LocationManager.PASSIVE_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                stratitude = String.valueOf(latitude);
                                strlongitude = String.valueOf(longitude);
                                Flag = "0";

                                if (stratitude.equals(String.valueOf(latitude))) {

                                    try {
                                        if(!user_id.equals( "" )) {
                                            //
                                           /* LongOperation serverRequest = new LongOperation();

                                            serverRequest.execute( serverUrl, user_id,
                                                    stratitude, strlongitude, Flag );*/
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

            // do nothing
        }
        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        return latitude;
    }


    public Location getLocationobject() {
        if (location != null) {
        }

        return location;
    }

    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        return longitude;
    }








    public String getAddress() throws IOException {
        String address="";
        if (location != null) {

            addresses = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
        }
        return address;
    }


    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub

        longitude_updated = location.getLatitude();
        latitudes_updated = location.getLongitude();
      //  String serverUrl = Constants.urlAll + "save_lat_long.php";

        Flag = "0";
        try {
            if(!user_id.equals( "" )) {

              /*  LongOperation serverRequest = new LongOperation();

                serverRequest.execute( serverUrl, user_id, String.valueOf(longitude_updated), String.valueOf(latitudes_updated),
                        Flag );*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }

    public double getupdatedLatitude() {
        if (location != null) {
            latitudes_updated_fun = latitudes_updated;
        }

        return latitudes_updated_fun;
    }

    public double getupdatedLongitude() {
        if (location != null) {
            longitude_updated_fun = longitude_updated;
        }

        return longitude_updated_fun;
    }



    /*public class LongOperation extends AsyncTask<String, Void, String> {

        // Required initialization

        // private final HttpClient Client = new DefaultHttpClient();
        // private Controller aController = null;
        private String Error = null;

        String data = "";
        int sizeData = 0;

        protected void onPreExecute() {
            // NOTE: You can call UI Element here.

            // Start Progress Dialog (Message)

        }

        // Call after onPreExecute method
        protected String doInBackground(String... params) {

            *//************ Make Post Call To Web Server ***********//*
            BufferedReader reader = null;
            String Content = "";
            JSONObject jsonResponse;
            URL url = null;
            // Send data
            try {

                // Defined URL where to send data
                url = new URL(params[0]);

                // Set Request parameter

                if (!params[1].equals(""))
                    data += "&" + URLEncoder.encode("employee_id", "UTF-8")
                            + "=" + params[1].toString();

                if (!params[2].equals(""))
                    data += "&" + URLEncoder.encode("lat", "UTF-8") + "="
                            + params[2].toString();
                if (!params[3].equals(""))
                    data += "&" + URLEncoder.encode("long", "UTF-8") + "="
                            + params[3].toString();
                if (!params[4].equals(""))
                    data += "&" + URLEncoder.encode("flag", "UTF-8") + "="
                            + params[4].toString();

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

                // Append Server Response To Content String
                Content = sb.toString();
                final String data = Content;

                // if (!(Content == "")) {
                // SharedPreferences.Editor editor = pref.edit();
                // editor.putBoolean(Constants.IS_REGISTERED, true);
                // editor.commit();
                //
                // }
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Toast.makeText(getApplicationContext(), data,
                        // Toast.LENGTH_SHORT).show();
                    }
                });

                try {
                    jsonResponse = new JSONObject(Content);
                    String success = jsonResponse.getString("status");
                    if (success.equalsIgnoreCase("success")) {
                        //
                        // // JSONArray jsonMainNode = jsonResponse
                        // // .optJSONArray("status");
                        //
                        // *//*********** Process each JSON Node ************//*
                        //
                        // // int lengthJsonArr = jsonMainNode.length();
                        //
                        // // for (int i = 0; i < lengthJsonArr; i++) {
                        // *//****** Get Object for each JSON node. ***********//*
                        // // JSONObject jsonChildNode = jsonMainNode
                        // // .getJSONObject(i);
                        //
                        // *//******* Fetch node values **********//*
                        // String password = jsonResponse.getString("otp");
                        // String email_id = jsonResponse.getString("email");
                        // String device_id =
                        // jsonResponse.getString("device_id");
                        // String token = jsonResponse.getString("token");
                        // String user_id = jsonResponse.getString("user_id");
                        //
                        // // String password = jsonChildNode.optString("flag")
                        // // .toString();
                        //
                        // edit.putString(Constants.EMAIL_ID, email_id);
                        // edit.commit();
                        //
                        // edit.putString(Constants.PASSWORD, password);
                        // edit.commit();
                        // // String user_id =
                        // jsonChildNode.optString("user_id")
                        // // .toString();
                        // edit.putString(Constants.USER_ID, user_id);
                        // edit.commit();
                        // // String device_id = jsonChildNode.optString(
                        // // "device_id").toString();
                        //
                        // edit.putString(Constants.DEVICE_ID, device_id);
                        // edit.commit();
                        // // String token = jsonChildNode.optString("token")
                        // // .toString();
                        //
                        // edit.putString(Constants.TOKEN, token);
                        // edit.commit();

                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //
            } catch (Exception ex) {
                Error = ex.getMessage();
            } finally {
                try {

                    reader.close();
                }

                catch (Exception ex) {
                }
            }

            *//*****************************************************//*
            return Content;
        }

        protected void onPostExecute(String Result) {
            // NOTE: You can call UI Element here.

            // Close progress dContentialog

            if (Error != null) {
                // Toast.makeText(getBaseContext(), "Error: " + Error,
                // Toast.LENGTH_LONG).show();

            } else {

                // // Show Response Json On Screen (activity)
                // Toast.makeText(getBaseContext(), "Message sent." + Result,
                // Toast.LENGTH_LONG).show();

            }
        }
    }*/

}
