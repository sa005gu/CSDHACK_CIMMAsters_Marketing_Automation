package com.example.sa005gu.swaggersdksamples;

import com.example.sa005gu.swaggersdksamples.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Space;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;
import com.pb.identify.interfaces.IdentifyServiceManager;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.Address;
import com.pb.identify.interfaces.RequestObserver;
import com.pb.identify.network.ErrorResponse;
import com.pb.identify.utils.Log;
import com.pb.identify.utils.Utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import android.os.Parcelable;
import android.os.Bundle;
import android.widget.ListView;



import java.util.Arrays;

import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import pb.ApiClient;
import pb.Configuration;
import pb.locationintelligence.LIAPIGeoLifeServiceApi;
import pb.locationintelligence.LIAPIGeocodeServiceApi;
import pb.locationintelligence.model.Demographics;
import pb.ApiException;
import pb.locationintelligence.model.DemographicsThemes;

import static android.R.color.white;

public class MainActivity extends AppCompatActivity {

    String address = "";
    String country = "";
    private ListView listView;
    private ItemArrayAdapter itemArrayAdapter;
    private ProgressBar PBar = null;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private RequestObserver validateMailingAddressProCallBack(final List<ValidateMailingAddressProAPIResponse> responses,
                                                              final ErrorResponse[] errorResponses) {
        return new RequestObserver<ValidateMailingAddressProAPIResponseList>() {
            @Override
            public void onSucess(ValidateMailingAddressProAPIResponseList responseList) {

                Gson gson = new GsonBuilder().create();

                PBar.setVisibility(View.VISIBLE);
                String response = gson.toJson(responseList);
                Intent intent=  new Intent(getApplicationContext(), ValidtadActivity.class);
                int countaddress = responseList.getResponses().size();
                int Validatedadd = 0;
                for(int i = 0; i< countaddress; i++)
                {

                   String stat =  responseList.getResponses().get(i).getStatus();
                    if (stat == null)
                    {
                        Validatedadd++;
                    }
                }
                String countValidated = Integer.toString(Validatedadd);
                intent.putExtra("SuccessAddress",countValidated);
                intent.putExtra("Response", response);

                Log.d(response);

                startActivity(intent);
            }

            @Override
            public void onRequestStart() {
                Log.d("Request initiated");
            }

            @Override
            public void onFailure(ErrorResponse e) {
                Log.d("Error: " + e.getRootErrorMessage());
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Something went wrong:" + e.getRootErrorMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        };
    }

  /*  private RequestObserver validateMailingAddressPro1CallBack(final List<ValidateMailingAddressProAPIResponse> responses,
                                                              final ErrorResponse[] errorResponses) {
        return new RequestObserver<ValidateMailingAddressProAPIResponseList>() {
            @Override
            public void onSucess(ValidateMailingAddressProAPIResponseList responseList) {

                Gson gson = new GsonBuilder().create();

                String response = gson.toJson(responseList);
                Intent intent=  new Intent(getApplicationContext(), ResultActivity.class);

                Log.d(response);

                address = responseList.getResponses().get(0).getAddressLine1() + " " + responseList.getResponses().get(0).getCity() + " " + responseList.getResponses().get(0).getStateProvince() + " " + responseList.getResponses().get(0).getPostalCode() ;
                country = responseList.getResponses().get(0).getCountry();

                intent.putExtra("Address",address);
                intent.putExtra("Block_Address",responseList.getResponses().get(0).getBlockAddress());
                intent.putExtra("Country",country);

                startActivity(intent);
            }

            @Override
            public void onRequestStart() {
                Log.d("Request initiated");
            }

            @Override
            public void onFailure(ErrorResponse e) {
                Log.d("Error: " + e.getRootErrorMessage());
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Something went wrong:" + e.getRootErrorMessage());
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        };
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Address Validation and Demogrphy");

        final Context context = this;

        final String API_KEY = context.getString(R.string.API_KEY);
        final String SECRET =   context.getString(R.string.SECRET);


        PBar = (ProgressBar)findViewById(R.id.PBar);
        PBar.setVisibility(View.GONE);

        final IdentifyServiceManager identifyServiceManager = IdentifyServiceManager.getInstance(context, API_KEY, SECRET);

                    //listView = (ListView) findViewById(R.id.listView);
        LinearLayout ll = new LinearLayout(this);
        ll = (LinearLayout)findViewById(R.id.empView);

        CardView cv = new CardView(this);
        cv = (CardView) findViewById(R.id.card_view);

                    //itemArrayAdapter = new ItemArrayAdapter(getApplicationContext(), R.layout.item_layout);

                   // Parcelable state = listView.onSaveInstanceState();
                   // listView.setAdapter(itemArrayAdapter);
                   // listView.onRestoreInstanceState(state);



                    InputStream inputStream = getResources().openRawResource(R.raw.address);
                    CSVFile csvFile = new CSVFile(inputStream);
                    final List<String[]>  addressList = csvFile.read();

                   final ArrayList<Address> addressList1 = new ArrayList<Address>();
                    int count = 0;
                    for(String[] scoreData:addressList ) {
                       // itemArrayAdapter.add(scoreData);
                        String[] add = addressList.get(count);
                        Address address = new Address();
                       address.setAddressLine1(add[0]);
                        address.setAddressLine2(add[1]);
                        address.setCity(add[2]);
                        address.setStateProvince(add[3]);
                        address.setPostalCode(add[4]);
                        address.setCountry(add[5]);
                        addressList1.add(address);
                     final   ArrayList<Address> addressList2 = new ArrayList<Address>();
                        addressList2.add(address);
                       count++;

                        LinearLayout l1 = new LinearLayout(this);

                        l1.setLayoutParams(new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));



                        View v = new View(this);
                        v.setLayoutParams(new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                5
                        ));
                        v.setBackgroundColor(Color.parseColor("#B3B3B3"));

                        ll.addView(v);
                        Space sp = new Space(this);
                        sp.setMinimumHeight(200);
                        l1.addView(sp);
                        TextView address1 = new TextView(this);
                        String displayAddress = add[0] + " " + add[1]+ " " + add[2]+ " " + add[3]+ " " + add[4]+ " " + add[5];
                        address1.setText(displayAddress);
                        address1.setTextSize(18);
                        //address1.setTextAppearance(this, android.R.style.TextAppearance_Large);
                        address1.setTextColor(Color.parseColor("#ffffff"));
                        address1.setClickable(true);
                        l1.addView(address1);
                        Space sp1 = new Space(this);
                        sp1.setMinimumHeight(150);
                        l1.addView(sp1);
                        ll.addView(l1);
                       // cv.addView(l1);
                        address1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                               // final ValidateMailingAddressProAPIResponseList validateMailingAddressResponse = new ValidateMailingAddressProAPIResponseList();
                               // identifyServiceManager.getIdentifyAddressService().validateMailingAddressPro(context, addressList2, null, validateMailingAddressPro1CallBack(validateMailingAddressResponse.getResponses(), null));


                            }
                        });



                    }


        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                TextView tv = (TextView) parent.getAdapter().getItem(position);
                String[] add = addressList.get(position);

                Address address = new Address();
                address.setAddressLine1(add[0]);
                address.setAddressLine2(add[1]);
                address.setCity(add[2]);
                address.setStateProvince(add[3]);
                address.setPostalCode(add[4]);
                address.setCountry(add[5]);
                addressList1.add(address);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Error");
                alertDialog.setMessage("Clicked:");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                final ValidateMailingAddressProAPIResponseList validateMailingAddressResponse = new ValidateMailingAddressProAPIResponseList();
                identifyServiceManager.getIdentifyAddressService().validateMailingAddressPro(context, addressList1, null, validateMailingAddressPro1CallBack(validateMailingAddressResponse.getResponses(), null));

            }
        });

*/
        Button validateButton = (Button) findViewById(R.id.buttonValidate1);
       validateButton.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View v) {

                                                 PBar.setVisibility(View.VISIBLE);

                                                 final ValidateMailingAddressProAPIResponseList validateMailingAddressResponse = new ValidateMailingAddressProAPIResponseList();
                                                 identifyServiceManager.getIdentifyAddressService().validateMailingAddressPro(context, addressList1, null, validateMailingAddressProCallBack(validateMailingAddressResponse.getResponses(), null));


                                             }
                                         });

                         /*    }
            }
        });

        /* Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EditText) findViewById(R.id.textAddressLine1)).setText("");
                ((EditText) findViewById(R.id.textAddressLine2)).setText("");
                ((EditText) findViewById(R.id.textCity)).setText("");
                ((EditText) findViewById(R.id.textCountry)).setText("");
                ((EditText) findViewById(R.id.textState)).setText("");
                ((EditText) findViewById(R.id.textFirmName)).setText("");
                ((EditText) findViewById(R.id.textPostalCode)).setText("");

            }
        });

        */





                // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }



    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}