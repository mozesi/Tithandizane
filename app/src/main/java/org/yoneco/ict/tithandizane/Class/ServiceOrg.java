package org.yoneco.ict.tithandizane.Class;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Moses on 10/24/2017.
 */
public class ServiceOrg {
    private String orgName;
    private String orgPhone;
    private String orgDescription;
    private String focalPerson;
    private String focalNumber;
    private String orgEmail;
    private String longtude;
    private String latitude;
    private String category;

    SharedPreferences orgProfile;
    public static String responseF;
    public static boolean registered =false;

    private static final String REPORT_URL = "http://helpline.yoneco.org/registerOrganisations";

    public ServiceOrg(String [] data){
        orgName =data[0];
        orgPhone =data[1];
        orgEmail =data[2];
        orgDescription =data[3];
        focalPerson =data[4];
        focalNumber =data[5];
        latitude = data[6];
        longtude = data[7];

    }
    public String registerService(final Context context){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, REPORT_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            ServiceOrg.registered =true;

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error){
                            ServiceOrg.registered =false;
                            //Toast.makeText(this, "Organization successfuly registered", Toast.LENGTH_SHORT).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("NAME",orgName);
                    params.put("ORGPHONE",orgPhone);
                    params.put("ORGEMAIL",orgEmail);
                    params.put("ORGDESCRIPT",orgDescription);
                    params.put("FOCALPERSON",focalPerson);
                    params.put("FOCALNUMBER",focalNumber);
                    params.put("LONGTUDE",latitude);
                    params.put("LATITUDE",longtude);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);

        return responseF;
    }
}

