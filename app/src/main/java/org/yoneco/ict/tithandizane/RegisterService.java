package org.yoneco.ict.tithandizane;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.yoneco.ict.tithandizane.Class.ServiceOrg;

import com.yoneco.ict.tithandizane.R;

public class RegisterService extends AppCompatActivity {

    EditText orgname;
    EditText orgemail;
    EditText orgphonenumber;
    EditText orgdescription;
    EditText focalperson;
    EditText focalpersonNumber;

    Button registerOrg;
    SharedPreferences orgProfile;

    public final  static String orgName="ORGNAME";
    public final static String orgEmail="ORGEMAIL";
    public final static String orgPhoneNumber="ORGPHONUMBER";
    public final static String focalPerson ="FOCALPERSON";
    public final static String focalPersonNumber="FOCALPERSONNUMBER";
    public final static String description ="DESCRIPTION";
    public final static String orgpref ="orgpref";

    public  static String registerValue ="registerValue";

    private double latitude;
    private double longtude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orgname =(EditText)findViewById(R.id.org_name);
        orgemail =(EditText)findViewById(R.id.org_email);
        orgphonenumber =(EditText)findViewById(R.id.org_number);
        orgdescription =(EditText)findViewById(R.id.org_descript);
        focalperson =(EditText)findViewById(R.id.focal_name);
        focalpersonNumber =(EditText)findViewById(R.id.focal_person_num);
        orgProfile = getSharedPreferences(orgpref, Context.MODE_PRIVATE);


        latitude = getIntent().getDoubleExtra("latitude",0.0);
        longtude = getIntent().getDoubleExtra("longtude", 0.0);


        registerOrg =(Button)findViewById(R.id.register_org);
        registerOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orgnameIn    =orgname.getText().toString();
                String orgphoneIn   =orgphonenumber.getText().toString();
                String orgemailIn   =orgemail.getText().toString();
                String orgdescriptIn =orgdescription.getText().toString();
                String fpersonIn     =focalperson.getText().toString();
                String fpersonumIN   =focalpersonNumber.getText().toString();
                    if(!orgnameIn.isEmpty() || !orgphoneIn.isEmpty() || !orgemailIn.isEmpty() || !orgdescriptIn.isEmpty()) {

                        SharedPreferences.Editor editor = orgProfile.edit();

                        editor.putString(orgName, orgname.getText().toString());
                        editor.putString(orgEmail, orgemail.getText().toString());
                        editor.putString(orgPhoneNumber, orgphonenumber.getText().toString());
                        editor.putString(description, orgdescription.getText().toString());
                        editor.putString(focalPerson, focalperson.getText().toString());
                        editor.putString(focalPersonNumber, focalpersonNumber.getText().toString());
                        editor.putString("lat", String.valueOf(latitude));
                        editor.putString("long", String.valueOf(longtude));
                        editor.apply();

                        String orgName = orgProfile.getString(RegisterService.orgName, "");
                        String orgPhone = orgProfile.getString(RegisterService.orgPhoneNumber, "");
                        String orgEmail = orgProfile.getString(RegisterService.orgEmail, "");
                        String fPerson = orgProfile.getString(RegisterService.focalPerson, "");
                        String fNumber = orgProfile.getString(RegisterService.focalPersonNumber, "");
                        String orgDescript = orgProfile.getString(RegisterService.description, "");
                        String lat = orgProfile.getString("lat", "");
                        String lon = orgProfile.getString("long", "");

                        String data[] = {orgName, orgPhone, orgEmail, orgDescript, fPerson, fNumber, lat, lon};
                        ServiceOrg serviceOrg = new ServiceOrg(data);
                        serviceOrg.registerService(RegisterService.this);
                        if (ServiceOrg.registered) {
                            orgProfile = getSharedPreferences(orgpref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = orgProfile.edit();
                            editor2.putInt(registerValue, 1);
                            editor2.apply();
                            Toast.makeText(RegisterService.this, "Organization successfuly registered", Toast.LENGTH_SHORT).show();
                        } else {
                            orgProfile = getSharedPreferences(orgpref, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor2 = orgProfile.edit();
                            editor2.putInt(registerValue, 0);
                            editor2.apply();
                            Toast.makeText(RegisterService.this, "Organization pending registration", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(RegisterService.this, "All fields are required.", Toast.LENGTH_LONG).show();
                    }
            }
        });

    }

}

