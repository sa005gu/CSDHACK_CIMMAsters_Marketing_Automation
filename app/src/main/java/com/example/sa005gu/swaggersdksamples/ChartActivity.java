package com.example.sa005gu.swaggersdksamples;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.BoolRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        final Context context = this;

        final Boolean exist = getIntent().getBooleanExtra("Existing", true);
        //BarChart chart = (BarChart) findViewById(R.id.chart);
        //BarChart chart1 = (BarChart) findViewById(R.id.chart1);

        final PieChart chart = (PieChart) findViewById(R.id.chart);
        final PieChart chart1 = (PieChart) findViewById(R.id.chart1);
        setTitle("Demographic by Age and Income");

        PieData data = new PieData(getXAxisValues(1), getDataSet(1));
        //data.setValueTextColor(Color.RED);
        data.setValueTextSize(18);

        PieData data1 = new PieData(getXAxisValues(2), getDataSet(2));
        data1.setValueTextSize(18);

        //BarData data = new BarData(getXAxisValues(1), getDataSet(1));
        // BarData data1 = new BarData(getXAxisValues(2), getDataSet(2));


        chart.setData(data);
        //chart.setEntryLabelColor(Color.BLUE);
        //chart.setDescriptionPosition(400,400);
        chart.setDescriptionTextSize(20);
        chart.setDescription("Segment by Age");
        chart.animateXY(2000, 2000);
        chart.invalidate();
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                final String val  = chart.getXValue((e.getXIndex()));


               /* AlertDialog alertDialog = new AlertDialog.Builder(ChartActivity.this).create();
                // alertDialog.setTitle("API_KEY and SECRET Missing");
                alertDialog.setMessage(String.valueOf(e.getXIndex()) + val );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/

                Intent intent = new Intent(getApplicationContext(), MarketingActivity.class);
                intent.putExtra("range",val);
                intent.putExtra("Existing", exist);
                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });

        chart1.setData(data1);
        chart1.setDescriptionTextSize(20);
        chart1.setDescription("Segment by Income");
        chart1.animateXY(2000, 2000);
        chart1.invalidate();
        chart1.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                //final String val  = getXAxisValues(5).get(e.getXIndex());
                final String val  = chart1.getXValue((e.getXIndex()));

               /* AlertDialog alertDialog = new AlertDialog.Builder(ChartActivity.this).create();
                // alertDialog.setTitle("API_KEY and SECRET Missing");
                alertDialog.setMessage(String.valueOf(e.getXIndex()) + val );
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/

                Intent intent = new Intent(getApplicationContext(), MarketingActivity.class);
                intent.putExtra("range",val);
                intent.putExtra("Existing", exist);

                startActivity(intent);
            }

            @Override
            public void onNothingSelected() {

            }
        });



    }

    private IPieDataSet getDataSet(int val) {
        IPieDataSet dataSets = null;

        ArrayList<Entry> valueSet1 = new ArrayList<>();

        Entry v1e1 = new Entry(100, 0); // Jan

        valueSet1.add(v1e1);
        Entry v1e2 = new Entry(20, 1); // Feb
        valueSet1.add(v1e2);
        Entry v1e3 = new Entry(50, 2); // Mar
        valueSet1.add(v1e3);
        Entry v1e4 = new Entry(60, 3); // Apr
        valueSet1.add(v1e4);
        Entry v1e5 = new Entry(80, 4); // May
        valueSet1.add(v1e5);


        ArrayList<Entry> valueSet2 = new ArrayList<>();
        Entry v2e1 = new Entry(150, 0); // Jan
        valueSet2.add(v2e1);
        Entry v2e2 = new Entry(90, 1); // Feb
        valueSet2.add(v2e2);
        Entry v2e3 = new Entry(120, 2); // Mar
        valueSet2.add(v2e3);
        Entry v2e4 = new Entry(600, 3); // Apr
        valueSet2.add(v2e4);
        Entry v2e5 = new Entry(200, 4); // May
        valueSet2.add(v2e5);


        PieDataSet pieDataSet2 = new PieDataSet(valueSet2, "Age Groups");
        pieDataSet2.setValueTextSize(18);
        pieDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);

        PieDataSet pieDataSet1 = new PieDataSet(valueSet1, "Income in thousand $");
        pieDataSet1.setColors(ColorTemplate.COLORFUL_COLORS);


        //dataSets = new ArrayList<>();

        if (val == 1) {

            return pieDataSet2;
        } else {

            return pieDataSet1;
        }

    }

    private ArrayList<String> getXAxisValues(int val) {
        ArrayList<String> xAxis = new ArrayList<>();


        if (val == 1) {

            xAxis.add("20-24");
            xAxis.add("25-29");
            xAxis.add("30-34");
            xAxis.add("35-39");
            xAxis.add("40-44");
        } else {

            xAxis.add("40-59");
            xAxis.add("60-79");
            xAxis.add("80-99");
            xAxis.add("100-119");
            xAxis.add(">120");
        }

        return xAxis;
    }




}



