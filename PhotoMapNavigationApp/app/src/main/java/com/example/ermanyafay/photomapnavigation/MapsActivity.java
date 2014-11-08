package com.example.ermanyafay.photomapnavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private static int REQUEST_TAKE_PHOTO = 1;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";

    private String mCurrentPhotoPath;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<Bitmap> allPhotos = new ArrayList<Bitmap>();
    private List<Location> allLocations = new ArrayList<Location>();

    private File getAlbumDir() {
        File storageDir = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myApp");

            if (storageDir != null) {
                if(!storageDir.mkdirs()) {
                    if(! storageDir.exists()) {
                        Log.d("myApp", "failed to create directory");

                        return null;
                    }
                }
            }
        } else {
            Log.v("Device Storage", "External storage is not mounted READ/WRITE");
        }

        return storageDir;
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = JPEG_FILE_PREFIX + timeStamp + "_";
        File albumF = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumF);

        return imageF;
    }

    private File setUpPhotoFile() throws IOException {
        File f = createImageFile();

        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);

        this.sendBroadcast(mediaScanIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_maps);
        setAllPhotos();
        setUpMapIfNeeded();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);

        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_take_picture) {
            dispatchTakePictureIntent();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = null;
        try {
            f = setUpPhotoFile();
            mCurrentPhotoPath = f.getAbsolutePath();
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        } catch (IOException ex) {
            ex.printStackTrace();
            f = null;
            mCurrentPhotoPath = null;
        }

        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
    }

    private void handleBigCameraPhoto() {
        if(mCurrentPhotoPath != null) {
            GeoTagOperation op = new GeoTagOperation();
            op.setImagePath(mCurrentPhotoPath);
            Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);
            bmp = op.fixRotation(bmp);
            Location loc = op.tagAndGetLoc(getSystemService(Context.LOCATION_SERVICE));

            if(loc != null) {
                allLocations.add(loc);
                allPhotos.add(bmp);

                addNewMarker(bmp, loc);
                galleryAddPic();
                String stringBitmap = bitmapToString(bmp);

                new UploadImageTask().execute(stringBitmap);
            }
            mCurrentPhotoPath = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        setUpMapIfNeeded();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            handleBigCameraPhoto();
        } else {
            File f = new File(mCurrentPhotoPath);
            f.delete();
        }
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {

        Location currentUserLocation = new GeoTagOperation().getCurrentLocation(getSystemService(Context.LOCATION_SERVICE));
        LatLng latLng = new LatLng(currentUserLocation.getLatitude(), currentUserLocation.getLongitude());

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));

        for(int i = 0; i < allPhotos.size(); i++) {
            Bitmap currentBitmap = allPhotos.get(i);
            Bitmap bmp = Bitmap.createScaledBitmap(currentBitmap, (int) (currentBitmap.getWidth()*0.1), (int) (currentBitmap.getHeight()*0.1), false);
            Location loc = allLocations.get(i);

            mMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
        }
    }

    private void setAllPhotos() {
        File [] allfiles = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "myApp").listFiles();
        Log.d("root file", getFilesDir().getName());

        if(allfiles != null) {
            for(File f : allfiles) {
                String path = f.getAbsolutePath();
                Log.d("File path", path);
                GeoTagOperation op = new GeoTagOperation();
                op.setImagePath(path);
                Location loc =  op.readPhotoLocation();

                if(loc != null) {
                    allPhotos.add(BitmapFactory.decodeFile(path));
                    allLocations.add(loc);
                }
            }
        }
    }

    private void addNewMarker(Bitmap bmp, Location loc) {
        bmp = Bitmap.createScaledBitmap(bmp, (int) (bmp.getWidth()*0.1), (int) (bmp.getHeight()*0.1), false);
        LatLng latLng = new LatLng(loc.getLatitude(), loc.getLongitude());

        mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
    }

    public String bitmapToString(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 80, baos);

        byte [] b = baos.toByteArray();

        String temp = Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }
}
