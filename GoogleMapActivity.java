package com.fit2081.assignment12081;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.fit2081.assignment12081.databinding.ActivityGoogleMapBinding;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityGoogleMapBinding binding;

    private SupportMapFragment mapFragment;

    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoogleMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        geocoder = new Geocoder(this, Locale.getDefault());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng gettingEventLocation = new LatLng(4.2105,101.9758);

        LatLng defaultLocation = new LatLng(4.2105, 101.9758);

        List<Address> nameToAddressList = new ArrayList<>();

        String eventLocationAddress = getIntent().getExtras().getString("eventLocation");



        if (eventLocationAddress == null || eventLocationAddress.isEmpty()) {

            eventLocationAddress = "Malaysia";
            gettingEventLocation = defaultLocation;
            Toast.makeText(getApplicationContext(), "Defaulting to " + eventLocationAddress, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Event Location: " + eventLocationAddress, Toast.LENGTH_LONG).show();

            try {
                nameToAddressList = geocoder.getFromLocationName(eventLocationAddress, 1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (!nameToAddressList.isEmpty()) {
                gettingEventLocation = new LatLng(nameToAddressList.get(0).getLatitude(), nameToAddressList.get(0).getLongitude());
            } else {
                Toast.makeText(getApplicationContext(), "Defaulting to Malaysia" , Toast.LENGTH_LONG).show();
                eventLocationAddress = "Malaysia";


            }
        }

        mMap.addMarker(new MarkerOptions().position(gettingEventLocation).title(eventLocationAddress));


        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(gettingEventLocation));
        //googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gettingEventLocation, 10));


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){

            @Override
            public void onMapClick(@NonNull LatLng latLng) {
                String msg;

                List<Address> latlongtoAddressList = new ArrayList<>();

                try {
                    latlongtoAddressList = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if(latlongtoAddressList.isEmpty()) {
                    msg = "Category address not found";
                }else{
                    android.location.Address address = latlongtoAddressList.get(0);

                    msg = "The selected country is " + address.getCountryName();


                }

                Snackbar.make(mapFragment.getView(),msg, Snackbar.LENGTH_LONG).show();




            }
        });




    }
}