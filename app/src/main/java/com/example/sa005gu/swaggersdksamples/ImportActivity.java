package com.example.sa005gu.swaggersdksamples;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ImportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import);
        setTitle("Address Validation and Demogrphy");
        final Context context = this;
        Button importButton = (Button) findViewById(R.id.buttonImport);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String API_KEY = context.getString(R.string.API_KEY);
                final String SECRET = context.getString(R.string.SECRET);
                if (API_KEY.isEmpty() || SECRET.isEmpty()) {

                    AlertDialog alertDialog = new AlertDialog.Builder(ImportActivity.this).create();
                    alertDialog.setTitle("API_KEY and SECRET Missing");
                    alertDialog.setMessage("Enter your API_KEY and SECRET in build.gradle file to make this app running");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}