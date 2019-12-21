package com.atrinfanavaran.kheiriyeh.Kernel.Helper;

import android.location.Location;

import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Scanner;

public class Wkt {
    /**
     * @param points is points of a shape
     * @return wkt string like <code>POINT (30 10)</code> or <code>LINESTRING (30 10, 10 30, 40 40)</code>
     */
    public static String toWkt(Location... points) {
        StringBuilder result;
        if (points.length == 1) {
            result = new StringBuilder("POINT (");
        } else {
            result = new StringBuilder("LINESTRING (");
        }
        for (Location point : points) {
            result.append(point.getLongitude()).append(" ").append(point.getLatitude()).append(", ");
        }
        result.deleteCharAt(result.length() - 1);
        result.deleteCharAt(result.length() - 1);
        result.append(")");
        return result.toString();
    }

    public static String toWktPolygon(ArrayList<Location> locations) {
        StringBuilder result = new StringBuilder("POLYGON ((");

        for (Location point : locations) {
            result.append(point.getLongitude()).append(" ").append(point.getLatitude()).append(",");
        }
        result.append(locations.get(0).getLongitude()).append(" ").append(locations.get(0).getLatitude()).append(",");
        result.deleteCharAt(result.length() - 1);
        result.append("))");
        return result.toString();
    }


    /**
     * @param wkt is a string like <code>POINT (30 10)</code> or <code>LINESTRING (30 10, 10 30, 40 40)</code>
     * @return point of wkt
     */
    public static GeoPoint getPoint(String wkt) {
        return getPoints(wkt).get(0);
    }

    /**
     * @param wkt is a string like <code>POINT (30 10)</code> or <code>LINESTRING (30 10, 10 30, 40 40)</code>
     * @return points of wkt
     */
    public static ArrayList<GeoPoint> getPoints(String wkt) {
//        Log.i("<--START WKT", " ");
//        Log.i("WKT", "wkt = " + wkt);
        ArrayList<GeoPoint> result = new ArrayList<>();
        Scanner sc = new Scanner(wkt);
        String type = sc.next();
//        Log.i("WKT", "type = " + type);
        if (type.equalsIgnoreCase("POLYGON")) {
            double lng = Double.parseDouble(sc.next().substring(2));
            String s = sc.next();

            String[] latlng = s.split(",");
            double lat = Double.parseDouble(s.substring(0, latlng[0].length() - 1));


//            double lat = Double.parseDouble(s.substring(0, s.length() - 2));
            GeoPoint point = new GeoPoint(lat, lng);
            result.add(point);


            while (sc.hasNext()) {
                String s2 = sc.next();

                lng = Double.parseDouble(latlng[1]);
                latlng = s2.split(",");

                if (latlng[0].contains("))")) {
                    lat = Double.parseDouble(latlng[0].substring(0, latlng[0].length() - 3));
                } else {
                    lat = Double.parseDouble(latlng[0]);
                }

                GeoPoint point2 = new GeoPoint(lat, lng);
                result.add(point2);
            }

//            Log.i("WKT", "point = " + point.getLatitude() + ", " + point.getLongitude());
        } else if (type.equalsIgnoreCase("POINT") || type.equalsIgnoreCase("LINESTRING")) {
            double lng = Double.parseDouble(sc.next().substring(1));
            String s = sc.next();
            double lat = Double.parseDouble(s.substring(0, s.length() - 2));
            GeoPoint point = new GeoPoint(lat, lng);
            result.add(point);
//            Log.i("WKT", "point = " + point.getLatitude() + ", " + point.getLongitude());
        }
        while (sc.hasNext()) {
            double lng = Double.parseDouble(sc.next());
            String s = sc.next();
            double lat = Double.parseDouble(s.substring(0, s.length() - 2));
            GeoPoint point = new GeoPoint(lat, lng);
            result.add(point);
//            Log.i("WKT", "point = " + point.getLatitude() + ", " + point.getLongitude());
        }
//        Log.i("-->END WKT", " ");
        return result;
    }
}
