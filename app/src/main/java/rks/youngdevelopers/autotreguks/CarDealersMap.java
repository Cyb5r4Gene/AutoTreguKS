package rks.youngdevelopers.autotreguks;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import Fragments.ProfileFragment;
import Models.Adresa;
import Models.Autosallon;
import Models.Markers;
import Models.User;

public class CarDealersMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    List<Markers> markersList;
    int width, height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_dealers_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                markersList= new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.child("carDealers").getChildren()) {
                    String id=ds.getKey();
                    Autosallon autosallon = ds.getValue(Autosallon.class);
                    User user  = dataSnapshot.child("users").child(id).getValue(User.class);
                    Adresa adresa = dataSnapshot.child("addresses").child(user.getAdresaID()).getValue(Adresa.class);
                    // Add a marker in Sydney and move the camera
                    LatLng markeri = new LatLng(Double.parseDouble(adresa.getLat()), Double.parseDouble(adresa.getLng()));
                    Marker marker = mMap.addMarker(new MarkerOptions().position(markeri).title(autosallon.getEmri())
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                    markersList.add(new Markers(marker.getId(), id));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markeri,16));

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                String id = marker.getId();
                String carDealerId=null;
                for(int i=0;i<markersList.size();i++){
                    if(id.equals(markersList.get(i).getMarkerId())){
                        carDealerId=markersList.get(i).getCarDealerId();
                    }
                }
                if(carDealerId!=null){
                    Toast.makeText(getApplicationContext(), "Test", Toast.LENGTH_LONG).show();
                    ProfileFragment.userID=carDealerId;
                    Intent intent = new Intent(getApplicationContext(), CarDealerActivity.class);
                    startActivity(intent);
                }

            }
        });




    }
}
