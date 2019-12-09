package org.yoneco.ict.tithandizane;

import android.annotation.TargetApi;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.yoneco.ict.tithandizane.R;

import java.util.HashMap;
import java.util.Map;

public class ReportAbuse extends AppCompatActivity{
    Spinner districtsSpinner;
    Spinner perpetratorSpinner;
    Spinner abuseTypeSpinner;
    protected LocationManager locationManager;

    Button submitForm;

    String[] districts;
    String[] perpetrators;
    String[] abuseType;

    String selectedDistrict;
    String selectedPerpetrator;
    String selectedAbusetype;
    double latitude;
    double longtude;

    EditText fullName;
    EditText email;
    EditText phoneNumber;
    EditText dateOfAbuse;
    EditText abuseDescription;
    EditText age;
    Switch getLoc;


    public static final String KEY_FULL_NAME = "fullname";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_AGE = "age";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONENUMBER = "phoneNumber";
    public static final String KEY_DATE_OF_ABUSE = "dateOfAbuse";
    public static final String KEY_DISTRICT = "district";
    public static final String KEY_LONGTUDE ="longtude";
    public static final String KEY_LATITUDE ="latitude";
    public static final String KEY_PERPETRATOR = "perpetrator";
    public static final String KEY_TYPE_ABUSE = "abuse";
    public static final String KEY_TYPE_DESCRIPTION = "description";
    private static final String REPORT_URL = "http://helpline.yoneco.org/register.php";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_abuse);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        latitude = getIntent().getDoubleExtra("latitude",0.0);
        longtude = getIntent().getDoubleExtra("longtude",0.0);

        districts    = getResources().getStringArray(R.array.districts);
        perpetrators = getResources().getStringArray(R.array.perpetrators);
        abuseType    = getResources().getStringArray(R.array.abuse_type);

        submitForm   =(Button)findViewById(R.id.report_button);
        fullName     =(EditText)findViewById(R.id.name);
        phoneNumber  =(EditText)findViewById(R.id.phone_number);
        dateOfAbuse  =(EditText)findViewById(R.id.date_of_abuse);
        getLoc       =(Switch)findViewById(R.id.location);

        abuseDescription =(EditText)findViewById(R.id.abuse_description);
        email =(EditText)findViewById(R.id.email);
        age =(EditText)findViewById(R.id.age);


        districtsSpinner   =(Spinner)findViewById(R.id.district_spinner);
        perpetratorSpinner =(Spinner)findViewById(R.id.perpetrator_spinner);
        abuseTypeSpinner   =(Spinner)findViewById(R.id.abuse_type_spinner);

        ArrayAdapter<String> districtsAdapter   = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,districts);
        ArrayAdapter<String> perpetratorAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,perpetrators);
        ArrayAdapter<String> abuseTypeAdapter   = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,abuseType);

        districtsSpinner.setAdapter(districtsAdapter);
        perpetratorSpinner.setAdapter(perpetratorAdapter);
        abuseTypeSpinner.setAdapter(abuseTypeAdapter);

        districtsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = position+1;
                selectedDistrict = String.valueOf(pos);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        perpetratorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = position+1;
                selectedPerpetrator = String.valueOf(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        abuseTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int pos = position+1;
                selectedAbusetype = String.valueOf(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getLoc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    Toast.makeText(ReportAbuse.this," latitude : "+ latitude +" longtude:"+ longtude, Toast.LENGTH_SHORT).show();

                }
            }
        });
        submitForm.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                reportAbuse(selectedDistrict, selectedPerpetrator, selectedAbusetype);
            }
        });
    }

    public void reportAbuse(final String districtSelect,final  String perpetratorSelect,final String abuseTypeSelect ){
        final String full_name    = fullName.getText().toString().trim();
        final String phone_number = phoneNumber.getText().toString().trim();
        final String age_         = age.getText().toString().trim();
        final String date_ofAbuse = dateOfAbuse.getText().toString().trim();
        final String email_       = email.getText().toString().trim();
        // not entered
        final String abuse_description = abuseDescription.getText().toString().trim();
        ////
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ReportAbuse.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ReportAbuse.this,"Please check your internet connection!",Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_FULL_NAME,full_name);
                params.put(KEY_PHONENUMBER,phone_number);
                params.put(KEY_AGE,age_);
                params.put(KEY_LONGTUDE,String.valueOf(longtude));
                params.put(KEY_LATITUDE,String.valueOf(latitude));
                params.put(KEY_DATE_OF_ABUSE,date_ofAbuse);
                params.put(KEY_DISTRICT,districtSelect);
                params.put(KEY_PERPETRATOR,perpetratorSelect);
                params.put(KEY_TYPE_ABUSE,abuseTypeSelect);
                params.put(KEY_TYPE_DESCRIPTION,abuse_description);
                params.put(KEY_EMAIL, email_);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
