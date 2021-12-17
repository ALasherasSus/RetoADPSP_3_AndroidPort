package com.elorrieta.retoadpsp_3_androidport;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.elorrieta.retoadpsp_3_androidport.databinding.ActivityElorrietaBinding;

public class Elorrieta extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityElorrietaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityElorrietaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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

        // Add a marker in Sydney and move the camera
        LatLng elorrieta = new LatLng(43.28470, -2.96466);
        mMap.addMarker(new MarkerOptions().position(elorrieta).title("Localización de Elorrieta"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(elorrieta));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(elorrieta, 15), 5000, null);
    }
}