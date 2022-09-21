package com.dbsh.skup.bus;

import android.os.AsyncTask;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class BusArriveTask extends AsyncTask<String, Void, ArrayList<String>> {
    @Override
    protected ArrayList<String> doInBackground(String... strings) {
        ArrayList<String> shortestStation = new ArrayList<>();
        try {
            String result = (String) downloadUrl((String) strings[0]);
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                String headerCd = "";
                String arrmsg1 = "";            // 첫번째 오는 버스
                String arrmsg2 = "";            // 두번째 오는 버스

                boolean isHeaderCd = false;
				boolean isArrmsg1 = false;
				boolean isArrmsg2 = false;

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) { ; }
                    else if(eventType == XmlPullParser.START_TAG) {
	                    String tag_name = xpp.getName();
                        if(tag_name.equals("headerCd"))
	                        isHeaderCd = true;
                        if(tag_name.equals("arrmsg1"))
	                        isArrmsg1 = true;
                        if(tag_name.equals("arrmsg2"))
	                        isArrmsg2 = true;
                    }
                    else if(eventType == XmlPullParser.TEXT)
                    {
                        if(isHeaderCd)
                        {
                            headerCd = xpp.getText();
                            isHeaderCd = false;
                        }
                        if(headerCd.equals("0")) {
                            if(isArrmsg1) {
                                arrmsg1 = xpp.getText(); // 1164-서경대, 2115-서경대입구
	                            isArrmsg1 = false;
                            }
                            if (isArrmsg2) {
	                            arrmsg2 = xpp.getText();
	                            isArrmsg2 = false;
                            }
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG) { ; }
                    eventType = xpp.next();
                }
	            shortestStation.add(arrmsg1);
	            shortestStation.add(arrmsg2);
            } catch (Exception e) { Log.e("Error", e.getMessage()); }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return shortestStation;
    }
    @Override
    protected void onPostExecute(ArrayList<String> result) {

    }
    private String downloadUrl(String busUrl) throws IOException {
        HttpURLConnection conn = null;
        try {

            URL url = new URL(busUrl);
            conn = (HttpURLConnection) url.openConnection();
            BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
            BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
            String line = null;
            String page = "";
            while((line = bufreader.readLine()) != null)
                page += line;
            return page;
        } finally {
            conn.disconnect();
        }
    }
}