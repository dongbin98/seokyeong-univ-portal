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

public class StationTask extends AsyncTask<String, Void, ArrayList<Station>> {

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
				String routeId = "";            // 노선 아이디
                String stationId = "";          // 정류장 아이디
                String stationName = "";        // 정류장 이름
                String direction = "";          // 방향
                String posX = "";               // 정류장 좌표X
                String posY = "";               // 정류장 좌표Y
                String seq = "";                // 정류장 순번

                boolean isHeaderCd = false;
				boolean isRouteId = false;
                boolean isGpsX = false;
                boolean isGpsY = false;
                boolean isStationid = false;
                boolean isDirection = false;
                boolean isStationName = false;
                boolean isSequence = false;

                while(eventType != XmlPullParser.END_DOCUMENT) {
                    if(eventType == XmlPullParser.START_DOCUMENT) { ; }
                    else if(eventType == XmlPullParser.START_TAG) {
                        String tag_name = xpp.getName();
                        if(tag_name.equals("station"))
	                        isStationid = true;
						if(tag_name.equals("busRouteId"))
							isRouteId = true;
                        if(tag_name.equals("headerCd"))
	                        isHeaderCd = true;
                        if(tag_name.equals("gpsX"))
	                        isGpsX = true;
                        if(tag_name.equals("gpsY"))
	                        isGpsY = true;
                        if(tag_name.equals("direction"))
	                        isDirection = true;
                        if(tag_name.equals("stationNm"))
	                        isStationName = true;
                        if(tag_name.equals("seq"))
	                        isSequence = true;
                    }
                    else if(eventType == XmlPullParser.TEXT)
                    {
                        if(isHeaderCd)
                        {
                            headerCd = xpp.getText();
                            isHeaderCd = false;
                        }
                        if(headerCd.equals("0")) {
                            if(isDirection) {
                                direction = xpp.getText(); // 1164-서경대, 2115-서경대입구
                                isDirection = false;
                            }
							if(isRouteId) {
								routeId = xpp.getText();
								isRouteId = false;
							}
                            if (isStationid) {
                                stationId = xpp.getText();
                                isStationid = false;
                            }
                            if (isGpsX) {
                                posX = xpp.getText();
                                isGpsX = false;
                            }
                            if (isGpsY) {
                                posY = xpp.getText();
                                isGpsY = false;
                            }
                            if (isSequence) {
                                seq = xpp.getText();
                                isSequence = false;
                            }
                            if (isStationName) {
                                stationName = xpp.getText();
                                isStationName = false;
                                Station station = new Station(routeId, stationId, stationName, seq, direction, Double.parseDouble(posX), Double.parseDouble(posY));
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