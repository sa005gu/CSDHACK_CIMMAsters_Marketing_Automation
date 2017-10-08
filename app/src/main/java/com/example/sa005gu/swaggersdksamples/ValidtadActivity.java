package com.example.sa005gu.swaggersdksamples;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponse;
import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;

import java.util.ArrayList;
import java.util.List;

public class ValidtadActivity extends AppCompatActivity {

    private ListView listView;

    private List<String> array= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validtad);
        setTitle("Address Validation");
        String output = getIntent().getStringExtra("Response");
        String Addressvalidated = getIntent().getStringExtra("SuccessAddress");
        TextView textView = (TextView) findViewById(R.id.AddressesValue1);
        textView.setText(Addressvalidated);



        Gson gson = new GsonBuilder().create();
       ValidateMailingAddressProAPIResponseList listResponse = gson.fromJson(output,ValidateMailingAddressProAPIResponseList.class);

        List<ValidateMailingAddressProAPIResponse> responses = listResponse.getResponses();

        int size = responses.size();
        String sizeValue = Integer.toString(size);
        TextView textView1 = (TextView) findViewById(R.id.NoAddressesValue);
        textView1.setText(sizeValue);
        //listView = (ListView) findViewById(R.id.listView);
        LinearLayout lenear = new LinearLayout(this);
        LinearLayout llNon = new LinearLayout(this);
        lenear = (LinearLayout) findViewById(R.id.ValidatedView);
        llNon = (LinearLayout) findViewById(R.id.NonValidatedView);

        for(int i=0; i< responses.size(); i++) {

            final ValidateMailingAddressProAPIResponse resp = responses.get(i);
           final String displayAddress = resp.getAddressLine1() + " " + resp.getCity() + " " + resp.getStateProvince() + "  " + resp.getPostalCode() + " " + resp.getCountry();
            String status = resp.getStatus();




            LinearLayout l1 = new LinearLayout(this);

            l1.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            View v = new View(this);
            v.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    5
            ));
            v.setBackgroundColor(Color.parseColor("#B3B3B3"));


            TextView address1 = new TextView(this);

            address1.setText(displayAddress);
            address1.setTextSize(18);
            address1.setTextAppearance(this, android.R.style.TextAppearance_DeviceDefault_Widget_Button);
            address1.setTextColor(Color.parseColor("#ffffff"));


            Space sp1 = new Space(this);
            sp1.setMinimumHeight(50);


            if (status == null)
            {
                address1.setClickable(true);
                lenear.addView(v);
                l1.addView(address1);
                lenear.addView(sp1);
                lenear.addView(l1);

                address1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        Intent intent=  new Intent(getApplicationContext(), ResultActivity.class);
                        intent.putExtra("Address",displayAddress);
                        intent.putExtra("Block_Address",resp.getBlockAddress());
                        intent.putExtra("Country",resp.getCountry());

                        startActivity(intent);


                    }
                });
            }
            else
            {
                llNon.addView(v);
                l1.addView(address1);
                llNon.addView(sp1);
                llNon.addView(l1);

            }


        }

        Button validateButton = (Button) findViewById(R.id.buttonDemo);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=  new Intent(getApplicationContext(), AudienceActivity.class);


                startActivity(intent);


            }
        });


    }
}
