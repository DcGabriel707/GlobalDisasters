package com.dcgabriel.globaldisasters;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private DisasterArrayAdapter disasterArrayAdapter;
    private ArrayList<Disaster> disasterArrayList;
    private static final String DISASTER_API_ENDPOINT_URL = "https://api.reliefweb.int/v1/disasters?appname=rwint-user-0&profile=list&preset=latest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ************************************");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disasterArrayList = new ArrayList<Disaster>();

        ListView listView = findViewById(R.id.disaster_listView);
        disasterArrayAdapter = new DisasterArrayAdapter(this, disasterArrayList);
        listView.setAdapter(disasterArrayAdapter);

        DisastersAsyncTask task = new DisastersAsyncTask();
        task.execute();


    }

    private class DisastersAsyncTask extends AsyncTask<URL, Void, ArrayList<Disaster>> {

        @Override
        protected ArrayList<Disaster> doInBackground(URL... urls) {
            URL url;
            try {
                url = new URL(DISASTER_API_ENDPOINT_URL);
            } catch (MalformedURLException e) {
                Log.e(TAG, "doInBackground: ");
                return null;
            }
            String jsonResponse = "";

            try {
                jsonResponse = handleHttpRequest(url);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: HTTP request failed");
            }

            return extractJson(jsonResponse); //todo why return??
        }

        @Override
        protected void onPostExecute(ArrayList<Disaster> disasters) {
            disasterArrayAdapter.addAll();
        }

        private String handleHttpRequest(URL url) throws Exception {
            Log.d(TAG, "onCreate: ************************************");

            String jsonResponse = "";

            if (url == null)
                return jsonResponse;
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                if (urlConnection.getResponseCode() == 200) { // 200 means the http request was successful
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = stringFromStream(inputStream);
                } else {
                    Log.e(TAG, "handleHttpRequest: Error code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(TAG, "handleHttpRequest: JSON retrieval failed");
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private ArrayList<Disaster> extractJson(String jsonString) {
            Log.d(TAG, "onCreate: ************************************");

            //temporary json string
            //String jsonString = "{\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters?appname=rwint-user-0&profile=list&preset=latest\",\"time\":3,\"links\":{\"self\":{\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters?offset=0&limit=10&preset=latest\"},\"next\":{\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters?offset=10&limit=10&preset=latest\"}},\"totalCount\":3019,\"count\":10,\"data\":[{\"id\":\"47508\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47508\",\"fields\":{\"date\":{\"created\":\"2019-02-06T00:00:00+00:00\"},\"country\":[{\"name\":\"Moldova\"}],\"name\":\"Moldova: Cold Wave - Jan 2019\",\"glide\":\"CW-2019-000013-MDA\",\"type\":[{\"name\":\"Cold Wave\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47508\",\"status\":\"current\"}},{\"id\":\"47523\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47523\",\"fields\":{\"date\":{\"created\":\"2019-02-06T00:00:00+00:00\"},\"country\":[{\"name\":\"Philippines\"}],\"name\":\"Philippines: Measles Outbreak - Feb 2019\",\"glide\":\"EP-2019-000112-PHL\",\"type\":[{\"name\":\"Epidemic\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47523\",\"status\":\"current\"}},{\"id\":\"47458\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47458\",\"fields\":{\"date\":{\"created\":\"2019-01-27T00:00:00+00:00\"},\"country\":[{\"name\":\"Cuba\"}],\"name\":\"Cuba: Severe Local Storm - Jan 2019\",\"glide\":\"ST-2019-000111-CUB\",\"type\":[{\"name\":\"Severe Local Storm\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47458\",\"status\":\"current\"}},{\"id\":\"47434\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47434\",\"fields\":{\"date\":{\"created\":\"2019-01-23T00:00:00+00:00\"},\"country\":[{\"name\":\"Argentina\"}],\"name\":\"Argentina: Floods - Jan 2019\",\"glide\":\"FL-2019-000009-ARG\",\"type\":[{\"name\":\"Flood\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47434\",\"status\":\"current\"}},{\"id\":\"47534\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47534\",\"fields\":{\"date\":{\"created\":\"2019-01-22T00:00:00+00:00\"},\"country\":[{\"name\":\"Malawi\"}],\"name\":\"Malawi: Floods - Jan 2019\",\"glide\":\"FL-2019-000014-MWI\",\"type\":[{\"name\":\"Flood\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47534\",\"status\":\"current\"}},{\"id\":\"47453\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47453\",\"fields\":{\"date\":{\"created\":\"2019-01-21T00:00:00+00:00\"},\"country\":[{\"name\":\"Nigeria\"}],\"name\":\"Nigeria: Lassa Fever Outbreak - Jan 2019\",\"glide\":\"EP-2019-000012-NGA\",\"type\":[{\"name\":\"Epidemic\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47453\",\"status\":\"current\"}},{\"id\":\"47344\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47344\",\"fields\":{\"date\":{\"created\":\"2019-01-18T00:00:00+00:00\"},\"country\":[{\"name\":\"Philippines\"}],\"name\":\"Tropical Depression Amang - Jan 2019\",\"glide\":\"EC-2019-000006-PHL\",\"type\":[{\"name\":\"Tropical Cyclone\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47344\",\"status\":\"past\"}},{\"id\":\"47483\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47483\",\"fields\":{\"date\":{\"created\":\"2019-01-11T00:00:00+00:00\"},\"country\":[{\"name\":\"Algeria\"}],\"name\":\"Algeria: Cold Wave - Jan 2019\",\"glide\":\"CW-2019-000010-DZA\",\"type\":[{\"name\":\"Cold Wave\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47483\",\"status\":\"current\"}},{\"id\":\"47314\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47314\",\"fields\":{\"date\":{\"created\":\"2019-01-07T00:00:00+00:00\"},\"country\":[{\"name\":\"Iran (Islamic Republic of)\"},{\"name\":\"Iraq\"},{\"name\":\"Jordan\"},{\"name\":\"Lebanon\"},{\"name\":\"Syrian Arab Republic\"}],\"name\":\"Middle East: Floods and Cold Wave - Dec 2018\",\"glide\":\"ST-2019-000002-LBN\",\"type\":[{\"name\":\"Cold Wave\"},{\"name\":\"Flood\"},{\"name\":\"Severe Local Storm\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47314\",\"status\":\"current\"}},{\"id\":\"47224\",\"score\":1,\"href\":\"https:\\/\\/api.reliefweb.int\\/v1\\/disasters\\/47224\",\"fields\":{\"date\":{\"created\":\"2018-12-25T00:00:00+00:00\"},\"country\":[{\"name\":\"Philippines\"}],\"name\":\"Tropical Depression Usman - Dec 2018\",\"glide\":\"EC-2018-000426-PHL\",\"type\":[{\"name\":\"Flood\"},{\"name\":\"Land Slide\"},{\"name\":\"Tropical Cyclone\"}],\"url\":\"https:\\/\\/reliefweb.int\\/taxonomy\\/term\\/47224\",\"status\":\"current\"}}]}";

            if (TextUtils.isEmpty(jsonString))
                return null;

            if (jsonString != null) {
                try {
                    JSONObject rootJsonObject = new JSONObject(jsonString);
                    JSONArray data = rootJsonObject.getJSONArray("data");

                    for (int i = 0; i < data.length(); i++) {
                        JSONObject fields = data.getJSONObject(i).getJSONObject("fields");
                        String date = fields.getJSONObject("date").getString("created");

                        String countryName = "";
                        JSONArray countryArray = fields.getJSONArray("country");
                        for (int j = 0; j < countryArray.length(); j++) {
                            if (j > 0)
                                countryName = countryName + ", ";
                            countryName = countryName + countryArray.getJSONObject(j).getString("name");
                        }

                        String name = fields.getString("name");

                        String type = "";
                        JSONArray typeArray = fields.getJSONArray("type");
                        for (int j = 0; j < typeArray.length(); j++) {
                            if (j > 0)
                                type = type + ", ";
                            type = type + typeArray.getJSONObject(j).getString("name");
                        }

                        String url = fields.getString("url");
                        String status = fields.getString("status");

                        Disaster disaster = new Disaster(name, type, countryName, date, status, url);
                        disasterArrayList.add(disaster);
                    }

                } catch (final JSONException e) {

                }
            }

            return disasterArrayList; //why??
        }


        private String stringFromStream(InputStream inputStream) throws IOException {
            Log.d(TAG, "onCreate: ************************************");

            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }

            return output.toString();
        }


    }


}
