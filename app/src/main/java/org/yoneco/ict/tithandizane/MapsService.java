package org.yoneco.ict.tithandizane;

import androidx.fragment.app.FragmentActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.yoneco.ict.tithandizane.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MapsService extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_service);
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("Helpline Services");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
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
        try {
            InputStream is = getAssets().open("service_providers.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("marker");
            int t = nList.getLength();
            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {

                     Element element2 = (Element) node;

                     String providerName   ="Name\t\t\t\t: "+getValue("name", element2)
                                            +"\nCategory\t\t\t: "+getValue("category", element2)
                                            +"\nFocal Person: "+getValue("focalname", element2)
                                            +"\nDistrict\t\t\t\t: "+getValue("district", element2);

                     String phoneNumber    = "Phone Number : "+ getValue("focalnum",element2);

                     Float lng = Float.parseFloat(getValue("lng", element2));
                     Float lat = Float.parseFloat(getValue("lat", element2));

                    LatLng PVU1 = new LatLng(lat,lng);

                    mMap.addMarker(new MarkerOptions().position(PVU1)
                            .title(providerName)
                            .snippet(phoneNumber));
                    mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        public View getInfoWindow(Marker marker) {
                            TextView providerValue;
                            TextView contactValue;

                            View v = getLayoutInflater().inflate(R.layout.map_info_window, null);

                            providerValue = (TextView) v.findViewById(R.id.provider_value);
                            contactValue  = (TextView)v.findViewById(R.id.contact_value);

                            String title   = marker.getTitle();
                            String phoneNumber = marker.getSnippet();

                            providerValue.setText(title);
                            contactValue.setText(phoneNumber);

                            return v;
                        }
                        public View getInfoContents(Marker arg0) {

                            return null;

                        }
                    });
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(PVU1));
                }
            }

        } catch (Exception e) {e.printStackTrace();}
        LatLng PVU1 = new LatLng(-13.962612,33.774119);

        mMap.addMarker(new MarkerOptions().position(PVU1)
                .title("Lilongwe")
                .snippet("Contact:0882000303"));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PVU1, 7));

    }

    private static String getValue(String tag, Element element) {
        String nodeList = element.getAttribute(tag);
        return nodeList;
    }
}
