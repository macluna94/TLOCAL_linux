package com.example.tlocal_linux;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Button btnUbication;
    String ltt, lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnUbication = (Button) findViewById(R.id.slcUbication);
        btnUbication.setVisibility(View.GONE);



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings settings = googleMap.getUiSettings();

        settings.setZoomControlsEnabled(true);

        mMap.setMinZoomPreference(15);

        // Add a marker in Sydney and move the camera
        LatLng tlalte = new LatLng(21.7894522, -103.2983269);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney").draggable(true));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tlalte));


    mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
        @Override
        public void onMapClick(LatLng latLng) {
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker in Sydney"));
            Toast.makeText(MapsActivity.this, latLng.latitude+"   " +latLng.longitude, Toast.LENGTH_SHORT).show();
            ltt = String.valueOf(latLng.latitude);
            lng = String.valueOf(latLng.longitude);
            btnUbication.setVisibility(View.VISIBLE);
        }
    });

        btnUbication.setOnClickListener(view -> {

            Toast.makeText(getApplicationContext(), "DAta: "+ltt +" "+lng, Toast.LENGTH_LONG).show();

            Intent _int = getIntent();
            _int.putExtra("lt", ltt);//Double.toString(lt));
            _int.putExtra("lg", lng);//Double.toString(ln));
            setResult(RESULT_OK,_int);
            finish();


        });



    }
}
