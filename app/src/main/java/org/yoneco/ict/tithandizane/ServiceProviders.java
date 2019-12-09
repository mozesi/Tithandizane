package org.yoneco.ict.tithandizane;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;

import com.yoneco.ict.tithandizane.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ServiceProviders extends AppCompatActivity {

    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_providers);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv1 = (TextView)findViewById(R.id.tv1);
        try {
            InputStream is = getAssets().open("service_providers.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(is);

            Element element=doc.getDocumentElement();
            element.normalize();

            NodeList nList = doc.getElementsByTagName("marker");

            for (int i=0; i<nList.getLength(); i++) {

                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element2 = (Element) node;

                    tv1.setText(tv1.getText()+"\nName : " + getValue("name", element2)+"\n");
                    tv1.setText(tv1.getText()+"Surname : " + getValue("lng", element2)+"\n");
                    tv1.setText(tv1.getText()+"-----------------------");
                }
            }
        } catch (Exception e) {e.printStackTrace();}
    }
    private static String getValue(String tag, Element element) {
        String nodeList = element.getAttribute(tag);
        return nodeList;
    }

}
