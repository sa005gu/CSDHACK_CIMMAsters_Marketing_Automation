package com.example.sa005gu.swaggersdksamples;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.pb.identify.identifyAddress.validateMailingAddressPro.model.ValidateMailingAddressProAPIResponseList;

public class AudienceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience);

        Button validateButton = (Button) findViewById(R.id.buttonValidate1);
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=  new Intent(getApplicationContext(), ChartActivity.class);
                startActivity(intent);


            }
        });

        Button validateButton2 = (Button) findViewById(R.id.buttonValidate2);
        validateButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=  new Intent(getApplicationContext(), CoordinateActivity.class);
                startActivity(intent);


            }
        });
    }
}
