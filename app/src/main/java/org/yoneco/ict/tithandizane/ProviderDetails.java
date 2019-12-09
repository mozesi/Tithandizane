package org.yoneco.ict.tithandizane;

import android.content.Intent;
import android.net.Uri;
import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoneco.ict.tithandizane.R;

import org.w3c.dom.Element;

public class ProviderDetails extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String person;
    String Phone;
    String email;
    String district;
    String organization;
    String category;

    float lat;
    float longt;

    TextView personView;
    TextView phoneView;
    TextView emailView;
    TextView districtView;
    TextView catView;
    TextView orgView;

    ImageView callImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_details);
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);


        Bundle providerDetails = getIntent().getExtras();
        person = providerDetails.getString("person");
        Phone  = providerDetails.getString("contact");
        organization = providerDetails.getString("org");
        district = providerDetails.getString("district");
        category = providerDetails.getString("category");
        email    = providerDetails.getString("email");

        lat = Float.parseFloat(providerDetails.getString("lat"));
        longt = Float.parseFloat(providerDetails.getString("lng"));

        personView =(TextView)findViewById(R.id.person);
        phoneView = (TextView)findViewById(R.id.phone);
        emailView = (TextView)findViewById(R.id.email);
        districtView =(TextView)findViewById(R.id.dist);
        orgView =(TextView)findViewById(R.id.org);
        catView =(TextView)findViewById(R.id.category);

        toolbar.setTitle(organization);
        callImageview = (ImageView)findViewById(R.id.callProvider);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        personView.setText(person);
        phoneView.setText(Phone);
        orgView.setText(organization);
        emailView.setText(email);
        catView.setText(category);
        districtView.setText(district);
        callImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ProviderIntent = new Intent(Intent.ACTION_CALL);
                ProviderIntent.setData(Uri.parse("tel:"+Phone));
                startActivity(ProviderIntent);
            }
        });

        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng PVU1 = new LatLng(lat,longt);

        mMap.addMarker(new MarkerOptions().position(PVU1)
                .title(email)
                .snippet(district));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PVU1, 8));

    }
    private static String getValue(String tag, Element element) {
        String nodeList = element.getAttribute(tag);
        return nodeList;
    }
}
