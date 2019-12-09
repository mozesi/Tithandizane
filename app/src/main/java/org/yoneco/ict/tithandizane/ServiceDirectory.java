package org.yoneco.ict.tithandizane;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.SearchView;

import org.yoneco.ict.tithandizane.Class.ServiceProvider;
import org.yoneco.ict.tithandizane.Class.ServiceRecyclerViewAdapter;

import com.yoneco.ict.tithandizane.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ServiceDirectory extends AppCompatActivity implements SearchView.OnQueryTextListener {
    ArrayList<ServiceProvider> serviceProvidersList = new ArrayList<ServiceProvider>();
    RecyclerView mRecyclerView;
    ServiceRecyclerViewAdapter adapter;
    ProgressBar progressBar;
    SearchView searchView;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_directory);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       // serviceList = (ListView)findViewById(R.id.service_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        //progressBar =(ProgressBar)findViewById(R.id.progress_bar);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

     //   new PostTask().execute("dgdd");
        ////////////////////////
        serviceProvidersList = getProviders();
        adapter = new ServiceRecyclerViewAdapter(ServiceDirectory.this, serviceProvidersList);
        mRecyclerView.setAdapter(adapter);

        //////////////////////////
        searchView = (SearchView)findViewById(R.id.search_contacts);
        searchView.setOnQueryTextListener(ServiceDirectory.this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.service, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //searchView.setIconified(false);
            serviceProvidersList = getProviders();
            adapter = new ServiceRecyclerViewAdapter(ServiceDirectory.this, serviceProvidersList);
            mRecyclerView.setAdapter(adapter);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private ArrayList<ServiceProvider> filter(ArrayList<ServiceProvider> models, String query) {
        query = query.toLowerCase();final ArrayList<ServiceProvider> filteredModelList = new ArrayList<>();
        for (ServiceProvider model : models) {
            final String district = model.getDistrict().toLowerCase();
            final String org = model.getOrganization().toLowerCase();
            final String focalPerson = model.getFocalPerson().toLowerCase();
            final String contact = model.getContact().toLowerCase();

            if (district.contains(query) || org.contains(query) || focalPerson.contains(query) || contact.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }




    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text  = "Police";
                text = newText;
        serviceProvidersList = filter(serviceProvidersList,text );
        adapter.setFilter(serviceProvidersList);
        return false;
    }
    private String getValue(String tag, Element element) {
        String nodeList = element.getAttribute(tag);
        return nodeList;
    }

    private ArrayList<ServiceProvider> getProviders(){

        ArrayList<ServiceProvider> list = new ArrayList<ServiceProvider>();
        try {
            InputStream is = getAssets().open("service_providers.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element = doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("marker");

            for (int i = 0; i < nList.getLength(); i++) {
                ServiceProvider item;
                item = new ServiceProvider();
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;
                    item.setTitle(getValue("district", element2));
                    item.setOrganization(getValue("name", element2));
                    item.setFocalPerson(getValue("focalname", element2));
                    item.setContact(getValue("focalnum", element2));
                    item.setLat(getValue("lat", element2));
                    item.setLongt(getValue("lng", element2));
                    item.setCategory(getValue("category", element2));
                    item.setEmail(getValue("email",element2));
                    list.add(item);
                }
                //serviceProviders.add(item);
            }
        }catch (Exception e) {e.printStackTrace();
        }
        return list;
    }
}
