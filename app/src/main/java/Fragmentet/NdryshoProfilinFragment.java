package Fragmentet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import Database.RemoteDatabase;
import de.hdodenhof.circleimageview.CircleImageView;
import models.Adresa;
import models.Autosallon;
import rks.youngdevelopers.autotreguks.MapsActivity;
import rks.youngdevelopers.autotreguks.ProfiliActivity;
import rks.youngdevelopers.autotreguks.R;
import models.User;

public class NdryshoProfilinFragment extends Fragment {

    Spinner editQyteti;
    public  static CircleImageView imgProfilEdit, imgAutosallonEdit;
    ImageView imgProfil, imgAutosallon;

    int ZGJEDH_FOTON = 71;
    int foto ;
    Uri fotoPath;

    EditText editEmri, editMbiemri, editDatelindja, editTel, editAutoEmri, editAutoTel, editAutoPershkrimi,
            editRruga, editNr, editKodi, editEmail, editPassVjeter, editPassRi, editPassRi2;
    Button btnEditHarta, btnNdryshoFjalekalimin, btnAnuloNdryshimin;
    RadioButton radioP, radioA;
    ImageView gEmri, gMbiemri, gDatelindja, gTel, gAutoEmri, gRruga, gNr, gKodi, gQyteti, gEmail;
    LinearLayout layoutAutosallon;
    ConstraintLayout editPhotoAutosallon;
    public static LatLng updateLatLng;
    static MapView mapView;
    private GoogleMap googleMap;
    String emri, mbiemri, datelindja, telefoni, autoEmri, autoTel, autoPershkrimi, adresaRruga, adresaNr,adresaKodi,adresaQyteti, email,tipi;

    FirebaseAuth firebaseAuth;
    FirebaseDatabase db;
    FirebaseUser user;
    DatabaseReference dRef;

    User perdoruesi = new User();
    Autosallon autosalloni = new Autosallon();
    Adresa adresa = new Adresa();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_ndrysho_profilin, container, false);
        setHasOptionsMenu(true);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();
        dRef = db.getReference();


        editEmri = (EditText)v.findViewById(R.id.editEmri);
        editMbiemri = (EditText)v.findViewById(R.id.editMbiemri);
        editDatelindja = (EditText)v.findViewById(R.id.editDatelindja);
        editTel = (EditText)v.findViewById(R.id.editTel);
        editAutoEmri = (EditText)v.findViewById(R.id.editEmriAutosallon);
        editAutoTel = (EditText)v.findViewById(R.id.editAutoTel);
        editAutoPershkrimi = (EditText)v.findViewById(R.id.editAutoPershkrimi);
        editRruga = (EditText)v.findViewById(R.id.editRruga);
        editNr = (EditText)v.findViewById(R.id.editNr);
        editKodi = (EditText)v.findViewById(R.id.editKodi);
        editEmail = (EditText)v.findViewById(R.id.editEmail);
        editPassVjeter = (EditText)v.findViewById(R.id.editPassVjeter);
        editPassRi = (EditText)v.findViewById(R.id.editPassRi);
        editPassRi2 = (EditText)v.findViewById(R.id.editPassRi2);
        btnEditHarta = (Button)v.findViewById(R.id.btnEditHarta);
        btnNdryshoFjalekalimin = (Button)v.findViewById(R.id.btnNdryshoFjalekalimin);
        btnAnuloNdryshimin = (Button)v.findViewById(R.id.btnAnuloNdryshimin);
        radioP = (RadioButton)v.findViewById(R.id.radioP);
        radioA = (RadioButton)v.findViewById(R.id.radioA);

        mapView = (MapView)v.findViewById(R.id.editMapView);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        imgProfilEdit = (CircleImageView) v.findViewById(R.id.editProfilIcon);
        imgAutosallonEdit = (CircleImageView) v.findViewById(R.id.editAutosallonIcon);

        gQyteti = (ImageView)v.findViewById(R.id.gQyteti);

        gQyteti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Ju lutem zgjidhni qytetin", Toast.LENGTH_SHORT).show();
            }
        });


        layoutAutosallon = (LinearLayout)v.findViewById(R.id.editLayoutAutosallon);
        editPhotoAutosallon = (ConstraintLayout)v.findViewById(R.id.editPhotoAutosallon);

        editQyteti = (Spinner) v.findViewById(R.id.editSpinner);
        // adapteri qe e mbush me te dhena dropdown-spinnerin
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(getActivity().getApplicationContext(), R.array.qytetet,
                        R.layout.spinner_profili);

        // Layout-i i cili i aplikohet elementeve te dropdown-qyteti
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Aplikimi i adapterit te spineri
        editQyteti.setAdapter(staticAdapter);

        radioP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    layoutAutosallon.setVisibility(View.GONE);
            }
        });

        radioA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutAutosallon.setVisibility(View.VISIBLE);
            }
        });


        dRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userData(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnAnuloNdryshimin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editPassVjeter.setText("");
                editPassRi.setText("");
                editPassRi2.setText("");
            }
        });

        btnNdryshoFjalekalimin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = editPassVjeter.getText().toString().trim();
                if(pass != null && !pass.isEmpty()) {
                    RemoteDatabase.loginUser(FirebaseAuth.getInstance().getCurrentUser().getEmail(), pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String pass1 = editPassRi.getText().toString().trim();
                                String pass2 = editPassRi2.getText().toString().trim();
                                if (pass1 != null && pass1.equals(pass2) && !pass1.isEmpty() && pass1.length()>=6) {
                                    RemoteDatabase.updatePass(user, editPassRi.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity(), "Fjalëkalimi u ndërrua me sukses!", Toast.LENGTH_LONG).show();
                                                btnAnuloNdryshimin.callOnClick();
                                            } else {
                                                Toast.makeText(getActivity(), "Ndërrimi i fjalëkalimit dështoi!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Snackbar.make(getView(), "Fjalëkalimet nuk përputhen!", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(getView(), "Fjalëkalimi i vjetër është gabim!", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });



        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProfiliActivity.toolbar.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ok, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_ok)
        {
            // behet ruajtja e ndryshimeve

            int gabime = 0;
            emri = editEmri.getText().toString().trim();
            mbiemri = editMbiemri.getText().toString().trim();
            datelindja = editDatelindja.getText().toString().trim();
            telefoni = editTel.getText().toString().trim();
            autoEmri = editAutoEmri.getText().toString().trim();
            autoTel = editAutoTel.getText().toString().trim();
            autoPershkrimi = editAutoPershkrimi.getText().toString().trim();
            adresaRruga = editRruga.getText().toString().trim();
            adresaNr = editNr.getText().toString().trim();
            adresaKodi = editKodi.getText().toString().trim();
            adresaQyteti = String .valueOf(editQyteti.getSelectedItemPosition()).trim();
            email = editEmail.getText().toString().trim();

            if (emri.isEmpty() || emri.length() < 3) {
                gabime++;
                editEmri.setError("Emri duhet të jetë më i gjatë se 2 karaktere");
                editEmri.requestFocus();
            }

            if (mbiemri.isEmpty() || mbiemri.length() < 3) {
                gabime++;
                editMbiemri.setError("Mbiemri duhet të jetë më i gjatë se 2 karaktere");
                editMbiemri.requestFocus();
            }

            if (datelindja.equals("Datëlindja")) {
                gabime++;
                editDatelindja.setError("Caktoni datëlindjen");
                editDatelindja.requestFocus();
            }

            if (radioA.isChecked()) {
                tipi = "2";
                if (autoEmri.isEmpty() || autoEmri.length() < 3) {
                    gabime++;
                    editAutoEmri.setError("Shënoni emrin e Autosallonit");
                    editAutoEmri.requestFocus();
                }
            }
            else
                tipi = "1";

            if (adresaRruga.isEmpty() || adresaRruga.length() < 3) {
                gabime++;
                editRruga.setError("Shënoni emrin e rrugës");
                editRruga.requestFocus();
            }

            if (adresaNr.isEmpty()) {
                gabime++;
                editNr.setError("Shënoni numrin e shtëpisë");
                editNr.requestFocus();
            }

            if (adresaKodi.isEmpty() || adresaKodi.length() < 4) {
                gabime++;
                editKodi.setError("Shënoni kodin postar");
                editKodi.requestFocus();
            }

            if (Integer.valueOf(adresaQyteti) == 0) {
                gabime++;
                gQyteti.setVisibility(View.VISIBLE);
            } else {
                gQyteti.setVisibility(View.GONE);
            }

            if (telefoni.isEmpty() || telefoni.length() != 9) {
                gabime++;
                editTel.setError("Shënoni numrin e telefonit");
                editTel.requestFocus();
            }

            if (email.isEmpty() || email.length() < 10) {
                gabime++;
                editEmail.setError("Shënoni email valid");
                editEmail.requestFocus();
            }

            if(gabime==0) {
                if (!email.equals(perdoruesi.getEmail())) {
                    RemoteDatabase.emailUpdate(user, email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                dbUpdate();
                            } else {
                                Toast.makeText(getContext(), "Emaili ekziston në databazë!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
                else
                {
                    dbUpdate();
                }
            }


        }
        return super.onOptionsItemSelected(item);
    }

    private void userData(DataSnapshot dataSnapshot)
    {
        perdoruesi = RemoteDatabase.getPerdoruesi(dataSnapshot, user.getUid());
        editEmri.setText(perdoruesi.getEmri());
        editMbiemri.setText(perdoruesi.getMbiemri());
        editTel.setText(perdoruesi.getTel());
        editDatelindja.setText(perdoruesi.getDatelindja());
        editEmail.setText(perdoruesi.getEmail());

        adresa = RemoteDatabase.getAdresa(dataSnapshot, user.getUid());
        editRruga.setText(adresa.getRruga());
        editNr.setText(adresa.getNr());
        editKodi.setText(adresa.getKodi());
        editQyteti.setSelection(Integer.valueOf(adresa.getQyteti()));

        if(Integer.valueOf(perdoruesi.getTipiLlogarise())==2)
        {
            autosalloni = RemoteDatabase.getAutosalloni(dataSnapshot, user.getUid());
            radioA.callOnClick();
            radioA.setChecked(true);
            editAutoEmri.setText(autosalloni.getEmri());
            editAutoTel.setText(autosalloni.getTelefoni());
            editAutoPershkrimi.setText(autosalloni.getPershkrimi());
            editPhotoAutosallon.setVisibility(View.VISIBLE);
        }
        else
        {
            radioP.setChecked(true);
            radioP.callOnClick();
            editPhotoAutosallon.setVisibility(View.GONE);
        }

        if(!adresa.getLat().equals("-"))
        {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap mMap) {
                    googleMap = mMap;
                    LatLng autosalloniMap = new LatLng(Double.parseDouble(adresa.getLat()), Double.parseDouble(adresa.getLng()));
                    if(autosalloni!=null)
                        googleMap.addMarker(new MarkerOptions().position(autosalloniMap).title(autosalloni.getEmri()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                    else
                        googleMap.addMarker(new MarkerOptions().position(autosalloniMap).title(perdoruesi.getEmri()).icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));

                    googleMap.getUiSettings().setAllGesturesEnabled(false);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(autosalloniMap, 17));
                }
            });
        }
        else
            mapView.setVisibility(View.GONE);

        btnEditHarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent objIntent = new Intent(getContext(), MapsActivity.class);
                startActivity(objIntent);
            }
        });
    }

    public static void update(final LatLng latLng)
    {
        updateLatLng = latLng;
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Lokacioni i përzgjedhur").icon(BitmapDescriptorFactory.fromResource(R.mipmap.loc_sallon)));
                googleMap.getUiSettings().setAllGesturesEnabled(false);
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                mapView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void dbUpdate()
    {
        if(radioA.isChecked()) {
            String autosalloniTel;
            if(autoTel.isEmpty())
                autosalloniTel = telefoni;
            else
                autosalloniTel = autoTel;
            String pershkrimi;
            if(autoPershkrimi.isEmpty())
                pershkrimi = " ";
            else
                pershkrimi = autoPershkrimi;
            Autosallon autoUpdate = new Autosallon(autoEmri, autosalloniTel, pershkrimi);
            RemoteDatabase.autosalloniUpdate(user, autoUpdate);
        }

        String data = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(Calendar.getInstance().getTime());
        User userUpdate = new User(emri, mbiemri, email, datelindja, telefoni, tipi, perdoruesi.getDataKrijimit(), data);
        RemoteDatabase.userDbUpdate(user, userUpdate);

        String lat, lng;
        if(updateLatLng != null) {
            lat = String.valueOf(updateLatLng.latitude);
            lng = String.valueOf(updateLatLng.longitude);
        } else {
            lat = "-";
            lng = "-";
        }
        Adresa adresaUpdate = new Adresa(adresaRruga, adresaNr,adresaKodi, adresaQyteti, lat, lng);
        RemoteDatabase.adresaUpdate(user, adresaUpdate);

        if(radioP.isChecked()) {
            if(autosalloni!=null)
                RemoteDatabase.deleteAutosallon(user);
        }
        getActivity().getSupportFragmentManager().popBackStack();
    }




}
