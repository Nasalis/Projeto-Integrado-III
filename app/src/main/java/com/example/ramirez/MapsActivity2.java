package com.example.ramirez;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.ramirez.helpers.Permission;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.ramirez.databinding.ActivityMapsBinding;

import java.io.IOException;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private String[] necessaryPermissions = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Permission.validatePermissions(necessaryPermissions, this, 1);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Bundle extras = getIntent().getExtras();
        String locationCity = extras.getString("MAP_LOCATION");

        double lat = getLocationFromAddress(locationCity)[0];
        double longt = getLocationFromAddress(locationCity)[1];

        LatLng location = new LatLng(lat, longt);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in " + locationCity));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }

    public double[] getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        double[] latLang = new double[2];

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);

            latLang[0] = location.getLatitude() ;
            latLang[1] = location.getLongitude() ;

            return latLang;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}