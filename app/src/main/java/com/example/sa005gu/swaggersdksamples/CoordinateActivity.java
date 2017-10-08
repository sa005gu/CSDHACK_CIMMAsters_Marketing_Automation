package com.example.sa005gu.swaggersdksamples;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pb.identify.utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONParser;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pb.ApiClient;
import pb.ApiException;
import pb.Configuration;
import pb.locationintelligence.LIAPIGeoLifeServiceApi;
import pb.locationintelligence.LIAPIGeocodeServiceApi;
import pb.locationintelligence.model.Demographics;
import pb.locationintelligence.model.DemographicsThemes;
import pb.locationintelligence.model.GeocodeAddress;
import pb.locationintelligence.model.GeocodeRequest;
import pb.locationintelligence.model.GeocodeServiceResponseList;
import pb.locationintelligence.model.Location;

public class CoordinateActivity extends AppCompatActivity {

    String Addressvalidated = "";
    String Country = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = this;
        setContentView(R.layout.activity_coordinate);
        setTitle("Customer segments");





        new CoordinateActivity.MyAsyncTask().execute();

    }

    private class MyAsyncTask extends AsyncTask {
        @Override
        protected void onPostExecute(Object obj) {
            //super.onPostExecute(resp);
        GeocodeServiceResponseList resp = (GeocodeServiceResponseList)  obj;


            List<location> loclist = new ArrayList<>();

            for (int i = 0; i < resp.getResponses().size(); i++ )

            {
                List<Double> coordinates = resp.getResponses().get(i).getCandidates().get(0).getGeometry().getCoordinates();
                Double Long = coordinates.get(0);
                Double lat = coordinates.get(1);
                location loc = new location(lat, Long);
                loclist.add(loc);
            }

            CoordinateModel CO = new CoordinateModel(loclist,"3");
            Gson g = new Gson();
            String req = g.toJson(CO);
            callLIAPIforMarket(getApplicationContext(),req);

            Button validateButton = (Button) findViewById(R.id.buttonValidate1);
            validateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent=  new Intent(getApplicationContext(), ChartActivity.class);
                    intent.putExtra("Existing", false );
                    startActivity(intent);


                }
            });

        }

        @Override
        protected GeocodeServiceResponseList doInBackground(Object[] params) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            final String  API_KEY = getApplicationContext().getString(R.string.API_KEY);
            final String SECRET = getApplicationContext().getString(R.string.SECRET);

            defaultClient.setoAuthApiKey(API_KEY);
            defaultClient.setoAuthSecret(SECRET);

            final LIAPIGeocodeServiceApi api = new LIAPIGeocodeServiceApi();

            String datapackBundle = "premium";
            GeocodeRequest body = new GeocodeRequest();
            List<GeocodeAddress> addresses = new ArrayList<GeocodeAddress>();
            GeocodeAddress address = new GeocodeAddress();
            address.mainAddressLine("Empire State Building, 350 5th Ave, New York, USA");
            addresses.add(address);
            body.setAddresses(addresses);

            GeocodeServiceResponseList resp = null;

            try {

                resp = api.geocodeBatch(datapackBundle, body);


            } catch (ApiException e) {
                e.printStackTrace();
                Log.d(e.getResponseBody());
                return null;
            }
            return resp;

        }




        @Override
        protected void onPreExecute() {}



    }

    private void callLIAPIforMarket(final Context context, String req) {
        RequestQueue queue = Volley.newRequestQueue(context);

       String url = "https://api-qa.pitneybowes.com/limarketing/v1/getSourceDestination";

        JSONObject jsonBody = null;
        try {

            jsonBody = new JSONObject(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        Gson g = new Gson();
                        JSONArray arr = null;
                        try {
                           arr = response.getJSONArray("deviceLocations");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        ResponseListLIMarketing responselist =  g.fromJson(response.toString(), ResponseListLIMarketing.class);

                        for(int i=0; i< responselist.getResponselist().size(); i++ ) {
                            String devid = responselist.getResponselist().get(i).getDeviceId();


                      /* AlertDialog alertDialog = new AlertDialog.Builder(CoordinateActivity.this).create();
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                        //alertDialog.setMessage(response.toString());
                        alertDialog.setMessage(m_androidId + "-" + devid);

                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();*/


                            LinearLayout llNon = new LinearLayout(context);
                            llNon = (LinearLayout) findViewById(R.id.dataview);

                            LinearLayout l1 = new LinearLayout(context);

                            l1.setLayoutParams(new ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));
                            View v = new View(context);
                            v.setLayoutParams(new LinearLayout.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    5
                            ));
                            v.setBackgroundColor(Color.parseColor("#B3B3B3"));


                            TextView address1 = new TextView(context);

                            address1.setText(devid);
                            address1.setTextSize(18);
                            address1.setTextAppearance(context, android.R.style.TextAppearance_DeviceDefault_Widget_Button);
                            address1.setTextColor(Color.parseColor("#ffffff"));


                            Space sp1 = new Space(context);
                            sp1.setMinimumHeight(50);


                            llNon.addView(v);
                            l1.addView(address1);
                            llNon.addView(sp1);
                            llNon.addView(l1);
                        }
                        String m_androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                        }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AlertDialog alertDialog = new AlertDialog.Builder(CoordinateActivity.this).create();
                // alertDialog.setTitle("API_KEY and SECRET Missing");
                String logText;
                if (volleyError.networkResponse == null) {
                    logText = volleyError.getMessage();
                } else {
                    logText = volleyError.getMessage() + ", status "
                            + volleyError.networkResponse.statusCode
                            + " - " + volleyError.networkResponse.toString();
                }
                alertDialog.setMessage(logText);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        }) {

        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }


}
