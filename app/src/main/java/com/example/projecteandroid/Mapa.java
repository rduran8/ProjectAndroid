package com.example.projecteandroid;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Mapa extends FragmentActivity implements OnMapReadyCallback {

    public GoogleMap mMap;
    Intent intent;
    public LatLng patata = new LatLng(0,0);
    MarkerOptions marker;
    int pos;
    String titul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        intent = getIntent();
        pos = intent.getIntExtra("POSITION",0);
        double si = intent.getDoubleExtra("LATITUDE",0);
        double no = intent.getDoubleExtra("LONGITUDE",0);
        titul = intent.getStringExtra("TITUL");
        patata = new LatLng(si,no);
    }

    private void setupMapListener(){
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                intent.putExtra("LATITUDE",patata.latitude);
                intent.putExtra("LONGITUDE",patata.longitude);
                intent.putExtra("POSITION",pos);
                setResult(2,intent);
                finish();
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                mMap.clear();
                patata = point;
                marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude)).title(titul).draggable(true);
                mMap.addMarker(marker);
            }
        });
    }

    public void onBackPressed() {
        intent.putExtra("LATITUDE",patata.latitude);
        intent.putExtra("LONGITUDE",patata.longitude);
        intent.putExtra("POSITION",pos);
        setResult(2,intent);
        finish();
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
        if(patata.latitude!=0 && patata.longitude!=0){
            marker = new MarkerOptions().position(patata).title(titul).draggable(true);
            mMap.addMarker(marker);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(patata));
        }
        setupMapListener();
    }

}
