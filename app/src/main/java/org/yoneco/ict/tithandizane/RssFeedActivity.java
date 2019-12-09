package org.yoneco.ict.tithandizane;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.yoneco.ict.tithandizane.Class.PcWorldRssParser;
import org.yoneco.ict.tithandizane.Class.RssItem;

import com.yoneco.ict.tithandizane.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssFeedActivity extends AppCompatActivity {
    ArrayList<String> rssTitles = new ArrayList<String>();
    ArrayList<String> rssLinks = new ArrayList<String>();
    String url;
    ListView list;
    ProgressBar pb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srh);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pb  =(ProgressBar)findViewById(R.id.progressBar);
        list=(ListView)findViewById(R.id.listview);
        url =getIntent().getStringExtra("url");
        new PostTask().execute(url);
    }

    // The definition of our task class
    private class PostTask extends AsyncTask<String, Integer, String> {
        List<RssItem> rssItems = null;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // displayProgressBar("Downloading...");
        }

        @Override
        protected String doInBackground(String... params) {
            String url1 = params[0];

            try {
                PcWorldRssParser parser = new PcWorldRssParser();
                rssItems = parser.parse(getInputStream(url1));
            } catch (XmlPullParserException e) {
                Log.w(e.getMessage(), e);
            } catch (IOException e) {
                Log.w(e.getMessage(), e);
            }

            return rssItems.get(3).getTitle();
        }

        public InputStream getInputStream(String link) {
            try {
                URL url = new URL(link);
                return url.openConnection().getInputStream();
            } catch (IOException e) {
                //Log.w(Constants.TAG, "Exception while retrieving the input stream", e);
                return null;
            }
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // updateProgressBar(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // dismissProgressBar();

            for(int x=0; x<rssItems.size(); x++){
                rssTitles.add(rssItems.get(x).getTitle());
                rssLinks.add(rssItems.get(x).getLink());
            }

            pb.setVisibility(View.GONE);
            final ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.list_black,rssTitles);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                    String url =rssLinks.get(position);
                    Intent rssIntentView = new Intent(getApplicationContext(),RssWebView.class);
                    rssIntentView.putExtra("url",url);
                    startActivity(rssIntentView);
                }
            });

;        }
    }
}
