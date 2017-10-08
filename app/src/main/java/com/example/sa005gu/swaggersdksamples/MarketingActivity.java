package com.example.sa005gu.swaggersdksamples;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MarketingActivity extends AppCompatActivity {

   String CampaignID = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);
        final Context context = this;
        String Range = getIntent().getStringExtra("range");
        final Boolean exist = getIntent().getBooleanExtra("Existing", true);
        TextView textView = (TextView) findViewById(R.id.text1);
        String x = "";
        if (exist == true) {

             x = "Following Content will be emailed to " + Range + " segment ";

        }
        else
        {
            x = "Following Content will be sent to facebook page of " + Range + " segment ";
        }
        textView.setText(x);




        /* String url = "https://us16.api.mailchimp.com/3.0/lists";
                String req = "{	\"name\": \"listnew11\",	\"contact\": {		\"company\": \"PB\",		\"address1\": \"50 water st\",		\"address2\": \"\",		\"city\": \"lee\",		\"state\": \"ma\",		\"zip\": \"\",		\"country\": \"us\",		\"phone\": \"\"                },	\"permission_reminder\": \"yes\",	\"use_archive_bar\": false,	\"campaign_defaults\": {		\"from_name\": \"san\",		\"from_email\": \"sandhya.gupta@pb.com\",		\"subject\": \"hello All\",		\"language\": \"en\"            },                                                    \"notify_on_subscribe\": \"\",                                                    \"notify_on_unsubscribe\": \"\",                                                    \"email_type_option\": true,                                                    \"visibility\": \"pub\"                                        };                ";
                callAPIs(context, url, req); */

        String memberAddUrl = "https://us16.api.mailchimp.com/3.0/lists/" + "372bbcc92f";
        String listid = "372bbcc92f";
        String req = "{\"members\": [{\"email_address\": \"dharmeshgupta@live.com\", \"status\": \"unsubscribed\"},{\"email_address\": \"mukeshkumar.gupta@pb.com\", \"status\": \"subscribed\"},{\"email_address\": \"dharmesh.gupta@newgen.co.in\", \"status\": \"subscribed\"},{\"email_address\": \"sandhya.gupta@pb.com\", \"status\": \"subscribed\"}], \"update_existing\": true}";
        addMemberAPI(context, memberAddUrl, req, listid);




        Button importButton = (Button) findViewById(R.id.buttonMail);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendUrl = "https://us16.api.mailchimp.com/3.0/campaigns/" + CampaignID + "/actions/send" ;
                String req = " ";
                if(exist == true) {
                    sendCampAPI(context, sendUrl, req);
                }



            }
        });
    }

    private void callAPIs(final Context context, String url, String req) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String API_KEY = "0129a1f2989515a1f9fa1af375e04358-us16";


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
                      /*  AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                        alertDialog.setMessage(response.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show(); */

                        try {
                            String listid = response.getString("id");
                            String memberAddUrl = "https://us16.api.mailchimp.com/3.0/lists/" + "372bbcc92f";
                            //String memberAddUrl = "https://us16.api.mailchimp.com/3.0/lists/" + listid;

                            String req = "{\"members\": [{\"email_address\": \"dharmeshgupta@live.com\", \"status\": \"subscribed\"},{\"email_address\": \"dharmesh.gupta@newgen.co.in\", \"status\": \"subscribed\"},{\"email_address\": \"sandhya.gupta@pb.com\", \"status\": \"subscribed\"}], \"update_existing\": true}";
                            addMemberAPI(context, memberAddUrl, req, listid);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
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
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", "Sandhya", API_KEY).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                map.put("Content-Type", "application/json");
                map.put(key, value);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void addMemberAPI(final Context context, String url, String req, String listid) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String API_KEY = "0129a1f2989515a1f9fa1af375e04358-us16";
        final String id = listid;

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
                       /* AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                        alertDialog.setMessage(response.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();*/


                        String caimpaignurl = "https://us16.api.mailchimp.com/3.0/campaigns";
                        //String campreq = "{\"recipients\":{\"list_id\":\"" + id + "\"},\"type\":\"regular\",\"settings\":{\"subject_line\":\"You are Invited\",\"preview_text\":\"Look at the details\",\"template_id\":49051,\"reply_to\":\"sandhya.gupta@pb.com\",\"from_name\":\"Sandhya\"}}";
                        String campreq = "{\"recipients\":{\"list_id\":\"" + "372bbcc92f" + "\"},\"type\":\"regular\",\"settings\":{\"subject_line\":\"You are Invited\",\"preview_text\":\"Look at the details\",\"template_id\":49051,\"reply_to\":\"sandhya.gupta@pb.com\",\"from_name\":\"Sandhya\"}}";

                        createCampaignAPI(context,caimpaignurl,campreq);



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
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
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", "Sandhya", API_KEY).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                map.put("Content-Type", "application/json");
                map.put(key, value);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void createCampaignAPI(final Context context, String url, String req) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String API_KEY = "0129a1f2989515a1f9fa1af375e04358-us16";


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
                     /*   AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                        alertDialog.setMessage(response.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show(); */

                        try {
                            String campid =response.getString("id");
                            CampaignID = campid;
                            String contentgetUrl = "https://us16.api.mailchimp.com/3.0/campaigns/" + campid + "/content" ;
                            String req = " ";
                            getCampcontentAPI(context,contentgetUrl,req);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
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
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", "Sandhya", API_KEY).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                map.put("Content-Type", "application/json");
                map.put(key, value);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }

    private void sendCampAPI(final Context context, String url, String req) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String API_KEY = "0129a1f2989515a1f9fa1af375e04358-us16";


        JSONObject jsonBody = null;
        try {

            jsonBody = new JSONObject(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                      /*  AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                        alertDialog.setMessage(response.toString());
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();*/

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                String json = null;

                NetworkResponse response = volleyError.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 400:
                            json = new String(response.data);
                            //json = trimMessage(json, "message");
                            //if(json != null) displayMessage(json);
                            break;
                        case 405:
                            json = new String(response.data);
                            //json = trimMessage(json, "message");
                            //if(json != null) displayMessage(json);
                            break;
                    }
                    //Additional cases
                }
                //AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                // alertDialog.setTitle("API_KEY and SECRET Missing");
                String logText;
                if (volleyError.networkResponse == null) {
                    logText = volleyError.getMessage();
                } else {
                    logText = volleyError.getMessage() + ", status "
                            + volleyError.networkResponse.statusCode
                            + " - " + volleyError.networkResponse.toString() + "json -" + json;
                }
               /* alertDialog.setMessage(logText);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show(); */
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", "Sandhya", API_KEY).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                //map.put("Content-Type", "application/json");
                map.put(key, value);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }

    private void getCampcontentAPI(final Context context, String url, String req) {
        RequestQueue queue = Volley.newRequestQueue(context);

        final String API_KEY = "0129a1f2989515a1f9fa1af375e04358-us16";


        JSONObject jsonBody = null;
        try {

            jsonBody = new JSONObject(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }

// Request a string response from the provided URL.
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText("Response is: "+ response.substring(0,500));
                        String content = "";
                        //AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                        try {
                           content = (String)response.get("html");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // alertDialog.setTitle("API_KEY and SECRET Missing");
                       /* alertDialog.setMessage(content);
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show(); */

                        WebView wv = (WebView) findViewById(R.id.webview);
                        final String mimeType = "text/html";
                        final String encoding = "UTF-8";
                        /*String html = "<br /><br />Read the handouts please for tomorrow.<br /><br /><!--homework help homework" +
                                "help help with homework homework assignments elementary school high school middle school" +
                                "// --><font color='#60c000' size='4'><strong>Please!</strong></font>" +
                                "<img src='http://www.homeworknow.com/hwnow/upload/images/tn_star300.gif'  />";*/


                        wv.loadDataWithBaseURL("", content, mimeType, encoding, "");


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {


                String json = null;

                NetworkResponse response = volleyError.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 400:
                            json = new String(response.data);
                            //json = trimMessage(json, "message");
                            //if(json != null) displayMessage(json);
                            break;
                        case 405:
                            json = new String(response.data);
                            //json = trimMessage(json, "message");
                            //if(json != null) displayMessage(json);
                            break;
                    }
                    //Additional cases
                }
               // AlertDialog alertDialog = new AlertDialog.Builder(MarketingActivity.this).create();
                // alertDialog.setTitle("API_KEY and SECRET Missing");
                String logText;
                if (volleyError.networkResponse == null) {
                    logText = volleyError.getMessage();
                } else {
                    logText = volleyError.getMessage() + ", status "
                            + volleyError.networkResponse.statusCode
                            + " - " + volleyError.networkResponse.toString() + "json -" + json;
                }
                /*alertDialog.setMessage(logText);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();*/
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> map = new HashMap<String, String>();
                String key = "Authorization";
                String encodedString = Base64.encodeToString(String.format("%s:%s", "Sandhya", API_KEY).getBytes(), Base64.NO_WRAP);
                String value = String.format("Basic %s", encodedString);
                //map.put("Content-Type", "application/json");
                map.put(key, value);
                return map;
            }
        };

// Add the request to the RequestQueue.
        queue.add(stringRequest);



    }
}
