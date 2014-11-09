package com.example.ermanyafay.photomapnavigation.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.example.ermanyafay.photomapnavigation.R;
import com.example.ermanyafay.photomapnavigation.logic.Event;
import com.example.ermanyafay.photomapnavigation.logic.UnregisteredUser;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class TourActivity extends FragmentActivity
{
    private GoogleMap publicEventMap;
    private UnregisteredUser unregisteredUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        this.unregisteredUser = new UnregisteredUser(getAllPublicEvents());
        setUpMapIfNeeded();
    }

    private ArrayList<Event> getAllPublicEvents()
    {
        ArrayList<Event> publicEvents = new ArrayList<Event>();

        //get all public events

        return publicEvents;
    }

    private void setUpMapIfNeeded()
    {
        if (publicEventMap == null)
        {
            publicEventMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.publicEventMap)).getMap();
            if (publicEventMap != null)
            {
                setUpMap();
            }
        }
    }

    private void setUpMap()
    {
        publicEventMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for(int i = 0; i < unregisteredUser.getAccessableEvents().size(); i++) {
            Bitmap currentBitmap = unregisteredUser.getAccessableEvents().get(i).getRootPhoto().getSource();
            Bitmap bmp = Bitmap.createScaledBitmap(currentBitmap, (int) (currentBitmap.getWidth()*0.1), (int) (currentBitmap.getHeight()*0.1), false);
            Location loc = unregisteredUser.getAccessableEvents().get(i).getRootPhoto().getLatLng();

            publicEventMap.addMarker(new MarkerOptions().position(new LatLng(loc.getLatitude(), loc.getLongitude())).icon(BitmapDescriptorFactory.fromBitmap(bmp)));
        }
    }

    private void goRegisterActivity()
    {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.tour_actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.backToRegisterActivityAction)
        {
            goRegisterActivity();
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }
}
