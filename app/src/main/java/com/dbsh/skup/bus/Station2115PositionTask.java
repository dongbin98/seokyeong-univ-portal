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

public class Station2115PositionTask extends AsyncTask<String, Void, ArrayList<Station>> {

    @Override
    protected ArrayList<Station> doInBackground(String... strings) {
        ArrayList<Station> stations = new ArrayList<>();
        try {
            String result = (String) downloadUrl((String) strings[0]);
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                String headerCd = "";
                String stationId = "";       // 정류소아이디
                String stationName = "";     // 정류소명
                String direction = "";       // 방향
                String posX = "";            // 정류소 좌표X
                String posY = "";            // 정류소 좌표Y
                String seq = "";             // 정류소 순번

                boolean bSet_headerCd = false;
                boolean bSet_gpsX = false;
                boolean bSet_gpsY = false;
                boolean bSet_stationId = false;
                boolean bSet_direction = false;
                boolean bSet_stationNm = false;
                boolean bSet_seq = false;

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) { ; }
                    else if(eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if(tag_name.equals("station"))
                            bSet_stationId = true;
                        if(tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if(tag_name.equals("gpsX"))
                            bSet_gpsX = true;
                        if(tag_name.equals("gpsY"))
                            bSet_gpsY = true;
                        if(tag_name.equals("direction"))
                            bSet_direction = true;
                        if(tag_name.equals("stationNm"))
                            bSet_stationNm = true;
                        if(tag_name.equals("seq"))
                            bSet_seq = true;
                    }
                    else if(eventType == XmlPullParser.TEXT)
                    {
                        if(bSet_headerCd)
                        {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }
                        if(headerCd.equals("0")) {
                            if(bSet_direction) {
                                direction = xpp.getText(); // 1164-서경대, 2115-서경대입구
                                bSet_direction = false;
                            }
                            if (bSet_stationId) {
                                stationId = xpp.getText();
                                bSet_stationId = false;
                            }
                            if (bSet_gpsX) {
                                posX = xpp.getText();
                                bSet_gpsX = false;
                            }
                            if (bSet_gpsY) {
                                posY = xpp.getText();
                                bSet_gpsY = false;
                            }
                            if (bSet_seq) {
                                seq = xpp.getText();
                                bSet_seq = false;
                            }
                            if (bSet_stationNm) {
                                stationName = xpp.getText();
                                bSet_stationNm = false;
                                Station station = new Station(stationId, stationName, seq, direction, Double.parseDouble(posX), Double.parseDouble(posY));
                                stations.add(station);
                            }
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG) { ; }
                    eventType = xpp.next();
                }
            } catch (Exception e) { Log.e("Error", e.getMessage()); }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return stations;
    }
    @Override
    protected void onPostExecute(ArrayList<Station> result) {

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