package org.yoneco.ict.tithandizane;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDelegate;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.yoneco.ict.tithandizane.Class.HttpHandler;
import org.yoneco.ict.tithandizane.Class.ServiceOrg;

import com.yoneco.ict.tithandizane.BuildConfig;
import com.yoneco.ict.tithandizane.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LocationListener {
    ImageView gbvCall;
    ImageView helplineCall;
    ImageView drugSubstance;
    ImageView panicButton;
    String url = "http://helpline.yoneco.org/messages";
    private static final String REPORT_URL = "http://helpline.yoneco.org/register.php";

    List<Map<String, String>> contactList = new ArrayList<Map<String, String>>();
    ProgressBar progressbar;

    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_LONGTUDE = "longtude";

    double longtude;
    double latitude;

    ListView messageList;

    LinearLayout reportChildAbuse;
    LinearLayout serviceProviders;
    LinearLayout sexualReproductiveHealth;
    LinearLayout iwfMw;
    LinearLayout tips;
    LinearLayout registerService;
    LinearLayout serviceDirectory;
    LinearLayout registerUser;

    TextView profile;

    String phone1;
    String phone2;
    String phone3;

    SharedPreferences userDetails;
    SharedPreferences orgprofile;
    public final static String NAME = "name";

    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //
        gbvCall = (ImageView) findViewById(R.id.gbv);
        helplineCall = (ImageView) findViewById(R.id.chilgHelpline);
        drugSubstance = (ImageView) findViewById(R.id.drugSubstance);
        messageList = (ListView) findViewById(R.id.message_list);
        progressbar = (ProgressBar) findViewById(R.id.progress_message);
        panicButton = (ImageView) findViewById(R.id.panic_button);

        reportChildAbuse = (LinearLayout) findViewById(R.id.report_abuse);
        serviceProviders = (LinearLayout) findViewById(R.id.service_providers);
        // sexualReproductiveHealth = (LinearLayout) findViewById(R.id.srh);
        iwfMw = (LinearLayout) findViewById(R.id.iwfMw);

        registerService = (LinearLayout) findViewById(R.id.register_service);
        // tips = (LinearLayout) findViewById(R.id.tips);
        serviceDirectory = (LinearLayout) findViewById(R.id.service_directory);
        registerUser = (LinearLayout) findViewById(R.id.register_user);
        profile = (TextView) findViewById(R.id.profile);

        //change the user profile handler on the menu
        userDetails = getSharedPreferences(RegisterUser.mypref, Context.MODE_PRIVATE);
        if (userDetails.contains(RegisterUser.PHONE2)) {
            phone1 = userDetails.getString(RegisterUser.PHONE2, "");
        }
        if (userDetails.contains(RegisterUser.PHONE3)) {
            phone2 = userDetails.getString(RegisterUser.PHONE3, "");
        }
        if (userDetails.contains(RegisterUser.PHONE4)) {
            phone3 = userDetails.getString(RegisterUser.PHONE4, "");
        }

            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /////////////////////////////////////////////////////
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an expanation to the user
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2, 1, this);
        }
/////////////////////////////////////////////////////

        panicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.SEND_SMS)) {
                        // Show an expanation to the user
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS},1);
                    }
                } else {
                    SendMessageAlert();
                }
                //////

            }
        });
        serviceDirectory.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                Intent serviceDirectory = new Intent(MainActivity.this,ServiceDirectory.class );
                startActivity(serviceDirectory);
            }
        });
        serviceProviders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent serviceProviders = new Intent(MainActivity.this, MapsService.class);
                startActivity(serviceProviders);
            }
        });
        iwfMw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iwfMwIntent = new Intent(MainActivity.this, RssWebView.class);
                iwfMwIntent.putExtra("url", "https://report.iwf.org.uk/mw/");
                startActivity(iwfMwIntent);
            }
        });
        registerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerService = new Intent(MainActivity.this,RegisterService.class);
                registerService.putExtra("latitude",latitude);
                registerService.putExtra("longtude",longtude);
                startActivity(registerService);
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerUser = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(registerUser);
            }
        });
        reportChildAbuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reportAbuse = new Intent(MainActivity.this, ReportAbuse.class);
                reportAbuse.putExtra("latitude",latitude);
                reportAbuse.putExtra("longtude",longtude);
                startActivity(reportAbuse);
            }
        });
        gbvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent gbvcallIntent = new Intent(Intent.ACTION_CALL);
                gbvcallIntent.setData(Uri.parse("tel:5600"));
                /////////////////////////////////////////////////////
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {
                        // Show an expanation to the user
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                } else {
                    startActivity(gbvcallIntent);
                }


            }
        });
        helplineCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent helplineIntent = new Intent(Intent.ACTION_CALL);
                helplineIntent.setData(Uri.parse("tel:116"));
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {
                        // Show an expanation to the user
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                } else {
                    startActivity(helplineIntent);
                }

            }
        });
        drugSubstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent drugcallIntent = new Intent(Intent.ACTION_CALL);
                drugcallIntent.setData(Uri.parse("tel:6600"));
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.CALL_PHONE)) {
                        // Show an expanation to the user
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                    }
                } else {
                    startActivity(drugcallIntent);
                }

            }
        });

        new GetMessages().execute();
        orgprofile = getSharedPreferences(RegisterService.orgpref,Context.MODE_PRIVATE);
        if(orgprofile.contains(RegisterService.orgName) ||  orgprofile.getString(RegisterService.orgName,"000").equals("000")){
           if(orgprofile.getInt("registerValue", 0) == 0) {
               String orgName = orgprofile.getString(RegisterService.orgName, "");
               String orgPhone = orgprofile.getString(RegisterService.orgPhoneNumber, "");
               String orgEmail = orgprofile.getString(RegisterService.orgEmail, "");
               String fPerson = orgprofile.getString(RegisterService.focalPerson, "");
               String fNumber = orgprofile.getString(RegisterService.focalPersonNumber, "");
               String orgDescript = orgprofile.getString(RegisterService.description, "");
               String lat = orgprofile.getString("lat", "");
               String lon = orgprofile.getString("long", "");

               String data[] = {orgName, orgPhone, orgEmail, orgDescript, fPerson, fNumber, lat, lon};
               ServiceOrg serviceOrg = new ServiceOrg(data);
               serviceOrg.registerService(MainActivity.this);

               //if saved change the reg pointer value to 1
               SharedPreferences.Editor editor = orgprofile.edit();
               if(ServiceOrg.registered) {
                   editor.putInt("registerValue", 1);
                   editor.apply();
               }else{
                   editor.putInt("registerValue", 0);
                   editor.apply();
               }

               Toast.makeText(MainActivity.this, String.valueOf(orgprofile.getInt("registerValue", 0)), Toast.LENGTH_SHORT).show();
           }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_about) {
            Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(aboutIntent);
        } else if (id == R.id.nav_share) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Helpline services");
            String shareMessage= "\nLet me recommend you this application\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        }else if(id == R.id.nav_contact){
            Intent contactUS = new Intent(Intent.ACTION_SENDTO,Uri.parse("mailto:ict@yoneco.org"));
            contactUS.putExtra(Intent.EXTRA_SUBJECT,"Tithandizane Helpline mobile Application");
            startActivity(contactUS);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longtude = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private class GetMessages extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            //Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("messages");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                         Log.d("total count",""+contacts.length());
                        String date = c.getString("date");
                        String message = c.getString("message");


                        // tmp hash map for single contact
                        HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                        contact.put("date", date);
                        contact.put("message", message);

                        //Log.d("message",message);
                        // adding contact to contact list
                       contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.d("error",e.getMessage());
                   // Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                //Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Failed to load messages!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            progressbar.setVisibility(View.GONE);
            //final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.list_item, contactList);
            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), contactList,
                    R.layout.list_black,
                    new String[] {"date", "message"},
                    new int[] {R.id.date,
                            R.id.message});
            messageList.setAdapter(adapter);
        }

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, \n do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void SendMessageAlert() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("An SOS will be sent to YONECO.\nDo you wish to continue?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        SmsManager smsManager = SmsManager.getDefault();
                        // smsManager.sendTextMessage("0888797605",null,"GBV; am being abused, the latitude:" +latitude+" the longtude:" +longtude,null,null);
                        String phone ="";
                        if (phone1 != "" || phone1 != "" || phone1 != "") {
                            smsManager.sendTextMessage("0888977000",null,"SOS! Please help! My location is :  latitude: " +latitude+" longtude: " +longtude,null,null);
                            smsManager.sendTextMessage("0888958726",null,"SOS! Please help! My location is :  latitude: " +latitude+"  longtude:" +longtude,null,null);
                           // smsManager.sendTextMessage("0999953542",null,"SOS! Please help! My location is :  latitude: " +latitude+"  longtude:" +longtude,null,null);
                            Toast.makeText(MainActivity.this, "SOS sent to Yoneco.", Toast.LENGTH_SHORT).show();
                        } else{
                            smsManager.sendTextMessage("0888977000",null,"SOS! Please help! My location is :  latitude: " +latitude+"  longtude:" +longtude,null,null);
                            smsManager.sendTextMessage("0882088644",null,"SOS! Please help! My location is :  latitude: " +latitude+"  longtude:" +longtude,null,null);
                            smsManager.sendTextMessage(phone1, null, "SOS! Please help! My location is :  latitude: " + latitude + "   longtude:" + longtude, null, null);
                            smsManager.sendTextMessage(phone2, null, "SOS! Please help! My location is :  latitude: " + latitude + "   longtude:" + longtude, null, null);
                            smsManager.sendTextMessage(phone3, null, "SOS! Please help! My location is :  latitude: " + latitude + "   longtude:" + longtude, null, null);
                            Toast.makeText(MainActivity.this, "SOS sent to Yoneco.", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void reportAbuse(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REPORT_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_LATITUDE,String.valueOf(latitude));
                params.put(KEY_LONGTUDE,String.valueOf(longtude));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
