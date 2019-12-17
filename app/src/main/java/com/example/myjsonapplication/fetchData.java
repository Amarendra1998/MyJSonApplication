package com.example.myjsonapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.stream.Stream;

public class fetchData extends AsyncTask<Void,Void,Void> {
    String data = "data";
    String datas = "";
    String datass = "";
    String dataparsed = "";
    String singleparsed = "";
    String multipleparsed = "";
    String thirdparsed = "";
    String finalvalue = "";
    private ProgressDialog pDialog;
    //Context context;

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("http://139.59.29.144:8080/emason/Building/RCC/OfficeBuilding/3001");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setRequestMethod("GET");
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data = data + line;
            }

                JSONObject js = new JSONObject();
                JSONObject json = (JSONObject) js.get("status");
                multipleparsed = "status:"+ json.get("status")+ "\n";

                JSONObject jsons = (JSONObject) js.get("message");
                thirdparsed = "message:"+ jsons.get("message")+ "\n";

                JSONObject jsonAPI = (JSONObject) js.get("result");
                JSONArray JA = (JSONArray) jsonAPI.get(data);
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);
                    singleparsed = "material:" + JO.get("material") + "\n" +
                            "quantity:" + JO.get("quantity") + "\n" +
                            "units:" + JO.get("units") + "\n" +
                            "rate:" + JO.get("rate") + "\n" +
                            "amount:" + JO.get("amount") + "\n";
                    dataparsed = dataparsed + singleparsed;

                }
                 finalvalue = dataparsed + multipleparsed + thirdparsed;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


        @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.textView.setText(this.finalvalue);
    }

}
