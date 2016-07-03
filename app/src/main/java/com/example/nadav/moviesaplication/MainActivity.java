package com.example.nadav.moviesaplication;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends ActionBarActivity {

    private static final String ADDRESS = "http://www.omdbapi.com/?s=";
    EditText txtFindMovies;
    ScrollView scrollView;
    static ListView listView;
    static ArrayAdapter adapter;
    static ImageView[] views;
    int viewLength = 0;
    ImageView imagePoster;
    MyReciver reciver;
    String[] text;

    public String readJSONFeed(String URL) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);

        HttpResponse response = httpClient.execute(httpGet);
        StatusLine statusLine = response.getStatusLine();
        int statusCode = statusLine.getStatusCode();

        if (statusCode == 200){

            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null){
                stringBuilder.append(line);
            }

            inputStream.close();

        }else {
            Log.d("JSON", "Failed to download file");
        }
        return stringBuilder.toString();
    }

    private class ReadJsonFeedTask extends AsyncTask<String, Void, String >{

        @Override
        protected String doInBackground(String... urls) {
            String s = "";
            try {
                s = readJSONFeed(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON" ,"execute is "+ s);
            try {
                JSONObject jsonObject = new JSONObject(s);
//                String poster = jsonObject.getString("Poster");
//                Log.d("JSON",poster);

                JSONArray jsonArray = jsonObject.getJSONArray("Search");
                //String poster = (String)jsonArray.get("Poster");
                Log.d("JSON", "jsonArray is " + jsonArray.toString());


                JSONObject poser1 = jsonArray.optJSONObject(0);
                String url = (String)poser1.get("Poster");
                WebView webView0 = (WebView) findViewById(R.id.posterView0);
                webView0.loadUrl(url);

                poser1 = jsonArray.optJSONObject(1);
                url = (String)poser1.get("Poster");
                WebView webView1 = (WebView) findViewById(R.id.posterView1);
                webView1.loadUrl(url);

                poser1 = jsonArray.optJSONObject(2);
                url = (String)poser1.get("Poster");
                WebView webView2 = (WebView) findViewById(R.id.posterView2);
                webView2.loadUrl(url);

                poser1 = jsonArray.optJSONObject(3);
                url = (String)poser1.get("Poster");
                WebView webView3 = (WebView) findViewById(R.id.posterView3);
                webView3.loadUrl(url);

                poser1 = jsonArray.optJSONObject(4);
                url = (String)poser1.get("Poster");
                WebView webView4 = (WebView) findViewById(R.id.posterView4);
                webView4.loadUrl(url);

                poser1 = jsonArray.optJSONObject(5);
                url = (String)poser1.get("Poster");
                WebView webView5 = (WebView) findViewById(R.id.posterView5);
                webView5.loadUrl(url);

                poser1 = jsonArray.optJSONObject(6);
                url = (String)poser1.get("Poster");
                WebView webView6 = (WebView) findViewById(R.id.posterView6);
                webView6.loadUrl(url);

                poser1 = jsonArray.optJSONObject(7);
                url = (String)poser1.get("Poster");
                WebView webView7 = (WebView) findViewById(R.id.posterView7);
                webView7.loadUrl(url);

                poser1 = jsonArray.optJSONObject(8);
                url = (String)poser1.get("Poster");
                WebView webView8 = (WebView) findViewById(R.id.posterView8);
                webView8.loadUrl(url);

                poser1 = jsonArray.optJSONObject(9);
                url = (String)poser1.get("Poster");
                WebView webView9 = (WebView) findViewById(R.id.posterView9);
                webView9.loadUrl(url);




                views = new ImageView[jsonArray.length()];
                ImageView imageView;
                for (int i = 0; i < jsonArray.length(); i++) {

                    String posterAddress = (String)jsonArray.getJSONObject(i).get("Poster");

                    new FetchBitmapTask().execute(posterAddress);

                    imageView = new ImageView(MainActivity.this);
                    views[i] = imageView;



                }




//                Intent intent = new Intent("MY_ACTION");
//                sendBroadcast(intent, null);


                //ArrayAdapter adapter = new ArrayAdapter<View>(this, android.R.layout.activity_list_item, views);
                //Log.d("JSON", poster);

//                String[] ss = s.split("Title");
//                for (int i = 0; i < ss.length; i++) {
//                    Log.d("JSON", ss[i]);
//                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtFindMovies = (EditText) findViewById(R.id.txtFindMovies);
        scrollView = (ScrollView) findViewById(R.id.scrView);
        //listView = (ListView) findViewById(R.id.lstView);

        reciver = new MyReciver();
        text = new String[10];

        for (int i = 0; i < 10; i++) {
            text[i] = "heloo";
        }

    }

    public void btnSearch(View view) throws MalformedURLException {

        String s = txtFindMovies.getText().toString();

            //URL url = new URL("http://www.omdbapi.com/?s="+s);

        new ReadJsonFeedTask().execute(ADDRESS+s);

        viewLength = 0;


//        IntentFilter intentFilter = new IntentFilter("MY_ACTION");
//        registerReceiver(reciver, intentFilter);



    }
    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e("Problem", "Error getting bitmap", e);
        }
        Log.d("BITMAP", bm.toString());
        return bm;
    }

    private class FetchBitmapTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (bitmap != null) {
                        imagePoster = new ImageView(MainActivity.this);
                        imagePoster.setImageBitmap(bitmap);
                    }
                    else {
                        Log.d("BITMAP", "bitmap is null");
                    }
                    views[viewLength++] = imagePoster;
                    Log.d("VIEWS", views[viewLength - 1].toString());
//                    Intent intent = new Intent("MY_ACTION");
//                    sendBroadcast(intent, null);
//                    unregisterReceiver(reciver);
                }
            });
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            return getImageBitmap(strings[0]);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (MainActivity.views!=null) {
//            ListAdapter adpter = new ArrayAdapter<ImageView>(this, R.layout.poster, views);
//            MainActivity.listView.setAdapter(adpter);
//        }
    }
}
