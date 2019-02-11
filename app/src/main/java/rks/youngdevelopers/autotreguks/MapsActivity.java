package rks.youngdevelopers.autotreguks;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import Fragments.CreateAccountFragment;
import Fragments.UpdateProfileFragment;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;

    LatLng latLng = null;
    LatLng ruajLatLng = null;
    Marker lokacioniIm, lokacioniClick;

    Button btnKerko, btnOk;

    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    buildAlertMessageNoGps();
                } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    getLocation();
                }
            }
        });

        btnKerko = (Button) findViewById(R.id.adresaKerko);
        btnOk = (Button) findViewById(R.id.btnOk);

        btnKerko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(MapsActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {

                } catch (GooglePlayServicesNotAvailableException e) {

                }

            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lokacioniClick == null && lokacioniIm == null) {
                    Snackbar.make(v, "Ju lutem përzgjedhni lokacionin tuaj!", Snackbar.LENGTH_LONG).show();
                } else {
                    CreateAccountFragment.adresaLatLng = ruajLatLng;
                    UpdateProfileFragment.updateLatLng = ruajLatLng;
                    finish();
                }
            }
        });
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location location2 = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            double lat, lng;

            if (location != null) {
                lat = location.getLatitude();
                lng = location.getLongitude();
                latLng = new LatLng(lat, lng);
                ruajLatLng = new LatLng(lat,lng);

            } else if (location1 != null) {
                lat = location1.getLatitude();
                lng = location1.getLongitude();
                latLng = new LatLng(lat, lng);
                ruajLatLng = new LatLng(lat,lng);

            } else if (location2 != null) {
                lat = location2.getLatitude();
                lng = location2.getLongitude();
                latLng = new LatLng(lat, lng);
                ruajLatLng = new LatLng(lat,lng);
            } else {
                Toast.makeText(this, "Lokacioni i padisponueshem", Toast.LENGTH_SHORT).show();
            }

            if (latLng != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
                mMap.clear();
                lokacioniIm = mMap.addMarker(new MarkerOptions().position(latLng)
                        .title("Lokacioni juaj")
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc)));
            }
        }
    }

    protected void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kërkohet qasje në GPS")
                .setCancelable(false)
                .setPositiveButton("Në Rregull", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("Anulo", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLngClick) {
                if (lokacioniClick != null)
                    lokacioniClick.remove();
                MarkerOptions marker = new MarkerOptions().position(latLngClick).title("Lokacioni i përzgjedhur").icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon));
                lokacioniClick = mMap.addMarker(marker);
                ruajLatLng = latLngClick;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (lokacioniClick != null)
                    lokacioniClick.remove();
                MarkerOptions marker = new MarkerOptions().position(place.getLatLng()).title("Lokacioni i përzgjedhur").icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));
                lokacioniClick = mMap.addMarker(marker);
                ruajLatLng = place.getLatLng();

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                String toastMsg = String.format("Error: %s", status.getStatusMessage());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {
                // Useri e ka anulu kerkimin
            }
        }
    }


}
