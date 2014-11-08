package com.example.ermanyafay.photomapnavigation;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.util.Log;

import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Created by Erman Yafay on 30.10.2014.
 */
public class GeoTagOperation {

    private String imagePath;
    private ExifInterface exif;

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Location tagAndGetLoc(Object systemService) {

        Location loc = getCurrentLocation(systemService);
        Log.d("lat", "" + loc.getLatitude());
        Log.d("long", "" + loc.getLongitude());
        String [] latLotStr;
        latLotStr = getExifFormattedLoc(loc);
        Log.d("latS", latLotStr[0]);
        Log.d("longS", latLotStr[1]);

        try {
            exif = new ExifInterface(imagePath);
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, latLotStr[0]);
            exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, loc.getLatitude()>0?"N":"S");
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, latLotStr[1]);
            exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, loc.getLongitude()>0?"E":"W");
            exif.saveAttributes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return loc;

    }

    public Location readPhotoLocation() {

        Location loc = new Location(LocationManager.PASSIVE_PROVIDER);

        try {
            exif = new ExifInterface(imagePath);

            String lat = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
            String longt = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
            String latRef = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
            String longtRef = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
            if((lat != null) && (longt != null)) {
                Log.d("Raw: Lat, Long, LatRef, LongRef", lat + ", " + longt + ", " + latRef + ", " + longtRef);
                Log.d("Converted: Lat, Long", String.valueOf(convertToDegree(lat)) + ", " + String.valueOf(convertToDegree(longt)));
                Double dLat = (double) convertToDegree(lat);
                Double dLong = (double) convertToDegree(longt);
                Log.d("Dlat, Dlong", String.valueOf(dLat) + ", " + String.valueOf(dLong));
                DecimalFormat df = new DecimalFormat("0.000000");
                String latFormatted = df.format(dLat);
                String longFormatted = df.format(dLong);
                latFormatted = latFormatted.replace(',', '.');
                longFormatted = longFormatted.replace(',', '.');
                Log.d("Fixed decimals", latFormatted + ", " + longFormatted);
                Double fLat = Double.valueOf(latFormatted);
                Double fLong = Double.valueOf(longFormatted);

                if (latRef.equals("N")) {
                    loc.setLatitude(fLat);
                } else {
                    loc.setLatitude(0 - fLat);
                }

                if (longtRef.equals("E")) {
                    loc.setLongitude(fLong);
                } else {
                    loc.setLongitude(0 - fLong);
                }
            } else {
                return null;
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        Log.d("Lat and Long", loc.getLatitude() + ", " + loc.getLongitude());
        return loc;
    }

    public Location getCurrentLocation(Object systemService) {

        LocationManager lm = (LocationManager) systemService;
        Location location;

        if (lm.getLastKnownLocation(LocationManager.GPS_PROVIDER) != null) {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } else {
            location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        return location;
    }

    private String[] getExifFormattedLoc(Location loc) {

        String [] latLotStr = new String[2];
        latLotStr[0] = convertLocToExif(loc.getLatitude());
        latLotStr[1] = convertLocToExif(loc.getLongitude());

        return latLotStr;
    }

    private String convertLocToExif(double dimension) {

        double alat = Math.abs(dimension);

        String dms = Location.convert(alat, Location.FORMAT_SECONDS);
        String[] splits = dms.split(":");
        String[] secnds = (splits[2]).split("\\.");
        String seconds;

        if(secnds.length == 0) {
            seconds = splits[2];
        } else {
            seconds = secnds[0];
        }

        String dimensionStr = splits[0] + "/1," + splits[1] + "/1," + seconds + "/1";

        return dimensionStr;
    }

    private Float convertToDegree(String stringDMS) {
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = Double.valueOf(stringD[0]);
        Double D1 = Double.valueOf(stringD[1]);
        Double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = Double.valueOf(stringM[0]);
        Double M1 = Double.valueOf(stringM[1]);
        Double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = Double.valueOf(stringS[0]);
        Double S1 = Double.valueOf(stringS[0]);
        Double FloatS = S0 / S1;

        result = new Float(FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;
    }

    public Bitmap fixRotation(Bitmap bmp) {
        Matrix matrix = new Matrix();

        try {
            exif = new ExifInterface(imagePath);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        Log.d("Rotation Value: ", String.valueOf(rotation));

        switch (rotation) {
            case ExifInterface.ORIENTATION_NORMAL: {} break;
            case ExifInterface.ORIENTATION_ROTATE_90: {
                matrix.postRotate(90);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            } break;
            case ExifInterface.ORIENTATION_ROTATE_180: {
                matrix.postRotate(180);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            } break;
            case ExifInterface.ORIENTATION_ROTATE_270: {
                matrix.postRotate(270);
                bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            } break;
        }
        return bmp;
    }
}
